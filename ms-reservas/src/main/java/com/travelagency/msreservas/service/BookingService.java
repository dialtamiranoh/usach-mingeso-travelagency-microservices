package com.travelagency.msreservas.service;

import com.travelagency.msreservas.entity.BookingEntity;
import com.travelagency.msreservas.entity.StatusEntity;
import com.travelagency.msreservas.model.PromotionDTO;
import com.travelagency.msreservas.model.TouristPackageDTO;
import com.travelagency.msreservas.repository.BookingRepository;
import com.travelagency.msreservas.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final StatusRepository statusRepository;
    private final RestTemplate restTemplate;

    public List<BookingEntity> findAll() { return bookingRepository.findAll(); }
    public Optional<BookingEntity> findById(Long id) { return bookingRepository.findById(id); }
    public List<BookingEntity> findByUserId(Long userId) { return bookingRepository.findByUserId(userId); }
    public List<BookingEntity> findByKeycloakId(String keycloakId) { return bookingRepository.findByKeycloakId(keycloakId); }
    public List<BookingEntity> findByTouristPackageId(Long packageId) { return bookingRepository.findByTouristPackageId(packageId); }
    public List<BookingEntity> findByStatus(StatusEntity status) { return bookingRepository.findByStatus(status); }
    public List<BookingEntity> findExpiredBookings() { return bookingRepository.findExpiredBookings(LocalDateTime.now()); }
    public List<BookingEntity> findByDateRangeExcludingCancelled(LocalDateTime start, LocalDateTime end) {
        return bookingRepository.findByDateRangeExcludingCancelled(start, end);
    }
    public BookingEntity update(BookingEntity b) { return bookingRepository.save(b); }

    @Transactional
    public BookingEntity createBooking(Long packageId, String keycloakId, int passengerCount) {

        // 1. Obtener paquete desde ms-paquetes via RestTemplate
        TouristPackageDTO pkg = restTemplate.getForObject(
            "http://ms-paquetes/api/tourist-packages/" + packageId,
            TouristPackageDTO.class);

        if (pkg == null) throw new RuntimeException("Paquete no encontrado");

        // 2. Validar estado del paquete
        if (!pkg.getStatus().getName().equals("AVAILABLE"))
            throw new RuntimeException("El paquete no esta disponible");

        // 3. Validar pasajeros y cupos
        if (passengerCount <= 0)
            throw new RuntimeException("La cantidad de pasajeros debe ser mayor que cero");
        if (pkg.getAvailableSlots() < passengerCount)
            throw new RuntimeException("No hay suficientes cupos disponibles");

        // 4. Calcular monto base
        BigDecimal baseAmount = pkg.getPrice().multiply(BigDecimal.valueOf(passengerCount));

        // 5. Obtener promociones activas desde ms-paquetes
        List<PromotionDTO> promotions = new ArrayList<>();
        try {
            promotions = restTemplate.exchange(
                "http://ms-paquetes/api/promotions/active/package/" + packageId,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<PromotionDTO>>() {}).getBody();
            if (promotions == null) promotions = new ArrayList<>();
        } catch (Exception e) {
            log.warn("No se pudieron obtener promociones: {}", e.getMessage());
        }

        // 6. Calcular descuentos (logica identica al monolito)
        long confirmedBookings = bookingRepository.countConfirmedBookingsByKeycloakId(keycloakId);

        BigDecimal totalDiscountPct = BigDecimal.ZERO;
        List<String> discountDetails = new ArrayList<>();
        boolean hasNonAccumulable = false;
        BigDecimal maxDiscountPct = new BigDecimal("20");

        for (PromotionDTO promo : promotions) {
            boolean applies = false;
            String reason = "";

            if (promo.getMinPassengers() != null && passengerCount >= promo.getMinPassengers()) {
                applies = true;
                reason = "Descuento por grupo";
            }
            if (promo.getMinBookingsHistory() != null && confirmedBookings >= promo.getMinBookingsHistory()) {
                applies = true;
                reason = "Cliente frecuente";
            }
            if (promo.getMinBookingsSession() != null) {
                long sessionBookings = bookingRepository.findRecentByKeycloakId(
                    keycloakId, LocalDateTime.now().minusHours(1)).size();
                if (sessionBookings >= promo.getMinBookingsSession()) {
                    applies = true;
                    reason = "Compra multiple";
                }
            }

            if (applies) {
                if (Boolean.TRUE.equals(promo.getIsAccumulable())) {
                    totalDiscountPct = totalDiscountPct.add(promo.getDiscountPercentage());
                    discountDetails.add(reason + ": " + promo.getDiscountPercentage() + "%");
                } else if (!hasNonAccumulable) {
                    hasNonAccumulable = true;
                    totalDiscountPct = totalDiscountPct.add(promo.getDiscountPercentage());
                    discountDetails.add(reason + ": " + promo.getDiscountPercentage() + "%");
                }
            }
        }

        if (totalDiscountPct.compareTo(maxDiscountPct) > 0)
            totalDiscountPct = maxDiscountPct;

        BigDecimal discountAmount = baseAmount
            .multiply(totalDiscountPct)
            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal finalAmount = baseAmount.subtract(discountAmount);
        if (finalAmount.compareTo(BigDecimal.ZERO) < 0)
            finalAmount = BigDecimal.ZERO;

        // 7. Descontar cupos en ms-paquetes via RestTemplate
        int newSlots = pkg.getAvailableSlots() - passengerCount;
        String newStatus = newSlots == 0 ? "SOLD_OUT" : null;
        try {
            restTemplate.put(
                "http://ms-paquetes/api/tourist-packages/" + packageId +
                "/slots?delta=-" + passengerCount +
                (newStatus != null ? "&newStatus=" + newStatus : ""),
                null);
        } catch (Exception e) {
            throw new RuntimeException("Error al descontar cupos: " + e.getMessage());
        }

        // 8. Obtener estado PENDING_PAYMENT
        StatusEntity pendingStatus = statusRepository
            .findByNameAndEntityType("PENDING_PAYMENT", "BOOKING")
            .orElseThrow(() -> new RuntimeException("Estado PENDING_PAYMENT no encontrado"));

        // 9. Crear reserva
        BookingEntity booking = new BookingEntity();
        booking.setKeycloakId(keycloakId);
        booking.setTouristPackageId(packageId);
        booking.setPassengerCount(passengerCount);
        booking.setBaseAmount(baseAmount);
        booking.setDiscountAmount(discountAmount);
        booking.setFinalAmount(finalAmount);
        booking.setDiscountDetail(String.join(", ", discountDetails));
        booking.setStatus(pendingStatus);
        booking.setExpiresAt(LocalDateTime.now().plusHours(24));

        return bookingRepository.save(booking);
    }

    @Transactional
    public BookingEntity updateBooking(Long bookingId, StatusEntity newStatus, int newPassengerCount) {
        BookingEntity booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        String currentStatus = booking.getStatus().getName();

        if (currentStatus.equals("CANCELLED") || currentStatus.equals("EXPIRED"))
            throw new RuntimeException("No se puede modificar una reserva en estado " + currentStatus);

        if (newStatus.getName().equals("EXPIRED"))
            throw new RuntimeException("El estado EXPIRED solo lo asigna el sistema");

        // Si se cancela, liberar cupos en ms-paquetes
        if (newStatus.getName().equals("CANCELLED")) {
            try {
                restTemplate.put(
                    "http://ms-paquetes/api/tourist-packages/" + booking.getTouristPackageId() +
                    "/slots?delta=" + booking.getPassengerCount() + "&newStatus=AVAILABLE",
                    null);
            } catch (Exception e) {
                log.warn("Error liberando cupos: {}", e.getMessage());
            }
        }

        booking.setPassengerCount(newPassengerCount);
        booking.setStatus(newStatus);
        return bookingRepository.save(booking);
    }

    // Job de expiracion - igual al monolito
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void expireBookings() {
        List<BookingEntity> expired = bookingRepository.findExpiredBookings(LocalDateTime.now());
        if (expired.isEmpty()) return;

        StatusEntity expiredStatus = statusRepository
            .findByNameAndEntityType("EXPIRED", "BOOKING").orElse(null);
        if (expiredStatus == null) return;

        for (BookingEntity booking : expired) {
            // Liberar cupos en ms-paquetes
            try {
                restTemplate.put(
                    "http://ms-paquetes/api/tourist-packages/" + booking.getTouristPackageId() +
                    "/slots?delta=" + booking.getPassengerCount() + "&newStatus=AVAILABLE",
                    null);
            } catch (Exception e) {
                log.warn("Error liberando cupos reserva #{}: {}", booking.getId(), e.getMessage());
            }
            booking.setStatus(expiredStatus);
            bookingRepository.save(booking);
            log.info("Reserva #{} expirada, cupos liberados", booking.getId());
        }
    }
}

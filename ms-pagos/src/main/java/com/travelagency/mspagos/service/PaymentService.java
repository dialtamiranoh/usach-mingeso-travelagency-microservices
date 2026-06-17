package com.travelagency.mspagos.service;

import com.travelagency.mspagos.entity.PaymentEntity;
import com.travelagency.mspagos.entity.StatusEntity;
import com.travelagency.mspagos.model.BookingDTO;
import com.travelagency.mspagos.repository.PaymentRepository;
import com.travelagency.mspagos.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StatusRepository statusRepository;
    private final RestTemplate restTemplate;

    public List<PaymentEntity> findAll() { return paymentRepository.findAll(); }
    public Optional<PaymentEntity> findById(Long id) { return paymentRepository.findById(id); }
    public Optional<PaymentEntity> findByBookingId(Long bookingId) { return paymentRepository.findByBookingId(bookingId); }
    public Optional<PaymentEntity> findByTransactionCode(String code) { return paymentRepository.findByTransactionCode(code); }
    public List<PaymentEntity> findByStatus(StatusEntity status) { return paymentRepository.findByStatus(status); }
    public List<PaymentEntity> findByDateRange(LocalDateTime start, LocalDateTime end) { return paymentRepository.findByDateRange(start, end); }
    public PaymentEntity update(PaymentEntity p) { return paymentRepository.save(p); }
    public void deleteById(Long id) { paymentRepository.deleteById(id); }

    @Transactional
    public PaymentEntity processPayment(Long bookingId, String cardNumber, String cardExpiry, String cardCvv) {

        // 1. Obtener reserva desde ms-reservas
        BookingDTO booking = restTemplate.getForObject(
            "http://ms-reservas/api/bookings/" + bookingId,
            BookingDTO.class);

        if (booking == null)
            throw new RuntimeException("Reserva no encontrada");

        // 2. Validar estado PENDING_PAYMENT
        if (!booking.getStatus().getName().equals("PENDING_PAYMENT"))
            throw new RuntimeException("La reserva no esta en estado PENDING_PAYMENT");

        // 3. Validar pago previo
        if (paymentRepository.existsByBookingId(bookingId))
            throw new RuntimeException("Esta reserva ya tiene un pago registrado");

        // 4. Validar expiracion
        if (booking.getExpiresAt() != null && booking.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new RuntimeException("La reserva ha expirado");

        // 5. Obtener estado APPROVED
        StatusEntity approvedStatus = statusRepository
            .findByNameAndEntityType("APPROVED", "PAYMENT")
            .orElseThrow(() -> new RuntimeException("Estado APPROVED no encontrado"));

        // 6. Crear pago
        PaymentEntity payment = new PaymentEntity();
        payment.setBookingId(bookingId);
        payment.setAmount(booking.getFinalAmount());
        payment.setCardNumber(cardNumber);
        payment.setCardExpiry(cardExpiry);
        payment.setCardCvv(cardCvv);
        payment.setTransactionCode(UUID.randomUUID().toString());
        payment.setStatus(approvedStatus);

        paymentRepository.save(payment);

        // 7. Confirmar reserva en ms-reservas via RestTemplate
        try {
            restTemplate.put(
                "http://ms-reservas/api/bookings/" + bookingId + "/confirm",
                null);
        } catch (Exception e) {
            throw new RuntimeException("Error al confirmar reserva: " + e.getMessage());
        }

        return payment;
    }
}

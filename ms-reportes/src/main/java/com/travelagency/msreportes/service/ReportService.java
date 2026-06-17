package com.travelagency.msreportes.service;

import com.travelagency.msreportes.model.BookingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final RestTemplate restTemplate;

    private List<BookingDTO> getBookingsInRange(LocalDateTime startDate, LocalDateTime endDate) {
        String url = UriComponentsBuilder
            .fromHttpUrl("http://ms-reservas/api/bookings/date-range")
            .queryParam("startDate", startDate.toString())
            .queryParam("endDate", endDate.toString())
            .toUriString();

        List<BookingDTO> bookings = restTemplate.exchange(
            url, HttpMethod.GET, null,
            new ParameterizedTypeReference<List<BookingDTO>>() {}).getBody();

        return bookings != null ? bookings : new ArrayList<>();
    }

    public List<Map<String, Object>> getSalesReport(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate))
            throw new RuntimeException("La fecha de inicio no puede ser posterior a la fecha de termino");

        return getBookingsInRange(startDate, endDate).stream()
            .filter(b -> !b.getStatus().getName().equals("CANCELLED"))
            .sorted(Comparator.comparing(BookingDTO::getCreatedAt))
            .map(b -> {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("bookingId", b.getId());
                map.put("operationDate", b.getCreatedAt());
                map.put("keycloakId", b.getKeycloakId());
                map.put("touristPackageId", b.getTouristPackageId());
                map.put("passengerCount", b.getPassengerCount());
                map.put("totalAmount", b.getFinalAmount());
                map.put("bookingStatus", b.getStatus().getName());
                return map;
            })
            .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getPackageRanking(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate))
            throw new RuntimeException("La fecha de inicio no puede ser posterior a la fecha de termino");

        List<BookingDTO> bookings = getBookingsInRange(startDate, endDate).stream()
            .filter(b -> !b.getStatus().getName().equals("CANCELLED"))
            .collect(Collectors.toList());

        // Agrupar por packageId - logica equivalente al monolito
        Map<Long, List<BookingDTO>> grouped = bookings.stream()
            .collect(Collectors.groupingBy(BookingDTO::getTouristPackageId));

        return grouped.entrySet().stream()
            .map(entry -> {
                Long packageId = entry.getKey();
                List<BookingDTO> pkgBookings = entry.getValue();
                long totalBookings = pkgBookings.size();
                long totalPassengers = pkgBookings.stream().mapToLong(BookingDTO::getPassengerCount).sum();
                double totalAmount = pkgBookings.stream()
                    .mapToDouble(b -> b.getFinalAmount().doubleValue()).sum();

                Map<String, Object> map = new LinkedHashMap<>();
                map.put("packageId", packageId);
                map.put("totalBookings", totalBookings);
                map.put("totalPassengers", totalPassengers);
                map.put("totalAmount", totalAmount);
                return map;
            })
            .sorted((a, b) -> Long.compare(
                (Long) b.get("totalBookings"),
                (Long) a.get("totalBookings")))
            .collect(Collectors.toList());
    }
}

package com.travelagency.msseguimiento.service;

import com.travelagency.msseguimiento.model.BookingDTO;
import com.travelagency.msseguimiento.model.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeguimientoService {

    private final RestTemplate restTemplate;

    public List<BookingDTO> getBookingsByKeycloakId(String keycloakId) {
        return restTemplate.exchange(
            "http://ms-reservas/api/bookings/keycloak/" + keycloakId,
            HttpMethod.GET, null,
            new ParameterizedTypeReference<List<BookingDTO>>() {}).getBody();
    }

    public BookingDTO getBookingById(Long id) {
        return restTemplate.getForObject(
            "http://ms-reservas/api/bookings/" + id,
            BookingDTO.class);
    }

    public List<BookingDTO> getAllBookings() {
        return restTemplate.exchange(
            "http://ms-reservas/api/bookings",
            HttpMethod.GET, null,
            new ParameterizedTypeReference<List<BookingDTO>>() {}).getBody();
    }

    public PaymentDTO getPaymentByBooking(Long bookingId) {
        try {
            return restTemplate.getForObject(
                "http://ms-pagos/api/payments/booking/" + bookingId,
                PaymentDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}

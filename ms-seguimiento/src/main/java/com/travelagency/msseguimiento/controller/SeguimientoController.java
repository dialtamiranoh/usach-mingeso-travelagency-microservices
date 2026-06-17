package com.travelagency.msseguimiento.controller;

import com.travelagency.msseguimiento.model.BookingDTO;
import com.travelagency.msseguimiento.model.PaymentDTO;
import com.travelagency.msseguimiento.service.SeguimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tracking")
@RequiredArgsConstructor
public class SeguimientoController {

    private final SeguimientoService seguimientoService;

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(seguimientoService.getAllBookings());
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        BookingDTO booking = seguimientoService.getBookingById(id);
        if (booking == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/bookings/keycloak/{keycloakId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByKeycloakId(@PathVariable String keycloakId) {
        return ResponseEntity.ok(seguimientoService.getBookingsByKeycloakId(keycloakId));
    }

    @GetMapping("/bookings/{id}/payment")
    public ResponseEntity<PaymentDTO> getPaymentByBooking(@PathVariable Long id) {
        PaymentDTO payment = seguimientoService.getPaymentByBooking(id);
        if (payment == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(payment);
    }
}

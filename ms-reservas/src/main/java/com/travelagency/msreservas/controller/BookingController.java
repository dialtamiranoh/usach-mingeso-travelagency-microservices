package com.travelagency.msreservas.controller;

import com.travelagency.msreservas.entity.BookingEntity;
import com.travelagency.msreservas.entity.StatusEntity;
import com.travelagency.msreservas.repository.StatusRepository;
import com.travelagency.msreservas.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final StatusRepository statusRepository;

    @GetMapping
    public ResponseEntity<List<BookingEntity>> findAll() {
        return ResponseEntity.ok(bookingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingEntity> findById(@PathVariable Long id) {
        return bookingService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingEntity>> findByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.findByUserId(userId));
    }

    @GetMapping("/keycloak/{keycloakId}")
    public ResponseEntity<List<BookingEntity>> findByKeycloakId(@PathVariable String keycloakId) {
        return ResponseEntity.ok(bookingService.findByKeycloakId(keycloakId));
    }

    @GetMapping("/package/{packageId}")
    public ResponseEntity<List<BookingEntity>> findByPackage(@PathVariable Long packageId) {
        return ResponseEntity.ok(bookingService.findByTouristPackageId(packageId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<BookingEntity>> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(bookingService.findByDateRangeExcludingCancelled(startDate, endDate));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(
            @RequestParam Long packageId,
            @RequestParam int passengerCount,
            @RequestParam String keycloakId,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authToken) {
        try {
            BookingEntity booking = bookingService.createBooking(packageId, keycloakId, passengerCount, authToken);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateBooking(
            @PathVariable Long id,
            @RequestParam int passengerCount,
            @RequestParam Long statusId) {
        try {
            StatusEntity status = statusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
            return ResponseEntity.ok(bookingService.updateBooking(id, status, passengerCount));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<?> confirmBooking(@PathVariable Long id) {
        try {
            StatusEntity confirmedStatus = statusRepository
                .findByNameAndEntityType("CONFIRMED", "BOOKING")
                .orElseThrow(() -> new RuntimeException("Estado CONFIRMED no encontrado"));
            return ResponseEntity.ok(bookingService.updateBooking(id, confirmedStatus,
                bookingService.findById(id).orElseThrow().getPassengerCount()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}




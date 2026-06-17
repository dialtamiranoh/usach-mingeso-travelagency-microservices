package com.travelagency.mspagos.controller;

import com.travelagency.mspagos.entity.PaymentEntity;
import com.travelagency.mspagos.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentEntity>> findAll() {
        return ResponseEntity.ok(paymentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentEntity> findById(@PathVariable Long id) {
        return paymentService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<PaymentEntity> findByBooking(@PathVariable Long bookingId) {
        return paymentService.findByBookingId(bookingId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/transaction/{transactionCode}")
    public ResponseEntity<PaymentEntity> findByTransactionCode(@PathVariable String transactionCode) {
        return paymentService.findByTransactionCode(transactionCode)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<PaymentEntity>> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(paymentService.findByDateRange(startDate, endDate));
    }

    @PostMapping("/process")
    public ResponseEntity<?> processPayment(
            @RequestParam Long bookingId,
            @RequestParam String cardNumber,
            @RequestParam String cardExpiry,
            @RequestParam String cardCvv) {
        try {
            PaymentEntity payment = paymentService.processPayment(bookingId, cardNumber, cardExpiry, cardCvv);
            return ResponseEntity.status(HttpStatus.CREATED).body(payment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentEntity> update(@PathVariable Long id, @RequestBody PaymentEntity payment) {
        if (paymentService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        payment.setId(id);
        return ResponseEntity.ok(paymentService.update(payment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (paymentService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        paymentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

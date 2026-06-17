package com.travelagency.msseguimiento.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Long id;
    private Long bookingId;
    private BigDecimal amount;
    private String cardNumber;
    private String transactionCode;
    private StatusDTO status;
    private LocalDateTime paidAt;

    @Data
    public static class StatusDTO {
        private String name;
    }
}

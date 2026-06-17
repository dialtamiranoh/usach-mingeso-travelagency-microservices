package com.travelagency.msseguimiento.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDTO {
    private Long id;
    private Long userId;
    private String keycloakId;
    private Long touristPackageId;
    private int passengerCount;
    private BigDecimal baseAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private String discountDetail;
    private StatusDTO status;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    @Data
    public static class StatusDTO {
        private Long id;
        private String name;
    }
}

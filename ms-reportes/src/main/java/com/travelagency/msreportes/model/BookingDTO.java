package com.travelagency.msreportes.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private Long id;
    private String keycloakId;
    private Long touristPackageId;
    private int passengerCount;
    private BigDecimal baseAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private StatusDTO status;
    private LocalDateTime createdAt;

    @Data
    public static class StatusDTO {
        private String name;
    }
}

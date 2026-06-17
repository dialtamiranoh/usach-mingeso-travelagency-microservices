package com.travelagency.mspagos.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private Long id;
    private Long touristPackageId;
    private String keycloakId;
    private int passengerCount;
    private BigDecimal finalAmount;
    private LocalDateTime expiresAt;
    private StatusDTO status;

    @Data
    public static class StatusDTO {
        private Long id;
        private String name;
    }
}

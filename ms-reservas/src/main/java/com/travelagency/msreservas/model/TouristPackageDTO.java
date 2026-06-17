package com.travelagency.msreservas.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TouristPackageDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private int availableSlots;
    private int totalSlots;
    private StatusDTO status;

    @Data
    public static class StatusDTO {
        private Long id;
        private String name;
        private String entityType;
    }
}

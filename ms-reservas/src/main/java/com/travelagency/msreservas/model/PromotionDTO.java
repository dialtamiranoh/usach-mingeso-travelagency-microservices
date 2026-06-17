package com.travelagency.msreservas.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PromotionDTO {
    private Long id;
    private String name;
    private BigDecimal discountPercentage;
    private Integer minPassengers;
    private Integer minBookingsSession;
    private Integer minBookingsHistory;
    private Boolean isAccumulable;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private StatusDTO status;

    @Data
    public static class StatusDTO {
        private String name;
    }
}

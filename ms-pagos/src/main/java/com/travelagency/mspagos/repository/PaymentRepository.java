package com.travelagency.mspagos.repository;

import com.travelagency.mspagos.entity.PaymentEntity;
import com.travelagency.mspagos.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findByBookingId(Long bookingId);
    Optional<PaymentEntity> findByTransactionCode(String transactionCode);
    boolean existsByBookingId(Long bookingId);
    List<PaymentEntity> findByStatus(StatusEntity status);

    @Query("SELECT p FROM PaymentEntity p WHERE p.paidAt BETWEEN :startDate AND :endDate")
    List<PaymentEntity> findByDateRange(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate);
}

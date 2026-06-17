package com.travelagency.msreservas.repository;

import com.travelagency.msreservas.entity.BookingEntity;
import com.travelagency.msreservas.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    List<BookingEntity> findByUserId(Long userId);
    List<BookingEntity> findByKeycloakId(String keycloakId);
    List<BookingEntity> findByTouristPackageId(Long touristPackageId);
    List<BookingEntity> findByStatus(StatusEntity status);

    @Query("SELECT COUNT(b) FROM BookingEntity b WHERE b.keycloakId = :keycloakId " +
           "AND b.status.name = 'CONFIRMED'")
    long countConfirmedBookingsByKeycloakId(@Param("keycloakId") String keycloakId);

    @Query("SELECT b FROM BookingEntity b WHERE b.status.name = 'PENDING_PAYMENT' " +
           "AND b.expiresAt <= :now")
    List<BookingEntity> findExpiredBookings(@Param("now") LocalDateTime now);

    @Query("SELECT b FROM BookingEntity b WHERE b.createdAt BETWEEN :startDate AND :endDate " +
           "AND b.status.name != 'CANCELLED'")
    List<BookingEntity> findByDateRangeExcludingCancelled(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate);

    @Query("SELECT b FROM BookingEntity b WHERE b.keycloakId = :keycloakId " +
           "AND b.createdAt >= :since")
    List<BookingEntity> findRecentByKeycloakId(
        @Param("keycloakId") String keycloakId,
        @Param("since") LocalDateTime since);
}

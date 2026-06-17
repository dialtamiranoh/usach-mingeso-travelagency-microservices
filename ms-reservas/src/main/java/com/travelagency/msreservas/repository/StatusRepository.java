package com.travelagency.msreservas.repository;

import com.travelagency.msreservas.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    List<StatusEntity> findByEntityType(String entityType);
    Optional<StatusEntity> findByNameAndEntityType(String name, String entityType);
}

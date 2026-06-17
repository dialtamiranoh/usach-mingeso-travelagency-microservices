package com.travelagency.mspagos.repository;

import com.travelagency.mspagos.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    Optional<StatusEntity> findByNameAndEntityType(String name, String entityType);
}

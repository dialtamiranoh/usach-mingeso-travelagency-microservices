package com.travelagency.mspaquetes.repository;

import com.travelagency.mspaquetes.entity.DestinationEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DestinationRepository extends JpaRepository<DestinationEntity, Long> {
    Optional<DestinationEntity> findByName(String name);
    List<DestinationEntity> findByStatus(StatusEntity status);
}

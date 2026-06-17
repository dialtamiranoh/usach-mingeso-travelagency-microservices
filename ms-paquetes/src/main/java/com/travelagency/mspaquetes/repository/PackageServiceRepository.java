package com.travelagency.mspaquetes.repository;

import com.travelagency.mspaquetes.entity.PackageServiceEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PackageServiceRepository extends JpaRepository<PackageServiceEntity, Long> {
    Optional<PackageServiceEntity> findByName(String name);
    List<PackageServiceEntity> findByStatus(StatusEntity status);
}

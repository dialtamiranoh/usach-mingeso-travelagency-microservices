package com.travelagency.mspaquetes.repository;

import com.travelagency.mspaquetes.entity.PackageTypeEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PackageTypeRepository extends JpaRepository<PackageTypeEntity, Long> {
    Optional<PackageTypeEntity> findByName(String name);
    List<PackageTypeEntity> findByStatus(StatusEntity status);
}

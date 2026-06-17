package com.travelagency.mspaquetes.repository;

import com.travelagency.mspaquetes.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TouristPackageRepository extends JpaRepository<TouristPackageEntity, Long> {
    List<TouristPackageEntity> findByStatus(StatusEntity status);
    List<TouristPackageEntity> findByDestination(DestinationEntity destination);
    List<TouristPackageEntity> findByCategory(CategoryEntity category);
    List<TouristPackageEntity> findBySeason(SeasonEntity season);
    List<TouristPackageEntity> findByType(PackageTypeEntity type);

    @Query("SELECT p FROM TouristPackageEntity p WHERE p.status.name = 'AVAILABLE'")
    List<TouristPackageEntity> findAvailablePackages();
}

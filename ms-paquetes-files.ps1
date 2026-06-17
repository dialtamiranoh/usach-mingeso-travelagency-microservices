# ============================================================
# ms-paquetes - Migracion desde monolito
# Ejecutar desde: C:\Users\diego.altamirano\Desktop\usach-mingeso-travelagency-microservices
# ============================================================

$BASE = "C:\Users\diego.altamirano\Desktop\usach-mingeso-travelagency-microservices\ms-paquetes"
$PKG  = "$BASE\src\main\java\com\travelagency\mspaquetes"

Write-Host "Creando archivos ms-paquetes..." -ForegroundColor Cyan

# ============================================================
# ENTITIES
# ============================================================

Set-Content -Path "$PKG\entity\StatusEntity.java" -Value @"
package com.travelagency.mspaquetes.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}
"@

Set-Content -Path "$PKG\entity\DestinationEntity.java" -Value @"
package com.travelagency.mspaquetes.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "destinations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}
"@

Set-Content -Path "$PKG\entity\CategoryEntity.java" -Value @"
package com.travelagency.mspaquetes.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}
"@

Set-Content -Path "$PKG\entity\PackageTypeEntity.java" -Value @"
package com.travelagency.mspaquetes.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "package_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}
"@

Set-Content -Path "$PKG\entity\SeasonEntity.java" -Value @"
package com.travelagency.mspaquetes.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seasons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeasonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}
"@

Set-Content -Path "$PKG\entity\PackageServiceEntity.java" -Value @"
package com.travelagency.mspaquetes.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}
"@

Set-Content -Path "$PKG\entity\PromotionEntity.java" -Value @"
package com.travelagency.mspaquetes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "promotions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "discount_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(name = "min_passengers")
    private Integer minPassengers;

    @Column(name = "min_bookings_session")
    private Integer minBookingsSession;

    @Column(name = "min_bookings_history")
    private Integer minBookingsHistory;

    @Column(name = "is_accumulable", nullable = false)
    private Boolean isAccumulable;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;

    @JsonIgnore
    @ManyToMany(mappedBy = "promotions", fetch = FetchType.LAZY)
    private List<TouristPackageEntity> touristPackages;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}
"@

Set-Content -Path "$PKG\entity\TouristPackageEntity.java" -Value @"
package com.travelagency.mspaquetes.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tourist_packages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TouristPackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id", nullable = false)
    private DestinationEntity destination;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "duration_days", nullable = false)
    private int durationDays;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "total_slots", nullable = false)
    private int totalSlots;

    @Column(name = "available_slots", nullable = false)
    private int availableSlots;

    @Column(columnDefinition = "TEXT")
    private String conditions;

    @Column(columnDefinition = "TEXT")
    private String restrictions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "package_type_id", nullable = false)
    private PackageTypeEntity type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "season_id", nullable = false)
    private SeasonEntity season;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private StatusEntity status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "tourist_package_services",
        joinColumns = @JoinColumn(name = "tourist_package_id"),
        inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<PackageServiceEntity> services;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "tourist_package_promotions",
        joinColumns = @JoinColumn(name = "tourist_package_id"),
        inverseJoinColumns = @JoinColumn(name = "promotion_id")
    )
    private List<PromotionEntity> promotions;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}
"@

# ============================================================
# REPOSITORIES
# ============================================================

Set-Content -Path "$PKG\repository\StatusRepository.java" -Value @"
package com.travelagency.mspaquetes.repository;

import com.travelagency.mspaquetes.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    List<StatusEntity> findByEntityType(String entityType);
    Optional<StatusEntity> findByNameAndEntityType(String name, String entityType);
    boolean existsByNameAndEntityType(String name, String entityType);
}
"@

Set-Content -Path "$PKG\repository\DestinationRepository.java" -Value @"
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
"@

Set-Content -Path "$PKG\repository\CategoryRepository.java" -Value @"
package com.travelagency.mspaquetes.repository;

import com.travelagency.mspaquetes.entity.CategoryEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);
    List<CategoryEntity> findByStatus(StatusEntity status);
}
"@

Set-Content -Path "$PKG\repository\PackageTypeRepository.java" -Value @"
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
"@

Set-Content -Path "$PKG\repository\SeasonRepository.java" -Value @"
package com.travelagency.mspaquetes.repository;

import com.travelagency.mspaquetes.entity.SeasonEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeasonRepository extends JpaRepository<SeasonEntity, Long> {
    Optional<SeasonEntity> findByName(String name);
    List<SeasonEntity> findByStatus(StatusEntity status);
}
"@

Set-Content -Path "$PKG\repository\PackageServiceRepository.java" -Value @"
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
"@

Set-Content -Path "$PKG\repository\PromotionRepository.java" -Value @"
package com.travelagency.mspaquetes.repository;

import com.travelagency.mspaquetes.entity.PromotionEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionEntity, Long> {
    List<PromotionEntity> findByStatus(StatusEntity status);

    @Query("SELECT p FROM PromotionEntity p WHERE p.status.name = 'ACTIVE' " +
           "AND (p.startDate IS NULL OR p.startDate <= :now) " +
           "AND (p.endDate IS NULL OR p.endDate >= :now)")
    List<PromotionEntity> findActivePromotions(@Param("now") LocalDateTime now);

    @Query("SELECT p FROM PromotionEntity p " +
           "JOIN p.touristPackages tp " +
           "WHERE tp.id = :packageId " +
           "AND p.status.name = 'ACTIVE' " +
           "AND (p.startDate IS NULL OR p.startDate <= :now) " +
           "AND (p.endDate IS NULL OR p.endDate >= :now)")
    List<PromotionEntity> findActivePromotionsByPackageId(
        @Param("packageId") Long packageId,
        @Param("now") LocalDateTime now);
}
"@

Set-Content -Path "$PKG\repository\TouristPackageRepository.java" -Value @"
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
"@

# ============================================================
# SERVICES
# ============================================================

Set-Content -Path "$PKG\service\StatusService.java" -Value @"
package com.travelagency.mspaquetes.service;

import com.travelagency.mspaquetes.entity.StatusEntity;
import com.travelagency.mspaquetes.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

    public List<StatusEntity> findAll() { return statusRepository.findAll(); }
    public Optional<StatusEntity> findById(Long id) { return statusRepository.findById(id); }
    public List<StatusEntity> findByEntityType(String entityType) { return statusRepository.findByEntityType(entityType); }
    public StatusEntity save(StatusEntity s) { return statusRepository.save(s); }
    public StatusEntity update(StatusEntity s) { return statusRepository.save(s); }
    public void deleteById(Long id) { statusRepository.deleteById(id); }
}
"@

Set-Content -Path "$PKG\service\DestinationService.java" -Value @"
package com.travelagency.mspaquetes.service;

import com.travelagency.mspaquetes.entity.DestinationEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import com.travelagency.mspaquetes.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DestinationService {
    private final DestinationRepository destinationRepository;

    public List<DestinationEntity> findAll() { return destinationRepository.findAll(); }
    public Optional<DestinationEntity> findById(Long id) { return destinationRepository.findById(id); }
    public List<DestinationEntity> findByStatus(StatusEntity status) { return destinationRepository.findByStatus(status); }
    public DestinationEntity save(DestinationEntity d) { return destinationRepository.save(d); }
    public DestinationEntity update(DestinationEntity d) { return destinationRepository.save(d); }
    public void deleteById(Long id) { destinationRepository.deleteById(id); }
}
"@

Set-Content -Path "$PKG\service\CategoryService.java" -Value @"
package com.travelagency.mspaquetes.service;

import com.travelagency.mspaquetes.entity.CategoryEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import com.travelagency.mspaquetes.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryEntity> findAll() { return categoryRepository.findAll(); }
    public Optional<CategoryEntity> findById(Long id) { return categoryRepository.findById(id); }
    public List<CategoryEntity> findByStatus(StatusEntity status) { return categoryRepository.findByStatus(status); }
    public CategoryEntity save(CategoryEntity c) { return categoryRepository.save(c); }
    public CategoryEntity update(CategoryEntity c) { return categoryRepository.save(c); }
    public void deleteById(Long id) { categoryRepository.deleteById(id); }
}
"@

Set-Content -Path "$PKG\service\PackageTypeService.java" -Value @"
package com.travelagency.mspaquetes.service;

import com.travelagency.mspaquetes.entity.PackageTypeEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import com.travelagency.mspaquetes.repository.PackageTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackageTypeService {
    private final PackageTypeRepository packageTypeRepository;

    public List<PackageTypeEntity> findAll() { return packageTypeRepository.findAll(); }
    public Optional<PackageTypeEntity> findById(Long id) { return packageTypeRepository.findById(id); }
    public List<PackageTypeEntity> findByStatus(StatusEntity status) { return packageTypeRepository.findByStatus(status); }
    public PackageTypeEntity save(PackageTypeEntity p) { return packageTypeRepository.save(p); }
    public PackageTypeEntity update(PackageTypeEntity p) { return packageTypeRepository.save(p); }
    public void deleteById(Long id) { packageTypeRepository.deleteById(id); }
}
"@

Set-Content -Path "$PKG\service\SeasonService.java" -Value @"
package com.travelagency.mspaquetes.service;

import com.travelagency.mspaquetes.entity.SeasonEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import com.travelagency.mspaquetes.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeasonService {
    private final SeasonRepository seasonRepository;

    public List<SeasonEntity> findAll() { return seasonRepository.findAll(); }
    public Optional<SeasonEntity> findById(Long id) { return seasonRepository.findById(id); }
    public List<SeasonEntity> findByStatus(StatusEntity status) { return seasonRepository.findByStatus(status); }
    public SeasonEntity save(SeasonEntity s) { return seasonRepository.save(s); }
    public SeasonEntity update(SeasonEntity s) { return seasonRepository.save(s); }
    public void deleteById(Long id) { seasonRepository.deleteById(id); }
}
"@

Set-Content -Path "$PKG\service\PackageServiceService.java" -Value @"
package com.travelagency.mspaquetes.service;

import com.travelagency.mspaquetes.entity.PackageServiceEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import com.travelagency.mspaquetes.repository.PackageServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackageServiceService {
    private final PackageServiceRepository packageServiceRepository;

    public List<PackageServiceEntity> findAll() { return packageServiceRepository.findAll(); }
    public Optional<PackageServiceEntity> findById(Long id) { return packageServiceRepository.findById(id); }
    public List<PackageServiceEntity> findByStatus(StatusEntity status) { return packageServiceRepository.findByStatus(status); }
    public PackageServiceEntity save(PackageServiceEntity p) { return packageServiceRepository.save(p); }
    public PackageServiceEntity update(PackageServiceEntity p) { return packageServiceRepository.save(p); }
    public void deleteById(Long id) { packageServiceRepository.deleteById(id); }
}
"@

Set-Content -Path "$PKG\service\PromotionService.java" -Value @"
package com.travelagency.mspaquetes.service;

import com.travelagency.mspaquetes.entity.PromotionEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import com.travelagency.mspaquetes.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;

    public List<PromotionEntity> findAll() { return promotionRepository.findAll(); }
    public Optional<PromotionEntity> findById(Long id) { return promotionRepository.findById(id); }
    public List<PromotionEntity> findByStatus(StatusEntity status) { return promotionRepository.findByStatus(status); }
    public List<PromotionEntity> findActivePromotions() { return promotionRepository.findActivePromotions(LocalDateTime.now()); }
    public List<PromotionEntity> findActivePromotionsByPackageId(Long packageId) { return promotionRepository.findActivePromotionsByPackageId(packageId, LocalDateTime.now()); }
    public PromotionEntity save(PromotionEntity p) { return promotionRepository.save(p); }
    public PromotionEntity update(PromotionEntity p) { return promotionRepository.save(p); }
    public void deleteById(Long id) { promotionRepository.deleteById(id); }
}
"@

Set-Content -Path "$PKG\service\TouristPackageService.java" -Value @"
package com.travelagency.mspaquetes.service;

import com.travelagency.mspaquetes.entity.*;
import com.travelagency.mspaquetes.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TouristPackageService {

    private final TouristPackageRepository touristPackageRepository;

    public List<TouristPackageEntity> findAll() { return touristPackageRepository.findAll(); }
    public Optional<TouristPackageEntity> findById(Long id) { return touristPackageRepository.findById(id); }
    public List<TouristPackageEntity> findByStatus(StatusEntity status) { return touristPackageRepository.findByStatus(status); }

    public List<TouristPackageEntity> findAvailableWithFilters(
            DestinationEntity destination, CategoryEntity category, PackageTypeEntity type,
            BigDecimal minPrice, BigDecimal maxPrice, LocalDate startDate, LocalDate endDate) {

        return touristPackageRepository.findAvailablePackages().stream()
            .filter(p -> destination == null || p.getDestination().getId().equals(destination.getId()))
            .filter(p -> category == null || p.getCategory().getId().equals(category.getId()))
            .filter(p -> type == null || p.getType().getId().equals(type.getId()))
            .filter(p -> minPrice == null || p.getPrice().compareTo(minPrice) >= 0)
            .filter(p -> maxPrice == null || p.getPrice().compareTo(maxPrice) <= 0)
            .filter(p -> startDate == null || !p.getStartDate().isBefore(startDate))
            .filter(p -> endDate == null || !p.getEndDate().isAfter(endDate))
            .collect(Collectors.toList());
    }

    public TouristPackageEntity save(TouristPackageEntity pkg) {
        validatePackage(pkg);
        return touristPackageRepository.save(pkg);
    }

    public TouristPackageEntity update(TouristPackageEntity pkg) {
        validatePackage(pkg);
        return touristPackageRepository.save(pkg);
    }

    public void deleteById(Long id) {
        touristPackageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paquete no encontrado"));
        touristPackageRepository.deleteById(id);
    }

    // Llamado desde ms-reservas via RestTemplate para descontar/restaurar cupos
    public TouristPackageEntity updateSlots(Long id, int delta, String newStatus) {
        TouristPackageEntity pkg = touristPackageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paquete no encontrado"));
        pkg.setAvailableSlots(pkg.getAvailableSlots() + delta);
        if (newStatus != null) {
            pkg.getStatus().setName(newStatus);
        }
        return touristPackageRepository.save(pkg);
    }

    private void validatePackage(TouristPackageEntity pkg) {
        if (pkg.getPrice() == null || pkg.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            throw new RuntimeException("El precio debe ser mayor que cero");
        if (pkg.getTotalSlots() <= 0)
            throw new RuntimeException("Los cupos totales deben ser mayores que cero");
        if (pkg.getStartDate() != null && pkg.getEndDate() != null
                && !pkg.getEndDate().isAfter(pkg.getStartDate()))
            throw new RuntimeException("La fecha de termino debe ser posterior a la fecha de inicio");
    }
}
"@

# ============================================================
# CONTROLLERS
# ============================================================

Set-Content -Path "$PKG\controller\StatusController.java" -Value @"
package com.travelagency.mspaquetes.controller;

import com.travelagency.mspaquetes.entity.StatusEntity;
import com.travelagency.mspaquetes.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/statuses")
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;

    @GetMapping
    public ResponseEntity<List<StatusEntity>> findAll() { return ResponseEntity.ok(statusService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<StatusEntity> findById(@PathVariable Long id) {
        return statusService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/entity-type/{entityType}")
    public ResponseEntity<List<StatusEntity>> findByEntityType(@PathVariable String entityType) {
        return ResponseEntity.ok(statusService.findByEntityType(entityType));
    }

    @PostMapping
    public ResponseEntity<StatusEntity> save(@RequestBody StatusEntity s) {
        return ResponseEntity.status(HttpStatus.CREATED).body(statusService.save(s));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusEntity> update(@PathVariable Long id, @RequestBody StatusEntity s) {
        if (statusService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        s.setId(id);
        return ResponseEntity.ok(statusService.update(s));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (statusService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        statusService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
"@

Set-Content -Path "$PKG\controller\DestinationController.java" -Value @"
package com.travelagency.mspaquetes.controller;

import com.travelagency.mspaquetes.entity.DestinationEntity;
import com.travelagency.mspaquetes.service.DestinationService;
import com.travelagency.mspaquetes.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@RequiredArgsConstructor
public class DestinationController {
    private final DestinationService destinationService;
    private final StatusService statusService;

    @GetMapping
    public ResponseEntity<List<DestinationEntity>> findAll() { return ResponseEntity.ok(destinationService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationEntity> findById(@PathVariable Long id) {
        return destinationService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DestinationEntity> save(@RequestBody DestinationEntity d) {
        return ResponseEntity.status(HttpStatus.CREATED).body(destinationService.save(d));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinationEntity> update(@PathVariable Long id, @RequestBody DestinationEntity d) {
        if (destinationService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        d.setId(id);
        return ResponseEntity.ok(destinationService.update(d));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (destinationService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        destinationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
"@

Set-Content -Path "$PKG\controller\TouristPackageController.java" -Value @"
package com.travelagency.mspaquetes.controller;

import com.travelagency.mspaquetes.entity.*;
import com.travelagency.mspaquetes.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tourist-packages")
@RequiredArgsConstructor
public class TouristPackageController {

    private final TouristPackageService touristPackageService;
    private final DestinationService destinationService;
    private final CategoryService categoryService;
    private final PackageTypeService packageTypeService;
    private final StatusService statusService;

    @GetMapping
    public ResponseEntity<List<TouristPackageEntity>> findAll() {
        return ResponseEntity.ok(touristPackageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TouristPackageEntity> findById(@PathVariable Long id) {
        return touristPackageService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/available")
    public ResponseEntity<List<TouristPackageEntity>> findAvailable(
            @RequestParam(required = false) Long destinationId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long typeId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        DestinationEntity destination = destinationId != null ? destinationService.findById(destinationId).orElse(null) : null;
        CategoryEntity category = categoryId != null ? categoryService.findById(categoryId).orElse(null) : null;
        PackageTypeEntity type = typeId != null ? packageTypeService.findById(typeId).orElse(null) : null;

        return ResponseEntity.ok(touristPackageService.findAvailableWithFilters(
            destination, category, type, minPrice, maxPrice, startDate, endDate));
    }

    // Endpoint interno para que ms-reservas actualice cupos
    @PutMapping("/{id}/slots")
    public ResponseEntity<?> updateSlots(
            @PathVariable Long id,
            @RequestParam int delta,
            @RequestParam(required = false) String newStatus) {
        try {
            return ResponseEntity.ok(touristPackageService.updateSlots(id, delta, newStatus));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<TouristPackageEntity> save(@RequestBody TouristPackageEntity pkg) {
        return ResponseEntity.status(HttpStatus.CREATED).body(touristPackageService.save(pkg));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TouristPackageEntity> update(@PathVariable Long id, @RequestBody TouristPackageEntity pkg) {
        if (touristPackageService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        pkg.setId(id);
        return ResponseEntity.ok(touristPackageService.update(pkg));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (touristPackageService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        touristPackageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
"@

Set-Content -Path "$PKG\controller\PromotionController.java" -Value @"
package com.travelagency.mspaquetes.controller;

import com.travelagency.mspaquetes.entity.PromotionEntity;
import com.travelagency.mspaquetes.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    @GetMapping
    public ResponseEntity<List<PromotionEntity>> findAll() { return ResponseEntity.ok(promotionService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionEntity> findById(@PathVariable Long id) {
        return promotionService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<List<PromotionEntity>> findActive() { return ResponseEntity.ok(promotionService.findActivePromotions()); }

    @GetMapping("/active/package/{packageId}")
    public ResponseEntity<List<PromotionEntity>> findActiveByPackage(@PathVariable Long packageId) {
        return ResponseEntity.ok(promotionService.findActivePromotionsByPackageId(packageId));
    }

    @PostMapping
    public ResponseEntity<PromotionEntity> save(@RequestBody PromotionEntity p) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promotionService.save(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotionEntity> update(@PathVariable Long id, @RequestBody PromotionEntity p) {
        if (promotionService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        p.setId(id);
        return ResponseEntity.ok(promotionService.update(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (promotionService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        promotionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
"@

# Controllers simples (Category, PackageType, Season, PackageService)
@(
    @{Name="CategoryController"; Entity="CategoryEntity"; Service="CategoryService"; Path="categories"},
    @{Name="PackageTypeController"; Entity="PackageTypeEntity"; Service="PackageTypeService"; Path="package-types"},
    @{Name="SeasonController"; Entity="SeasonEntity"; Service="SeasonService"; Path="seasons"},
    @{Name="PackageServiceController"; Entity="PackageServiceEntity"; Service="PackageServiceService"; Path="package-services"}
) | ForEach-Object {
    $n = $_.Name; $e = $_.Entity; $s = $_.Service; $p = $_.Path
    $lower = $s.Substring(0,1).ToLower() + $s.Substring(1)
    Set-Content -Path "$PKG\controller\$n.java" -Value @"
package com.travelagency.mspaquetes.controller;

import com.travelagency.mspaquetes.entity.$e;
import com.travelagency.mspaquetes.service.$s;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/$p")
@RequiredArgsConstructor
public class $n {
    private final $s $lower;

    @GetMapping
    public ResponseEntity<List<$e>> findAll() { return ResponseEntity.ok(${lower}.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<$e> findById(@PathVariable Long id) {
        return ${lower}.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<$e> save(@RequestBody $e entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(${lower}.save(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<$e> update(@PathVariable Long id, @RequestBody $e entity) {
        if (${lower}.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        entity.setId(id);
        return ResponseEntity.ok(${lower}.update(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (${lower}.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        ${lower}.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
"@
}

Write-Host "ms-paquetes creado correctamente." -ForegroundColor Green
Write-Host "Ahora ejecuta: cd ..\ms-paquetes && mvn compile" -ForegroundColor Cyan

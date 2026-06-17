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

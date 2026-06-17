package com.travelagency.mspaquetes.controller;

import com.travelagency.mspaquetes.entity.PackageTypeEntity;
import com.travelagency.mspaquetes.service.PackageTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/package-types")
@RequiredArgsConstructor
public class PackageTypeController {
    private final PackageTypeService packageTypeService;

    @GetMapping
    public ResponseEntity<List<PackageTypeEntity>> findAll() { return ResponseEntity.ok(packageTypeService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<PackageTypeEntity> findById(@PathVariable Long id) {
        return packageTypeService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PackageTypeEntity> save(@RequestBody PackageTypeEntity entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(packageTypeService.save(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PackageTypeEntity> update(@PathVariable Long id, @RequestBody PackageTypeEntity entity) {
        if (packageTypeService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        entity.setId(id);
        return ResponseEntity.ok(packageTypeService.update(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (packageTypeService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        packageTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

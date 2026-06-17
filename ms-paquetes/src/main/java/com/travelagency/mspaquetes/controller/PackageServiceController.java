package com.travelagency.mspaquetes.controller;

import com.travelagency.mspaquetes.entity.PackageServiceEntity;
import com.travelagency.mspaquetes.service.PackageServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/package-services")
@RequiredArgsConstructor
public class PackageServiceController {
    private final PackageServiceService packageServiceService;

    @GetMapping
    public ResponseEntity<List<PackageServiceEntity>> findAll() { return ResponseEntity.ok(packageServiceService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<PackageServiceEntity> findById(@PathVariable Long id) {
        return packageServiceService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PackageServiceEntity> save(@RequestBody PackageServiceEntity entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(packageServiceService.save(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PackageServiceEntity> update(@PathVariable Long id, @RequestBody PackageServiceEntity entity) {
        if (packageServiceService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        entity.setId(id);
        return ResponseEntity.ok(packageServiceService.update(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (packageServiceService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        packageServiceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

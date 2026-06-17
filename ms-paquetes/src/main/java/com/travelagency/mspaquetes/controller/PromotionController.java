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

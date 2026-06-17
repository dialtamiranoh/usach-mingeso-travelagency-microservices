package com.travelagency.mspaquetes.controller;

import com.travelagency.mspaquetes.entity.SeasonEntity;
import com.travelagency.mspaquetes.service.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/seasons")
@RequiredArgsConstructor
public class SeasonController {
    private final SeasonService seasonService;

    @GetMapping
    public ResponseEntity<List<SeasonEntity>> findAll() { return ResponseEntity.ok(seasonService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<SeasonEntity> findById(@PathVariable Long id) {
        return seasonService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SeasonEntity> save(@RequestBody SeasonEntity entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(seasonService.save(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeasonEntity> update(@PathVariable Long id, @RequestBody SeasonEntity entity) {
        if (seasonService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        entity.setId(id);
        return ResponseEntity.ok(seasonService.update(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (seasonService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        seasonService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

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

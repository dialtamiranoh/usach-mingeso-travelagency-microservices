package com.travelagency.mspaquetes.controller;

import com.travelagency.mspaquetes.entity.CategoryEntity;
import com.travelagency.mspaquetes.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> findAll() { return ResponseEntity.ok(categoryService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryEntity> findById(@PathVariable Long id) {
        return categoryService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> save(@RequestBody CategoryEntity entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryEntity> update(@PathVariable Long id, @RequestBody CategoryEntity entity) {
        if (categoryService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        entity.setId(id);
        return ResponseEntity.ok(categoryService.update(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (categoryService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

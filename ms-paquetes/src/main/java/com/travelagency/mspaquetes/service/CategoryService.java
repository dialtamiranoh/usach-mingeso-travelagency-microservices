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

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

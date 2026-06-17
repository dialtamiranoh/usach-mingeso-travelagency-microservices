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

package com.travelagency.mspaquetes.service;

import com.travelagency.mspaquetes.entity.PackageServiceEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import com.travelagency.mspaquetes.repository.PackageServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackageServiceService {
    private final PackageServiceRepository packageServiceRepository;

    public List<PackageServiceEntity> findAll() { return packageServiceRepository.findAll(); }
    public Optional<PackageServiceEntity> findById(Long id) { return packageServiceRepository.findById(id); }
    public List<PackageServiceEntity> findByStatus(StatusEntity status) { return packageServiceRepository.findByStatus(status); }
    public PackageServiceEntity save(PackageServiceEntity p) { return packageServiceRepository.save(p); }
    public PackageServiceEntity update(PackageServiceEntity p) { return packageServiceRepository.save(p); }
    public void deleteById(Long id) { packageServiceRepository.deleteById(id); }
}

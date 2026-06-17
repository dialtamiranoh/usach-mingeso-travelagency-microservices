package com.travelagency.mspaquetes.service;

import com.travelagency.mspaquetes.entity.StatusEntity;
import com.travelagency.mspaquetes.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

    public List<StatusEntity> findAll() { return statusRepository.findAll(); }
    public Optional<StatusEntity> findById(Long id) { return statusRepository.findById(id); }
    public List<StatusEntity> findByEntityType(String entityType) { return statusRepository.findByEntityType(entityType); }
    public StatusEntity save(StatusEntity s) { return statusRepository.save(s); }
    public StatusEntity update(StatusEntity s) { return statusRepository.save(s); }
    public void deleteById(Long id) { statusRepository.deleteById(id); }
}

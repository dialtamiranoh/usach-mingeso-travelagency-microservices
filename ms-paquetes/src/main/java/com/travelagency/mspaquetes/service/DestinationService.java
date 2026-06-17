package com.travelagency.mspaquetes.service;

import com.travelagency.mspaquetes.entity.DestinationEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import com.travelagency.mspaquetes.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DestinationService {
    private final DestinationRepository destinationRepository;

    public List<DestinationEntity> findAll() { return destinationRepository.findAll(); }
    public Optional<DestinationEntity> findById(Long id) { return destinationRepository.findById(id); }
    public List<DestinationEntity> findByStatus(StatusEntity status) { return destinationRepository.findByStatus(status); }
    public DestinationEntity save(DestinationEntity d) { return destinationRepository.save(d); }
    public DestinationEntity update(DestinationEntity d) { return destinationRepository.save(d); }
    public void deleteById(Long id) { destinationRepository.deleteById(id); }
}

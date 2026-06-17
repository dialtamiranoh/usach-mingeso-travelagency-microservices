package com.travelagency.mspaquetes.service;

import com.travelagency.mspaquetes.entity.SeasonEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import com.travelagency.mspaquetes.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeasonService {
    private final SeasonRepository seasonRepository;

    public List<SeasonEntity> findAll() { return seasonRepository.findAll(); }
    public Optional<SeasonEntity> findById(Long id) { return seasonRepository.findById(id); }
    public List<SeasonEntity> findByStatus(StatusEntity status) { return seasonRepository.findByStatus(status); }
    public SeasonEntity save(SeasonEntity s) { return seasonRepository.save(s); }
    public SeasonEntity update(SeasonEntity s) { return seasonRepository.save(s); }
    public void deleteById(Long id) { seasonRepository.deleteById(id); }
}

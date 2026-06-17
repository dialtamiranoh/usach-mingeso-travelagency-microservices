package com.travelagency.mspaquetes.service;

import com.travelagency.mspaquetes.entity.*;
import com.travelagency.mspaquetes.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TouristPackageService {

    private final TouristPackageRepository touristPackageRepository;

    public List<TouristPackageEntity> findAll() { return touristPackageRepository.findAll(); }
    public Optional<TouristPackageEntity> findById(Long id) { return touristPackageRepository.findById(id); }
    public List<TouristPackageEntity> findByStatus(StatusEntity status) { return touristPackageRepository.findByStatus(status); }

    public List<TouristPackageEntity> findAvailableWithFilters(
            DestinationEntity destination, CategoryEntity category, PackageTypeEntity type,
            BigDecimal minPrice, BigDecimal maxPrice, LocalDate startDate, LocalDate endDate) {

        return touristPackageRepository.findAvailablePackages().stream()
            .filter(p -> destination == null || p.getDestination().getId().equals(destination.getId()))
            .filter(p -> category == null || p.getCategory().getId().equals(category.getId()))
            .filter(p -> type == null || p.getType().getId().equals(type.getId()))
            .filter(p -> minPrice == null || p.getPrice().compareTo(minPrice) >= 0)
            .filter(p -> maxPrice == null || p.getPrice().compareTo(maxPrice) <= 0)
            .filter(p -> startDate == null || !p.getStartDate().isBefore(startDate))
            .filter(p -> endDate == null || !p.getEndDate().isAfter(endDate))
            .collect(Collectors.toList());
    }

    public TouristPackageEntity save(TouristPackageEntity pkg) {
        validatePackage(pkg);
        return touristPackageRepository.save(pkg);
    }

    public TouristPackageEntity update(TouristPackageEntity pkg) {
        validatePackage(pkg);
        return touristPackageRepository.save(pkg);
    }

    public void deleteById(Long id) {
        touristPackageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paquete no encontrado"));
        touristPackageRepository.deleteById(id);
    }

    // Llamado desde ms-reservas via RestTemplate para descontar/restaurar cupos
    public TouristPackageEntity updateSlots(Long id, int delta, String newStatus) {
        TouristPackageEntity pkg = touristPackageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paquete no encontrado"));
        pkg.setAvailableSlots(pkg.getAvailableSlots() + delta);
        if (newStatus != null) {
            pkg.getStatus().setName(newStatus);
        }
        return touristPackageRepository.save(pkg);
    }

    private void validatePackage(TouristPackageEntity pkg) {
        if (pkg.getPrice() == null || pkg.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            throw new RuntimeException("El precio debe ser mayor que cero");
        if (pkg.getTotalSlots() <= 0)
            throw new RuntimeException("Los cupos totales deben ser mayores que cero");
        if (pkg.getStartDate() != null && pkg.getEndDate() != null
                && !pkg.getEndDate().isAfter(pkg.getStartDate()))
            throw new RuntimeException("La fecha de termino debe ser posterior a la fecha de inicio");
    }
}

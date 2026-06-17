package com.travelagency.mspaquetes.repository;

import com.travelagency.mspaquetes.entity.SeasonEntity;
import com.travelagency.mspaquetes.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeasonRepository extends JpaRepository<SeasonEntity, Long> {
    Optional<SeasonEntity> findByName(String name);
    List<SeasonEntity> findByStatus(StatusEntity status);
}

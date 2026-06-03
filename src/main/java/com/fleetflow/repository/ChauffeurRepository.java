package com.fleetflow.repository;

import com.fleetflow.entity.Chauffeur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChauffeurRepository extends JpaRepository<Chauffeur, Long> {

    Page<Chauffeur> findByDisponibleTrue(Pageable pageable);
    Optional<Chauffeur> findByUsername(String username);

}

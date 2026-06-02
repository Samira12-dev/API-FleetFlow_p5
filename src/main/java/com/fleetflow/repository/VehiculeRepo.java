package com.fleetflow.repository;

import com.fleetflow.entity.StatutVehicule;
import com.fleetflow.entity.Vehicule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculeRepo extends JpaRepository<Vehicule, Long> {

    Page<Vehicule> findByStatut(StatutVehicule statut, Pageable pageable);
    Page<Vehicule> findByCapaciteGreaterThan(int capacite, Pageable pageable);
}

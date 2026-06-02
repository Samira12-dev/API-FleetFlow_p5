package com.fleetflow.service;

import com.fleetflow.dto.VehiculeRequestDTO;
import com.fleetflow.dto.VehiculeResponseDTO;
import com.fleetflow.entity.StatutVehicule;

import java.util.List;

public interface VehiculeService {
    VehiculeResponseDTO ajouterVehicule(VehiculeRequestDTO dto);
    List<VehiculeResponseDTO> listeVehiculesDisponibles();
    List<VehiculeResponseDTO> findVehiculeByStatut(StatutVehicule statut);
    List<VehiculeResponseDTO> findCapaciteVehiculeGreaterThan(int capacite);
    void supprimerVehiculeById(Long id);
    VehiculeResponseDTO modifier(Long id, VehiculeRequestDTO dto);

}

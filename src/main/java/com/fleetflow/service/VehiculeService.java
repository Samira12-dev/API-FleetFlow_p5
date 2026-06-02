package com.fleetflow.service;

import com.fleetflow.dto.VehiculeRequestDTO;
import com.fleetflow.dto.VehiculeResponseDTO;
import com.fleetflow.entity.StatutVehicule;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VehiculeService {
    VehiculeResponseDTO ajouterVehicule(VehiculeRequestDTO dto);
    Page<VehiculeResponseDTO> listeVehiculesDisponibles(int page ,int size, String sortby);
    Page<VehiculeResponseDTO> findVehiculeByStatut(StatutVehicule statut,int page,int size, String sortby);
    Page<VehiculeResponseDTO> findCapaciteVehiculeGreaterThan(int capacite, int page, int size,String sortby);
    void supprimerVehiculeById(Long id);
    VehiculeResponseDTO modifier(Long id, VehiculeRequestDTO dto);

}

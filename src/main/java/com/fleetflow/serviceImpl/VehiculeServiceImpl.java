package com.fleetflow.serviceImpl;

import com.fleetflow.dto.VehiculeRequestDTO;
import com.fleetflow.dto.VehiculeResponseDTO;
import com.fleetflow.entity.StatutVehicule;
import com.fleetflow.entity.Vehicule;
import com.fleetflow.mapper.VehiculeMapper;
import com.fleetflow.repository.VehiculeRepo;
import com.fleetflow.service.VehiculeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VehiculeServiceImpl implements VehiculeService {

    private final VehiculeMapper mapper;
    private final VehiculeRepo repo;

    @Transactional
    public VehiculeResponseDTO ajouterVehicule(VehiculeRequestDTO dto) {
        Vehicule vehicule = mapper.toEntity(dto);
        Vehicule savedVehicule = repo.save(vehicule);
        return mapper.toResponseDto(savedVehicule);
    }

    @Transactional
    public List<VehiculeResponseDTO> listeVehiculesDisponibles() {
        return mapper.toResponseDtoList(repo.findByStatut(StatutVehicule.DISPONIBLE));
    }

    @Transactional
    public List<VehiculeResponseDTO> findVehiculeByStatut(StatutVehicule statut) {
        return mapper.toResponseDtoList(repo.findByStatut(statut));
    }

    @Transactional
    public List<VehiculeResponseDTO> findCapaciteVehiculeGreaterThan(int capacite) {
        return mapper.toResponseDtoList(repo.findByCapaciteGreaterThan(capacite));
    }

    @Transactional
    public void supprimerVehiculeById(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Vehicule non trouve");
        }
        repo.deleteById(id);
    }

    @Transactional
    public VehiculeResponseDTO modifier(Long id, VehiculeRequestDTO dto) {
        Vehicule vehicule = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicule non trouve"));

        mapper.updateEntityFromDto(dto, vehicule);
        Vehicule updatedVehicule = repo.save(vehicule);
        return mapper.toResponseDto(updatedVehicule);
    }
}



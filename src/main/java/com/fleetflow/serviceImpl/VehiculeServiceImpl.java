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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<VehiculeResponseDTO> listeVehiculesDisponibles(int page,int size, String sortby) {
        Pageable pageable= PageRequest.of(page,size, Sort.by(sortby).ascending());
        Page<Vehicule> vehicules=repo.findByStatut(StatutVehicule.DISPONIBLE,pageable);
        return vehicules.map(mapper::toResponseDto);
    }

    @Transactional
    public Page<VehiculeResponseDTO> findVehiculeByStatut(StatutVehicule statut, int page,int size, String sortby) {
        Pageable pageable =PageRequest.of(page,size,Sort.by(sortby).ascending());
        Page<Vehicule> vehicules=repo.findByStatut(statut,pageable);
        return vehicules.map(mapper::toResponseDto);
    }

    @Transactional
    public Page<VehiculeResponseDTO> findCapaciteVehiculeGreaterThan(int capacite,int page, int size, String sortby) {
        Pageable pageable=PageRequest.of(page,size,Sort.by(sortby).ascending());
        Page<Vehicule> vehicules= repo.findByCapaciteGreaterThan(capacite,pageable);
        return  vehicules.map(mapper::toResponseDto);
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



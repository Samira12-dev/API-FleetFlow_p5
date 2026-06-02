package com.fleetflow.service;

import com.fleetflow.dto.VehiculeResponseDTO;
import com.fleetflow.entity.StatutVehicule;
import com.fleetflow.entity.Vehicule;
import com.fleetflow.mapper.VehiculeMapper;
import com.fleetflow.repository.VehiculeRepo;
import com.fleetflow.serviceImpl.VehiculeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehiculeServiceTest {

    @Mock
    VehiculeRepo vehiculeRepo;

    @Mock
    VehiculeMapper mapper;

    @InjectMocks
    VehiculeServiceImpl vehiculeService;

    @Test
    void listeVehiculesDisponibles() {

        // ARRANGE
        Vehicule v1 = new Vehicule();
        v1.setStatut(StatutVehicule.DISPONIBLE);

        VehiculeResponseDTO dto = new VehiculeResponseDTO();

        Page<Vehicule> page = new PageImpl<>(List.of(v1));

        when(vehiculeRepo.findByStatut(eq(StatutVehicule.DISPONIBLE), any(Pageable.class)))
                .thenReturn(page);

        when(mapper.toResponseDto(v1))
                .thenReturn(dto);

        // ACT
        Page<VehiculeResponseDTO> result =
                vehiculeService.listeVehiculesDisponibles(0, 10, "id");

        // ASSERT
        assertEquals(1, result.getContent().size());
    }

    @Test
    void findCapaciteVehiculeGreaterThan() {

        // ARRANGE
        int capaciteMin = 10;

        Vehicule v1 = new Vehicule();
        v1.setCapacite(15);

        VehiculeResponseDTO dto = new VehiculeResponseDTO();
        dto.setCapacite(15);

        Page<Vehicule> page = new PageImpl<>(List.of(v1));

        when(vehiculeRepo.findByCapaciteGreaterThan(eq(capaciteMin), any(Pageable.class)))
                .thenReturn(page);

        when(mapper.toResponseDto(v1))
                .thenReturn(dto);

        // ACT
        Page<VehiculeResponseDTO> result =
                vehiculeService.findCapaciteVehiculeGreaterThan(capaciteMin, 0, 10, "id");

        // ASSERT
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertTrue(result.getContent().get(0).getCapacite() > capaciteMin);
    }
}
package com.fleetflow.controller;
import com.fleetflow.dto.VehiculeRequestDTO;
import com.fleetflow.dto.VehiculeResponseDTO;
import com.fleetflow.entity.StatutVehicule;
import com.fleetflow.service.VehiculeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final VehiculeService service;

    @PostMapping
    @Operation(summary = "Ajouter un vehicule")
    public VehiculeResponseDTO ajouter(@Valid @RequestBody VehiculeRequestDTO dto) {
        return service.ajouterVehicule(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un vehicule")
    public VehiculeResponseDTO modifier(@PathVariable Long id, @Valid @RequestBody VehiculeRequestDTO dto) {
        return service.modifier(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un vehicule")
    public void supprimerVehicule(@PathVariable Long id) {
        service.supprimerVehiculeById(id);
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Lister les vehicules disponibles")
    public ResponseEntity<Page<VehiculeResponseDTO> >listerDisponibles(@RequestParam(defaultValue = "0")int  page,
                                                                      @RequestParam(defaultValue = "10")int size,
                                                                      @RequestParam(defaultValue = "nom")String sotby) {
        Page<VehiculeResponseDTO> vehiculeResponseDTOS=service.listeVehiculesDisponibles(page, size, sotby);
        return ResponseEntity.ok(vehiculeResponseDTOS);
    }

    @GetMapping("/statut/{statut}")
    @Operation(summary = "Trouver les vehicules par statut")
    public ResponseEntity< Page<VehiculeResponseDTO> >getVehiculeByStatut(@PathVariable StatutVehicule statut,
                                                                          @RequestParam(defaultValue = "0")int page,
                                                                           @RequestParam(defaultValue = "10")int size,
                                                                          @RequestParam(defaultValue = "nom")String sortby) {
        Page<VehiculeResponseDTO>vehicules=service.findVehiculeByStatut(statut,page,size,sortby);
        return ResponseEntity.ok(vehicules);
    }

    @GetMapping("/capacite/{capacite}")
    @Operation(summary = "Trouver les vehicules avec capacite superieure")
    public ResponseEntity<Page<VehiculeResponseDTO>>getVehiculeByCapacite(@PathVariable int capacite,
                                                           @RequestParam(defaultValue = "0")int page,
                                                           @RequestParam(defaultValue = "10")int size,
                                                           @RequestParam(defaultValue = "nom")String sortby)  {
        Page<VehiculeResponseDTO>vehiculeResponseDTOS=service.findCapaciteVehiculeGreaterThan(capacite, page, size, sortby);
        return ResponseEntity.ok(vehiculeResponseDTOS);
    }
}

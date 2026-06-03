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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final VehiculeService service;

    @PreAuthorize("hasRole('ADMIN')")

    @PostMapping
    @Operation(summary = "admin Ajouter un vehicule")
    public VehiculeResponseDTO ajouter(@Valid @RequestBody VehiculeRequestDTO dto) {
        return service.ajouterVehicule(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = " admin Modifier un vehicule")
    public VehiculeResponseDTO modifier(@PathVariable Long id, @Valid @RequestBody VehiculeRequestDTO dto) {
        return service.modifier(id, dto);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "admin Supprimer un vehicule")
    public void supprimerVehicule(@PathVariable Long id) {
        service.supprimerVehiculeById(id);
    }


    @PreAuthorize("hasAnyRole('ADMIN','MANAGER'")
    @GetMapping("/disponibles")
    @Operation(summary = "admin & manager Lister les vehicules disponibles")
    public ResponseEntity<Page<VehiculeResponseDTO> >listerDisponibles(@RequestParam(defaultValue = "0")int  page,
                                                                      @RequestParam(defaultValue = "10")int size,
                                                                      @RequestParam(defaultValue = "id")String sotby) {
        Page<VehiculeResponseDTO> vehiculeResponseDTOS=service.listeVehiculesDisponibles(page, size, sotby);
        return ResponseEntity.ok(vehiculeResponseDTOS);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/statut/{statut}")
    @Operation(summary = "admin Trouver les vehicules par statut")
    public ResponseEntity< Page<VehiculeResponseDTO> >getVehiculeByStatut(@PathVariable StatutVehicule statut,
                                                                          @RequestParam(defaultValue = "0")int page,
                                                                           @RequestParam(defaultValue = "10")int size,
                                                                          @RequestParam(defaultValue = "id")String sortby) {
        Page<VehiculeResponseDTO>vehicules=service.findVehiculeByStatut(statut,page,size,sortby);
        return ResponseEntity.ok(vehicules);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/capacite/{capacite}")
    @Operation(summary = " admin  Trouver les vehicules avec capacite superieure")
    public ResponseEntity<Page<VehiculeResponseDTO>>getVehiculeByCapacite(@PathVariable int capacite,
                                                           @RequestParam(defaultValue = "0")int page,
                                                           @RequestParam(defaultValue = "10")int size,
                                                           @RequestParam(defaultValue = "id")String sortby)  {
        Page<VehiculeResponseDTO>vehiculeResponseDTOS=service.findCapaciteVehiculeGreaterThan(capacite, page, size, sortby);
        return ResponseEntity.ok(vehiculeResponseDTOS);
    }
}

package com.fleetflow.controller;

import com.fleetflow.dto.ChauffeurRequestDTO;
import com.fleetflow.dto.ChauffeurResponseDTO;
import com.fleetflow.service.ChauffeurService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/chauffeurs")
@RequiredArgsConstructor
public class ChauffeurController {
    private final ChauffeurService chauffeurService;

    @PostMapping
    public ResponseEntity<ChauffeurResponseDTO> ajouterChauffeur(@Valid @RequestBody ChauffeurRequestDTO dto) {
        return new ResponseEntity<>(chauffeurService.ajouterChauffeur(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChauffeurResponseDTO> modifierChauffeur(
            @PathVariable Long id,
            @Valid @RequestBody ChauffeurRequestDTO dto) {
        return ResponseEntity.ok(chauffeurService.modifierChauffeur(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerChauffeur(@PathVariable Long id) {
        chauffeurService.supprimerChauffeur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "liste tous les chauffeur")
    public ResponseEntity<Page<ChauffeurResponseDTO>> listerTousLesChauffeurs(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "nom")String sortBy) {
        Page<ChauffeurResponseDTO> responseDTOPage= chauffeurService.listerTousLesChauffeurs(page,size,sortBy);
        return ResponseEntity.ok(responseDTOPage);
    }



    @GetMapping("/disponibles")
    @Operation(summary = "listerChauffeursDisponibles")
    public ResponseEntity<Page<ChauffeurResponseDTO>>listerChauffeursDisponibles(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nom") String  sortBy) {
        Page<ChauffeurResponseDTO> responseDTOPage= chauffeurService.listerChauffeursDisponibles(page, size, sortBy);
        return ResponseEntity.ok(responseDTOPage);
    }
}

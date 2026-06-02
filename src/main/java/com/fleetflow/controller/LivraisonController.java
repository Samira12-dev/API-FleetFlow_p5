package com.fleetflow.controller;

import com.fleetflow.dto.LivraisonRequestDTO;
import com.fleetflow.dto.LivraisonResponseDTO;
import com.fleetflow.dto.LivraisonStatutRequestDTO;
import com.fleetflow.entity.StatutLivraison;
import com.fleetflow.service.LivraisonService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/livraisons")
@RequiredArgsConstructor
public class LivraisonController {

    private final LivraisonService service;

    @PostMapping
    public ResponseEntity<LivraisonResponseDTO> createLivraison(@Valid @RequestBody LivraisonRequestDTO livraisonRequestDTO){
        LivraisonResponseDTO livraison = service.createLivraison(livraisonRequestDTO);
        return ResponseEntity.ok(livraison);
    }

    @PutMapping("/{id}/assigner")
    public ResponseEntity<LivraisonResponseDTO> assignerRessources(
            @PathVariable Long id,
            @Valid @RequestParam Long chauffeurId,
            @Valid @RequestParam Long vehiculeId) {
        return ResponseEntity.ok(service.assignerChauffeurEtVehicule(id, chauffeurId, vehiculeId));
    }

    @GetMapping
    public ResponseEntity<Page<LivraisonResponseDTO>> getAllLivraison(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "nom") String sortby
    ){

        Page<LivraisonResponseDTO> listLivraison=service.getAllLivraison(page, size, sortby);
        return ResponseEntity.ok(listLivraison);
    }

    @PutMapping("/{id}/statut")
    @Operation(summary = "Modifier le statut d'une livraison")
    public LivraisonResponseDTO modifierStatut(@PathVariable Long id, @Valid @RequestBody LivraisonStatutRequestDTO dto) {
        return service.modifierStatut(id, dto);
    }

    @GetMapping("/statut/{statut}")
    @Operation(summary = "Trouver les livraisons par statut")
    public ResponseEntity<Page<LivraisonResponseDTO>> findByStatut(@PathVariable StatutLivraison statut,
                                                   @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "nom")String sortby) {
        Page<LivraisonResponseDTO>livraisonResponseDTOS= service.findByStatut(statut,page,size,sortby);
        return ResponseEntity.ok(livraisonResponseDTOS);
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "Trouver les livraisons par client")
    public ResponseEntity<Page<LivraisonResponseDTO>> findByClientId(@PathVariable Long clientId,
                                                     @RequestParam(defaultValue = "0")int page,
                                                     @RequestParam(defaultValue = "10")int size,
                                                     @RequestParam(defaultValue = "nom")String sortby) {
        Page<LivraisonResponseDTO> livraisonResponseDTOS =
                service.findByClientId(clientId, page, size, sortby);

        return ResponseEntity.ok(livraisonResponseDTOS);
    }

    @GetMapping("/between-dates")
    public ResponseEntity<Page<LivraisonResponseDTO>> getBetweenDates(@RequestParam LocalDate start, @RequestParam LocalDate end,
                                                      @RequestParam(defaultValue = "0")int page,
                                                      @RequestParam(defaultValue = "10")int size,
                                                      @RequestParam(defaultValue = "nom")String sortby
    ){
        Page<LivraisonResponseDTO> livraisonResponseDTOS= service.getBewteenTwoDates(start,end,page,size,sortby);
        return ResponseEntity.ok(livraisonResponseDTOS);
    }

    @GetMapping("/recherche/ville")
    public ResponseEntity<Page<LivraisonResponseDTO>> listerLivraisonsParVille(@RequestParam String ville,
                                                                               @RequestParam(defaultValue = "0")int page,
                                                                               @RequestParam(defaultValue = "10")int size,
                                                                               @RequestParam(defaultValue = "nom")String sortby) {

        Page<LivraisonResponseDTO> livraisons = service.listerLivraisonsParVilleDestination(ville,page,size,sortby);
        return ResponseEntity.ok(livraisons);
    }
}

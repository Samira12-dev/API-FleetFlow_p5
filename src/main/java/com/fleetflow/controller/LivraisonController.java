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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/livraisons")
@RequiredArgsConstructor
public class LivraisonController {

    private final LivraisonService service;


    @PreAuthorize("hasAnyRole('ADMIN','MANAGER'")
    @Operation(summary = "admin & maanger can create livraison")
    @PostMapping
    public ResponseEntity<LivraisonResponseDTO> createLivraison(@Valid @RequestBody LivraisonRequestDTO livraisonRequestDTO){
        LivraisonResponseDTO livraison = service.createLivraison(livraisonRequestDTO);
        return ResponseEntity.ok(livraison);
    }


    @PreAuthorize("hasAnyRole('ADMIN','MANAGER'")
    @Operation(summary = "admin & maanger can assignerRessources")
    @PutMapping("/{id}/assigner")
    public ResponseEntity<LivraisonResponseDTO> assignerRessources(
            @PathVariable Long id,
            @Valid @RequestParam Long chauffeurId,
            @Valid @RequestParam Long vehiculeId) {
        return ResponseEntity.ok(service.assignerChauffeurEtVehicule(id, chauffeurId, vehiculeId));
    }


    @PreAuthorize("hasAnyRole('ADMIN','MANAGER'")
    @Operation(summary = "admin & maanger can get all  livraisons")
    @GetMapping
    public ResponseEntity<Page<LivraisonResponseDTO>> getAllLivraison(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id") String sortby
    ){

        Page<LivraisonResponseDTO> listLivraison=service.getAllLivraison(page, size, sortby);
        return ResponseEntity.ok(listLivraison);
    }


    @PreAuthorize("hasAnyRole('ADMIN','MANAGER',CHAUFFEUR")
    @PutMapping("/{id}/statut")
    @Operation(summary = " admin & manager, CHAUFFEUR Modifier le statut d'une livraison")
    public LivraisonResponseDTO modifierStatut(@PathVariable Long id, @Valid @RequestBody LivraisonStatutRequestDTO dto) {
        return service.modifierStatut(id, dto);
    }


    @PreAuthorize("hasAnyRole('ADMIN','MANAGER'")
    @GetMapping("/statut/{statut}")
    @Operation(summary = " admin & manager Trouver les livraisons par statut")
    public ResponseEntity<Page<LivraisonResponseDTO>> findByStatut(@PathVariable StatutLivraison statut,
                                                   @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "statut")String sortby) {
        Page<LivraisonResponseDTO>livraisonResponseDTOS= service.findByStatut(statut,page,size,sortby);
        return ResponseEntity.ok(livraisonResponseDTOS);
    }


    @PreAuthorize("hasAnyRole('ADMIN','MANAGER'")
    @GetMapping("/client/{clientId}")
    @Operation(summary =  " Admin & manager Trouver les livraisons par client")
    public ResponseEntity<Page<LivraisonResponseDTO>> findByClientId(@PathVariable Long clientId,
                                                     @RequestParam(defaultValue = "0")int page,
                                                     @RequestParam(defaultValue = "10")int size,
                                                     @RequestParam(defaultValue = "id")String sortby) {
        Page<LivraisonResponseDTO> livraisonResponseDTOS =
                service.findByClientId(clientId, page, size, sortby);

        return ResponseEntity.ok(livraisonResponseDTOS);
    }


    @PreAuthorize("hasAnyRole('ADMIN','MANAGER'")
    @Operation(summary = "admin & maanger can get between days")
    @GetMapping("/between-dates")
    public ResponseEntity<Page<LivraisonResponseDTO>> getBetweenDates(@RequestParam LocalDate start, @RequestParam LocalDate end,
                                                      @RequestParam(defaultValue = "0")int page,
                                                      @RequestParam(defaultValue = "10")int size,
                                                      @RequestParam(defaultValue = "date")String sortby
    ){
        Page<LivraisonResponseDTO> livraisonResponseDTOS= service.getBewteenTwoDates(start,end,page,size,sortby);
        return ResponseEntity.ok(livraisonResponseDTOS);
    }



    @PreAuthorize("hasAnyRole('ADMIN','MANAGER'")
    @Operation(summary = "admin & maanger can lister livraison par ville")
    @GetMapping("/recherche/ville")
    public ResponseEntity<Page<LivraisonResponseDTO>> listerLivraisonsParVille(@RequestParam String ville,
                                                                               @RequestParam(defaultValue = "0")int page,
                                                                               @RequestParam(defaultValue = "10")int size,
                                                                               @RequestParam(defaultValue = "nom")String sortby) {

        Page<LivraisonResponseDTO> livraisons = service.listerLivraisonsParVilleDestination(ville,page,size,sortby);
        return ResponseEntity.ok(livraisons);
    }



    @PreAuthorize("hasRole('CHAUFFEUR')")
    @Operation(summary = "Get my deliveries")
    @GetMapping("/me")
    public ResponseEntity<Page<LivraisonResponseDTO>> getMyLivraisons(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {

        Page<LivraisonResponseDTO> myLivraisons =
                service.getMyLivrassion(page, size, sortBy);

        return ResponseEntity.ok(myLivraisons);
    }


}

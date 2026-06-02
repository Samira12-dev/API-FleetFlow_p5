package com.fleetflow.service;

import com.fleetflow.dto.LivraisonRequestDTO;
import com.fleetflow.dto.LivraisonResponseDTO;
import com.fleetflow.dto.LivraisonStatutRequestDTO;
import com.fleetflow.entity.StatutLivraison;
import com.fleetflow.service.LivraisonService;
import java.time.LocalDate;
import java.util.List;

public interface LivraisonService {
    LivraisonResponseDTO createLivraison(LivraisonRequestDTO livraisondto);
    LivraisonResponseDTO modifierStatut(Long id, LivraisonStatutRequestDTO dto);
    List<LivraisonResponseDTO> getAllLivraison();
    List<LivraisonResponseDTO> findByStatut(StatutLivraison statut);
    List<LivraisonResponseDTO> findByClientId(Long clientId);
    LivraisonResponseDTO assignerChauffeurEtVehicule(Long livraisonId, Long chauffeurId, Long vehiculeId);
    List<LivraisonResponseDTO> getBewteenTwoDates(LocalDate start , LocalDate end);
    List<LivraisonResponseDTO> listerLivraisonsParVilleDestination(String ville);
}

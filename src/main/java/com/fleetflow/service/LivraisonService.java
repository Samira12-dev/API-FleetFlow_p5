package com.fleetflow.service;

import com.fleetflow.dto.LivraisonRequestDTO;
import com.fleetflow.dto.LivraisonResponseDTO;
import com.fleetflow.dto.LivraisonStatutRequestDTO;
import com.fleetflow.entity.StatutLivraison;
import com.fleetflow.service.LivraisonService;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface LivraisonService {
    LivraisonResponseDTO assignerChauffeurEtVehicule(Long livraisonId, Long chauffeurId, Long vehiculeId);
    LivraisonResponseDTO createLivraison(LivraisonRequestDTO livraisondto);
    LivraisonResponseDTO modifierStatut(Long id, LivraisonStatutRequestDTO dto);
    Page<LivraisonResponseDTO> getAllLivraison(int page, int size, String sortby);
    Page<LivraisonResponseDTO> findByStatut(StatutLivraison statut, int page, int size, String sortby);

    Page<LivraisonResponseDTO> findByClientId(Long clientId, int page, int size, String sortby);

    Page<LivraisonResponseDTO> getBewteenTwoDates(LocalDate start, LocalDate end, int page, int size, String sortby);

    Page<LivraisonResponseDTO> listerLivraisonsParVilleDestination(String ville, int page, int size, String sortby);

    Page<LivraisonResponseDTO> getMyLivrassion(int page, int size, String sortby);
    LivraisonResponseDTO updateMyLivraisonStatut(Long livraisonId, LivraisonStatutRequestDTO dto);
}



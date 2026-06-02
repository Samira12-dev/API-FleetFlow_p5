package com.fleetflow.service;

import com.fleetflow.dto.ChauffeurRequestDTO;
import com.fleetflow.dto.ChauffeurResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChauffeurService {

    ChauffeurResponseDTO ajouterChauffeur(ChauffeurRequestDTO requestDTO);
    ChauffeurResponseDTO modifierChauffeur(Long id, ChauffeurRequestDTO requestDTO);
    void supprimerChauffeur(Long id);
    List<ChauffeurResponseDTO> listerChauffeursDisponibles();
    Page<ChauffeurResponseDTO> listerTousLesChauffeurs(int page, int size, String sortBy);}

package com.fleetflow.serviceImpl;

import com.fleetflow.dto.LivraisonRequestDTO;
import com.fleetflow.dto.LivraisonResponseDTO;
import com.fleetflow.dto.LivraisonStatutRequestDTO;
import com.fleetflow.entity.*;
import com.fleetflow.mapper.LivraisonMapper;
import com.fleetflow.repository.ChauffeurRepository;
import com.fleetflow.repository.ClientRepo;
import com.fleetflow.repository.LivraisonRepo;
import com.fleetflow.repository.VehiculeRepo;
import com.fleetflow.service.LivraisonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LivaisonServiceImpl implements LivraisonService {
    private final LivraisonRepo repo;
    private final LivraisonMapper mapper;
    private final ClientRepo clientRepo;
    private final VehiculeRepo vehiculeRepo;
    private final ChauffeurRepository chauffeurRepo;

    @Transactional
    public LivraisonResponseDTO createLivraison(LivraisonRequestDTO livraisondto){
        Livraison createLivraison=mapper.toEntity(livraisondto);
        if (livraisondto.getClientId() != null) {
            Client client = clientRepo.findById(livraisondto.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client introuvable avec l'ID : " + livraisondto.getClientId()));
            createLivraison.setClient(client);
        }
        if(createLivraison.getStatut()== null){
            createLivraison.setStatut(StatutLivraison.EN_ATTENTE);
        }
        Livraison saveLivraison =repo.save(createLivraison);
        return  mapper.toResponseDto(saveLivraison);
    }


    @Transactional
    public LivraisonResponseDTO modifierStatut(Long id, LivraisonStatutRequestDTO dto) {
        Livraison livraison = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvee"));

        mapper.updateStatutFromDto(dto, livraison);
        Livraison updatedLivraison = repo.save(livraison);
        return mapper.toResponseDto(updatedLivraison);
    }

    @Transactional
    public List<LivraisonResponseDTO> getAllLivraison(){
        List<Livraison> livraisons = repo.findAll();
        //return livraisons.stream().map(livraisonMapper::toDto).toList();
        return mapper.toResponseDtoList(livraisons);
    }

    public List<LivraisonResponseDTO> findByStatut(StatutLivraison statut) {
        return mapper.toResponseDtoList(repo.findByStatut(statut));
    }

    public List<LivraisonResponseDTO> findByClientId(Long clientId) {
        return mapper.toResponseDtoList(repo.findByClientId(clientId));
    }

    public LivraisonResponseDTO assignerChauffeurEtVehicule(Long livraisonId, Long chauffeurId, Long vehiculeId) {
        Livraison livraison = repo.findById(livraisonId).orElseThrow(() -> new RuntimeException("Livraison n'est pas trouvée"));
        Chauffeur chauffeur = chauffeurRepo.findById(chauffeurId).orElseThrow(() -> new RuntimeException("Chauffeur introuvable"));
        Vehicule vehicule = vehiculeRepo.findById(vehiculeId).orElseThrow(() -> new RuntimeException("Véhicule introuvable"));
        livraison.setVehicule(vehicule);
        livraison.setChauffeur(chauffeur);
        livraison.setStatut(StatutLivraison.EN_COURS);
        return mapper.toResponseDto(repo.save(livraison));
    }

    public List<LivraisonResponseDTO> getBewteenTwoDates(LocalDate start , LocalDate end){
        List<Livraison> livraisons = repo.findByDateLivraisonBetween(start,end);
        return mapper.toResponseDtoList(livraisons);
    }

    public List<LivraisonResponseDTO> listerLivraisonsParVilleDestination(String ville) {
        return repo.findLivraisonsParVilleDestination(ville).stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }
}

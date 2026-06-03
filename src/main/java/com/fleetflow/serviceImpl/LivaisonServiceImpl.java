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
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
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
    public Page<LivraisonResponseDTO> getAllLivraison(int page,int size,String sortby){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortby).ascending());
        Page<Livraison> livraisons = repo.findAll(pageable);

        return livraisons.map(mapper::toResponseDto);
    }

    public Page<LivraisonResponseDTO> findByStatut(StatutLivraison statut, int page,int size, String sortby) {
        Pageable pageable= PageRequest .of(page,size,Sort.by(sortby).ascending());
        Page<Livraison> livraisons=repo.findByStatut(statut,pageable);
        return livraisons.map(mapper::toResponseDto);

    }

    public Page<LivraisonResponseDTO> findByClientId(Long clientId,int page, int size, String sortby) {
        Pageable pageable= PageRequest.of(page,size,Sort.by(sortby).ascending());
        Page<Livraison> livraisons= repo.findByClientId(clientId,pageable);
        return livraisons.map(mapper::toResponseDto);
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

    public Page<LivraisonResponseDTO> getBewteenTwoDates(LocalDate start , LocalDate end, int page, int size, String sortby){
        Pageable pageable =PageRequest.of(page,size,Sort.by(sortby).ascending());
        Page<Livraison> livraisons = repo.findByDateLivraisonBetween(start,end,pageable);
        return livraisons.map(mapper::toResponseDto);
    }

    public Page<LivraisonResponseDTO> listerLivraisonsParVilleDestination(String ville,int page,int size, String sortby) {
        Pageable pageable=PageRequest.of(page,size,Sort.by(sortby).ascending());
        Page<Livraison> livraisons= repo.findLivraisonsParVilleDestination(ville,pageable);
        return  livraisons.map(mapper::toResponseDto);
    }

    @Override
    public Page<LivraisonResponseDTO> getMyLivrassion(int page, int size, String sortby) {
     String username= SecurityContextHolder.getContext()
             .getAuthentication().getName();
     Chauffeur chauffeur = chauffeurRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Chauffeur not found"));

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortby).ascending()
        );

        Page<Livraison> livraisons =
                repo.findByChauffeurId(chauffeur.getId(), pageable);

        return livraisons.map(mapper::toResponseDto);
    }
    @Transactional
    public LivraisonResponseDTO updateMyLivraisonStatut(
            Long livraisonId,
            LivraisonStatutRequestDTO dto)
    {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Chauffeur chauffeur = chauffeurRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Chauffeur not found"));

        Livraison livraison = repo.findById(livraisonId)
                .orElseThrow(() -> new EntityNotFoundException("Livraison not found"));

        if (!livraison.getChauffeur().getId().equals(chauffeur.getId())) {
            throw new RuntimeException("You can't modify this delivery");
        }

        livraison.setStatut(dto.getStatut());

        Livraison saved = repo.save(livraison);

        return mapper.toResponseDto(saved);
    }


}

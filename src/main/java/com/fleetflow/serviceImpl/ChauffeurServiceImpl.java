package com.fleetflow.serviceImpl;

import com.fleetflow.dto.ChauffeurRequestDTO;
import com.fleetflow.dto.ChauffeurResponseDTO;
import com.fleetflow.entity.Chauffeur;
import com.fleetflow.mapper.ChauffeurMapper;
import com.fleetflow.repository.ChauffeurRepository;
import com.fleetflow.repository.LivraisonRepo;
import com.fleetflow.service.ChauffeurService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChauffeurServiceImpl implements ChauffeurService {

    private final ChauffeurRepository chauffeurRepository;
    private final ChauffeurMapper chauffeurMapper;
    private final LivraisonRepo livraisonRepo;

    public ChauffeurServiceImpl(ChauffeurRepository chauffeurRepository, ChauffeurMapper chauffeurMapper, LivraisonRepo livraisonRepo) {
        this.chauffeurRepository = chauffeurRepository;
        this.chauffeurMapper = chauffeurMapper;
        this.livraisonRepo = livraisonRepo;
    }


    @Transactional
    public ChauffeurResponseDTO ajouterChauffeur(ChauffeurRequestDTO requestDTO) {
        Chauffeur chauffeur = chauffeurMapper.toEntity(requestDTO);
        chauffeur.setDisponible(true);
        Chauffeur savedChauffeur = chauffeurRepository.save(chauffeur);
        return chauffeurMapper.toDto(savedChauffeur);
    }

    @Transactional
    public ChauffeurResponseDTO modifierChauffeur(Long id, ChauffeurRequestDTO requestDTO) {
        Chauffeur chauffeur = chauffeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chauffeur introuvable avec l'ID : " + id));

        chauffeurMapper.updateEntityFromDto(requestDTO, chauffeur);
        return chauffeurMapper.toDto(chauffeurRepository.save(chauffeur));
    }

    @Transactional
    public void supprimerChauffeur(Long id) {
        if (!chauffeurRepository.existsById(id)) {
            throw new RuntimeException("Chauffeur introuvable avec l'ID : " + id);
        }
        chauffeurRepository.deleteById(id);
    }

    public List<ChauffeurResponseDTO> listerChauffeursDisponibles() {
        return chauffeurRepository.findByDisponibleTrue().stream()
                .map(chauffeurMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<ChauffeurResponseDTO> listerTousLesChauffeurs(int page,int size,String sortby) {
        Pageable pageable=PageRequest.of(page,size,Sort.by(sortby).ascending());
        Page<Chauffeur>chauffeurs=chauffeurRepository.findAll(pageable);
        return chauffeurs.map(chauffeurMapper::toDto);
    }
}



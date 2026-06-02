package com.fleetflow.service;

import com.fleetflow.dto.ChauffeurResponseDTO;
import com.fleetflow.entity.Chauffeur;
import com.fleetflow.mapper.ChauffeurMapper;
import com.fleetflow.repository.ChauffeurRepository;
import com.fleetflow.serviceImpl.ChauffeurServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChauffeurServiceTest {

    @Mock
    private ChauffeurRepository chauffeurRepository;

    @Mock
    private ChauffeurMapper chauffeurMapper;

    @InjectMocks
    private ChauffeurServiceImpl chauffeurService;

    @Test
    void listerChauffeursDisponibles() {

        Chauffeur chauffeur = new Chauffeur();
        chauffeur.setId(1L);

        ChauffeurResponseDTO dto = new ChauffeurResponseDTO();

        Page<Chauffeur> page =
                new PageImpl<>(List.of(chauffeur));

        when(chauffeurRepository.findByDisponibleTrue(any(Pageable.class)))
                .thenReturn(page);

        when(chauffeurMapper.toDto(chauffeur))
                .thenReturn(dto);

        Page<ChauffeurResponseDTO> result =
                chauffeurService.listerChauffeursDisponibles(0, 10, "id");

        assertEquals(1, result.getContent().size());

        verify(chauffeurRepository)
                .findByDisponibleTrue(any(Pageable.class));
    }
}
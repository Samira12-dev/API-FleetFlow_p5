package com.fleetflow.entity;

import com.fleetflow.dto.ClientRequestDTO;
import com.fleetflow.dto.ClientResponseDTO;
import com.fleetflow.mapper.ClientMapper;
import com.fleetflow.repository.ClientRepo;
import com.fleetflow.serviceImpl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientTest {
    @Mock
    private ClientRepo clientRepo;

    @Mock
    private ClientMapper clientMapper;
    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
   void AjouterClient(){
     ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
     clientRequestDTO.setEmail("samira@gmail.com");
     Client client = new Client();
     client.setEmail("samira@gmail.com");
     ClientResponseDTO clientResponseDTO= new ClientResponseDTO();
     clientResponseDTO.setEmail("samira@gmail.com");

     when(clientRepo.existsByEmail(clientRequestDTO.getEmail())).thenReturn(false);
     when(clientMapper.toEntity(clientRequestDTO)).thenReturn(client);
     when(clientRepo.save(client)).thenReturn(client);
     when(clientMapper.toDTO(client)).thenReturn(clientResponseDTO);


     ClientResponseDTO result= clientService.addClient(clientRequestDTO);

     assertNotNull(result);
     assertEquals("samira@gmail.com", result.getEmail());
    }


    @Test
    void IsMailExist(){
        ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
        clientRequestDTO.setEmail("samira@gmail.com");

        when(clientRepo.existsByEmail(clientRequestDTO.getEmail())).thenReturn(true);

        assertThrows(RuntimeException.class,()->{
            clientService.addClient(clientRequestDTO);
        });

    }



}
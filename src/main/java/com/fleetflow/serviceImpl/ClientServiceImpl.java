package com.fleetflow.serviceImpl;

import com.fleetflow.dto.ClientRequestDTO;
import com.fleetflow.dto.ClientResponseDTO;
import com.fleetflow.entity.Client;
import com.fleetflow.mapper.ClientMapper;
import com.fleetflow.repository.ClientRepo;
import com.fleetflow.service.ClientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepo clientRepo;
    private  final ClientMapper clientMapper;

    @Override
    public ClientResponseDTO addClient(ClientRequestDTO client){
        if (clientRepo.existsByEmail(client.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Client addClient = clientMapper.toEntity(client);
        Client saveClient =clientRepo.save(addClient);
        return clientMapper.toDTO(saveClient);
    }
    public ClientResponseDTO updateClient(Long id , ClientRequestDTO client){
        Client findClient =clientRepo.findById(id).orElseThrow(()->new RuntimeException("not Exist"));
        clientMapper.updateClient(client, findClient);
        Client clientUpdated = clientRepo.save(findClient);
        return  clientMapper.toDTO(clientUpdated);
    }
    public  ClientResponseDTO findById(Long id){
        return  clientRepo.findById(id).map(c->clientMapper.toDTO(c)).orElseThrow(()->new EntityNotFoundException("not found"));
    }
    public  void deleteClient( Long id){
        clientRepo.deleteById(id);
    }


    public Page<ClientResponseDTO> getAllClient(int page, int size, String sortby){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortby).ascending());
        Page<Client> clients = clientRepo.findAll(pageable);
        return clients.map(clientMapper::toDTO);

    }

}



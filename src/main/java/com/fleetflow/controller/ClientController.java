package com.fleetflow.controller;

import com.fleetflow.dto.ChauffeurResponseDTO;
import com.fleetflow.dto.ClientRequestDTO;
import com.fleetflow.dto.ClientResponseDTO;
import com.fleetflow.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private  final ClientService clientService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER'")
    @Operation(summary = "admin & maanger can create client")
    @PostMapping
    public ResponseEntity<ClientRequestDTO> createClient(@Valid @RequestBody ClientRequestDTO client){
        ClientResponseDTO create= clientService.addClient(client);
        return  ResponseEntity.ok(client);
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER'")
    @Operation(summary = "admin & maanger can update client")
    @PutMapping("{id}")
    public  ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @Valid @RequestBody ClientRequestDTO client){
        ClientResponseDTO updateClient= clientService.updateClient(id,client);
        return  ResponseEntity.ok(updateClient);
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER'")
    @Operation(summary = "admin & maanger can get client by id")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id){
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER'")
    @Operation(summary = "admin & maanger can delete client by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientByID(@PathVariable Long id){
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER'")
    @Operation(summary = "admin & maanger can get all  clients")
    @GetMapping
    public ResponseEntity<Page<ClientResponseDTO>> getAllCients(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id")String sortby
    ){
        Page<ClientResponseDTO> clients= clientService.getAllClient(page, size, sortby);
        return  ResponseEntity.ok(clients);
    }
}

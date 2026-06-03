package com.fleetflow.dto;

import com.fleetflow.entity.StatutLivraison;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivraisonRequestDTO {
    @NotNull(message = "La date de livraison est obligatoire")
    private LocalDate dateLivraison;
    @NotBlank(message = "L'adresse de départ est obligatoire")
    private String adresseDepart;
    @NotBlank(message = "L'adresse de destination est obligatoire")
    private String adresseDestination;
    @NotNull(message = "L'ID du client est obligatoire")
    private Long clientId;

}

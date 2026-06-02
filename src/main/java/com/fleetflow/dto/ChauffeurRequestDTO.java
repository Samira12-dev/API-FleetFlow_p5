package com.fleetflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChauffeurRequestDTO {

    @NotBlank(message = "Le telephone est obligatoire")
    private String telephone;
    @NotBlank(message = "le type de permis est obligatoire")
    private String permisType;
    private boolean disponible;
}

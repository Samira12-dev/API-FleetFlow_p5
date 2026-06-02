package com.fleetflow.dto;

import lombok.Data;

@Data
public class ChauffeurResponseDTO {

    private String telephone;
    private String permisType;
    private boolean disponible;
}

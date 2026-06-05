package com.fleetflow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@PrimaryKeyJoinColumn(name = "id")
public class Chauffeur extends User {


    @Column(nullable = false, unique = true)
    private String telephone;

    @Column(nullable = false)
    private String permisType;

    @Column(nullable = false)
    private boolean disponible = true;


}
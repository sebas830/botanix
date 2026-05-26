package com.example.botanix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "jardineros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Jardineros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_jardinero;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El documento es obligatorio")
    private String documento;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "El rol es obligatorio")
    private String rol;
}

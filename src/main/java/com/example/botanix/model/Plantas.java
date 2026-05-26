package com.example.botanix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "plantas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Plantas
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_planta;

    @NotBlank(message = "El codigo es obligatorio")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El nombre cientifico es obligatorio")
    private String nombre_cientifico;

    @NotBlank(message = "La especie es obligatoria")
    private String especie;

    @NotNull(message = "La cantidad es obligatoria")
    private Integer cantidad;

    @NotNull(message = "El precio es obligatorio")
    private Double precio;
}

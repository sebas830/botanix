package com.example.botanix.model;

// Codigo que me tiro la IA ya que el que tenia no me funciono
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError
{
    private String field;
    private String message;
}

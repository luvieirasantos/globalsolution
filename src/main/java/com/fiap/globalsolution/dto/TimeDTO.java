package com.fiap.globalsolution.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeDTO {
    private Long id;
    private GerenteDTO gerente;
    @NotBlank
    private String nomeTime;
    private String descricao;
    private String status;
}
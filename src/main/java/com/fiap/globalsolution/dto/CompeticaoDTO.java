package com.fiap.globalsolution.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CompeticaoDTO {
    private Long id;
    private Long idEmpresa;
    @NotBlank
    private String nomeCompeticao;
    private String descricao;
    @NotNull
    private LocalDate dataInicio;
    @NotNull
    private LocalDate dataFim;
    @NotBlank
    private String tipoCompeticao;
    private Integer metaPontos;
    private String status;
}

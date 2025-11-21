package com.fiap.globalsolution.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CursoDTO {
    private Long id;
    private Long idCategoria;
    @NotBlank
    private String nomeCurso;
    private String descricao;
    @NotNull
    private Integer cargaHoraria;
    @NotBlank
    private String nivelDificuldade;
    private Integer pontosConclusao;
    private String status;
}

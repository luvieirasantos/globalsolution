package com.fiap.globalsolution.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MatriculaDTO {
    private Long id;
    private Long idFuncionario;
    private Long idCurso;
    private LocalDate dataMatricula;
    private LocalDate dataInicio;
    private LocalDate dataConclusao;
    private String status;
    private Integer percentualConclusao;
    private Double notaFinal;
}

package com.fiap.globalsolution.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FuncionarioDTO {
    private Long id;
    private Long idEmpresa;
    @NotBlank
    private String nome;
    @NotBlank
    private String cpf;
    @NotBlank @Email
    private String email;
    private String telefone;
    private String cargo;
    private String nivelAtual;
    private Integer pontosAcumulados;
    private String status;
}

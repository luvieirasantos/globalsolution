package com.fiap.globalsolution.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmpresaDTO {
    private Long id;
    @NotBlank
    private String cnpj;
    @NotBlank
    private String razaoSocial;
    private String nomeFantasia;
    @NotBlank
    @Email
    private String emailCorporativo;
    private String telefone;
    private String status;
}

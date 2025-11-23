package com.fiap.globalsolution.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GerenteDTO {
    private Long id;
    private EmpresaDTO empresa;
    @NotBlank
    private String nome;
    @NotBlank
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF inválido")
    private String cpf;
    @NotBlank
    @Email
    private String email;
    private String telefone;
    private String cargo;
    private String status;
}
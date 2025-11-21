package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_empresa")
    @SequenceGenerator(name = "seq_empresa", sequenceName = "seq_empresa", allocationSize = 1)
    @Column(name = "id_empresa")
    private Long id;

    @NotBlank
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", message = "CNPJ inválido")
    @Column(unique = true, nullable = false, length = 18)
    private String cnpj;

    @NotBlank
    @Column(name = "razao_social", nullable = false, length = 200)
    private String razaoSocial;

    @Column(name = "nome_fantasia", length = 200)
    private String nomeFantasia;

    @NotBlank
    @Email
    @Column(name = "email_corporativo", nullable = false, length = 100)
    private String emailCorporativo;

    @Column(length = 20)
    private String telefone;

    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDate dataCadastro;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String status = "ATIVO";

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Gerente> gerentes;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Funcionario> funcionarios;
}

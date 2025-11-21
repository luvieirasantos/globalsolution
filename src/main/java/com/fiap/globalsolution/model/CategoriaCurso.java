package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categoria_curso")
public class CategoriaCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_categoria_curso")
    @SequenceGenerator(name = "seq_categoria_curso", sequenceName = "seq_categoria_curso", allocationSize = 1)
    @Column(name = "id_categoria")
    private Long id;

    @NotBlank
    @Column(name = "nome_categoria", nullable = false, unique = true, length = 100)
    private String nomeCategoria;

    @Column(length = 500)
    private String descricao;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String status = "ATIVO";
}

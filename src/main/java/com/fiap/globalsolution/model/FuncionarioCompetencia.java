package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "funcionario_competencia")
public class FuncionarioCompetencia {

    @EmbeddedId
    private FuncionarioCompetenciaId id;

    @ManyToOne
    @MapsId("idFuncionario")
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;

    @ManyToOne
    @MapsId("idCompetencia")
    @JoinColumn(name = "id_competencia")
    private Competencia competencia;

    @Column(name = "nivel_proficiencia", nullable = false, length = 50)
    private String nivelProficiencia;

    @CreationTimestamp
    @Column(name = "data_aquisicao", nullable = false, updatable = false)
    private LocalDate dataAquisicao;

    @Column(name = "data_validacao")
    private LocalDate dataValidacao;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class FuncionarioCompetenciaId implements Serializable {
        private Long idFuncionario;
        private Long idCompetencia;
    }
}

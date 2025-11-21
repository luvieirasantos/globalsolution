package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "progresso")
public class Progresso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_progresso")
    @SequenceGenerator(name = "seq_progresso", sequenceName = "seq_progresso", allocationSize = 1)
    @Column(name = "id_progresso")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;

    @ManyToOne
    @JoinColumn(name = "id_modulo", nullable = false)
    private Modulo modulo;

    @CreationTimestamp
    @Column(name = "data_inicio", nullable = false, updatable = false)
    private LocalDate dataInicio;

    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;

    @Builder.Default
    @Column(name = "tempo_gasto", columnDefinition = "NUMBER")
    private Integer tempoGasto = 0;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String status = "EM_ANDAMENTO";

    @Min(0)
    @Max(10)
    @Column(columnDefinition = "NUMBER")
    private Double nota;
}

package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "premio_competicao")
public class PremioCompeticao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_premio")
    @SequenceGenerator(name = "seq_premio", sequenceName = "seq_premio", allocationSize = 1)
    @Column(name = "id_premio")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_competicao", nullable = false)
    private Competicao competicao;

    @ManyToOne
    @JoinColumn(name = "id_time")
    private Time time;

    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;

    @NotBlank
    @Column(name = "descricao_premio", nullable = false, length = 500)
    private String descricaoPremio;

    @Column(name = "valor_premio", columnDefinition = "NUMBER")
    private Double valorPremio;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer posicao;

    @CreationTimestamp
    @Column(name = "data_distribuicao", nullable = false, updatable = false)
    private LocalDate dataDistribuicao;
}

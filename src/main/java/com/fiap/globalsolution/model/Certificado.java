package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
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
@Table(name = "certificado")
public class Certificado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_certificado")
    @SequenceGenerator(name = "seq_certificado", sequenceName = "seq_certificado", allocationSize = 1)
    @Column(name = "id_certificado")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;

    @NotBlank
    @Column(name = "codigo_certificado", nullable = false, unique = true, length = 50)
    private String codigoCertificado;

    @CreationTimestamp
    @Column(name = "data_emissao", nullable = false, updatable = false)
    private LocalDate dataEmissao;

    @Column(name = "data_validade")
    private LocalDate dataValidade;

    @NotNull
    @Min(0)
    @Max(10)
    @Column(name = "nota_final", nullable = false, columnDefinition = "NUMBER")
    private Double notaFinal;

    @NotNull
    @Min(1)
    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String status = "ATIVO";
}

package com.fiap.globalsolution.service;

import com.fiap.globalsolution.model.*;
import com.fiap.globalsolution.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProgressoRepository progressoRepository;

    @Autowired
    private CertificadoRepository certificadoRepository;

    @Transactional
    public Matricula matricular(Long funcionarioId, Long cursoId) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        if (matriculaRepository.findByFuncionarioIdAndCursoId(funcionarioId, cursoId).isPresent()) {
            throw new RuntimeException("Funcionário já matriculado neste curso");
        }

        Matricula matricula = Matricula.builder()
                .funcionario(funcionario)
                .curso(curso)
                .status("EM_ANDAMENTO")
                .percentualConclusao(0)
                .build();

        return matriculaRepository.save(matricula);
    }

    @Transactional
    public Progresso atualizarProgresso(Long matriculaId, Long moduloId, boolean concluido) {
        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada"));
        
        // Find module in the course (simplified check)
        boolean moduloExists = matricula.getCurso().getModulos().stream()
                .anyMatch(m -> m.getId().equals(moduloId));
        if (!moduloExists) {
            throw new RuntimeException("Módulo não pertence ao curso da matrícula");
        }
        
        // Find or create progress
        Progresso progresso = progressoRepository.findByMatriculaIdAndModuloId(matriculaId, moduloId)
                .orElse(Progresso.builder()
                        .matricula(matricula)
                        .modulo(matricula.getCurso().getModulos().stream().filter(m -> m.getId().equals(moduloId)).findFirst().get())
                        .status("NAO_INICIADO")
                        .build());

        if (concluido) {
            progresso.setStatus("CONCLUIDO");
            progresso.setDataConclusao(LocalDate.now());
        } else {
            progresso.setStatus("EM_ANDAMENTO");
        }
        
        progresso = progressoRepository.save(progresso);

        atualizarPercentualConclusao(matricula);

        return progresso;
    }

    private void atualizarPercentualConclusao(Matricula matricula) {
        List<Modulo> modulos = matricula.getCurso().getModulos();
        int totalModulos = modulos.size();
        if (totalModulos == 0) return;

        long concluidos = modulos.stream()
                .map(m -> progressoRepository.findByMatriculaIdAndModuloId(matricula.getId(), m.getId()))
                .filter(opt -> opt.isPresent() && "CONCLUIDO".equals(opt.get().getStatus()))
                .count();

        int percentual = (int) ((concluidos * 100) / totalModulos);
        matricula.setPercentualConclusao(percentual);

        if (percentual == 100 && !"CONCLUIDO".equals(matricula.getStatus())) {
            matricula.setStatus("CONCLUIDO");
            matricula.setDataConclusao(LocalDate.now());
            gerarCertificado(matricula);
        }

        matriculaRepository.save(matricula);
    }

    private void gerarCertificado(Matricula matricula) {
        Certificado certificado = Certificado.builder()
                .matricula(matricula)
                .codigoCertificado(UUID.randomUUID().toString())
                .cargaHoraria(matricula.getCurso().getCargaHoraria())
                .notaFinal(matricula.getNotaFinal() != null ? matricula.getNotaFinal() : 10.0) // Default 10 if no grade
                .build();
        certificadoRepository.save(certificado);
    }
}

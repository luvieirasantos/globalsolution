package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.model.Matricula;
import com.fiap.globalsolution.model.Progresso;
import com.fiap.globalsolution.service.MatriculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matriculas")
@Tag(name = "Matricula", description = "Gerenciamento de Matrículas e Progresso")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @PostMapping("/matricular")
    @Operation(summary = "Matricular funcionário em curso")
    public ResponseEntity<Matricula> matricular(@RequestParam Long funcionarioId, @RequestParam Long cursoId) {
        return ResponseEntity.ok(matriculaService.matricular(funcionarioId, cursoId));
    }

    @PostMapping("/{matriculaId}/progresso/{moduloId}")
    @Operation(summary = "Atualizar progresso do módulo")
    public ResponseEntity<Progresso> atualizarProgresso(
            @PathVariable Long matriculaId,
            @PathVariable Long moduloId,
            @RequestParam boolean concluido) {
        return ResponseEntity.ok(matriculaService.atualizarProgresso(matriculaId, moduloId, concluido));
    }
}

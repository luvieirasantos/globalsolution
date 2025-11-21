package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.model.Competicao;
import com.fiap.globalsolution.model.PremioCompeticao;
import com.fiap.globalsolution.service.CompeticaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/competicoes")
@Tag(name = "Competicao", description = "Gerenciamento de Competições")
public class CompeticaoController {

    @Autowired
    private CompeticaoService competicaoService;

    @PostMapping
    @Operation(summary = "Criar nova competição")
    public ResponseEntity<Competicao> create(@RequestBody @Valid Competicao competicao) {
        return ResponseEntity.ok(competicaoService.criarCompeticao(competicao));
    }

    @PostMapping("/{id}/finalizar")
    @Operation(summary = "Finalizar competição")
    public ResponseEntity<Void> finalizar(@PathVariable Long id) {
        competicaoService.finalizarCompeticao(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/premios")
    @Operation(summary = "Adicionar prêmio à competição")
    public ResponseEntity<PremioCompeticao> adicionarPremio(@PathVariable Long id, @RequestBody @Valid PremioCompeticao premio) {
        return ResponseEntity.ok(competicaoService.adicionarPremio(id, premio));
    }
}

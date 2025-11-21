package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.model.Funcionario;
import com.fiap.globalsolution.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionarios")
@Tag(name = "Funcionario", description = "Gerenciamento de Funcionários")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    @Operation(summary = "Listar todos os funcionários")
    public ResponseEntity<Page<Funcionario>> findAll(Pageable pageable) {
        return ResponseEntity.ok(funcionarioService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar funcionário por ID")
    public ResponseEntity<Funcionario> findById(@PathVariable Long id) {
        return funcionarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar novo funcionário")
    public ResponseEntity<Funcionario> create(@RequestBody @Valid Funcionario funcionario) {
        return ResponseEntity.ok(funcionarioService.save(funcionario));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar funcionário")
    public ResponseEntity<Funcionario> update(@PathVariable Long id, @RequestBody @Valid Funcionario funcionario) {
        return funcionarioService.findById(id)
                .map(existing -> {
                    funcionario.setId(id);
                    return ResponseEntity.ok(funcionarioService.save(funcionario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar funcionário")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (funcionarioService.findById(id).isPresent()) {
            funcionarioService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.model.Empresa;
import com.fiap.globalsolution.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresas")
@Tag(name = "Empresa", description = "Gerenciamento de Empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    @Operation(summary = "Listar todas as empresas")
    public ResponseEntity<Page<Empresa>> findAll(Pageable pageable) {
        return ResponseEntity.ok(empresaService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar empresa por ID")
    public ResponseEntity<Empresa> findById(@PathVariable Long id) {
        return empresaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar nova empresa")
    public ResponseEntity<Empresa> create(@RequestBody @Valid Empresa empresa) {
        return ResponseEntity.ok(empresaService.save(empresa));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar empresa")
    public ResponseEntity<Empresa> update(@PathVariable Long id, @RequestBody @Valid Empresa empresa) {
        return empresaService.findById(id)
                .map(existing -> {
                    empresa.setId(id);
                    return ResponseEntity.ok(empresaService.save(empresa));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar empresa")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (empresaService.findById(id).isPresent()) {
            empresaService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

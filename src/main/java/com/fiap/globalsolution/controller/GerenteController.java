package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.dto.GerenteDTO;
import com.fiap.globalsolution.model.Gerente;
import com.fiap.globalsolution.service.GerenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/gerentes")
@Tag(name = "Gerente", description = "Gerenciamento de Gerentes")
public class GerenteController {

    @Autowired
    private GerenteService gerenteService;

    @GetMapping
    @Operation(summary = "Listar todos os gerentes")
    public ResponseEntity<Page<Gerente>> findAll(
            @Parameter(description = "Número da página (começa em 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "20")
            @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Ordenação", example = "nome,asc")
            @RequestParam(defaultValue = "") String sort) {

        org.springframework.data.domain.Pageable pageable =
            org.springframework.data.domain.PageRequest.of(page, size,
                sort.isEmpty() ? org.springframework.data.domain.Sort.unsorted() :
                parseSortParameter(sort));

        return ResponseEntity.ok(gerenteService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar gerente por ID")
    public ResponseEntity<Gerente> findById(@PathVariable Long id) {
        return gerenteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar novo gerente")
    public ResponseEntity<Gerente> create(@RequestBody @Valid GerenteDTO gerenteDTO) {
        Gerente gerente = convertToEntity(gerenteDTO);
        return ResponseEntity.ok(gerenteService.save(gerente));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar gerente")
    public ResponseEntity<Gerente> update(@PathVariable Long id, @RequestBody @Valid GerenteDTO gerenteDTO) {
        return gerenteService.findById(id)
                .map(existing -> {
                    Gerente gerente = convertToEntity(gerenteDTO);
                    gerente.setId(id);
                    return ResponseEntity.ok(gerenteService.save(gerente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar gerente")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (gerenteService.findById(id).isPresent()) {
            gerenteService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private Gerente convertToEntity(GerenteDTO dto) {
        Gerente gerente = new Gerente();
        gerente.setId(dto.getId());
        gerente.setNome(dto.getNome());
        gerente.setCpf(dto.getCpf());
        gerente.setEmail(dto.getEmail());
        gerente.setTelefone(dto.getTelefone());
        gerente.setCargo(dto.getCargo());
        gerente.setStatus(dto.getStatus() != null ? dto.getStatus() : "ATIVO");

        if (dto.getEmpresa() != null) {
            gerente.setEmpresa(convertEmpresaToEntity(dto.getEmpresa()));
        }

        return gerente;
    }

    private com.fiap.globalsolution.model.Empresa convertEmpresaToEntity(com.fiap.globalsolution.dto.EmpresaDTO dto) {
        com.fiap.globalsolution.model.Empresa empresa = new com.fiap.globalsolution.model.Empresa();
        empresa.setId(dto.getId());
        empresa.setCnpj(dto.getCnpj());
        empresa.setRazaoSocial(dto.getRazaoSocial());
        empresa.setNomeFantasia(dto.getNomeFantasia());
        empresa.setEmailCorporativo(dto.getEmailCorporativo());
        empresa.setTelefone(dto.getTelefone());
        empresa.setStatus(dto.getStatus());
        return empresa;
    }

    private org.springframework.data.domain.Sort parseSortParameter(String sortParam) {
        if (sortParam == null || sortParam.trim().isEmpty()) {
            return org.springframework.data.domain.Sort.unsorted();
        }

        String[] parts = sortParam.split(",");
        if (parts.length != 2) {
            return org.springframework.data.domain.Sort.unsorted();
        }

        String field = parts[0].trim();
        String direction = parts[1].trim().toLowerCase();

        if (field.isEmpty() || (!direction.equals("asc") && !direction.equals("desc"))) {
            return org.springframework.data.domain.Sort.unsorted();
        }

        if (direction.equals("asc")) {
            return org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.ASC, field);
        } else {
            return org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, field);
        }
    }
}
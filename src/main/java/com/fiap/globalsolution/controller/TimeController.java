package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.dto.TimeDTO;
import com.fiap.globalsolution.model.Time;
import com.fiap.globalsolution.service.TimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/times")
@Tag(name = "Time", description = "Gerenciamento de Times")
public class TimeController {

    @Autowired
    private TimeService timeService;

    @GetMapping
    @Operation(summary = "Listar todos os times")
    public ResponseEntity<Page<Time>> findAll(
            @Parameter(description = "Número da página (começa em 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "20")
            @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Ordenação", example = "nomeTime,asc")
            @RequestParam(defaultValue = "") String sort) {

        org.springframework.data.domain.Pageable pageable =
            org.springframework.data.domain.PageRequest.of(page, size,
                sort.isEmpty() ? org.springframework.data.domain.Sort.unsorted() :
                parseSortParameter(sort));

        return ResponseEntity.ok(timeService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar time por ID")
    public ResponseEntity<Time> findById(@PathVariable Long id) {
        return timeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar novo time")
    public ResponseEntity<Time> create(@RequestBody @Valid TimeDTO timeDTO) {
        Time time = convertToEntity(timeDTO);
        return ResponseEntity.ok(timeService.save(time));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar time")
    public ResponseEntity<Time> update(@PathVariable Long id, @RequestBody @Valid TimeDTO timeDTO) {
        return timeService.findById(id)
                .map(existing -> {
                    Time time = convertToEntity(timeDTO);
                    time.setId(id);
                    return ResponseEntity.ok(timeService.save(time));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar time")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (timeService.findById(id).isPresent()) {
            timeService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private Time convertToEntity(TimeDTO dto) {
        Time time = new Time();
        time.setId(dto.getId());
        time.setNomeTime(dto.getNomeTime());
        time.setDescricao(dto.getDescricao());
        time.setStatus(dto.getStatus() != null ? dto.getStatus() : "ATIVO");

        if (dto.getGerente() != null) {
            time.setGerente(convertGerenteToEntity(dto.getGerente()));
        }

        return time;
    }

    private com.fiap.globalsolution.model.Gerente convertGerenteToEntity(com.fiap.globalsolution.dto.GerenteDTO dto) {
        com.fiap.globalsolution.model.Gerente gerente = new com.fiap.globalsolution.model.Gerente();
        gerente.setId(dto.getId());
        gerente.setNome(dto.getNome());
        gerente.setCpf(dto.getCpf());
        gerente.setEmail(dto.getEmail());
        gerente.setTelefone(dto.getTelefone());
        gerente.setCargo(dto.getCargo());
        gerente.setStatus(dto.getStatus());
        return gerente;
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
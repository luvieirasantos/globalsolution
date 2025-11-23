package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.dto.FuncionarioDTO;
import com.fiap.globalsolution.model.Funcionario;
import com.fiap.globalsolution.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    public ResponseEntity<Page<Funcionario>> findAll(
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
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        required = true,
        description = "Dados do funcionário a ser criado",
        content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = FuncionarioDTO.class),
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Exemplo de Funcionário",
                value = """
                    {
                      "nome": "João Silva",
                      "cpf": "123.456.789-00",
                      "email": "joao.silva@empresa.com",
                      "telefone": "(11) 99999-9999",
                      "cargo": "Desenvolvedor",
                      "empresa": {
                        "id": 1
                      }
                    }
                    """
            )
        )
    )
    public ResponseEntity<Funcionario> create(@RequestBody @Valid FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = convertToEntity(funcionarioDTO);
        return ResponseEntity.ok(funcionarioService.save(funcionario));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar funcionário")
    public ResponseEntity<Funcionario> update(@PathVariable Long id, @RequestBody @Valid FuncionarioDTO funcionarioDTO) {
        return funcionarioService.findById(id)
                .map(existing -> {
                    Funcionario funcionario = convertToEntity(funcionarioDTO);
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

    private Funcionario convertToEntity(FuncionarioDTO dto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(dto.getId());
        funcionario.setNome(dto.getNome());
        funcionario.setCpf(dto.getCpf());
        funcionario.setEmail(dto.getEmail());
        funcionario.setTelefone(dto.getTelefone());
        funcionario.setCargo(dto.getCargo());
        funcionario.setNivelAtual(dto.getNivelAtual() != null ? dto.getNivelAtual() : "INICIANTE");
        funcionario.setPontosAcumulados(dto.getPontosAcumulados() != null ? dto.getPontosAcumulados() : 0);
        funcionario.setStatus(dto.getStatus() != null ? dto.getStatus() : "ATIVO");

        if (dto.getEmpresa() != null) {
            funcionario.setEmpresa(convertEmpresaToEntity(dto.getEmpresa()));
        }

        return funcionario;
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

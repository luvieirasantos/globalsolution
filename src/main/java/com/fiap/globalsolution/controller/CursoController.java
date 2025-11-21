package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.exception.ResourceNotFoundException;
import com.fiap.globalsolution.model.Curso;
import com.fiap.globalsolution.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/cursos")
@Tag(name = "Curso", description = "Gerenciamento de Cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private PagedResourcesAssembler<Curso> pagedResourcesAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os cursos")
    @Cacheable("cursos")
    public ResponseEntity<PagedModel<EntityModel<Curso>>> findAll(Pageable pageable) {
        Page<Curso> cursos = cursoService.findAll(pageable);
        PagedModel<EntityModel<Curso>> pagedModel = pagedResourcesAssembler.toModel(cursos, curso ->
            EntityModel.of(curso,
                linkTo(methodOn(CursoController.class).findById(curso.getId())).withSelfRel(),
                linkTo(methodOn(CursoController.class).findAll(pageable)).withRel("cursos")
            )
        );
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar curso por ID")
    @Cacheable(value = "cursos", key = "#id")
    public ResponseEntity<EntityModel<Curso>> findById(@PathVariable Long id) {
        Curso curso = cursoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", id));

        EntityModel<Curso> model = EntityModel.of(curso,
            linkTo(methodOn(CursoController.class).findById(id)).withSelfRel(),
            linkTo(methodOn(CursoController.class).findAll(Pageable.unpaged())).withRel("cursos")
        );

        return ResponseEntity.ok(model);
    }

    @PostMapping
    @Operation(summary = "Criar novo curso")
    @CacheEvict(value = "cursos", allEntries = true)
    public ResponseEntity<EntityModel<Curso>> create(@RequestBody @Valid Curso curso) {
        Curso saved = cursoService.save(curso);

        EntityModel<Curso> model = EntityModel.of(saved,
            linkTo(methodOn(CursoController.class).findById(saved.getId())).withSelfRel(),
            linkTo(methodOn(CursoController.class).findAll(Pageable.unpaged())).withRel("cursos")
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar curso")
    @CacheEvict(value = "cursos", allEntries = true)
    public ResponseEntity<EntityModel<Curso>> update(@PathVariable Long id, @RequestBody @Valid Curso curso) {
        if (!cursoService.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Curso", id);
        }

        curso.setId(id);
        Curso updated = cursoService.save(curso);

        EntityModel<Curso> model = EntityModel.of(updated,
            linkTo(methodOn(CursoController.class).findById(id)).withSelfRel(),
            linkTo(methodOn(CursoController.class).findAll(Pageable.unpaged())).withRel("cursos")
        );

        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar curso")
    @CacheEvict(value = "cursos", allEntries = true)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!cursoService.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Curso", id);
        }
        cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

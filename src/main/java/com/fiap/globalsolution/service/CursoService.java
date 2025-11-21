package com.fiap.globalsolution.service;

import com.fiap.globalsolution.model.Curso;
import com.fiap.globalsolution.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Cacheable("cursos")
    @Transactional(readOnly = true)
    public Page<Curso> findAll(Pageable pageable) {
        return cursoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Curso> findById(Long id) {
        return cursoRepository.findById(id);
    }

    @Transactional
    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Transactional
    public void delete(Long id) {
        cursoRepository.deleteById(id);
    }
}

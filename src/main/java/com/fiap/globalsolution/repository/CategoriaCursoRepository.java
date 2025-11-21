package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.CategoriaCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaCursoRepository extends JpaRepository<CategoriaCurso, Long> {
}

package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    Optional<Matricula> findByFuncionarioIdAndCursoId(Long funcionarioId, Long cursoId);
}

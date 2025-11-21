package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.Progresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProgressoRepository extends JpaRepository<Progresso, Long> {
    Optional<Progresso> findByMatriculaIdAndModuloId(Long matriculaId, Long moduloId);
}

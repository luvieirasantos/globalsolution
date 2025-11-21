package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.FuncionarioCompetencia;
import com.fiap.globalsolution.model.FuncionarioCompetencia.FuncionarioCompetenciaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioCompetenciaRepository extends JpaRepository<FuncionarioCompetencia, FuncionarioCompetenciaId> {
}

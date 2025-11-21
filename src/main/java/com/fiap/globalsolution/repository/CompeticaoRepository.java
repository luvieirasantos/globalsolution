package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.Competicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompeticaoRepository extends JpaRepository<Competicao, Long> {
}

package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.PremioCompeticao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremioCompeticaoRepository extends JpaRepository<PremioCompeticao, Long> {
}

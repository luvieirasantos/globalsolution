package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.Gerente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GerenteRepository extends JpaRepository<Gerente, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}

package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}

package com.fiap.globalsolution.service;

import com.fiap.globalsolution.exception.DuplicateResourceException;
import com.fiap.globalsolution.model.Funcionario;
import com.fiap.globalsolution.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Transactional(readOnly = true)
    public Page<Funcionario> findAll(Pageable pageable) {
        return funcionarioRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Funcionario> findById(Long id) {
        return funcionarioRepository.findById(id);
    }

    @Transactional
    public Funcionario save(Funcionario funcionario) {
        validateDuplicate(funcionario);
        return funcionarioRepository.save(funcionario);
    }

    private void validateDuplicate(Funcionario funcionario) {
        // Verificar CPF duplicado
        if (funcionario.getCpf() != null) {
            Optional<Funcionario> existingByCpf = funcionarioRepository.findByCpf(funcionario.getCpf());
            if (existingByCpf.isPresent() &&
                (funcionario.getId() == null || !existingByCpf.get().getId().equals(funcionario.getId()))) {
                throw new DuplicateResourceException("Funcionário", "CPF", funcionario.getCpf());
            }
        }

        // Verificar Email duplicado
        if (funcionario.getEmail() != null) {
            Optional<Funcionario> existingByEmail = funcionarioRepository.findByEmail(funcionario.getEmail());
            if (existingByEmail.isPresent() &&
                (funcionario.getId() == null || !existingByEmail.get().getId().equals(funcionario.getId()))) {
                throw new DuplicateResourceException("Funcionário", "Email", funcionario.getEmail());
            }
        }
    }

    @Transactional
    public void delete(Long id) {
        funcionarioRepository.deleteById(id);
    }
}

package com.fiap.globalsolution.service;

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
        return funcionarioRepository.save(funcionario);
    }

    @Transactional
    public void delete(Long id) {
        funcionarioRepository.deleteById(id);
    }
}

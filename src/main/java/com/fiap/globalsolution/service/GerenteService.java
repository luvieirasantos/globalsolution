package com.fiap.globalsolution.service;

import com.fiap.globalsolution.exception.DuplicateResourceException;
import com.fiap.globalsolution.model.Gerente;
import com.fiap.globalsolution.repository.GerenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GerenteService {

    @Autowired
    private GerenteRepository gerenteRepository;

    public Page<Gerente> findAll(Pageable pageable) {
        return gerenteRepository.findAll(pageable);
    }

    public Optional<Gerente> findById(Long id) {
        return gerenteRepository.findById(id);
    }

    @Transactional
    public Gerente save(Gerente gerente) {
        validateDuplicate(gerente);
        return gerenteRepository.save(gerente);
    }

    private void validateDuplicate(Gerente gerente) {
        // Check for duplicate CPF
        gerenteRepository.findByCpf(gerente.getCpf()).ifPresent(existingGerente -> {
            if (!existingGerente.getId().equals(gerente.getId())) {
                throw new DuplicateResourceException("Gerente", "CPF", gerente.getCpf());
            }
        });

        // Check for duplicate Email
        gerenteRepository.findByEmail(gerente.getEmail()).ifPresent(existingGerente -> {
            if (!existingGerente.getId().equals(gerente.getId())) {
                throw new DuplicateResourceException("Gerente", "email", gerente.getEmail());
            }
        });
    }

    public void delete(Long id) {
        gerenteRepository.deleteById(id);
    }
}
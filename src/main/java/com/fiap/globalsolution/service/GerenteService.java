package com.fiap.globalsolution.service;

import com.fiap.globalsolution.model.Gerente;
import com.fiap.globalsolution.repository.GerenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Gerente save(Gerente gerente) {
        return gerenteRepository.save(gerente);
    }

    public void delete(Long id) {
        gerenteRepository.deleteById(id);
    }
}
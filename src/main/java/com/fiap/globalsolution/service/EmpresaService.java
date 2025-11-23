package com.fiap.globalsolution.service;

import com.fiap.globalsolution.exception.DuplicateResourceException;
import com.fiap.globalsolution.model.Empresa;
import com.fiap.globalsolution.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Transactional(readOnly = true)
    public Page<Empresa> findAll(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Empresa> findById(Long id) {
        return empresaRepository.findById(id);
    }

    @Transactional
    public Empresa save(Empresa empresa) {
        validateDuplicate(empresa);
        return empresaRepository.save(empresa);
    }

    private void validateDuplicate(Empresa empresa) {
        // Verificar CNPJ duplicado
        if (empresa.getCnpj() != null) {
            Optional<Empresa> existingByCnpj = empresaRepository.findByCnpj(empresa.getCnpj());
            if (existingByCnpj.isPresent() &&
                (empresa.getId() == null || !existingByCnpj.get().getId().equals(empresa.getId()))) {
                throw new DuplicateResourceException("Empresa", "CNPJ", empresa.getCnpj());
            }
        }

        // Verificar Email Corporativo duplicado
        if (empresa.getEmailCorporativo() != null) {
            Optional<Empresa> existingByEmail = empresaRepository.findByEmailCorporativo(empresa.getEmailCorporativo());
            if (existingByEmail.isPresent() &&
                (empresa.getId() == null || !existingByEmail.get().getId().equals(empresa.getId()))) {
                throw new DuplicateResourceException("Empresa", "Email Corporativo", empresa.getEmailCorporativo());
            }
        }
    }

    @Transactional
    public void delete(Long id) {
        empresaRepository.deleteById(id);
    }
}

package com.fiap.globalsolution.service;

import com.fiap.globalsolution.model.*;
import com.fiap.globalsolution.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CompeticaoService {

    @Autowired
    private CompeticaoRepository competicaoRepository;

    @Autowired
    private PremioCompeticaoRepository premioRepository;

    @Autowired
    private TimeRepository timeRepository;

    @Transactional
    public Competicao criarCompeticao(Competicao competicao) {
        if (competicao.getDataFim().isBefore(competicao.getDataInicio())) {
            throw new IllegalArgumentException("Data fim deve ser após data início");
        }
        return competicaoRepository.save(competicao);
    }

    @Transactional
    public void finalizarCompeticao(Long competicaoId) {
        Competicao competicao = competicaoRepository.findById(competicaoId)
                .orElseThrow(() -> new RuntimeException("Competição não encontrada"));

        competicao.setStatus("FINALIZADA");
        competicaoRepository.save(competicao);

        // Logic to calculate winners and distribute prizes would go here
        // For simplicity, we are just changing the status
    }

    @Transactional
    public PremioCompeticao adicionarPremio(Long competicaoId, PremioCompeticao premio) {
        Competicao competicao = competicaoRepository.findById(competicaoId)
                .orElseThrow(() -> new RuntimeException("Competição não encontrada"));
        
        premio.setCompeticao(competicao);
        return premioRepository.save(premio);
    }
}

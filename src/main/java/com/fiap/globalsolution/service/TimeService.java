package com.fiap.globalsolution.service;

import com.fiap.globalsolution.exception.DuplicateResourceException;
import com.fiap.globalsolution.model.Time;
import com.fiap.globalsolution.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TimeService {

    @Autowired
    private TimeRepository timeRepository;

    public Page<Time> findAll(Pageable pageable) {
        return timeRepository.findAll(pageable);
    }

    public Optional<Time> findById(Long id) {
        return timeRepository.findById(id);
    }

    @Transactional
    public Time save(Time time) {
        validateDuplicate(time);
        return timeRepository.save(time);
    }

    private void validateDuplicate(Time time) {
        // Check for duplicate team name for the same manager
        timeRepository.findByNomeTime(time.getNomeTime()).ifPresent(existingTime -> {
            if (!existingTime.getId().equals(time.getId()) &&
                existingTime.getGerente().getId().equals(time.getGerente().getId())) {
                throw new DuplicateResourceException("Time", "nomeTime", time.getNomeTime());
            }
        });
    }

    public void delete(Long id) {
        timeRepository.deleteById(id);
    }
}
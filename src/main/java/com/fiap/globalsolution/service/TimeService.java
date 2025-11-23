package com.fiap.globalsolution.service;

import com.fiap.globalsolution.model.Time;
import com.fiap.globalsolution.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Time save(Time time) {
        return timeRepository.save(time);
    }

    public void delete(Long id) {
        timeRepository.deleteById(id);
    }
}
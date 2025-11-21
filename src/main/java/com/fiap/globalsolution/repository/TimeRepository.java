package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {
}

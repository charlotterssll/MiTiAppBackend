package com.example.mitiappbackend.domain;

import com.example.mitiappbackend.domain.entities.MiTi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiTiRepository extends JpaRepository<MiTi, Long> {
}

package com.example.mitiappbackend.domain;

import com.example.mitiappbackend.domain.entities.MiTi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiTiService {

    @Autowired
    private MiTiRepository miTiRepository;

    public List<MiTi> getMiTis() {
        return miTiRepository.findAll();
    }
}

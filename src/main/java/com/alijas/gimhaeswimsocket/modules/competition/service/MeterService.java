package com.alijas.gimhaeswimsocket.modules.competition.service;

import com.alijas.gimhaeswimsocket.modules.competition.entity.Meter;
import com.alijas.gimhaeswimsocket.modules.competition.repository.MeterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeterService {

    private final MeterRepository meterRepository;

    public MeterService(MeterRepository meterRepository) {
        this.meterRepository = meterRepository;
    }

    public List<Meter> findAll() {
        return meterRepository.findAll();
    }
}

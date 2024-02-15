package com.alijas.gimhaeswimsocket.modules.record.repository;

import com.alijas.gimhaeswimsocket.modules.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}

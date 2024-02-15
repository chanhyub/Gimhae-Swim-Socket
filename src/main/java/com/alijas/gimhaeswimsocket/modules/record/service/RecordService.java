package com.alijas.gimhaeswimsocket.modules.record.service;

import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswimsocket.modules.lane.entity.Lane;
import com.alijas.gimhaeswimsocket.modules.lane.repository.LaneRepository;
import com.alijas.gimhaeswimsocket.modules.record.entity.Record;
import com.alijas.gimhaeswimsocket.modules.record.repository.RecordRepository;
import com.alijas.gimhaeswimsocket.modules.record.request.RecordSaveRequest;
import com.alijas.gimhaeswimsocket.modules.section.repository.SectionRepository;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import com.alijas.gimhaeswimsocket.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RecordService {

    private final RecordRepository recordRepository;

    private final LaneRepository laneRepository;

    private final SectionRepository sectionRepository;

    private final UserRepository userRepository;

    public RecordService(RecordRepository recordRepository, LaneRepository laneRepository, SectionRepository sectionRepository, UserRepository userRepository) {
        this.recordRepository = recordRepository;
        this.laneRepository = laneRepository;
        this.sectionRepository = sectionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveRecord(List<RecordSaveRequest> recordSaveRequestList) {
        recordSaveRequestList.forEach(recordSaveRequest -> {
            recordRepository.save(
                    new Record(
                            null,
                            laneRepository.findById(recordSaveRequest.getLaneId()).get(),
                            userRepository.findById(recordSaveRequest.getUserId()).get(),
                            recordSaveRequest.getRecord()
                    )
            );
        });
    }
}

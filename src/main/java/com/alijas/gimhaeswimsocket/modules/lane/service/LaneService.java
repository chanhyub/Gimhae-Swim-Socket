package com.alijas.gimhaeswimsocket.modules.lane.service;

import com.alijas.gimhaeswimsocket.modules.common.exception.CustomRestException;
import com.alijas.gimhaeswimsocket.modules.lane.entity.Lane;
import com.alijas.gimhaeswimsocket.modules.lane.repository.LaneRepository;
import com.alijas.gimhaeswimsocket.modules.lane.response.LaneResponse;
import com.alijas.gimhaeswimsocket.modules.section.entity.Section;
import com.alijas.gimhaeswimsocket.modules.section.repository.SectionRepository;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaneService {

    private final LaneRepository laneRepository;

    private final SectionRepository sectionRepository;

    public LaneService(LaneRepository laneRepository, SectionRepository sectionRepository) {
        this.laneRepository = laneRepository;
        this.sectionRepository = sectionRepository;
    }

    public List<Lane> findBySection(Section section) {
        return laneRepository.findBySection(section);
    }

    public List<LaneResponse> getLaneBySectionId(Section section, User user) {

        List<Lane> laneList = laneRepository.findBySection(section);
        if (laneList.isEmpty()) {
            throw new CustomRestException("해당 조에 등록된 레인이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        boolean isReferee = false;

        for (Lane lane : laneList) {
            if (lane.getReferee().getUser().getId().equals(user.getId())) {
                isReferee = true;
            }
        }
        if (!isReferee) throw new CustomRestException("해당 조에 해당하는 심판이 아닙니다.", HttpStatus.BAD_REQUEST);

        return laneList
                .stream()
                .map(Lane::toLaneResponse)
                .toList();
    }

    public Optional<Lane> getLane(Long id) {
        return laneRepository.findById(id);
    }
}

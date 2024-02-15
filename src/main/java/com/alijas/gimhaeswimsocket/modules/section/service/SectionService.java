package com.alijas.gimhaeswimsocket.modules.section.service;

import com.alijas.gimhaeswimsocket.modules.common.exception.CustomRestException;
import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswimsocket.modules.competition.repository.CompetitionEventRepository;
import com.alijas.gimhaeswimsocket.modules.lane.entity.Lane;
import com.alijas.gimhaeswimsocket.modules.lane.repository.LaneRepository;
import com.alijas.gimhaeswimsocket.modules.referee.repository.RefereeRepository;
import com.alijas.gimhaeswimsocket.modules.section.entity.Section;
import com.alijas.gimhaeswimsocket.modules.section.repository.SectionRepository;
import com.alijas.gimhaeswimsocket.modules.section.response.SectionResponse;
import com.alijas.gimhaeswimsocket.modules.team.repository.TeamMemberRepository;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import com.alijas.gimhaeswimsocket.modules.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    private final CompetitionEventRepository competitionEventRepository;

    private final LaneRepository laneRepository;

    private final UserRepository userRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final RefereeRepository refereeRepository;

    public SectionService(SectionRepository sectionRepository, CompetitionEventRepository competitionEventRepository, LaneRepository laneRepository, UserRepository userRepository, TeamMemberRepository teamMemberRepository, RefereeRepository refereeRepository) {
        this.sectionRepository = sectionRepository;
        this.competitionEventRepository = competitionEventRepository;
        this.laneRepository = laneRepository;
        this.userRepository = userRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.refereeRepository = refereeRepository;
    }

    public Optional<Section> getSection(Long sectionId) {
        return sectionRepository.findById(sectionId);
    }

    public List<SectionResponse> getSectionByCompetitionEvent(CompetitionEvent competitionEvent) {
        List<Section> sectionList = sectionRepository.findByCompetitionEvent(competitionEvent);
        if (sectionList.isEmpty()) {
            throw new CustomRestException("해당 대회에 등록된 조가 없습니다.", HttpStatus.BAD_REQUEST);
        }

        return sectionList
                .stream()
                .map(Section::toSectionResponse)
                .toList();
    }
}

package com.alijas.gimhaeswimsocket.modules.record.service;

import com.alijas.gimhaeswimsocket.modules.common.exception.CustomRestException;
import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswimsocket.modules.lane.entity.Lane;
import com.alijas.gimhaeswimsocket.modules.point.entity.IndividualPoint;
import com.alijas.gimhaeswimsocket.modules.point.entity.TeamPoint;
import com.alijas.gimhaeswimsocket.modules.point.repository.IndividualPointRepository;
import com.alijas.gimhaeswimsocket.modules.point.repository.TeamPointRepository;
import com.alijas.gimhaeswimsocket.modules.record.entity.Record;
import com.alijas.gimhaeswimsocket.modules.record.repository.RecordRepository;
import com.alijas.gimhaeswimsocket.modules.record.request.RecordSaveRequest;
import com.alijas.gimhaeswimsocket.modules.section.entity.Section;
import com.alijas.gimhaeswimsocket.modules.section.repository.SectionRepository;
import com.alijas.gimhaeswimsocket.modules.team.entity.TeamMember;
import com.alijas.gimhaeswimsocket.modules.team.repository.TeamMemberRepository;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RecordService {

    private final RecordRepository recordRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final TeamPointRepository teamPointRepository;

    private final IndividualPointRepository individualPointRepository;

    private final SectionRepository sectionRepository;

    public RecordService(RecordRepository recordRepository, TeamMemberRepository teamMemberRepository, TeamPointRepository teamPointRepository, IndividualPointRepository individualPointRepository, SectionRepository sectionRepository) {
        this.recordRepository = recordRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.teamPointRepository = teamPointRepository;
        this.individualPointRepository = individualPointRepository;
        this.sectionRepository = sectionRepository;
    }

    @Transactional
    public void saveRecord(RecordSaveRequest recordSaveRequest, Lane lane) {
            recordRepository.save(
                    new Record(
                            null,
                            lane,
                            recordSaveRequest.getRecord()
                    )
            );
    }

    @Transactional
    public void saveTeamTotalRecord(List<Lane> laneList, CompetitionEvent competitionEvent) {
        // lane의 record 기록 빠른 순으로 정렬 후 for문 돌리면 됨.
        for (int i = 0; i < laneList.size(); i++) {
            if (i == 0) {
                Section section = laneList.get(i).getSection();
                section.setIsComplete(true);
                sectionRepository.save(section);
            }

            Record record = recordRepository.findByLane(laneList.get(i)).orElseThrow(() -> new CustomRestException("해당 레인에 대한 기록이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
            TeamMember teamMember = teamMemberRepository.findByUser(laneList.get(i).getUser()).orElseThrow(() -> new CustomRestException("해당 유저에 대한 팀원 정보가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

            TeamPoint teamPoint = teamPointRepository.findByCompetitionEventAndTeam(competitionEvent, teamMember.getTeam());
            if (teamPoint != null) {
//                TeamPoint teamPoint = optionalTeamPoint.get();
                switch (i) {
                    case 0:
                        teamPointRepository.save(
                                new TeamPoint(
                                        teamPoint.getId(),
                                        teamPoint.getCompetitionEvent(),
                                        teamPoint.getTeam(),
                                        teamPoint.getPoint() + competitionEvent.getFirstScore()
                                )
                        );
                        break;
                    case 1:
                        teamPointRepository.save(
                                new TeamPoint(
                                        teamPoint.getId(),
                                        teamPoint.getCompetitionEvent(),
                                        teamPoint.getTeam(),
                                        teamPoint.getPoint() + competitionEvent.getSecondScore()
                                )
                        );
                        break;
                    case 2:
                        teamPointRepository.save(
                                new TeamPoint(
                                        teamPoint.getId(),
                                        teamPoint.getCompetitionEvent(),
                                        teamPoint.getTeam(),
                                        teamPoint.getPoint() + competitionEvent.getThirdScore()
                                )
                        );
                        break;
                    case 3:
                        teamPointRepository.save(
                                new TeamPoint(
                                        teamPoint.getId(),
                                        teamPoint.getCompetitionEvent(),
                                        teamPoint.getTeam(),
                                        teamPoint.getPoint() + competitionEvent.getFourthScore()
                                )
                        );
                        break;
                    case 4:
                        teamPointRepository.save(
                                new TeamPoint(
                                        teamPoint.getId(),
                                        teamPoint.getCompetitionEvent(),
                                        teamPoint.getTeam(),
                                        teamPoint.getPoint() + competitionEvent.getFifthScore()
                                )
                        );
                        break;
                    case 5:
                        teamPointRepository.save(
                                new TeamPoint(
                                        teamPoint.getId(),
                                        teamPoint.getCompetitionEvent(),
                                        teamPoint.getTeam(),
                                        teamPoint.getPoint() + competitionEvent.getSixthScore()
                                )
                        );
                        break;
                    case 6:
                        teamPointRepository.save(
                                new TeamPoint(
                                        teamPoint.getId(),
                                        teamPoint.getCompetitionEvent(),
                                        teamPoint.getTeam(),
                                        teamPoint.getPoint() + competitionEvent.getSeventhScore()
                                )
                        );
                        break;
                    case 7:
                        teamPointRepository.save(
                                new TeamPoint(
                                        teamPoint.getId(),
                                        teamPoint.getCompetitionEvent(),
                                        teamPoint.getTeam(),
                                        teamPoint.getPoint() + competitionEvent.getEighthScore()
                                )
                        );
                        break;
                }
            } else {
                switch (i) {
                    case 0:
                        teamPointRepository.save(
                                new TeamPoint(
                                        null,
                                        competitionEvent,
                                        teamMember.getTeam(),
                                        competitionEvent.getFirstScore()
                                )
                        );
                        break;
                    case 1:
                        teamPointRepository.save(
                                new TeamPoint(
                                        null,
                                        competitionEvent,
                                        teamMember.getTeam(),
                                        competitionEvent.getSecondScore()
                                )
                        );
                        break;
                    case 2:
                        teamPointRepository.save(
                                new TeamPoint(
                                        null,
                                        competitionEvent,
                                        teamMember.getTeam(),
                                        competitionEvent.getThirdScore()
                                )
                        );
                        break;
                    case 3:
                        teamPointRepository.save(
                                new TeamPoint(
                                        null,
                                        competitionEvent,
                                        teamMember.getTeam(),
                                        competitionEvent.getFourthScore()
                                )
                        );
                        break;
                    case 4:
                        teamPointRepository.save(
                                new TeamPoint(
                                        null,
                                        competitionEvent,
                                        teamMember.getTeam(),
                                        competitionEvent.getFifthScore()
                                )
                        );
                        break;
                    case 5:
                        teamPointRepository.save(
                                new TeamPoint(
                                        null,
                                        competitionEvent,
                                        teamMember.getTeam(),
                                        competitionEvent.getSixthScore()
                                )
                        );
                        break;
                    case 6:
                        teamPointRepository.save(
                                new TeamPoint(
                                        null,
                                        competitionEvent,
                                        teamMember.getTeam(),
                                        competitionEvent.getSeventhScore()
                                )
                        );
                        break;
                    case 7:
                        teamPointRepository.save(
                                new TeamPoint(
                                        null,
                                        competitionEvent,
                                        teamMember.getTeam(),
                                        competitionEvent.getEighthScore()
                                )
                        );
                    break;
                }
            }
        }
    }

    @Transactional
    public void saveIndividualTotalRecord(List<Lane> laneList, CompetitionEvent competitionEvent) {
        for (int i = 0; i < laneList.size(); i++) {
            if (i == 0) {
                Section section = laneList.get(i).getSection();
                section.setIsComplete(true);
                sectionRepository.save(section);
            }

            Record record = recordRepository.findByLane(laneList.get(i)).orElseThrow(() -> new CustomRestException("해당 레인에 대한 기록이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
            User user = laneList.get(i).getUser();

            Optional<IndividualPoint> optionalIndividualPoint = individualPointRepository.findByCompetitionEventAndUser(competitionEvent, user);
            if (optionalIndividualPoint.isPresent()) {
                IndividualPoint individualPoint = optionalIndividualPoint.get();
                switch (i) {
                    case 0:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        individualPoint.getId(),
                                        individualPoint.getCompetitionEvent(),
                                        individualPoint.getUser(),
                                        individualPoint.getPoint() + competitionEvent.getFirstScore()
                                )
                        );
                        break;
                    case 1:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        individualPoint.getId(),
                                        individualPoint.getCompetitionEvent(),
                                        individualPoint.getUser(),
                                        individualPoint.getPoint() + competitionEvent.getSecondScore()
                                )
                        );
                        break;
                    case 2:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        individualPoint.getId(),
                                        individualPoint.getCompetitionEvent(),
                                        individualPoint.getUser(),
                                        individualPoint.getPoint() + competitionEvent.getThirdScore()
                                )
                        );
                        break;
                    case 3:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        individualPoint.getId(),
                                        individualPoint.getCompetitionEvent(),
                                        individualPoint.getUser(),
                                        individualPoint.getPoint() + competitionEvent.getFourthScore()
                                )
                        );
                        break;
                    case 4:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        individualPoint.getId(),
                                        individualPoint.getCompetitionEvent(),
                                        individualPoint.getUser(),
                                        individualPoint.getPoint() + competitionEvent.getFifthScore()
                                )
                        );
                        break;
                    case 5:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        individualPoint.getId(),
                                        individualPoint.getCompetitionEvent(),
                                        individualPoint.getUser(),
                                        individualPoint.getPoint() + competitionEvent.getSixthScore()
                                )
                        );
                        break;
                    case 6:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        individualPoint.getId(),
                                        individualPoint.getCompetitionEvent(),
                                        individualPoint.getUser(),
                                        individualPoint.getPoint() + competitionEvent.getSeventhScore()
                                )
                        );
                        break;
                    case 7:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        individualPoint.getId(),
                                        individualPoint.getCompetitionEvent(),
                                        individualPoint.getUser(),
                                        individualPoint.getPoint() + competitionEvent.getEighthScore()
                                )
                        );
                    break;
                }
            } else {
                switch (i) {
                    case 0:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        null,
                                        competitionEvent,
                                        user,
                                        competitionEvent.getFirstScore()
                                )
                        );
                        break;
                    case 1:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        null,
                                        competitionEvent,
                                        user,
                                        competitionEvent.getSecondScore()
                                )
                        );
                        break;
                    case 2:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        null,
                                        competitionEvent,
                                        user,
                                        competitionEvent.getThirdScore()
                                )
                        );
                        break;
                    case 3:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        null,
                                        competitionEvent,
                                        user,
                                        competitionEvent.getFourthScore()
                                )
                        );
                        break;
                    case 4:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        null,
                                        competitionEvent,
                                        user,
                                        competitionEvent.getFifthScore()
                                )
                        );
                        break;
                    case 5:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        null,
                                        competitionEvent,
                                        user,
                                        competitionEvent.getSixthScore()
                                )
                        );
                        break;
                    case 6:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        null,
                                        competitionEvent,
                                        user,
                                        competitionEvent.getSeventhScore()
                                )
                        );
                        break;
                    case 7:
                        individualPointRepository.save(
                                new IndividualPoint(
                                        null,
                                        competitionEvent,
                                        user,
                                        competitionEvent.getEighthScore()
                                )
                        );
                        break;
                }
            }
        }
    }
}

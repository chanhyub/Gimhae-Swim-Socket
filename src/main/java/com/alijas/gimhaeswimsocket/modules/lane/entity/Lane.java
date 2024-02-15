package com.alijas.gimhaeswimsocket.modules.lane.entity;

import com.alijas.gimhaeswimsocket.modules.common.jpa.BaseTime;
import com.alijas.gimhaeswimsocket.modules.lane.response.LaneResponse;
import com.alijas.gimhaeswimsocket.modules.referee.entity.Referee;
import com.alijas.gimhaeswimsocket.modules.section.entity.Section;
import com.alijas.gimhaeswimsocket.modules.team.entity.TeamMember;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "LANES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lane extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("레인 차수")
    private Integer laneNumber;

    @Comment("레인 참가 경기인")
    @ManyToOne
    private User user;

    @Comment("레인 참가 팀 경기인")
    @ManyToOne
    private TeamMember teamMember;

    @Comment("레인 담당 심판")
    @ManyToOne
    private Referee referee;

    @Comment("소속 조")
    @ManyToOne
    private Section section;

    public LaneResponse toLaneResponse() {
        return new LaneResponse(
                this.id,
                this.laneNumber,
                this.user == null ? this.teamMember.getUser().toUserLaneDTO() : this.user.toUserLaneDTO(),
                this.referee.toRefereeLaneDTO()
        );
    }
}

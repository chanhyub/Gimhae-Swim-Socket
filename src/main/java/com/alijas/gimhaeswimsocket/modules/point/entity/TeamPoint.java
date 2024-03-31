package com.alijas.gimhaeswimsocket.modules.point.entity;


import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswimsocket.modules.team.entity.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "TEAM_POINTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamPoint {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("대회 종목")
    @ManyToOne
    private CompetitionEvent competitionEvent;

    @Comment("팀")
    @ManyToOne
    private Team team;

    @Comment("점수")
    private Integer point;
}

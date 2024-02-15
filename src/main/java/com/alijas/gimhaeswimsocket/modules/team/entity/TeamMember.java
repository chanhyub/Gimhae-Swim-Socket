package com.alijas.gimhaeswimsocket.modules.team.entity;

import com.alijas.gimhaeswimsocket.modules.common.jpa.BaseTime;
import com.alijas.gimhaeswimsocket.modules.team.enums.TeamMemberPosition;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "TEAM_MEMBERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("팀")
    @ManyToOne
    private Team team;

    @Comment("팀 선수")
    @ManyToOne
    private User user;

    @Comment("팀 선수의 포지션")
    @Enumerated(EnumType.STRING)
    private TeamMemberPosition position;

}

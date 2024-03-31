package com.alijas.gimhaeswimsocket.modules.point.entity;

import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "INDIVIDUAL_POINTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndividualPoint {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("대회 종목")
    @ManyToOne
    private CompetitionEvent competitionEvent;

    @Comment("경기인")
    @ManyToOne
    private User user;

    @Comment("점수")
    private Integer point;
}

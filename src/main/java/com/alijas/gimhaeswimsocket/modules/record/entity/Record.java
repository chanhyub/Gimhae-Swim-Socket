package com.alijas.gimhaeswimsocket.modules.record.entity;

import com.alijas.gimhaeswimsocket.modules.common.jpa.BaseTime;
import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswimsocket.modules.lane.entity.Lane;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "RECORDS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Record extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("기록 대회 종목")
    @ManyToOne
    private Lane lane;

    @Comment("기록 대회 참가자")
    @ManyToOne
    private User user;

    @Comment("기록")
    private String record;
}

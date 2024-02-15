package com.alijas.gimhaeswimsocket.modules.competition.entity;

import com.alijas.gimhaeswim.module.competition.enums.status.EventStatus;
import com.alijas.gimhaeswimsocket.modules.common.jpa.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

/*
    * 경기 종목 마스터 테이블
 */

@Entity
@Table(name = "EVENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("종목 이름")
    @Column(unique = true)
    private String eventName;

    @Comment("종목 상태")
    @Enumerated(EnumType.STRING)
    private EventStatus status;

}

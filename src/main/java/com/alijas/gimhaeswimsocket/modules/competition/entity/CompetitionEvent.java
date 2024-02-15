package com.alijas.gimhaeswimsocket.modules.competition.entity;

import com.alijas.gimhaeswimsocket.modules.common.jpa.BaseTime;
import com.alijas.gimhaeswimsocket.modules.competition.dto.CompetitionEventResponse;
import com.alijas.gimhaeswimsocket.modules.competition.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

/*
*    대회 종목 테이블
*/

@Entity
@Table(name = "COMPETITION_EVENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionEvent extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("종목 유형")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Comment("종목 부별") /* ex) 13세이하(여자), 13세이하(남자) 등등 */
    @ManyToOne
    private Department department;

    @Comment("종목 종류")
    @ManyToOne
    private Event event;

    @Comment("종목 미터")
    @ManyToOne
    private Meter meter;

    @Comment("종목 정원")
    private Integer eventCapacity;

    @Comment("1등 스코어")
    private Integer firstScore;

    @Comment("2등 스코어")
    private Integer secondScore;

    @Comment("3등 스코어")
    private Integer thirdScore;

    @Comment("4등 스코어")
    private Integer fourthScore;

    @Comment("5등 스코어")
    private Integer fifthScore;

    @Comment("6등 스코어")
    private Integer sixthScore;

    @Comment("7등 스코어")
    private Integer seventhScore;

    @Comment("8등 스코어")
    private Integer eighthScore;

    @Comment("대회")
    @ManyToOne
    private Competition competition;

    public CompetitionEventResponse toCompetitionEventResponse() {
        return new CompetitionEventResponse(
                this.id,
                this.convertEventType(this.eventType),
                this.department.getDepartmentGender().name(),
                this.department.getDepartmentName(),
                this.event.getEventName(),
                this.meter.getMeter()
        );
    }

    public String convertEventType(EventType eventType) {
        return switch (eventType) {
            case INDIVIDUAL_MALE -> "남자 개인";
            case INDIVIDUAL_FEMALE -> "여자 개인";
            case ORGANIZATION_FEMALE -> "여자 단체";
            case ORGANIZATION_MALE -> "남자 단체";
            case ORGANIZATION_MIXED -> "혼성 단체";
            default -> "기타";
        };
    }

}
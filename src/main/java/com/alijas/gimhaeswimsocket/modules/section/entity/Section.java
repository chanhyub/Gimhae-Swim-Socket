package com.alijas.gimhaeswimsocket.modules.section.entity;

import com.alijas.gimhaeswimsocket.modules.common.jpa.BaseTime;
import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswimsocket.modules.section.response.SectionResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "SECTIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Section extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("조 차수")
    private Integer sectionNumber;

    @Comment("대회 종목")
    @ManyToOne
    private CompetitionEvent competitionEvent;

    @Comment("조 기록 측정 완료 여부")
    @Column(columnDefinition = "boolean default false")
    private Boolean isComplete;

    public SectionResponse toSectionResponse() {
        return new SectionResponse(
                this.id,
                this.sectionNumber
        );
    }
}

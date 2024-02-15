package com.alijas.gimhaeswimsocket.modules.competition.entity;

import com.alijas.gimhaeswimsocket.modules.common.jpa.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

/*
    * 미터 마스터 테이블
 */

@Entity
@Table(name = "METERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Meter extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("미터")
    private String meter;
}

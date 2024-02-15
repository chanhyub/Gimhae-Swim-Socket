package com.alijas.gimhaeswimsocket.modules.team.entity;


import com.alijas.gimhaeswimsocket.modules.common.jpa.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "TEAMS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("팀 이름")
    @Column(unique = true)
    private String teamName;
}

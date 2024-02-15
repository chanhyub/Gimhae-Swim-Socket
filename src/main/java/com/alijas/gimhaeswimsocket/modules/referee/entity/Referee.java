package com.alijas.gimhaeswimsocket.modules.referee.entity;

import com.alijas.gimhaeswimsocket.modules.common.jpa.BaseTime;
import com.alijas.gimhaeswimsocket.modules.referee.dto.RefereeLaneDTO;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import com.alijas.gimhaeswimsocket.modules.user.enums.ApplyStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "REFEREES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Referee extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("심판 정보")
    @ManyToOne
    private User user;

    @Comment("심판 상태")
    @Enumerated(EnumType.STRING)
    private ApplyStatus status;

    public RefereeLaneDTO toRefereeLaneDTO() {
        return new RefereeLaneDTO(
                this.id,
                this.user.getFullName()
        );
    }

}

package com.alijas.gimhaeswimsocket.modules.user.entity;


import com.alijas.gimhaeswimsocket.common.enums.Gender;
import com.alijas.gimhaeswimsocket.modules.common.jpa.BaseTime;
import com.alijas.gimhaeswimsocket.modules.user.dto.UserLaneDTO;
import com.alijas.gimhaeswimsocket.modules.user.enums.ApplyStatus;
import com.alijas.gimhaeswimsocket.modules.user.enums.RoleType;
import com.alijas.gimhaeswimsocket.modules.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;


@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("사용자 아이디")
    @Column(unique = true)
    private String username;

    @Comment("사용자 이름")
    @Column(name = "full_name")
    private String fullName;

    @Comment("사용자 비밀번호")
    @Column(name = "password")
    private String password;

    @Comment("사용자 생년월일")
    @Column(name = "birthday")
    private String birthday;

    @Comment("사용자 전화번호")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Comment("사용자 이메일")
    @Column(name = "email")
    private String email;

    @Comment("사용자 성별")
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Comment("이용약관 동의 여부")
    @Column(name = "is_agree")
    private boolean isAgree;

    @Comment("사용자 상태")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @Comment("사용자 승인 상태")
    @Column(name = "apply_status")
    @Enumerated(EnumType.STRING)
    private ApplyStatus applyStatus;

    @Comment("사용자 권한")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleType role;

    public UserLaneDTO toUserLaneDTO() {
        return new UserLaneDTO(
                this.id,
                this.fullName
        );
    }
}

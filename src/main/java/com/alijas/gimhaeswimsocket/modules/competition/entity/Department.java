package com.alijas.gimhaeswimsocket.modules.competition.entity;

import com.alijas.gimhaeswim.module.competition.enums.status.DepartmentStatus;
import com.alijas.gimhaeswim.module.competition.enums.MoreOrLess;
import com.alijas.gimhaeswimsocket.common.enums.Gender;
import com.alijas.gimhaeswimsocket.modules.common.jpa.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;


@Entity
@Table(name = "DEPARTMENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department extends BaseTime {

    @Id
    @Comment("고유 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("부 나이")
    private Integer departmentAge;

    @Comment("부 성별")
    @Enumerated(EnumType.STRING)
    private Gender departmentGender;

    @Comment("부 이상 또는 이하 값")
    @Enumerated(EnumType.STRING)
    private MoreOrLess moreOrLess;

    @Comment("부 상태")
    @Enumerated(EnumType.STRING)
    private DepartmentStatus status;

    @Comment("부 추가 정보")
    private String departmentInfo; /* (학생부) ... */

    public String getDepartmentName() {
        return this.departmentAge
                + "세 "
                + (this.moreOrLess.name().equals("MORE") ? "이상" : "이하")
                + (this.departmentInfo != null ? "(" +this.departmentInfo + ")" : "");
    }
}

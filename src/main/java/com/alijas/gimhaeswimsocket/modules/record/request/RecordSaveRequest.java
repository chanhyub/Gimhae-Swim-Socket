package com.alijas.gimhaeswimsocket.modules.record.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordSaveRequest {

    @NotNull(message = "레인 고유 번호는 필수입니다.")
    private Long laneId;

    @NotNull(message = "경기인 고유 번호는 필수입니다.")
    private Long userId;

    @NotBlank(message = "기록은 필수입니다.")
    private String record;

}

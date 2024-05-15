package com.alijas.gimhaeswimsocket.modules.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResUserLoginDTO {
    private String fullName;
    private String accessToken;
    private String refreshToken;
}

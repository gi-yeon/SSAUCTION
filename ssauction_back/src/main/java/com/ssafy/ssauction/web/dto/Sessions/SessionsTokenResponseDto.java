package com.ssafy.ssauction.web.dto.Sessions;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SessionsTokenResponseDto {
    String token;
    String cause;
    String error;
    String exception;
}

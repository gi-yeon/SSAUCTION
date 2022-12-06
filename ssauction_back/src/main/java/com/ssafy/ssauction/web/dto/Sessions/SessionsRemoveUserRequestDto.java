package com.ssafy.ssauction.web.dto.Sessions;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SessionsRemoveUserRequestDto {
    String sessionName;
    String cameraToken;
//    String screenToken;
    String userNo;
    String reason;
}

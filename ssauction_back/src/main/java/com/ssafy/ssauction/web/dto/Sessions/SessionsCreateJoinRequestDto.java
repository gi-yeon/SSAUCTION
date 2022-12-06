package com.ssafy.ssauction.web.dto.Sessions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SessionsCreateJoinRequestDto {
    String sessionName;
    String loggedUser;
    String userNo;
    Boolean isHost;
}

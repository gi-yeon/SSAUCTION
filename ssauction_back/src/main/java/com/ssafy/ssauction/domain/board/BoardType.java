package com.ssafy.ssauction.domain.board;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BoardType {
    @JsonProperty("0")
    BOARD_FREE,
    @JsonProperty("1")
    BOARD_NOTICE
}

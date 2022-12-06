package com.ssafy.ssauction.web.dto.comments;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentsDtoRes {

    private long commentNo;
    private String commentContent;
    private String commentRegDate;

    private Long boardNo;

    private Long userNo;
    private String userNickname;
}

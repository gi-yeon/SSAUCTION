package com.ssafy.ssauction.web.dto.board;

import com.ssafy.ssauction.domain.board.BoardType;
import com.ssafy.ssauction.web.dto.comments.CommentsDtoRes;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardDtoRes {

    private long boardNo;
    private String boardTitle;
    private String boardContent;
    private BoardType boardType;
    private int boardReply;
    private String boardRegDate;

    private List<CommentsDtoRes> comments;

    private long userNo;
    private String userNickname;
}

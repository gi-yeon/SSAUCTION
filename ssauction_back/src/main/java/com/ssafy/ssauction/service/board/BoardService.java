package com.ssafy.ssauction.service.board;

import com.ssafy.ssauction.domain.board.Board;
import com.ssafy.ssauction.domain.board.BoardType;
import com.ssafy.ssauction.web.dto.board.BoardDtoReq;
import com.ssafy.ssauction.web.dto.board.BoardDtoRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

public interface BoardService {

    Page<Board> boardList(PageRequest pageRequest, BoardType boardType, String searchType, String search);

    BoardDtoRes boardDetail(long boardNo);

    Board boardRegister(BoardDtoReq boardDtoReq);

    boolean boardModify(BoardDtoReq boardDtoReq);

    boolean boardDelete(long boardNo, long userNo);
}

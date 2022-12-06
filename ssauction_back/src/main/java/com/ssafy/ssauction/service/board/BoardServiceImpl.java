package com.ssafy.ssauction.service.board;


import com.ssafy.ssauction.domain.board.Board;
import com.ssafy.ssauction.domain.board.BoardRepository;
import com.ssafy.ssauction.domain.board.BoardType;
import com.ssafy.ssauction.domain.comments.Comments;
import com.ssafy.ssauction.domain.comments.CommentsRepository;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.web.dto.board.BoardDtoReq;
import com.ssafy.ssauction.web.dto.board.BoardDtoRes;
import com.ssafy.ssauction.web.dto.comments.CommentsDtoRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {


    private final BoardRepository boardRepository;

    private final CommentsRepository commentsRepository;

    private final UsersRepository usersRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, CommentsRepository commentsRepository, UsersRepository usersRepository) {
        this.boardRepository = boardRepository;
        this.commentsRepository = commentsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Page<Board> boardList(PageRequest pageRequest, BoardType boardType, String searchType, String search) {
        if(searchType == null)
            return boardRepository.findByBoardType(pageRequest, boardType);
        else if("title".equals(searchType))
            return boardRepository.findByBoardTitleContainingAndBoardType(pageRequest, search, boardType);
        else
            return boardRepository.findByUserNicknameAndBoardType(pageRequest, search, boardType);
    }

    @Override
    public BoardDtoRes boardDetail(long boardNo) {
        BoardDtoRes boardDtoRes = new BoardDtoRes();
        Board board = boardRepository.findById(boardNo).orElse(null);
        System.out.println(board.toString());
        if(board != null) {
            boardDtoRes.setBoardNo(board.getBoardNo());
            boardDtoRes.setBoardTitle(board.getBoardTitle());
            boardDtoRes.setBoardContent(board.getBoardContent());
            boardDtoRes.setBoardType(board.getBoardType());
            boardDtoRes.setBoardRegDate(board.getBoardRegDate());
            boardDtoRes.setBoardReply(board.getBoardReply());
            boardDtoRes.setUserNo(board.getUserNo());
            boardDtoRes.setUserNickname(board.getUserNickname());

            List<CommentsDtoRes> commentsRes = new ArrayList<>();
            List<Comments> comments = board.getComments();
            System.out.println(comments.toString());
            for (Comments comment : comments) {
                CommentsDtoRes commentRes = new CommentsDtoRes();

                commentRes.setCommentNo(comment.getCommentNo());
                commentRes.setCommentContent(comment.getCommentContent());
                commentRes.setCommentRegDate(comment.getCommentRegDate());
                commentRes.setBoardNo(comment.getBoard().getBoardNo());
                commentRes.setUserNo(comment.getUserNo());

                Users user = usersRepository.findById(commentRes.getUserNo()).orElse(null);
                if(user != null) {
                    commentRes.setUserNickname(user.getUserNickname());
                    commentsRes.add(commentRes);
                }
            }

            boardDtoRes.setComments(commentsRes);
        }
        return boardDtoRes;
    }

    @Override
    public Board boardRegister(BoardDtoReq boardDtoReq) {
        Board board = new Board();
        board.setBoardTitle(boardDtoReq.getBoardTitle());
        board.setBoardContent(boardDtoReq.getBoardContent());
        board.setBoardType(boardDtoReq.getBoardType());
        board.setBoardReply(0);

        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        board.setBoardRegDate(sDate.format(new Date()));

        board.setUserNo(boardDtoReq.getUserNo());
        board.setUserNickname(boardDtoReq.getUserNickname());

        return boardRepository.save(board);
    }

    @Override
    public boolean boardModify(BoardDtoReq boardDtoReq) {
        Board board = boardRepository.findById(boardDtoReq.getBoardNo()).orElse(null);

        if(board == null)
            return false;
        else {
            Users user = usersRepository.findById(boardDtoReq.getUserNo()).orElse(null);
            if(user == null)
                return false;
            board.setBoardTitle(boardDtoReq.getBoardTitle() + "(수정됨)");
            board.setBoardContent(boardDtoReq.getBoardContent());
            boardRepository.save(board);
            return true;
        }
    }

    @Override
    public boolean boardDelete(long boardNo, long userNo) {
        Board board = boardRepository.findById(boardNo).orElse(null);

        if(board == null)
            return false;
        else {
            if(board.getUserNo() != userNo)
                return false;
            boardRepository.delete(board);
            return true;
        }
    }
}

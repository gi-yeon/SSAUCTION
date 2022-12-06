package com.ssafy.ssauction.service.comments;

import com.ssafy.ssauction.domain.board.Board;
import com.ssafy.ssauction.domain.board.BoardRepository;
import com.ssafy.ssauction.domain.comments.Comments;
import com.ssafy.ssauction.domain.comments.CommentsRepository;
import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.domain.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class CommentsServiceImpl implements CommentsService {

    CommentsRepository commentsRepository;
    BoardRepository boardRepository;
    UsersRepository usersRepository;

    @Autowired
    public CommentsServiceImpl(CommentsRepository commentsRepository, BoardRepository boardRepository, UsersRepository usersRepository) {
        this.commentsRepository = commentsRepository;
        this.boardRepository = boardRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Comments commentRegister(Map<String, Object> map) {
        System.out.println(map.get("boardNo").getClass().getSimpleName());
        System.out.println(map.get("userNo").getClass().getSimpleName());
        String boardNo = String.valueOf(map.get("boardNo"));
        String userNo = String.valueOf(map.get("userNo"));
        System.out.println(Long.valueOf(boardNo));
        System.out.println(Long.valueOf(userNo));
        Board board = boardRepository.findById(Long.valueOf(boardNo)).orElse(null);
        Users user = usersRepository.findById(Long.valueOf(userNo)).orElse(null);
        System.out.println(board==null?true:false);
        System.out.println(user==null?true:false);
        if(board == null || user == null)
            return null;
        System.out.println((String)map.get("commentContent"));
        Comments comment = new Comments();
        comment.setCommentContent((String)map.get("commentContent"));
        comment.setBoard(board);
        comment.setUserNo(Long.valueOf(userNo));

        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        comment.setCommentRegDate(sDate.format(new Date()));
        board.getComments().add(comment);
        return commentsRepository.save(comment);
    }

    @Override
    public boolean commentDelete(long commentsNo, long userNo) {
        Comments comment = commentsRepository.findById(commentsNo).orElse(null);
        if(comment == null || comment.getUserNo() != userNo)
            return false;
        else {
            commentsRepository.delete(comment);
            return true;
        }

    }
}

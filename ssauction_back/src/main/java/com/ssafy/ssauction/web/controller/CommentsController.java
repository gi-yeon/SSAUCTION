package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.domain.comments.Comments;
import com.ssafy.ssauction.service.comments.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/comment")
@CrossOrigin("*")
public class CommentsController {

    private final CommentsService commentsService;

    @Autowired
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> commentRegister(@RequestBody Map<String, Object> map) {

        System.out.println(map.toString());
        System.out.println(map);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        Comments comment = commentsService.commentRegister(map);
        System.out.println(comment);
        if(comment == null) {
            resultMap.put("message", "올바르지 않은 게시글 또는 사용자 입니다.");
            status = HttpStatus.NOT_FOUND;
        } else {
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> commentDelete(@RequestParam long commentsNo, @RequestParam long userNo) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        if(commentsService.commentDelete(commentsNo, userNo)) {
            status = HttpStatus.OK;
        } else {
            resultMap.put("message", "댓글 삭제에 실패하였습니다.");
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(resultMap, status);
    }

}

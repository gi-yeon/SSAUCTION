package com.ssafy.ssauction.service.comments;

import com.ssafy.ssauction.domain.comments.Comments;

import java.util.List;
import java.util.Map;

public interface CommentsService {

    Comments commentRegister(Map<String, Object> map);

    boolean commentDelete(long commentsNo, long userNo);
}

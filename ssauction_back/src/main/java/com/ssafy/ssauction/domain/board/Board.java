package com.ssafy.ssauction.domain.board;

import com.ssafy.ssauction.domain.comments.Comments;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long boardNo;

    @Column(name = "board_title", nullable = false)
    private String boardTitle;
    @Column(name = "board_content", nullable = false)
    private String boardContent;
    @Column(name = "board_regdate", nullable = false)
    private String boardRegDate;
    @Enumerated
    @Column(name = "board_type", nullable = false)
    private BoardType boardType;
    @Column(name = "board_reply", nullable = false)
    private int boardReply;

    @Column(name = "user_no", nullable = false)
    private Long userNo;
    @Column(name = "user_nickname", nullable = false)
    private String userNickname;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
    @JoinColumn(name="board")
    private List<Comments> comments = new ArrayList<>();

    @Override
    public String toString() {
        return "Board{" +
                "boardNo=" + boardNo +
                ", boardTitle='" + boardTitle + '\'' +
                ", boardContent='" + boardContent + '\'' +
                ", boardRegDate='" + boardRegDate + '\'' +
                ", boardType=" + boardType +
                ", boardReply=" + boardReply +
                ", userNo=" + userNo +
                ", userNickname='" + userNickname + '\'' +
                ", comments=" + comments +
                '}';
    }
}

package com.ssafy.ssauction.domain.comments;

import com.ssafy.ssauction.domain.board.Board;
import com.ssafy.ssauction.domain.items.Items;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private long commentNo;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;

    @Column(name = "comment_regdate", nullable = false)
    private String commentRegDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_no")
    @Setter
    private Board board;

    @Column(name = "user_no", nullable = false)
    private Long userNo;
}

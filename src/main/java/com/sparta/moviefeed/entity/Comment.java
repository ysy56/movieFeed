package com.sparta.moviefeed.entity;

import com.sparta.moviefeed.dto.requestdto.BoardRequestDto;
import com.sparta.moviefeed.dto.requestdto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@Getter
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public Comment(CommentRequestDto requestDto, Board board, User user) {
        this.content = requestDto.getContent();
        this.user = user;
        this.board = board;
    }

}

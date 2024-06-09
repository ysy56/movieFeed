package com.sparta.moviefeed.entity;

import com.sparta.moviefeed.enumeration.LikeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "board_id"})
})
@Entity(name = "likes")
@Getter
@NoArgsConstructor
public class Like extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LikeType likeType;

    public Like(User user, Board board, LikeType likeType) {
        this.user = user;
        this.board = board;
        this.likeType = likeType;
    }
}

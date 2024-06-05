package com.sparta.moviefeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Table(name = "board")
@NoArgsConstructor @Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "content", length = 500)
    private String content;

    @Column(name = "likes_count")
    private Long likesCount;
}

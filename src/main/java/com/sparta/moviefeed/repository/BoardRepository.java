package com.sparta.moviefeed.repository;

import com.sparta.moviefeed.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}

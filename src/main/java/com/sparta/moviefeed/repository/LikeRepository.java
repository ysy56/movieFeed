package com.sparta.moviefeed.repository;

import com.sparta.moviefeed.entity.Board;
import com.sparta.moviefeed.entity.Like;
import com.sparta.moviefeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Long countByBoardId(Long boardId);

    Optional<Like> findByUserAndBoard(User user, Board board);
}

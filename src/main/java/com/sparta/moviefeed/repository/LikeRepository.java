package com.sparta.moviefeed.repository;

import com.sparta.moviefeed.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}

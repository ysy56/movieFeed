package com.sparta.moviefeed.service;

import com.sparta.moviefeed.dto.requestdto.BoardRequestDto;
import com.sparta.moviefeed.dto.responsedto.BoardResponseDto;
import com.sparta.moviefeed.entity.Board;
import com.sparta.moviefeed.entity.User;
import com.sparta.moviefeed.exception.DataNotFoundException;
import com.sparta.moviefeed.repository.BoardRepository;
import com.sparta.moviefeed.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public BoardResponseDto boardPosting(BoardRequestDto requestDto) {
        User user = userRepository.findById(1L).orElseThrow(
                () -> new DataNotFoundException("조회된 유저 정보가 없습니다.")
        );
        Board board = boardRepository.save(new Board(requestDto, user));
        return new BoardResponseDto(board, user.getUserName());
    }
}

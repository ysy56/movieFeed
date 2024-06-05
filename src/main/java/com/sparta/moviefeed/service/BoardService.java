package com.sparta.moviefeed.service;

import com.sparta.moviefeed.dto.requestdto.BoardRequestDto;
import com.sparta.moviefeed.dto.responsedto.BoardResponseDto;
import com.sparta.moviefeed.entity.Board;
import com.sparta.moviefeed.entity.User;
import com.sparta.moviefeed.exception.DataNotFoundException;
import com.sparta.moviefeed.repository.BoardRepository;
import com.sparta.moviefeed.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public BoardResponseDto postingBoard(BoardRequestDto requestDto) {
        Long userId = 1L;
        User user = findUser(userId);
        Board board = boardRepository.save(new Board(requestDto, user));
        return new BoardResponseDto(board, user.getUserName());
    }

    public BoardResponseDto selectBoard(Long boardId) {
        Long userId = 1L;
        User user = findUser(userId);
        Board board = findBoard(boardId);
        return new BoardResponseDto(board, user.getUserName());
    }

    public List<BoardResponseDto> selectAllBoard() {
        return boardRepository.findAll()
                .stream().map(BoardResponseDto::new).toList();
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("조회된 유저 정보가 없습니다.")
        );
    }

    private Board findBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new DataNotFoundException("조회된 게시글의 정보가 없습니다.")
        );
    }


}

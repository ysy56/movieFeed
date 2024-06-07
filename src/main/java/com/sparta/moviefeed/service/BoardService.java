package com.sparta.moviefeed.service;

import com.sparta.moviefeed.dto.requestdto.BoardRequestDto;
import com.sparta.moviefeed.dto.responsedto.BoardResponseDto;
import com.sparta.moviefeed.entity.Board;
import com.sparta.moviefeed.entity.Like;
import com.sparta.moviefeed.entity.User;
import com.sparta.moviefeed.enumeration.LikeType;
import com.sparta.moviefeed.exception.DataNotFoundException;
import com.sparta.moviefeed.exception.ForbiddenException;
import com.sparta.moviefeed.exception.ViolatedLikeException;
import com.sparta.moviefeed.repository.BoardRepository;
import com.sparta.moviefeed.repository.LikeRepository;
import com.sparta.moviefeed.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository, LikeRepository likeRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    public BoardResponseDto postingBoard(BoardRequestDto requestDto, User user) {
        User userInfo = findUser(user.getId());
        Board board = boardRepository.save(new Board(requestDto, userInfo));
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

    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto requestDto, User user) {
        Board board = findBoard(boardId);
        if(!Objects.equals(board.getUser().getUserId(), user.getUserId())) {
            throw new ForbiddenException("해당 게시글의 작성자가 아닙니다.");
        }
        board.update(requestDto);
        return new BoardResponseDto(board);
    }

    public void deleteBoard(Long boardId, User userDetails) {
        Board board = findBoard(boardId);
        if(!Objects.equals(board.getUser().getUserId(), userDetails.getUserId())) {
            throw new ForbiddenException("해당 게시글의 작성자가 아닙니다.");
        }
        boardRepository.delete(board);
    }

    public void increasedBoardLikes(Long boardId, User user) {
        User userInfo = findUser(user.getId());
        Board board = findBoard(boardId);
        Like like = new Like(userInfo, board, LikeType.BOARD);
        try {
            likeRepository.save(like);
        } catch(DataIntegrityViolationException ex) {
            throw new ViolatedLikeException("좋아요는 한번만 누를 수 있습니다.");
        }
    }

    public Long findByBoardLikes(Long boardId) {
        return likeRepository.countByBoardId(boardId);
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

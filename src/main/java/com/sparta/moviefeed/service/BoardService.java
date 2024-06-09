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
    private final LikeRepository likeRepository;

    public BoardService(BoardRepository boardRepository, LikeRepository likeRepository) {
        this.boardRepository = boardRepository;
        this.likeRepository = likeRepository;
    }

    public BoardResponseDto postingBoard(BoardRequestDto requestDto, User user) {
        Board board = boardRepository.save(new Board(requestDto, user));
        return new BoardResponseDto(board, user.getUserName());
    }

    public BoardResponseDto selectBoard(Long boardId) {
        Board board = findBoard(boardId);
        return new BoardResponseDto(board);
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

    public Long increasedBoardLikes(Long boardId, User user) {
        Board board = findBoard(boardId);
        Like like = new Like(user, board, LikeType.BOARD);
        try {
            likeRepository.save(like);
        } catch(DataIntegrityViolationException ex) {
            throw new ViolatedLikeException("좋아요는 한번만 누를 수 있습니다.");
        }
        return findByBoardLike(boardId);
    }

    public Long findByBoardLike(Long boardId) {
        return likeRepository.countByBoardId(boardId);
    }

    public Long deleteBoardLike(Long boardId, User user) {
        Board board = findBoard(boardId);
        Like like = likeRepository.findByUserAndBoard(user, board).orElseThrow(
                () -> new DataNotFoundException("좋아요의 정보가 없습니다.")
        );
        likeRepository.delete(like);
        return findByBoardLike(boardId);
    }

    private Board findBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new DataNotFoundException("조회된 게시글의 정보가 없습니다.")
        );
    }
}

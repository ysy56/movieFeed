package com.sparta.moviefeed.service;

import com.sparta.moviefeed.dto.requestdto.CommentRequestDto;
import com.sparta.moviefeed.dto.responsedto.CommentResponseDto;
import com.sparta.moviefeed.entity.Board;
import com.sparta.moviefeed.entity.Comment;
import com.sparta.moviefeed.entity.User;
import com.sparta.moviefeed.exception.DataNotFoundException;
import com.sparta.moviefeed.repository.BoardRepository;
import com.sparta.moviefeed.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public CommentService(CommentRepository commentRepository, BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
    }

    public CommentResponseDto postingComment(CommentRequestDto requestDto, Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new DataNotFoundException("해당 게시글을 찾을 수 없습니다.")
        );
        Comment comment = commentRepository.save(new Comment(requestDto, board, user));
        return new CommentResponseDto(comment);

    }

    public List<CommentResponseDto> getAllComments(Long boardId) {
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);

        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new DataNotFoundException("해당 댓글을 찾을 수 없습니다.")
        );

        // 댓글 작성자 확인
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("수정 권한이 없습니다.");
        }

        // 댓글 내용 수정
        comment.update(requestDto);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new DataNotFoundException("해당 댓글을 찾을 수 없습니다.")
        );

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("삭제 권한이 없습니다.");
        };

        commentRepository.delete(comment);
    }
}

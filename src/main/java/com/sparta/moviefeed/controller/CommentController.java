package com.sparta.moviefeed.controller;

import com.sparta.moviefeed.dto.requestdto.CommentRequestDto;
import com.sparta.moviefeed.dto.responsedto.CommentResponseDto;
import com.sparta.moviefeed.dto.responsedto.CommonResponse;
import com.sparta.moviefeed.security.UserDetailsImpl;
import com.sparta.moviefeed.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 댓글 등록 기능
     * @param requestDto : 등록할 댓글의 정보
     * @return : 등록 된 댓글의 정보
     */
    @PostMapping("/{id}/comment")
    public ResponseEntity<CommonResponse<CommentResponseDto>> postingComment(
            @PathVariable("id") Long boardId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommentResponseDto responseDto = commentService.postingComment(requestDto, boardId, userDetails.getUser());
        CommonResponse<CommentResponseDto> response = new CommonResponse<>(201, "댓글 등록 성공", responseDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<CommonResponse<List<CommentResponseDto>>> getAllComments(
            @PathVariable("id") Long boardId) {
        List<CommentResponseDto> comments = commentService.getAllComments(boardId);
        CommonResponse<List<CommentResponseDto>> response = new CommonResponse<>(200, "댓글 전체 조회 성공", comments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<CommentResponseDto>> updateComment(
            @PathVariable("boardId") Long boardId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, userDetails.getUser());
        CommonResponse<CommentResponseDto> response = new CommonResponse<>(200, "댓글 수정 성공", responseDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<Void>> deleteComment(
            @PathVariable("boardId") Long boardId,
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.deleteComment(commentId, userDetails.getUser());
        CommonResponse<Void> response = new CommonResponse<>(200, "댓글 삭제 성공", null);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

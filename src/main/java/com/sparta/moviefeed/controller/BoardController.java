package com.sparta.moviefeed.controller;

import com.sparta.moviefeed.dto.requestdto.BoardRequestDto;
import com.sparta.moviefeed.dto.responsedto.BoardResponseDto;
import com.sparta.moviefeed.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /**
     * 게시글 등록 기능
     * @param requestDto : 등록할 게시글의 정보
     * @return : 등록 된 게시글의 정보 및 username
     */
    @PostMapping // @AuthenticationPrincipal UserDetails
    public ResponseEntity<BoardResponseDto> boardPosting(@RequestBody BoardRequestDto requestDto) {
        BoardResponseDto responseDto = boardService.boardPosting(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * 특정 게시글 조회 기능
     * @param boardId : 특정 게시글의 번호
     * @return : 특정 게시글 조회 데이터
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> boardSelectOne(@PathVariable("id") Long boardId) {
        BoardResponseDto responseDto = boardService.boardSelectOne(boardId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}

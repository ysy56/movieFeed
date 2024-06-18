package com.sparta.moviefeed.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.moviefeed.config.WebSecurityConfig;
import com.sparta.moviefeed.controller.CommentController;
import com.sparta.moviefeed.controller.UserController;
import com.sparta.moviefeed.dto.requestdto.CommentRequestDto;
import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.dto.responsedto.CommentResponseDto;
import com.sparta.moviefeed.entity.Board;
import com.sparta.moviefeed.entity.Comment;
import com.sparta.moviefeed.entity.User;
import com.sparta.moviefeed.enumeration.UserStatus;
import com.sparta.moviefeed.security.UserDetailsImpl;
import com.sparta.moviefeed.service.CommentService;
import com.sparta.moviefeed.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {UserController.class, CommentController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)

public class CommentMvcTest {
    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    CommentService commentService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    private User mockUserSetup() {
        // Mock 테스트 유져 생성
        String userId = "testUser123";
        String password = "Password1234!";
        String userName = "Test User";
        String email = "testuser@example.com";
        String intro = "Hello, I am a test user.";
        UserStatus userStatus = UserStatus.NORMAL;

        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto(
                userId,
                password,
                userName,
                email,
                intro
        );

        User testUser = new User(userSignupRequestDto, userStatus);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());

        return new User(userSignupRequestDto, userStatus);
    }

    @Test
    @DisplayName("회원 가입 요청 처리")
    void test1() throws Exception {
        // given
        String userId = "testUser123";
        String password = "Password1234!";
        String userName = "Test User";
        String email = "testuser@example.com";
        String intro = "Hello, I am a test user.";

        UserSignupRequestDto requestDto = new UserSignupRequestDto(
                userId,
                password,
                userName,
                email,
                intro
        );

        String postInfo = objectMapper.writeValueAsString(requestDto);

        // when - then
        mvc.perform(post("/api/users/signup")
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 등록 요청 처리")
    void test2() throws Exception {
        User user = mockUserSetup();
        Board board = new Board();

        Long boardId = 1L;
        String content = "Test Content.";

        CommentRequestDto requestDto = new CommentRequestDto(content);

        Comment comment = new Comment(requestDto, board, user);

        CommentResponseDto responseDto = new CommentResponseDto(comment);

        given(commentService.postingComment(any(CommentRequestDto.class), any(Long.class), any(User.class)))
                .willReturn(responseDto);

        String postInfo = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/api/boards/{boardId}/comment", boardId)
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }
}

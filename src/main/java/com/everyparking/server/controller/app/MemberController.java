package com.everyparking.server.controller.app;

import com.everyparking.server.data.dto.MemberDto;
import com.everyparking.server.data.dto.MemberDto.UserInfoDto;
import com.everyparking.server.data.entity.Member;
import com.everyparking.server.exception.DuplicateUserException;
import com.everyparking.server.exception.InvalidPwdException;
import com.everyparking.server.exception.UserNotFoundException;
import com.everyparking.server.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/app/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     *
     * @param joinDto
     */
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberDto.Join joinDto) {
        try {
            memberService.join(joinDto);
            return ResponseEntity.status(HttpStatus.OK).build();


        } catch (DuplicateUserException e) {
            log.info("[MemberController] {}", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (RuntimeException e) {
            log.info("[MemberController] {}", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
    }

    /**
     * 로그인
     *
     * @param loginDto
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto.Login.Request loginDto) {
        try {
            Member member = memberService.login(loginDto);
            String userId = member.getUserId();

//            return ResponseEntity.status(HttpStatus.OK).build();
            return ResponseEntity.status(HttpStatus.OK)
                .body(MemberDto.Login.Response.builder()
                    .userId(userId)
                    .registered(true)
                    .build());
        } catch (UserNotFoundException e) {
            log.info("[MemberController] {}", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (InvalidPwdException e) {
            log.info("[MemberController] {}", e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (Exception e) {
            log.info("[MemberController] {}", e.toString());

        }

        return null;
    }

    @GetMapping("/userInfo")
    public MemberDto.UserInfoDto userInfoDto(HttpServletRequest request) {
        String userId = request.getHeader("userId").toString();
        log.debug("[{}] userId : {}", this.getClass().getName().toString(), userId);

        try {
            UserInfoDto findMember = memberService.findByUserId(userId);

            return findMember;

        } catch (Exception e) {
            log.info("[MemberController] {}", e);

        }

        throw new IllegalStateException();
    }


}


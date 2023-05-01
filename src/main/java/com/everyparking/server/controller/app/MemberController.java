package com.everyparking.server.controller.app;

import com.everyparking.server.data.dto.MemberDto;
import com.everyparking.server.exception.InvalidPwdException;
import com.everyparking.server.exception.UserNotFoundException;
import com.everyparking.server.service.MemberService;
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
    public void join(@RequestBody MemberDto.Join joinDto) {
        memberService.join(joinDto);
    }

    /**
     * 로그인
     *
     * @param loginDto
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto.Login loginDto) {
        try {
            memberService.login(loginDto);
            return ResponseEntity.status(HttpStatus.OK).build();
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



    /**
     * { "studentName" : "studentName", "status" : status, } studentName : String status : boolean
     * report : int
     * <p>
     * 메인화면 - userInfo GET http://everypaking.co.kr/app/member/userInfo
     */
//    @GetMapping("/userInfo")
//    public MemberDto.UserInfo userInfo() {
//
//
//
//    }
    @GetMapping("/test")
    public ResponseEntity<MemberDto.UserInfoDto> test() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                MemberDto.UserInfoDto.builder()
                    .studentName("s1")
                    .status(true)
                    .build());
    }


}


package com.everyparking.server.controller.app;

import com.everyparking.server.data.dto.MemberDto.UserFullInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@AllArgsConstructor
public class AppApiController {

    /**
     * 회원가입
     *
     * @param joinDto
     */
    @PostMapping("/app/api/join")
    @ResponseBody
    public void join(@RequestBody() UserFullInfo joinDto) {
        log.info("[join] {}", joinDto.toString());
    }



}

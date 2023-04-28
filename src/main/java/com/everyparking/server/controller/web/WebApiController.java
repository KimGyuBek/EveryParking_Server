package com.everyparking.server.controller.web;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController

@Slf4j
public class WebApiController {


    @CrossOrigin("*")
    @PostMapping("/web/api/test")
    public void test(@RequestBody() Test test) {
        log.info("[test] {}", test.toString());
    }

    @Data
    private static class Test {

        public Test(String email, String password) {
            this.email = email;
            this.password = password;
        }

        private String email;

        private String password;

    }


}

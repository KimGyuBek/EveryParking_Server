package com.everyparking.server.controller.web;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class TestWebController {

    @RequestMapping("/")
    @ResponseBody
    public String test(HttpServletRequest request){

        return "<h1>에브리파킹</h1>";
    }
}

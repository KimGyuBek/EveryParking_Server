package com.everyparking.server.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestWebController {

    @RequestMapping("/")
    @ResponseBody
    public String test(){
        return "<h1>에브리파킹</h1>";
    }

}

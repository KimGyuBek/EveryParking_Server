package com.everyparking.server.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestWebController {

    @RequestMapping("/")
    public String test(){
        return "에브리파킹";
    }

}

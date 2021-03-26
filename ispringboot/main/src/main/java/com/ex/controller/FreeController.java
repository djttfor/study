package com.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/free")
public class FreeController {
    @GetMapping("/free1")
    public String free1(Map<String,Object> map){
        map.put("title","Fantasy");
        map.put("flag",false);
        return "free1";
    }

    @GetMapping("/free2")
    public String free2(HttpServletRequest request){
        request.setAttribute("flag",true);
        request.setAttribute("title","今晚打老虎");
        return "free1";
    }
    @GetMapping("/thy1")
    public String thy1(){
        return "t1/thy1";
    }
    @GetMapping("/iThy1")
    public String iThy1(HttpServletRequest request){
        System.out.println(request.getContextPath() + "index/thy1.html");
        System.out.println(request.getContextPath() + "index/thy1.html".equals("index/thy1.html"));
        return "forward:/index/thy1.html";
    }


}

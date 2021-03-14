package com.mvc.controller;

import com.mvc.config.BaseException;
import com.mvc.pojo.User;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/haha")
public class HahaController {
    private final Logger logger = LoggerFactory.getLogger(HahaController.class);
    @RequestMapping("/toHello")
    public String goHello() throws BaseException {
        try {
            int a = 1/0;
        } catch (Exception e) {
            throw new BaseException("除零异常",false);
        }

        return "hello";
    }
    @RequestMapping("/hehe")
    @ResponseBody
    public String hehe(){
        return "hehe";
    }

    @RequestMapping("/toHello1")
    public void hello1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("info","这是Request的forward");
        request.getRequestDispatcher("/WEB-INF/page/hello.jsp").forward(request,response);
    }

    @RequestMapping("/toHello2")
    public ModelAndView hello2() throws ServletException, IOException {
        ModelAndView mv = new ModelAndView();
        mv.addObject("info","这是ModelAndView");
        mv.setViewName("hello");
        return mv;
    }

    @RequestMapping("/toHello3")
    public String hello3()  {
        return "forward:/WEB-INF/page/hello.jsp";
    }

    @RequestMapping("/toHello4")
    @ResponseBody
    public User hello4(@RequestBody User user,@RequestParam(name = "template") String t){
        System.out.println("template:"+t);
        System.out.println(user);
        return user;
    }
    @RequestMapping("/toHello5")
    public String hello5(User user){
        System.out.println(user);
        return "hello";
    }
    @RequestMapping("/toHello6")
    @ResponseBody
    public User hello6( User user){
        System.out.println(user);
        return user;
    }
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile multipartFile,HttpServletRequest request){
        String realPath = request.getSession().getServletContext().getRealPath("image");
        String filename = UUID.randomUUID().toString()+"-"+ multipartFile.getOriginalFilename();
        String contextPath = request.getContextPath();

        String filePath = realPath+"/"+filename;
        File file = new File(filePath);
        if(!file.getParentFile().exists()){
            file.mkdirs();
        }
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "http://localhost:8080"+contextPath+"/image/"+filename;
    }
    @RequestMapping("/download")
    public ResponseEntity<byte[]> export(HttpServletRequest request) throws IOException {

        String realPath = request.getSession().getServletContext().getRealPath("image");
        String fileName = "a.jpg";

        HttpHeaders headers = new HttpHeaders();
        File file = new File(realPath+"/"+fileName);

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
    @RequestMapping("/toHello7/{id}/{name}/{age}")
    public String testPathVariable(@PathVariable("id") String a,@PathVariable("name") String b,@PathVariable("age") String c){
        logger.debug("=========="+"id:"+a+",name:"+b+",age:"+c);
        return "hello";
    }
    @RequestMapping("/hehe1")
    public String cookieTest(HttpServletRequest request,HttpServletResponse response){
        for(int i = 1 ;i<=25;i++){
            Cookie cookie = new Cookie("hehe"+i,i+"");
            cookie.setMaxAge(360);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        }

        return "forward:/page/cctest.html";
    }
    @RequestMapping("/hehe2")
    public String cookieTest2(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            logger.info("Name:"+cookie.getName()+",Value:"+cookie.getValue());
        }
        return "hello";
    }
    @RequestMapping("/hehe3")
    public String sessionTest1(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();

        Object hehe = session.getAttribute("hehe");
        if (hehe!=null){
            String sessionId = session.getId();
            Cookie cookie2 = new Cookie("ss1",sessionId);
            cookie2.setMaxAge(6*60);
            cookie2.setPath("/");
            response.addCookie(cookie2);
            logger.info("JSESSIONID:"+sessionId);
            logger.info("sessionkey:hehe,value:"+hehe);
        }else{
            session.setAttribute("hehe","hehe1008611");
        }
        return "hello";
    }
    @PostMapping("/rp")
    @ResponseBody
    public User rememberPassword(HttpServletRequest request){
        return (User) request.getSession().getAttribute("user");
    }
    @RequestMapping("/demo1")
    @ResponseBody
    public User demo1(){
        User user = new User();
        user.setUsername("jimmy");
        user.setPassword("123");
        return user;
    }
//    @RequestMapping("/login")
//    public String login(User user){
//        logger.info(user.toString());
//        if("jimmy".equals(user.getUsername()) && "123".equals(user.getPassword())){
//            return "hello";
//        }else{
//            return "error";
//        }
//    }
}

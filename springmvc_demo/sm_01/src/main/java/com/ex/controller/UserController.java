package com.ex.controller;

import com.ex.mapper.UserMapper;
import com.ex.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);

    @GetMapping("/say")
    public void sayHello(String username, Long balance,
                         HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("用户名:{},余额:{}",username,balance);
        request.getRequestDispatcher("/page/aaaa.html").forward(request,response);
    }
    @PostMapping("/upload")
    @ResponseBody
    public void upload(@RequestParam("file")MultipartFile m,HttpServletRequest request) throws IOException {
        String originalFilename = m.getOriginalFilename();
        logger.info("2.{}",request.getServletContext().getRealPath("image"));
        logger.info("1.{}",request.getSession().getServletContext().getRealPath("image"));
        String realPath = request.getSession().getServletContext().getRealPath("image");
        String fileName = realPath +"\\"+originalFilename;
        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        m.transferTo(file);
        logger.info("上传{}成功",fileName);
    }
    @GetMapping("/download")
    public void doDownload(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String realPath = request.getServletContext().getRealPath("image\\测试2.txt");
        String fileName = realPath.substring(realPath.lastIndexOf("\\")+1);
        response.setHeader("content-disposition",
                "attachment;filename="+ URLEncoder.encode(fileName,"utf-8"));
        InputStream in = new FileInputStream(realPath);
        ServletOutputStream out = response.getOutputStream();
        int len;
        byte[] buff =new byte[1024*2];
        while((len=in.read(buff))!=-1){
            out.write(buff,0,len);
        }
    }
    @GetMapping("/access")
    @ResponseBody
    public User access(){
        User user = new User();
        user.setUsername("111");
        user.setPassword("222");
        user.setId(5);
        user.setPhone("555555555");
        return user;
    }
}

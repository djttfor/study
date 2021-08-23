package com.byteblogs.djttfor.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.byteblogs.common.base.domain.PageInfo;
import com.byteblogs.common.base.domain.Result;
import com.byteblogs.common.cache.ConfigCache;
import com.byteblogs.common.constant.Constants;
import com.byteblogs.common.enums.ErrorEnum;
import com.byteblogs.djttfor.entity.MyImg;
import com.byteblogs.djttfor.service.MyImgService;
import com.byteblogs.plumemo.file.factory.UploadFileFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;


@RestController
@RequestMapping("/myImg")
public class MyImgController {
    @Autowired
    MyImgService myImgService;

    @PostMapping("/upload")
    public Result uploadFile(@RequestParam(value = "file") final MultipartFile file,
                             @RequestParam(name="imgType",defaultValue = "0") int imgType) {
        MyImg myImg = new MyImg();
        myImg.setImgName(file.getOriginalFilename());
        myImg.setImgType(imgType);
        final String store = UploadFileFactory.getUploadFileService().saveFileStore(file);
        myImg.setImgUrl(store);
        myImgService.saveImgInfo(myImg);
        return Result.createWithSuccessMessage().setExtra(store);
    }
    @GetMapping("/queryAllImg/{imgType}")
    public List<String> queryAllImgUrl(@PathVariable("imgType") int imgType){
        return myImgService.queryAllImg(imgType);
    }
    @GetMapping("/selectPage/{current}/{pageSize}/{imgType}")
    public Result<MyImg> selectPage(@PathVariable("current") int current
            ,@PathVariable("pageSize") int pageSize,@PathVariable("imgType") int imgType){
        Page<MyImg> page =  myImgService.selectPage(current,pageSize,imgType);
        PageInfo pageInfo = new PageInfo().setPage(current).setSize(pageSize).setTotal(page.getTotal());
        return Result.createWithPaging(page.getRecords(),pageInfo);
    }
    @GetMapping("/removeImg/{id}")
    public Result remove(@PathVariable("id") int id){
        return myImgService.removeFile(id);
    }
}

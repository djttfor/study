package com.byteblogs.djttfor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byteblogs.common.base.domain.Result;
import com.byteblogs.common.cache.ConfigCache;
import com.byteblogs.common.constant.Constants;
import com.byteblogs.djttfor.entity.MyImg;
import com.byteblogs.djttfor.mapper.MyImgMapper;
import com.byteblogs.djttfor.service.MyImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyImgServiceImpl extends ServiceImpl<MyImgMapper, MyImg> implements MyImgService {
    @Autowired
    private MyImgMapper mapper;

    @Override
    public Boolean saveImgInfo(MyImg myImg) {
        return save(myImg);
    }

    @Override
    public Page<MyImg> selectPage(int current, int pageSize, int imgType) {
        Page<MyImg> page = new Page<>(current,pageSize);
        IPage<MyImg> iPage = mapper.selectPage(page, new LambdaQueryWrapper<MyImg>().eq(MyImg::getImgType, imgType));
        return (Page<MyImg>) iPage;
    }

    @Override
    public List<String> queryAllImg(int imgType) {
        List<MyImg> myImgs ;
        if(imgType==99){
            myImgs = mapper.selectList(null);
        }else{
            myImgs = mapper.selectList(new LambdaQueryWrapper<>(new MyImg()).eq(MyImg::getImgType,imgType));
        }
        return getUrl(myImgs);
    }

    @Override
    public Result removeFile(int id) {
        MyImg img = getOne(new LambdaQueryWrapper<>(new MyImg())
                .select(i -> ("imgUrl").equals(i.getProperty())).eq(MyImg::getId, id));
        if(img==null){
            return Result.createWithErrorMessage("删除图片失败","500");
        }
        //删除数据库记录
        removeById(id);
        //获取图片文件目录
        String imgDir = ConfigCache.getConfig(Constants.DEFAULT_PATH);
        //获取源文件名
        String imgDoMain = ConfigCache.getConfig(Constants.DEFAULT_IMAGE_DOMAIN)+"files/";
        String imgUrl = img.getImgUrl();
        String fileName = imgUrl.substring(imgDoMain.length());
        File file = new File(imgDir+fileName);
        if (file.exists()) {
            file.delete();
            return Result.createWithSuccessMessage();
        }else{
            return Result.createWithErrorMessage("删除图片失败","500");
        }
    }

    public List<String> getUrl(List<MyImg> imgList){
        int size = imgList.size();
        List<String> list = new ArrayList<>();
        if(size==0){
            return list;
        }
        for (MyImg myImg : imgList) {
            list.add(myImg.getImgUrl());
        }
        return list;
    }

}

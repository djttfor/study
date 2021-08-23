package com.xx.xx;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.byteblogs.PlumemoApplication;
import com.byteblogs.common.base.domain.Result;
import com.byteblogs.djttfor.entity.MyImg;
import com.byteblogs.djttfor.entity.MyPlaylist;
import com.byteblogs.djttfor.mapper.MyImgMapper;
import com.byteblogs.djttfor.mapper.MyPlaylistMapper;
import com.byteblogs.djttfor.service.MyImgService;
import com.byteblogs.djttfor.service.MyPlaylistService;
import com.byteblogs.plumemo.auth.dao.AuthTokenDao;
import com.byteblogs.plumemo.auth.dao.AuthUserDao;
import com.byteblogs.plumemo.auth.domain.po.AuthToken;
import com.byteblogs.plumemo.auth.domain.po.AuthUser;
import com.byteblogs.plumemo.config.dao.ConfigDao;
import com.byteblogs.plumemo.config.domain.po.Config;
import com.byteblogs.plumemo.music.domain.bo.Music;
import com.byteblogs.plumemo.music.util.MusicUtil;
import com.byteblogs.plumemo.posts.dao.PostsCommentsDao;
import com.byteblogs.plumemo.posts.domain.po.MyPostsComments;
import com.byteblogs.plumemo.posts.domain.po.PostsComments;
import com.byteblogs.plumemo.posts.service.PostsCommentsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = {PlumemoApplication.class})
@RunWith(SpringRunner.class)
public class Test1 {

    @Autowired
    private ConfigDao configDao;

    @Autowired
    private AuthTokenDao authTokenDao;

    @Autowired
    private MyPlaylistMapper myPlaylistMapper;

    @Autowired
    private MyPlaylistService myPlaylistService;

    @Autowired
    private MyImgMapper myImgMapper;

    @Autowired
    private MyImgService myImgService;

    @Autowired
    PostsCommentsDao postsCommentsDao;

    @Autowired
    PostsCommentsService postsCommentsService;

    @Test
    public void test1(){
        List<AuthToken> authTokens = authTokenDao.selectList(null);
        for (AuthToken authToken : authTokens) {
            System.out.println(authToken);
        }
    }


    @Test
    public void test2(){
        System.out.println(myPlaylistMapper.selectById(1));
    }

    @Test
    public void test3(){
        MyPlaylist p = myPlaylistService.getById(1);
        List<Music> aLlMusic = MusicUtil.getALlMusic(p.getDetail());
        for (Music music : aLlMusic) {
            System.out.println(music);
        }
    }

    @Test
    public void test4(){
        System.out.println(myImgService.save(new MyImg().setImgName("ddd").setImgType(1).setImgUrl("aaa")));
    }

    @Test
    public void test5(){
        List<MyImg> list = myImgService.list();
        for (MyImg myImg : list) {
            System.out.println(myImg);
        }
    }

    @Test
    public void test6(){
        List<MyImg> myImgs = myImgMapper.selectList(new LambdaQueryWrapper<>(new MyImg()).eq(MyImg::getImgType, 1));
        for (MyImg myImg : myImgs) {
            System.out.println(myImg);
        }
    }
    @Test
    public void test7(){
        List<MyImg> list = myImgService.list(Wrappers.lambdaQuery(new MyImg()).eq(MyImg::getImgType, 1).eq(MyImg::getId,1));
        for (MyImg myImg : list) {
            System.out.println(myImg);
        }
    }
    @Test
    public void test8(){
        List<MyImg> list = myImgService.list(Wrappers.query(new MyImg()).eq("img_type", 1));
        for (MyImg myImg : list) {
            System.out.println(myImg);
        }
    }

    @Test
    public void test9(){
        Page<MyPostsComments> page = new Page<>(1, 5);
        List<MyPostsComments> myPostsCommentsList = postsCommentsDao.selectReplyMyPostsCommentsList(page, 4L, 40L);
        for (MyPostsComments myPostsComments : myPostsCommentsList) {
            System.out.println(myPostsComments);
        }
        System.out.println(page.getTotal());
    }

    @Test
    public void test10(){
        Result<MyPostsComments> commentsList = postsCommentsService.getCommentsList(1, 5, 4L, 0L);
        List<MyPostsComments> models = commentsList.getModels();
        for (MyPostsComments model : models) {
            System.out.println(model);
        }
    }

    @Test
    public void test11(){
        Result<MyPostsComments> replyList = postsCommentsService.getReplyList(4L, 40L, 6);
        List<MyPostsComments> models = replyList.getModels();
        for (MyPostsComments model : models) {
            System.out.println(model);
        }
    }
    @Test
    public void test12(){

    }


}

class TestA{
    public static void main(String[] args) throws InterruptedException {
        String url = "https://img.djttfor.cn/api/blog/files/400ea7b9-c1e9-4f1a-ac78-41e5934e8c55.png";
        System.out.println(url.substring("https://img.djttfor.cn/api/blog/files/".length()));
    }
}
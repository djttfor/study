package com.byteblogs.plumemo.music.controller;

import com.byteblogs.common.base.domain.Result;
import com.byteblogs.djttfor.entity.MyPlaylist;
import com.byteblogs.djttfor.service.MyPlaylistService;
import com.byteblogs.plumemo.music.domain.bo.Music;
import com.byteblogs.plumemo.music.service.MusicService;
import com.byteblogs.plumemo.music.util.MusicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/music")
public class MusicController {

    @Autowired
    MyPlaylistService myPlaylistService;

    @GetMapping("/music/v1/list")
    public Result<Music> getPlayList(){
        MyPlaylist p = myPlaylistService.getById(1);
        List<Music> allMusic = MusicUtil.getALlMusic(p.getDetail());
        return Result.createWithModels(allMusic);
    }

}

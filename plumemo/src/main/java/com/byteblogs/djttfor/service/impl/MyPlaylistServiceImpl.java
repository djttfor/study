package com.byteblogs.djttfor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byteblogs.djttfor.entity.MyPlaylist;
import com.byteblogs.djttfor.mapper.MyPlaylistMapper;
import com.byteblogs.djttfor.service.MyPlaylistService;
import com.byteblogs.plumemo.auth.dao.AuthUserDao;
import com.byteblogs.plumemo.auth.domain.po.AuthUser;
import org.springframework.stereotype.Service;

@Service
public class MyPlaylistServiceImpl extends ServiceImpl<MyPlaylistMapper, MyPlaylist> implements MyPlaylistService {
}

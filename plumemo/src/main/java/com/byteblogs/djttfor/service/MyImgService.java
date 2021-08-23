package com.byteblogs.djttfor.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.byteblogs.common.base.domain.Result;
import com.byteblogs.common.base.service.BaseService;
import com.byteblogs.djttfor.entity.MyImg;
import com.byteblogs.djttfor.entity.MyPlaylist;

import java.util.List;

public interface MyImgService extends BaseService<MyImg> {
    Boolean saveImgInfo(MyImg myImg);

    Page<MyImg> selectPage(int current, int pageSize, int imgType);

    List<String> queryAllImg(int imgType);

    Result removeFile(int id);
}

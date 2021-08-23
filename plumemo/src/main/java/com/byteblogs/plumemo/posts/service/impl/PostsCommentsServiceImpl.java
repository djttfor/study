package com.byteblogs.plumemo.posts.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.byteblogs.common.base.domain.PageInfo;
import com.byteblogs.common.base.domain.Result;
import com.byteblogs.common.base.domain.vo.UserSessionVO;
import com.byteblogs.common.base.service.impl.BaseServiceImpl;
import com.byteblogs.common.constant.Constants;
import com.byteblogs.common.enums.ErrorEnum;
import com.byteblogs.common.util.ExceptionUtil;
import com.byteblogs.common.util.PageUtil;
import com.byteblogs.common.util.SessionUtil;
import com.byteblogs.plumemo.auth.dao.AuthUserDao;
import com.byteblogs.plumemo.auth.domain.po.AuthUser;
import com.byteblogs.plumemo.posts.dao.PostsCommentsDao;
import com.byteblogs.plumemo.posts.dao.PostsDao;
import com.byteblogs.plumemo.posts.domain.po.MyPostsComments;
import com.byteblogs.plumemo.posts.domain.po.Posts;
import com.byteblogs.plumemo.posts.domain.po.PostsComments;
import com.byteblogs.plumemo.posts.domain.vo.PostsCommentsVO;
import com.byteblogs.plumemo.posts.service.PostsCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 * @author byteblogs
 * @since 2019-09-03
 */
@Service
public class PostsCommentsServiceImpl extends BaseServiceImpl<PostsCommentsDao, PostsComments> implements PostsCommentsService {

    @Autowired
    private PostsCommentsDao postsCommentsDao;

    @Autowired
    private PostsDao postsDao;

    @Autowired
    private AuthUserDao authUserDao;

    @Override
    public Result savePostsComments(PostsCommentsVO postsCommentsVO) {
        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
        PostsComments postsComments = new PostsComments();
        postsComments.setAuthorId(userSessionInfo.getId());
        postsComments.setContent(postsCommentsVO.getContent());
        postsComments.setPostsId(postsCommentsVO.getPostsId());
        postsComments.setCreateTime(LocalDateTime.now());

        this.postsCommentsDao.insert(postsComments);

        this.postsDao.incrementComments( postsCommentsVO.getPostsId());

        return Result.createWithSuccessMessage();
    }

    @Override
    public Result saveMyPostsComments(MyPostsComments myPostsComments) {
        UserSessionVO userSessionInfo = SessionUtil.getUserSessionInfo();
        myPostsComments.setAuthorId(userSessionInfo.getId());
        myPostsComments.setCreateTime(LocalDateTime.now());
        myPostsComments.setStatus(0);
        postsCommentsDao.saveReplyComments(myPostsComments);
        postsDao.incrementComments( myPostsComments.getPostsId());
        return Result.createWithSuccessMessage();
    }

    @Override
    public Result<MyPostsComments> getReplyList(Long postsId, Long parentId,Integer replyCount) {
        List<MyPostsComments> myPostsCommentsList = postsCommentsDao.selectReplyMyPostsCommentsList(new Page<>(1, replyCount), postsId, parentId);
        return Result.createWithModels(myPostsCommentsList);
    }


    @Override
    public Result replyComments(PostsCommentsVO postsCommentsVO) {
        AuthUser authUser=authUserDao.selectAdmin();
        PostsComments postsComments=postsCommentsDao.selectById(postsCommentsVO.getParentId())
                .setParentId(postsCommentsVO.getParentId())
                .setContent(postsCommentsVO.getContent())
                .setAuthorId(authUser.getId())
                .setCreateTime(LocalDateTime.now());
        this.postsCommentsDao.insert(postsComments);
        String treePath = postsComments.getTreePath() + postsComments.getId() + Constants.TREE_PATH;
        this.postsCommentsDao.updateById(postsComments.setTreePath(treePath));
        this.postsDao.incrementComments( postsCommentsVO.getPostsId());
        return Result.createWithSuccessMessage();
    }

    @Override
    public Result<MyPostsComments> getCommentsList(Integer current, Integer pageSize, Long postsId, Long parentId) {
        current = Optional.ofNullable(current).orElse(1);
        pageSize = Optional.ofNullable(pageSize).orElse(5);
        Page<MyPostsComments> page = new Page<>(current,pageSize);
        List<MyPostsComments> myPostsCommentsList = postsCommentsDao.selectMyPostsCommentsList(page,postsId,parentId);
        //查询子回复
        if(parentId == 0){
            for (MyPostsComments myPostsComments : myPostsCommentsList) {
                int count = count(Wrappers.lambdaQuery(new PostsComments()).eq(PostsComments::getParentId, myPostsComments.getId()));
                myPostsComments.setReplyCount(count);
                if(count>0){
                    List<MyPostsComments> replyList = postsCommentsDao.selectReplyMyPostsCommentsList(new Page<>(1, 2),postsId,myPostsComments.getId());
                    myPostsComments.setReplyCommentsList(replyList);
                }else{
                    myPostsComments.setReplyCommentsList(new ArrayList<>());
                }
            }
        }
        Posts posts = postsDao.selectOne(Wrappers.lambdaQuery(new Posts()).eq(Posts::getId, postsId));
        Result<MyPostsComments> result = Result.createWithPaging(myPostsCommentsList, PageUtil.initPageInfo(page));
        result.setExtra(posts.getComments());
        return result;
    }

    @Override
    public Result getPostsCommentsByPostsIdList(PostsCommentsVO postsCommentsVO) {

        Page page = Optional.ofNullable(PageUtil.checkAndInitPage(postsCommentsVO)).orElse(PageUtil.initPage());
        List<PostsCommentsVO> postsCommentsVOLis = this.postsCommentsDao.selectPostsCommentsByPostsIdList(page, postsCommentsVO.getPostsId());

        return Result.createWithPaging(postsCommentsVOLis, PageUtil.initPageInfo(page));
    }

    @Override
    public Result getPostsCommentsList(PostsCommentsVO postsCommentsVO) {
        Page page = Optional.ofNullable(PageUtil.checkAndInitPage(postsCommentsVO)).orElse(PageUtil.initPage());
        List<PostsCommentsVO> postsCommentsVOLis = this.postsCommentsDao.selectPostsCommentsList(page, postsCommentsVO);
        return Result.createWithPaging(postsCommentsVOLis, PageUtil.initPageInfo(page));
    }

    @Override
    public Result deletePostsComments(Long id) {
        this.postsCommentsDao.deleteById(id);
        return Result.createWithSuccessMessage();
    }

    @Override
    public Result getPostsComment(Long id) {
        ExceptionUtil.isRollback(id==null,ErrorEnum.PARAM_ERROR);
        List<PostsCommentsVO> postsCommentsVOLis = this.postsCommentsDao.selectPostsCommentsList(new PostsCommentsVO().setId(id));
        if (postsCommentsVOLis!=null && postsCommentsVOLis.size()>0){
            return Result.createWithModel(postsCommentsVOLis.get(0));
        }
        return Result.createWithError();
    }
}

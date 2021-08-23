package com.byteblogs.plumemo.posts.domain.po;

import com.byteblogs.common.base.domain.PageInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class MyPostsComments implements Serializable {

    private static final long serialVersionUID = -7478191789087059519L;

    private Long id;

    private Long authorId;

    private Long replyAuthorId;

    private String content;

    private Long parentId;

    private Integer status;

    private Long postsId;

    private LocalDateTime createTime;

    private String authorName;

    private String replyAuthorName;

    private String authorAvatar;

    /**
     * 回复列表
     */
    private List<MyPostsComments> replyCommentsList;
    /**
     * 回复条数
     */
    private Integer replyCount;
}

package com.ex.ssm.mapper;

import com.ex.ssm.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-02-15
 */
public interface MemberMapper extends BaseMapper<Member> {
    List<Member> queryMemberByUid(Integer uid);
}
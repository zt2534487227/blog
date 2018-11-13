package com.zt.blog.dao;

import com.zt.blog.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-25
 */
@Repository
public interface UserDao extends BaseMapper<User> {

}

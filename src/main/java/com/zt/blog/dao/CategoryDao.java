package com.zt.blog.dao;

import com.zt.blog.model.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-25
 */
@Repository
public interface CategoryDao extends BaseMapper<Category> {

}

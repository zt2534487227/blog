package com.zt.blog.dao;

import com.zt.blog.model.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-25
 */
@Repository
public interface ArticleDao extends BaseMapper<Article> {

}

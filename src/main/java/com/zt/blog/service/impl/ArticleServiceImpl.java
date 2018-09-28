package com.zt.blog.service.impl;

import com.zt.blog.model.Article;
import com.zt.blog.dao.ArticleDao;
import com.zt.blog.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-28
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

}

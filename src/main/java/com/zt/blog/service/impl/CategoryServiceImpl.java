package com.zt.blog.service.impl;

import com.zt.blog.model.Category;
import com.zt.blog.dao.CategoryDao;
import com.zt.blog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-03
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

}

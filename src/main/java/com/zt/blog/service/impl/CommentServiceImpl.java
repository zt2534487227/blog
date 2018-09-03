package com.zt.blog.service.impl;

import com.zt.blog.model.Comment;
import com.zt.blog.dao.CommentDao;
import com.zt.blog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-03
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

}

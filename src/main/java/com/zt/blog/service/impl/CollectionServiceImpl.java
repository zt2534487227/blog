package com.zt.blog.service.impl;

import com.zt.blog.model.Collection;
import com.zt.blog.dao.CollectionDao;
import com.zt.blog.service.CollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 我的收藏 服务实现类
 * </p>
 *
 * @author ZhouTian
 * @since 2018-09-03
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionDao, Collection> implements CollectionService {

}

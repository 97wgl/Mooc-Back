package com.xgxfd.moocback.service.impl;

import com.xgxfd.moocback.entity.User;
import com.xgxfd.moocback.mapper.UserMapper;
import com.xgxfd.moocback.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

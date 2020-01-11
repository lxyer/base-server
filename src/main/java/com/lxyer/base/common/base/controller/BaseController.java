package com.lxyer.base.common.base.controller;

import com.lxyer.base.common.base.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 *
 * @author lxyer
 * @date 2019-08-07
 */
public abstract class BaseController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected UserEntity getUser() {
//        HttpServletRequest request=HttpContextUtils.getHttpServletRequest();
//        String token=request.getHeader("token");

        return new UserEntity() ;
    }


}

package com.lxyer.base.modules.businesslog.service;

import com.lxyer.base.modules.businesslog.entity.BusinessLogEntity;
import com.lxyer.base.modules.businesslog.vo.BusinessLogSearchVo;
import com.lxyer.base.modules.businesslog.vo.BusinessLogSaveVo;
import com.lxyer.base.modules.businesslog.vo.BusinessLogUpdateVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lxyer.base.common.base.PageBean;
import com.lxyer.base.common.base.entity.UserEntity;

/**
 * 业务日志
 * 
 * @author liangbing
 * @email liangbingsir@126.com
 * @date 2019-11-21 13:51:28
 */
public interface BusinessLogService extends IService<BusinessLogEntity> {

	PageBean<BusinessLogEntity> queryList(BusinessLogSearchVo searchVo);

	void save(UserEntity userEntity, BusinessLogSaveVo saveVo) throws Exception;

	void update(UserEntity userEntity, BusinessLogUpdateVo updateVo) throws Exception;

	void logicDelete(UserEntity userEntity,Long id);

}

package com.lxyer.base.modules.businesslog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lxyer.base.modules.businesslog.entity.BusinessLogEntity;
import com.lxyer.base.modules.businesslog.vo.BusinessLogSearchVo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 业务日志
 *
 * @author liangbing
 * @email liangbingsir@126.com
 * @date 2019-11-21 13:51:28
 */
@Mapper
public interface BusinessLogDao extends BaseMapper<BusinessLogEntity> {


    List<BusinessLogEntity> queryPage(BusinessLogSearchVo searchVo);

}

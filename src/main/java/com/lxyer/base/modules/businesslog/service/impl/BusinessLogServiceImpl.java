package com.lxyer.base.modules.businesslog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxyer.base.common.base.PageBean;
import com.lxyer.base.common.base.ResultCode;
import com.lxyer.base.common.base.entity.UserEntity;
import com.lxyer.base.modules.businesslog.dao.BusinessLogDao;
import com.lxyer.base.modules.businesslog.entity.BusinessLogEntity;
import com.lxyer.base.modules.businesslog.service.BusinessLogService;
import com.lxyer.base.modules.businesslog.vo.BusinessLogSaveVo;
import com.lxyer.base.modules.businesslog.vo.BusinessLogSearchVo;
import com.lxyer.base.modules.businesslog.vo.BusinessLogUpdateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务日志
 *
 * @author liangbing
 * @email liangbingsir@126.com
 * @date 2019-11-21 13:51:28
 */
@Service("businessLogService")
public class BusinessLogServiceImpl extends ServiceImpl<BusinessLogDao, BusinessLogEntity> implements BusinessLogService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BusinessLogDao businessLogDao;

    @Override
    public PageBean<BusinessLogEntity> queryList(BusinessLogSearchVo searchVo) {
        PageBean<BusinessLogEntity> pageBean = new PageBean<>();
        PageHelper.startPage(searchVo.getPage(), searchVo.getLimit());
        PageInfo<BusinessLogEntity> page = new PageInfo<>(businessLogDao.queryPage(searchVo));
        pageBean.setCount(new Long(page.getTotal()).intValue());
        //设置每页显示记录数
        pageBean.setLimit(searchVo.getLimit());
        //设置当前页数
        pageBean.setCurrentPage(searchVo.getPage());
        //设置数据
        pageBean.setData(page.getList());
        //设置状态码
        pageBean.setCode(ResultCode.SUCCESS.code());
        //设置返回信息
        pageBean.setMsg("查询成功");
        return pageBean;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserEntity userEntity, BusinessLogSaveVo saveVo) throws Exception {
        BusinessLogEntity businessLogEntity = new BusinessLogEntity();
        BeanUtils.copyProperties(saveVo, businessLogEntity);
        businessLogDao.insert(businessLogEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserEntity userEntity, BusinessLogUpdateVo updateVo) throws Exception {
        BusinessLogEntity businessLogEntity = new BusinessLogEntity();
        BeanUtils.copyProperties(updateVo, businessLogEntity);
        businessLogDao.updateById(businessLogEntity);
    }

    @Override
    public void logicDelete(UserEntity userEntity, Long id) {
        BusinessLogEntity businessLogEntity = new BusinessLogEntity();
        businessLogEntity.setId(id);
        businessLogDao.updateById(businessLogEntity);
    }

}

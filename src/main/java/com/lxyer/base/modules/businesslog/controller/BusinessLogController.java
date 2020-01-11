package com.lxyer.base.modules.businesslog.controller;

import com.lxyer.base.modules.businesslog.entity.BusinessLogEntity;
import com.lxyer.base.modules.businesslog.service.BusinessLogService;
import com.lxyer.base.modules.businesslog.vo.BusinessLogSaveVo;
import com.lxyer.base.modules.businesslog.vo.BusinessLogSearchVo;
import com.lxyer.base.modules.businesslog.vo.BusinessLogUpdateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lxyer.base.common.base.controller.BaseController;
import com.lxyer.base.common.base.PageBean;
import com.lxyer.base.common.base.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 业务日志
 *
 * @author liangbing
 * @email liangbingsir@126.com
 * @date 2019-11-21 13:51:28
 */
@Api(value = "业务日志", tags = "业务日志")
@RestController
@RequestMapping("businesslog")
public class BusinessLogController extends BaseController {
    @Autowired
    private BusinessLogService businessLogService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 列表
     */
    @ApiOperation(value = "获取列表", notes = "获取列表", produces = "application/json")
    @GetMapping(value = "list", produces = {"application/json;charset=UTF-8"})
    public PageBean<BusinessLogEntity> list(BusinessLogSearchVo searchVo) throws Exception {
        //查询列表数据
        PageBean<BusinessLogEntity> pageBean = new PageBean<BusinessLogEntity>();
        pageBean = businessLogService.queryList(searchVo);
        return pageBean;
    }

    /**
     * 信息
     */
    @ApiOperation(value = "查看详细信息", notes = "查看详细信息", produces = "application/json")
    @GetMapping(value = "info/{id}", produces = {"application/json;charset=UTF-8"})
    public RestResponse<BusinessLogEntity> info(@PathVariable("id") Long id) throws Exception {
        BusinessLogEntity entity = businessLogService.getById(id);
        return new RestResponse<BusinessLogEntity>().succuess(entity);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存", notes = "保存", produces = "application/json")
    @PostMapping(value = "save", produces = {"application/json;charset=UTF-8"})
    public RestResponse save(@RequestBody BusinessLogSaveVo saveVo) throws Exception {
        businessLogService.save(getUser(), saveVo);
        return RestResponse.succuess();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改", produces = "application/json")
    @PostMapping(value = "update", produces = {"application/json;charset=UTF-8"})
    public RestResponse update(@RequestBody BusinessLogUpdateVo updateVo) throws Exception {
        businessLogService.update(getUser(), updateVo);
        return RestResponse.succuess();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除", produces = "application/json")
    @DeleteMapping(value = "delete", produces = {"application/json;charset=UTF-8"})
    public RestResponse delete(Long id) throws Exception {
        businessLogService.removeById(id);
        return RestResponse.succuess();
    }

    /**
     * 逻辑删除
     */
    @ApiOperation(value = "逻辑删除", notes = "逻辑删除", produces = "application/json")
    @DeleteMapping(value = "logicDelete", produces = {"application/json;charset=UTF-8"})
    public RestResponse logicDelete(Long id) throws Exception {
        businessLogService.logicDelete(getUser(), id);
        return RestResponse.succuess();
    }

}

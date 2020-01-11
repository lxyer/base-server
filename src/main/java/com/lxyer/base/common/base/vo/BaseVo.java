package com.lxyer.base.common.base.vo;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseVo {

    @ApiModelProperty(value = "当前页数", example = "当前页数")
    @NotNull(message = "当前页数不能为空")
    private int page;
    
    
    @ApiModelProperty(value = "每页显示记录数", example = "每页显示记录数")
    @Max(value=1000,message="每页记录数最大不能超过1000条")
    private int limit;



}

package com.lxyer.base.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础响应类
 * 
 * @author lxyer
 *
 */
@Data
public class BaseResponse {
	@ApiModelProperty(value = "返回码，200 表示成功，其他为失败", example = "200")
	private int code;
	@ApiModelProperty(value = "提示信息", example = "操作成功")
	private String msg;

}

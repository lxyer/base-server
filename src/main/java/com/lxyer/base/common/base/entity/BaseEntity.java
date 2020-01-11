package com.lxyer.base.common.base.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseEntity {
	private Long id;

    @ApiModelProperty(value="创建人")
    private Long creator;

    @ApiModelProperty(value="修改人")
    private Long modifier;

	@ApiModelProperty(value="创建时间", example="2017-11-13 12:12:12")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@ApiModelProperty(value="修改时间", example="2017-11-13 12:12:12")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;

}

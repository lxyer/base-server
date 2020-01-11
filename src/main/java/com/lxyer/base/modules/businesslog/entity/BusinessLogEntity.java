package com.lxyer.base.modules.businesslog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 业务日志
 * 
 * @author liangbing
 * @email liangbingsir@126.com
 * @date 2019-11-21 13:51:28
 */
@Data
@TableName(value = "business_log")
@ApiModel(value = "BusinessLog", description = "业务日志")
public class BusinessLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
     * 自增主键
     */
	@ApiModelProperty(value="自增主键", example="100000", notes="自增主键")
	private Long id;
	/**
     * 业务主表id
     */
	@ApiModelProperty(value="业务主表id", example="100000", notes="业务主表id")
	private Long businessId;
	/**
     * 请求的方法
     */
	@ApiModelProperty(value="请求的方法", example="示例：请求的方法", notes="请求的方法")
	private String method;
	/**
     * 请求参数
     */
	@ApiModelProperty(value="请求参数", example="示例：请求参数", notes="请求参数")
	private String params;
	/**
     * 日志内容
     */
	@ApiModelProperty(value="日志内容", example="示例：日志内容", notes="日志内容")
	private String remark;
	/**
     * 执行时间
     */
	@ApiModelProperty(value="执行时间", example="1574315488963", notes="执行时间")
	private Long time;
	/**
     * 访问ip
     */
	@ApiModelProperty(value="访问ip", example="示例：访问ip", notes="访问ip")
	private String ip;
	/**
     * 创建人
     */
	@ApiModelProperty(value="创建人", example="100000", notes="创建人")
	private Long creatorId;
	/**
     * 创建时间
     */
	@ApiModelProperty(value="创建时间", example="1574315488964", notes="创建时间")
	private Date createdTime;
	/**
     * 修改人
     */
	@ApiModelProperty(value="修改人", example="100000", notes="修改人")
	private Long editorId;
	/**
     * 修改时间
     */
	@ApiModelProperty(value="修改时间", example="1574315488964", notes="修改时间")
	private Date modifiedTime;

}

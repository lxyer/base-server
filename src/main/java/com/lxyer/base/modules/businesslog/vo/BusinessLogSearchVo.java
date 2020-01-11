package com.lxyer.base.modules.businesslog.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 业务日志
 * 
 * @author liangbing
 * @email liangbingsir@126.com
 * @date 2019-11-21 13:51:28
 */
@Data
@ApiModel(value = "BusinessLogSearchVo", description = "业务日志")
public class BusinessLogSearchVo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    /**
     * 当前页数
     */
    @NotNull(message="当前页属性不能为空")
    @ApiModelProperty(value="当前页数", example="1", notes="当前页数")
    private int page;
    
    /**
     * 每页显示记录数
     */
    @Max(value=1000, message="每页显示记录数不能超过1000")
    @ApiModelProperty(value="每页显示记录数", example="10", notes="每页显示记录数")
    private int limit;
    
            
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
	    


    /**
     * 设置：当前页数
     */
    public void setPage(int page){
        this.page = page;
    }
    /**
     * 获取：当前页数
     */
    public int getPage(){
        return page;
    }
    /**
     * 设置：每页显示记录数
     */
    public void setLimit(int limit){
        this.limit = limit;
    }
    /**
     * 获取：每页显示记录数
     */
    public int getLimit(){
        return limit;
    }


}

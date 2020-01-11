package com.lxyer.base.common.utils;

/**
 * @description: 
 * @version: V1.0
 * @author: 朱剑
 * @date: 2019年12月19日 下午7:44:31
 **/
public class StrUtils {

	/**
	 * 检查字符中是否为空，是否有"null"字符串
	 * @param obj
	 * @return
	 */
	public static String checkNull(Object obj) {
		if(obj != null) {
			if("null".equals(obj.toString())) {
				return null;
			}else {
				return obj.toString();
			}
		}else {
			return null;
		}
		
	}
	
	/**
	 * 将json对象中long字符串转换为Long类型
	 * @param obj
	 * @return
	 */
	public static Long parseLong(Object obj) {
		if(obj != null && !"null".equals(obj.toString())) {
			return Long.parseLong(obj.toString());
		}else {
			return null;
		}
		
	}
}

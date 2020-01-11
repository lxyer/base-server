package com.lxyer.base.common.base;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class FastDfsFile {

	/**
	 * FastDfs文件信息
	 */
	private static final long serialVersionUID = 1L;

	private String fileId; // '文件ID'
	private String merchantCode; // '商户代码'
	private String projectCode; // '项目编号',
	private String fileType; // '文件类型'
	private String fileName; // '文件名称'
	private String filePath; // '文件路径'
	private String fileHash; // '文件hash值'
	private String annexNo; // '文件保全编号'
	private String verifyResult; // '校验结果'
	private Timestamp crtTime; // '创建时间'
	private Timestamp updTime; // '更新时间'
	private String fileSmallType; // 01证据hash值 02证据原文 03证据补充
	private Timestamp fileExt; // 文件后缀名
	private String fileData; // 文件BASE64
	private String fastdfsPath;
	private String fastdfsId;
	
}

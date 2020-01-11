package com.lxyer.base.common.utils;

import java.io.File;
import java.util.ArrayList;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;

/**
 * @description: zip工具类
 * @version: V1.0
 * @author: 朱剑
 * @date: 2019年12月11日 下午5:37:09
 **/
public class Zip4jUtil {
	
	 public static void compressWithPassword(String zipPath, String toZipPath, String password) {
	        try {
	            ZipFile zipFile = new ZipFile(zipPath);
	  
	            ArrayList<File> filesToAdd = new ArrayList<File>();
	            filesToAdd.add(new File(toZipPath));
	            ZipParameters parameters = new ZipParameters();
	            parameters.setCompressionMethod(8);
	            parameters.setCompressionLevel(5);
	            parameters.setEncryptFiles(true);
	            parameters.setEncryptionMethod(99);
	            parameters.setAesKeyStrength(3);
	            parameters.setPassword(password);
	            zipFile.addFiles(filesToAdd, parameters);
	        } catch (ZipException e) {
	        	e.printStackTrace();
	        	//("***************Zip4jUtil类compressWithPassword方法报错****************","发邮件过程中，针对邮件进行加密");
	        }
	 }
	 
	 public static void main(String[] args) {
	    System.out.println("350548195611291154".substring("350548195611291154".length() - 6));
	    String zipPath = "C:\\Users\\Administrator\\Desktop\\公证书(身份证后6位解压).zip";
	    String toZipPath = "C:\\Users\\Administrator\\Desktop\\公证书【（2019）京中信电证字00117号】.pdf";
	    compressWithPassword(zipPath, toZipPath, "350548195611291154".substring("350548195611291154".length() - 6));
	 }
	 
}

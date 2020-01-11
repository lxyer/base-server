package com.lxyer.base.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
	
	@SuppressWarnings("resource")
	public static void zip(List<Map<String, Object>> filePathList, String zipName, String pathUrl) throws IOException {
		OutputStream os = new BufferedOutputStream(new FileOutputStream(pathUrl+zipName));
		ZipOutputStream zos = new ZipOutputStream(os);
		byte[] buf = new byte[8192];
		int len;
		for (int i = 0; i < filePathList.size(); i++) {
			Map<String, Object> dataMap = filePathList.get(i);
			String file_name = dataMap.get("file_name").toString();
			String file_path = dataMap.get("file_path").toString();
			File file = new File(file_path);
			if (!file.isFile())
				continue;
			ZipEntry ze = new ZipEntry((i + 1) + file_name);
			zos.putNextEntry(ze);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			while ((len = bis.read(buf)) > 0) {
				zos.write(buf, 0, len);
			}
			zos.closeEntry();
		}
		zos.closeEntry();
		zos.close();
	}
}

package com.lxyer.base.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lxyer.base.common.base.FastDfsFile;

/**
 * 文件操作工具类
 */
public class FileUtil {
	
	/**
	 * 读取文件内容为二进制数组
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] read2ByteArray(String filePath) throws IOException {

		InputStream in = new FileInputStream(filePath);
		byte[] data = inputStream2ByteArray(in);
		in.close();

		return data;
	}

	public static void copyFileByChannel(File source, File target) throws IOException {
		try (FileChannel sc = new FileInputStream(source).getChannel();
			 FileChannel tc = new FileOutputStream(target).getChannel()) {
			long count = sc.size();
			while (count > 0) {
				long transferred = sc.transferTo(sc.position(), count, tc);
				count -= transferred;
			}
		}
	}

	/**
	 * 流转二进制数组
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] inputStream2ByteArray(InputStream in) throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
		}
		return out.toByteArray();
	}

	/**
     * 根据空间大小转化显示单位
     * @param value
     * @return
     */
    public static String format(long value){
        if(value > Math.pow(1024,4)){
            return (long)(value / Math.pow(1024,4)) + "TB";
        }else if(value > Math.pow(1024,3)){
            return (long)(value / Math.pow(1024,3)) + "GB";
        }else if(value > Math.pow(1024,2)){
            return (long)(value / Math.pow(1024,2)) + "MB";
        }else if(value > 1024){
            return (value / 1024) + "KB";
        }else if(value >= 0){
            return value + "B";
        }
        return null;
    }
    /**
	 * 获取文件类型
	 * @param fileType
	 * @return
	 */
	public static FileType fileType(String fileType) {
		if(fileType == null){
			return null;
		}
		// 获取文件后缀名并转化为写，用于后续比较
		fileType = fileType.toLowerCase();
		// 创建图片类型数组
		String img[] = { ".bmp", ".jpg", ".jpeg", ".png", ".tiff", ".gif", ".pcx", ".tga", ".exif", ".fpx", ".svg", ".psd",
				".cdr", ".pcd", ".dxf", ".ufo", ".eps", ".ai", ".raw", ".wmf" };
		for (int i = 0; i < img.length; i++) {
			if (img[i].equals(fileType)) {
				return FileType.IMAGE;
			}
		}
		// 创建文档类型数组
		String document[] = { ".txt", ".doc", ".docx", ".xls", ".htm", ".html", ".jsp", ".rtf", ".wpd", ".pdf", ".ppt" };
		for (int i = 0; i < document.length; i++) {
			if (document[i].equals(fileType)) {
				return FileType.DOCUMENT;
			}
		}
		// 创建视频类型数组
		String video[] = { ".mp4", ".avi", ".mov", ".wmv", ".asf", ".navi", ".3gp", ".mkv", ".f4v", ".rmvb", ".webm" };
		for (int i = 0; i < video.length; i++) {
			if (video[i].equals(fileType)) {
				return FileType.VIDEO;
			}
		}
		// 创建音乐类型数组
		String music[] = { ".mp3", ".wma", ".wav", ".mod", ".ra", ".cd", ".md", ".asf", ".aac", ".vqf", ".ape", ".mid", ".ogg",
				".m4a", ".vqf" };
		for (int i = 0; i < music.length; i++) {
			if (music[i].equals(fileType)) {
				return FileType.MUSIC;
			}
		}
		return FileType.OTHER;
	}
	
	/**
	 * 下载
	 * 
	 * @param filePo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void download(FastDfsFile fastDfsFile, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FastDFSClientWrapper fastDFSClient = new FastDFSClientWrapper();
		if (fastDfsFile != null) {
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			java.io.BufferedInputStream bis = null;
			java.io.BufferedOutputStream bos = null;
			if (fastDfsFile != null) {
				String downLoadPath = fastDfsFile.getFilePath();
				try {
					String agent = request.getHeader("user-agent");
					response.setContentType("application/x-download");
					String retName = fastDfsFile.getFileName();

					if ((agent.toUpperCase().indexOf("MSIE") > 0) || (agent.contains("Trident")))
						retName = URLEncoder.encode(retName, "UTF-8");
					else if (agent != null && -1 != agent.indexOf("Mozilla"))
						retName = new String(retName.getBytes("UTF-8"), "ISO-8859-1");

					response.setHeader("Content-disposition",
							(new StringBuilder("attachment; filename=")).append(retName).toString());
					bis = new BufferedInputStream(new FileInputStream(downLoadPath));
					bos = new BufferedOutputStream(response.getOutputStream());
					byte[] buff = new byte[1024];
					int bytesRead;
					while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
						bos.write(buff, 0, bytesRead);
					}
				} catch (FileNotFoundException e) {
					byte[] buff = fastDFSClient.downloadFile(fastDfsFile.getFastdfsId(), fastDfsFile.getFastdfsPath());
					byte2File(buff, fastDfsFile.getFilePath());
					fastDFSClient.fastdfsDownload(buff, fastDfsFile.getFileName(), request, response);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null)
						bis.close();
					if (bos != null)
						bos.close();
				}
			}
		}
	}

	public static void byte2File(byte[] buf, String filePath) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		String path = filePath.substring(0, filePath.lastIndexOf("/") + 1);
		try {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdir();
			}
			file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

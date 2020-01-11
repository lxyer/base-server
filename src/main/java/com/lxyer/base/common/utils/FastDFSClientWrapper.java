package com.lxyer.base.common.utils;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: FastDFSClientWrapper工具类
 * @version: V1.0
 * @author: 朱剑
 * @data: 2019-10-30 16:20
 **/
@Component
public class FastDFSClientWrapper {

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public StorePath uploadFile(String filePath, String fileName) throws Exception {
        File file = new File(filePath);
        FileInputStream is = null;
        StorePath storePath = null;
        try {
            is = new FileInputStream(file);
            storePath = storageClient.uploadFile(is, is.getChannel().size(), fileName.substring(fileName.indexOf(".") + 1), null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return storePath;
    }

    /**
     * 文件上传
     *
     * @param bytes     文件字节
     * @param fileSize  文件大小
     * @param extension 文件扩展名
     * @return fastDfs路径
     */
    public String uploadFile(byte[] bytes, long fileSize, String extension) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        StorePath storePath = storageClient.uploadFile(byteArrayInputStream, fileSize, extension, null);
        System.out.println(storePath.getGroup() + "===" + storePath.getPath() + "======" + storePath.getFullPath());
        return storePath.getFullPath();
    }

    /**
     * @param content       文件内容
     * @param fileExtension
     * @return
     */
    public StorePath uploadString(String content, String fileExtension) {
        ByteArrayInputStream stream = null;
        StorePath storePath = null;
        try {
            byte[] buff = content.getBytes(Charset.forName("UTF-8"));
            stream = new ByteArrayInputStream(buff);
            storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return storePath;
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件访问地址
     * @return
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     *
     * @param groupName
     * @param path
     * @return
     */
    public byte[] downloadFile(String groupName, String path) throws Exception {
        byte[] downloadResults = null;
        try {
            DownloadByteArray downloadByteArray = new DownloadByteArray();
            downloadResults = storageClient.downloadFile(groupName, path, downloadByteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return downloadResults;
    }

    public static void byte2File(byte[] buf, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
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


    /**
     * 下载文件
     *
     * @param fileUrl 文件URL
     * @return 文件字节
     * @throws IOException
     */
    public byte[] downloadFile(String fileUrl) throws IOException {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = storageClient.downloadFile(group, path, downloadByteArray);
        return bytes;
    }
    
	/**
	 * fastdfs下载
	 * 
	 * @param
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void fastdfsDownload(byte[] buff, String fileName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		try {
			request.setCharacterEncoding("UTF-8");
			String agent = request.getHeader("user-agent");
			response.setContentType("application/x-download");
			if ((agent.toUpperCase().indexOf("MSIE") > 0) || (agent.contains("Trident")))
				fileName = URLEncoder.encode(fileName, "UTF-8");
			else if (agent != null && -1 != agent.indexOf("Mozilla"))
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			response.reset();
			response.setHeader("Content-disposition",
					(new StringBuilder("attachment; filename=")).append(fileName).toString());
			bos = new BufferedOutputStream(response.getOutputStream());
			bos.write(buff);
			bos.flush();
			bos.close();
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

package com.lxyer.base.common.utils;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

public class SABase64Utils {

    /**
     * @Description: 将base64编码字符串转换为图片
     * @Author:
     * @CreateTime:
     * @param fileStr base64编码字符串
     * @param path 图片路径-具体到文件
     * @return
     */
    public static boolean generateFile(String fileStr, String path) throws IOException {
        if (fileStr == null){
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();

        // 解密
        byte[] b = decoder.decodeBuffer(fileStr);
        // 处理数据
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        OutputStream out = new FileOutputStream(path);
        out.write(b);
        out.flush();
        out.close();
        return true;
    }

    public static byte[] decode(String fileStr){
        if (fileStr == null){
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(fileStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将字节数组转换为base64 字符串
     * @param bytes
     * @return
     */
    public static String encodeBase64Str(byte[] bytes){
        return new BASE64Encoder().encode(bytes);
    }
    /**
     * @Description: 根据地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     * @return
     */
    public static String getFileStr(String filePath) throws IOException {
        InputStream inputStream = null;
        byte[] data = null;
        inputStream = new FileInputStream(filePath);
        data = new byte[inputStream.available()];
        inputStream.read(data);
        inputStream.close();
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * 将base64文件写到磁盘上
     * @param destPath
     * @param base64
     * @param fileName
     */
    public static String base64ToFile(String destPath,String base64, String fileName) {
        System.out.println("destPath = "+destPath);
        System.out.println("fileName = "+fileName);
        File file = null;
        //创建文件目录
        String filePath=destPath;
        File  dir=new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.decodeBase64(base64);
//            byte[] bytes = Base64.getDecoder().decode(base64);
            file=new File(filePath+File.separatorChar+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
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
        return destPath + File.separator + fileName;
    }
    /**
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     * @return
     */
    public static String getImageStr(String imgFile) throws IOException {
        InputStream inputStream = null;
        byte[] data = null;
        inputStream = new FileInputStream(imgFile);
        data = new byte[inputStream.available()];
        inputStream.read(data);
        inputStream.close();
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
    public static void main(String[] args){
        String source = "C:\\\\Users\\Caroline\\Desktop\\乱七八糟\\08500410071.png";
        String target = "C:\\\\Users\\Caroline\\Desktop\\乱七八糟\\085004100711.png";

        try {
            String fileStr = SABase64Utils.getFileStr(source);//加密
            System.out.println("fileStr = "+fileStr);
            boolean b = SABase64Utils.generateFile(fileStr, target);//解密
            System.out.println("b = "+b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

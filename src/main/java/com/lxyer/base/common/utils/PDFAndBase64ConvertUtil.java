package com.lxyer.base.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 用于PDF与Base64之间的转换
 * Created by Administrator on 2018/12/5 0005.
 */
public class PDFAndBase64ConvertUtil {
    /**
     * Description: 将base64编码内容转换为Pdf
     * @param  base64Content，文件的存储路径（含文件名）
     * @Author fuyuwei
     * Create Date: 2015年7月30日 上午9:40:23
     */
    public static void base64StringToPdf(String base64Content,String filePath) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            byte[] bytes = decoder.decodeBuffer(base64Content);//base64编码内容转换为字节数组
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
            bis = new BufferedInputStream(byteInputStream);
            File file = new File(filePath);
            File path = file.getParentFile();
            if(!path.exists()){
                path.mkdirs();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            byte[] buffer = new byte[1024];
            int length = bis.read(buffer);
            while(length != -1){
                bos.write(buffer, 0, length);
                length = bis.read(buffer);
            }
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            bis.close();
            fos.close();
            bos.close();
        }
    }



    /**
     * 将base64字符串转换为PDF在显示到页面中
     * @param base64String
     * @param httpServletResponse
     */
    public static void base64StringToPDFToPage(String base64String, HttpServletResponse httpServletResponse){
        BASE64Decoder decoder = new BASE64Decoder();
        ByteArrayOutputStream baos = null;
        ServletOutputStream sos = null;
        try {
            byte[] bytes = decoder.decodeBuffer(base64String);
            baos = new ByteArrayOutputStream();
            baos.write(bytes); //把byte写进输出流里
            if (baos != null) {
                httpServletResponse.setContentType("application/pdf");
                httpServletResponse.setContentLength(baos.size());
                httpServletResponse.setHeader("Expires", "0");
                httpServletResponse.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                httpServletResponse.setHeader("Pragma", "public");
                // 设置打印PDF的文件名
                String fileName = "社保证明文件.pdf";
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                httpServletResponse.setHeader("Content-Disposition", "filename=" + fileName);
                sos = httpServletResponse.getOutputStream();
                baos.writeTo(sos); //byte输出流写入servlet输出流
                sos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sos.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  将base64编码转换成PDF
     *  @param base64String
     *  1.使用BASE64Decoder对编码的字符串解码成字节数组
     *  2.使用底层输入流ByteArrayInputStream对象从字节数组中获取数据；
     *  3.建立从底层输入流中读取数据的BufferedInputStream缓冲输出流对象；
     *  4.使用BufferedOutputStream和FileOutputSteam输出数据到指定的文件中
     */
    public static void base64StringToPDF(String base64String, File file){
        BASE64Decoder decoder = new BASE64Decoder();
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            //将base64编码的字符串解码成字节数组
            byte[] bytes = decoder.decodeBuffer(base64String);
            //创建一个将bytes作为其缓冲区的ByteArrayInputStream对象
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            //创建从底层输入流中读取数据的缓冲输入流对象
            bin = new BufferedInputStream(bais);
            //创建到指定文件的输出流
            fout  = new FileOutputStream(file);
            //为文件输出流对接缓冲输出流对象
            bout = new BufferedOutputStream(fout);

            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while(len != -1){
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            bout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bout.close();
                fout.close();
                bin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * PDF转换为Base64编码
     * @param file
     * @return
     */
    public static String pdfToBase64(File file) {
        BASE64Encoder encoder = new BASE64Encoder();
        FileInputStream fin =null;
        BufferedInputStream bin =null;
        ByteArrayOutputStream baos = null;
        BufferedOutputStream bout =null;
        try {
            fin = new FileInputStream(file);
            bin = new BufferedInputStream(fin);
            baos = new ByteArrayOutputStream();
            bout = new BufferedOutputStream(baos);
            byte[] buffer = new byte[1024];
            int len = bin.read(buffer);
            while(len != -1){
                bout.write(buffer, 0, len);
                len = bin.read(buffer);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节
            bout.flush();
            byte[] bytes = baos.toByteArray();
            return encoder.encodeBuffer(bytes).trim();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fin.close();
                bin.close();
                baos.close();
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

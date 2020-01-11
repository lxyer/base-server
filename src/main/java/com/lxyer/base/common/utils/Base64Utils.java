package com.lxyer.base.common.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * 创建时间:2019年6月17日10:41:00 创建者 :cuishiliang 用于Base64解码和编码
 */
public class Base64Utils {

    private static Base64Utils utils = null;

    private Base64Utils() {

    }

    /**
     * 创建时间:2019年6月17日10:41:00 创建者 :cuishiliang 机能概要:单利 ，懒汉模式
     *
     * @return
     */
    public static Base64Utils getInstance() {
        if (utils == null) {
            synchronized (Base64Utils.class) {
                if (utils == null) {
                    utils = new Base64Utils();
                }
            }
        }
        return utils;
    }

    /**
     * 创建时间:2019年6月17日10:41:00 创建者 :cuishiliang 机能概要:获取文件的大小
     *
     * @param inFile 文件
     * @return 文件的大小
     */
    public int getFileSize(File inFile) {
        InputStream in = null;

        try {
            in = new FileInputStream(inFile);
            // 文件长度
            int len = in.available();
            return len;
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     * 创建时间:2019年6月17日10:41:00 创建者 :cuishiliang 机能概要:将文件转化为base64
     *
     * @return
     * @throws Exception
     */
    public String file2Base64(File inFile) {

        // 将文件转化为字节码
        byte[] bytes = copyFile2Byte(inFile);
        if (bytes == null) {
            return null;
        }

        // base64,将字节码转化为base64的字符串
        String result = Base64.getEncoder().encodeToString(bytes);
        return result;
    }

    /**
     *
     * 创建时间:2019年6月17日10:41:00 创建者 :cuishiliang 机能概要:将文件转化为字节码
     *
     * @param inFile
     * @return
     */
    private byte[] copyFile2Byte(File inFile) {
        InputStream in = null;

        try {
            in = new FileInputStream(inFile);
            // 文件长度
            int len = in.available();

            // 定义数组
            byte[] bytes = new byte[len];

            // 读取到数组里面
            in.read(bytes);
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建时间:2019年6月17日10:41:00 创建者 :cuishiliang 机能概要:将字符串转化为文件
     *
     * @param strBase64 base64 编码的文件
     * @param outFile   输出的目标文件地址
     * @return
     * @throws IOException
     */
    public boolean base64ToFile(String strBase64, File outFile) {
        try {
            // 解码，然后将字节转换为文件
            byte[] bytes = Base64.getDecoder().decode(strBase64); // 将字符串转换为byte数组
            return copyByte2File(bytes, outFile);
        } catch (Exception ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    /**
     * 创建时间:2019年6月17日10:41:00 创建者 :cuishiliang 机能概要:将字节码转化为文件
     *
     * @param bytes
     * @param file
     */
    private boolean copyByte2File(byte[] bytes, File file) {
        FileOutputStream out = null;
        try {
            // 转化为输入流
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);

            // 写出文件
            byte[] buffer = new byte[1024];

            out = new FileOutputStream(file);

            // 写文件
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len); // 文件写操作
            }
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        File file = new File("C:\\Users\\pan\\Desktop\\d2c89460d8bee94abceb983e9564e31a.mp4");
        String baseString = Base64Utils.getInstance().file2Base64(file);
        System.out.println(baseString);

        Base64Utils.getInstance().base64ToFile(baseString,new File("C:\\Users\\pan\\Desktop\\234.mp4"));

    }
}

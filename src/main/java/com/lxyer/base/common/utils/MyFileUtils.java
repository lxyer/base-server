package com.lxyer.base.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class MyFileUtils {
    /**
     * slf4j日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(MyFileUtils.class.getName());


    // 判断文件是否存在
    public static void checkFileExists(File file) {

        if (file.exists()) {
            logger.info("debtorfile exists");
        } else {
            logger.info("debtorfile not exists, create it ...");
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    // 判断文件夹是否存在
    public static void checkDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                logger.info("dir exists");
            } else {
                logger.info("the same name debtorfile exists, can not create dir");
            }
        } else {
            logger.info("dir not exists, create it ...");
            file.setWritable(true, false);
            file.mkdirs();
        }

    }


    /**
     * 获取一个文件夹下的所有文件全路径
     *
     * @param path
     * @param listFileName
     */
    public static void getAllFileName(String path, ArrayList<String> listFileName) {
        File file = new File(path);

        File[] files = file.listFiles();
        String[] names = file.list();

        if (names != null) {
            String[] completNames = new String[names.length];
            for (int i = 0; i < names.length; i++) {
                completNames[i] = path + names[i];
            }
            listFileName.addAll(Arrays.asList(completNames));
        }
        for (File a : files) {
            if (a.isDirectory()) {//如果文件夹下有子文件夹，获取子文件夹下的所有文件全路径。
                getAllFileName(a.getAbsolutePath() + File.separator, listFileName);
            }
        }
    }


    /**
     * 文件复制
     *
     * @param srcPath    源文件路径
     * @param targetPath 复制后存放路径
     * @throws Exception
     */
    public static void copyFile(String srcPath, String targetPath) throws Exception {
        File srcFile = new File(srcPath);
        File target = new File(targetPath);
        if (!srcFile.exists()) {
            logger.info("★★★★★ 文件不存在 srcPath :" + srcPath);
            return;
        }
        if (!srcFile.isFile()) {
            logger.info("★★★★★ 不是文件 srcPath :" + srcPath);
            return;
        }
        if (!target.exists()) {
            // 按照指定的路径创建文件夹
            target.setWritable(true, false);
            target.mkdirs();
        }
        //判断目标路径是否是目录
        if (!target.isDirectory()) {
            throw new Exception("文件路径不存在！");
        }

        // 获取源文件的文件名
        String fileName = srcPath.substring(srcPath.lastIndexOf(File.separator) + 1);
        //TODO:判断是否存在相同的文件名的文件
        File[] listFiles = target.listFiles();
        for (File file : listFiles) {
            if (fileName.equals(file.getName())) {
                fileName = "1_" + fileName;
            }
        }

        String newFileName = targetPath + File.separator + fileName;
        File targetFile = new File(newFileName);
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(targetFile);
            //从in中批量读取字节，放入到buf这个字节数组中，
            // 从第0个位置开始放，最多放buf.length个 返回的是读到的字节的个数
            byte[] buf = new byte[8 * 1024];
            int len = 0;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                logger.info("关闭输入流错误！");
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                logger.info("关闭输出流错误！");
            }
        }

    }

    /**
     * 将数据写入指定文件
     *
     * @param filePath
     * @param data
     */
    public static void saveDataToFile(String filePath, String data) {
        BufferedWriter writer = null;
        File file = new File(filePath);
        //如果文件不存在，则新建一个
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //写入
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件写入成功！");
    }


    /**
     * 读取文件中的json数据
     *
     * @param filePath
     * @return
     */
    public static String getDatafromFile(String filePath) {

        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }


    /**
     * 获取文件大小
     *
     * @param filePath
     */
    public static Long getFileSize(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.length();
        }
        return 0L;
    }


}

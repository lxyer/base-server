package com.lxyer.base.common.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class PdfUtil {
    private static String templatePath;
    @Value("${templatePath}")
    public void setTemplatePath(@Value("${templatePath}") String templatePath) {
        PdfUtil.templatePath = templatePath;
    }

    public static String generatePdf(String filePath, String fileName, String template, Map<String, Object> variables) throws Exception{
        String htmlStr = getHtml(template, variables);
        String pdfPath = html2pdf(filePath,fileName,htmlStr);
        return pdfPath;
    }

    public static String generatePdf(String filePath, String fileName, String template) throws Exception {
        String htmlStr = getHtml(filePath, template);
        String pdfPath = html2pdf(filePath, fileName, htmlStr);
        return pdfPath;
    }

    public static String generatePdfByHtml(String filePath, String fileName, String template, Map<String, Object> variables) throws Exception {
        String htmlStr = getHtml(template, variables);
        String pdfPath = html2pdf(filePath, fileName, htmlStr);
        return pdfPath;
    }

    public static String getHtml(String template, Map<String, Object> variables) throws IOException {
        //创建模板解析器
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setPrefix(templatePath);
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));

        //创建模板引擎并初始化解析器
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(templateResolver);
        engine.isInitialized();

        //输出流
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);

        //获取上下文
        Context ctx = new Context();
        ctx.setVariables(variables);
        engine.process(template,  ctx, writer);

        stringWriter.flush();
        stringWriter.close();
        writer.flush();
        writer.close();

        //输出html
        return stringWriter.toString();
    }

    public static String getHtml(String prefix, String template) throws IOException {
        //创建模板解析器
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setPrefix(prefix);
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));

        //创建模板引擎并初始化解析器
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(templateResolver);
        engine.isInitialized();

        //输出流
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);

        //获取上下文
        Context ctx = new Context();
        engine.process(template, ctx, writer);

        stringWriter.flush();
        stringWriter.close();
        writer.flush();
        writer.close();

        //输出html
        return stringWriter.toString();
    }

    private static String html2pdf(String path, String fileName, String content) throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();
        try {
//            设置字体，否则不支持中文,在html中使用字体，html{ font-family: SimSun;}
            fontResolver.addFont(templatePath+"SIMFANG.TTF",  BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        renderer.setDocumentFromString(content);
        renderer.layout();
        renderer.createPDF(new FileOutputStream(new File(path + fileName+".pdf")));
        return path + fileName + ".pdf";
    }


    public static String getPdf() throws IOException, DocumentException {
        Map<String,Object> map = new HashMap<>();
        map.put("name", "go啊od-啊123e");
        try {
            String html =  PdfUtil.generatePdf("/Users/deepsec/IdeaProjects/notarization/src/main/resources/templates/","bbb","/Users/deepsec/IdeaProjects/notarization/src/main/resources/templates/index.html",map);
            System.out.println(html);
            return html;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 合并原pdf为新文件
     *
     * @param files   pdf绝对路径集
     * @param newfile 新pdf绝对路径
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static void mergePdfFiles(List<String> files, String newfile) throws IOException, DocumentException {
        Document document = new Document(new PdfReader(files.get(0)).getPageSize(1));
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));
        document.open();
        for (int i = 0; i < files.size(); i++) {
            PdfReader reader = new PdfReader(files.get(i));
            int n = reader.getNumberOfPages();
            for (int j = 1; j <= n; j++) {
                document.newPage();
                PdfImportedPage page = copy.getImportedPage(reader, j);
                copy.addPage(page);
            }
        }
        document.close();
    }
    /**
     * @Title: encrypt_zip
     * @Description:将指定路径下的文件压缩至指定zip文件，并以指定密码加密,若密码为空，则不进行加密保护
     * @param src_file 待压缩文件路径
     * @param dst_file zip路径+文件名
     * @param encode 加密密码
     * @return
     */
    public static void encrypt_zip(String src_file, String dst_file, String encode) {
        File file = new File(src_file);

        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);//压缩方式
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 压缩级别

        parameters.setEncryptFiles(true);
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);//加密方式
        parameters.setPassword(encode.toCharArray());//设置密码
        try {
            ZipFile zipFile = new ZipFile(dst_file);
            zipFile.setFileNameCharset("gbk");
            zipFile.addFile(file, parameters);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

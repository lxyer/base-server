package com.lxyer.base.common.utils;

import com.itextpdf.awt.geom.Rectangle2D.Float;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.security.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

/**
 * 签名证书信息实体
 */
@Component
public class SignPDFUtil {
    private static final Logger logger = LoggerFactory.getLogger(SignPDFUtil.class.getName());// slf4j日志记录器

    private static String signPath;


    @Value("${signPath}")
    public void setSignPath(String signPath) {
        SignPDFUtil.signPath = signPath;
    }
//    @Value("${signPath}")
//    private  String signPath;

    /**
     * 为PDF文件进行签名
     *
     * @param pdfFilePath
     * @param signFileTargetPath
     * @param signatureEntity
     */
    @SuppressWarnings("resource")
    public static void sign(String pdfFilePath, String signFileTargetPath, SignatureEntity signatureEntity) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        ByteArrayOutputStream tempArrayOutputStream = new ByteArrayOutputStream();
        try {
            inputStream = new FileInputStream(pdfFilePath);
            PdfReader pdfReader = new PdfReader(inputStream, signatureEntity.getOwnerpwd().getBytes());//要求提交所有者密码

            //logger.info(reader.getNumberOfPages());
            if (signatureEntity.getPage() == 0) {
                signatureEntity.setPage(pdfReader.getNumberOfPages());
            }
            if (signatureEntity.getRectlly() == 0) {
                List<float[]> list = getKeyWords(pdfReader, signatureEntity.getPostKeyword());  // 二〇一九年三月二十九
                if (list.size() > 0) {
                    float[] xy = list.get(list.size() - 1);
                    signatureEntity.setRectllx(xy[0] + signatureEntity.getOffsetX());   //  260
                    signatureEntity.setRectlly(xy[1] - signatureEntity.getOffsetY());
                } else {
                    signatureEntity.setRectllx(400);
                    signatureEntity.setRectlly(650);
                    logger.error("*** " + System.currentTimeMillis() + "     未找到盖章坐标，关键字：" + signatureEntity.getPostKeyword());
                }
                signatureEntity.setRecturx(signatureEntity.getRectllx() + signatureEntity.getWidth());
                signatureEntity.setRectury(signatureEntity.getRectlly() + signatureEntity.getHeight());
                logger.info("*** " + System.currentTimeMillis() + "     盖章坐标： leftBottom(X = " + signatureEntity.getRectllx() + ", Y = " + signatureEntity.getRectlly() + "), rightTop(X = " + signatureEntity.getRecturx() + ", Y = " + signatureEntity.getRectury() + ")");
            }

            // 创建签章工具PdfStamper ，最后一个boolean参数是否允许被追加签名
            // false的话，pdf文件只允许被签名一次，多次签名，最后一次有效
            // true的话，pdf可以被追加签名，验签工具可以识别出每次签名之后文档是否被修改
            PdfStamper stamper = PdfStamper.createSignature(pdfReader, tempArrayOutputStream, '\0', null, true);
            // 获取数字签章属性对象
            PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
//            appearance.setImage(Image.getInstance(IMAGE));
            appearance.setReason(signatureEntity.getReason());           // 签名的原因，显示在pdf签名属性中
            appearance.setLocation(signatureEntity.getLocation());       // 签名的地点，显示在pdf签名属性中

            // 设置签名的位置，页码，签名域名称，多次追加签名的时候，签名预名称不能一样 图片大小受表单域大小影响（过小导致压缩）
            // 签名的位置，是图章相对于pdf页面的位置坐标，原点为pdf页面左下角
            // 四个参数的分别是，图章左下角x，图章左下角y，图章右上角x，图章右上角y
            Rectangle rectangle = new Rectangle(signatureEntity.getRectllx(), signatureEntity.getRectlly(), signatureEntity.getRecturx(), signatureEntity.getRectury());
            try {
                appearance.setVisibleSignature(rectangle, signatureEntity.getPage(), signatureEntity.getFieldName());
                appearance.setSignatureGraphic(Image.getInstance(signatureEntity.getSealWatermarImg()));
                appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
            } catch (IllegalArgumentException e) {
                logger.error("====>>>> setVisibleSignature: " + e.getLocalizedMessage());
                e.printStackTrace();
            }

            // 签章时间
            appearance.setSignDate(signatureEntity.getSignDate());
            appearance.setCertificationLevel(signatureEntity.getCertificationLevel());

//            // 读取图章图片
//            Image sealAreaImg = Image.getInstance(signatureEntity.getSealAreaImg());
//            // 设置图章
//            appearance.setSignatureGraphic(sealAreaImg);
//            appearance.setRenderingMode(signatureEntity.getRenderingMode());
//
//
//            // ========== 添加图章（以水印的方式添加，避免压住文字）
//            if (StringUtil.isNotEmpty(signatureEntity.getSealWatermarImg()) && (new File(signatureEntity.getSealWatermarImg())).exists()) {
//                Image sealWatermarImg = Image.getInstance(signatureEntity.getSealWatermarImg());
//                // 设置背景图片
//                sealWatermarImg.setAbsolutePosition(signatureEntity.getRectllx(), signatureEntity.getRectlly());
//                sealWatermarImg.scaleAbsolute(signatureEntity.getRecturx() - signatureEntity.getRectllx(), signatureEntity.getRectury() - signatureEntity.getRectlly());    // 自定义大小
//                // 设置透明度,1为不透明，值越小透明度越高
//                PdfGState gs = new PdfGState();
//                gs.setFillOpacity(1f);
//                // 写入水印
//                PdfContentByte content = stamper.getUnderContent(signatureEntity.getPage());
//                content.beginText();
//                content.setGState(gs);
//                content.setTextMatrix(signatureEntity.getRectllx(), signatureEntity.getRectlly());
//                content.addImage(sealWatermarImg);
//                content.endText();
//            }

            // 摘要算法
            ExternalDigest digest = new BouncyCastleDigest();
            // 签名算法
            if (signatureEntity.getPk() != null) {
                ExternalSignature signature = new PrivateKeySignature(signatureEntity.getPk(), signatureEntity.getDigestAlgorithm(), signatureEntity.getProvider());
                // 关键点：调用签名算法进行签名
                // 这里可以改成调用格尔的java签名API进行操作
                // 调用itext签名方法完成pdf签章 数字签名格式，CMS,CADE
                MakeSignature.signDetached(appearance, digest, signature,
                        signatureEntity.getChain(), null, null, null, 0,
                        MakeSignature.CryptoStandard.CADES);
                inputStream = new ByteArrayInputStream(tempArrayOutputStream.toByteArray());
                // 定义输入流为生成的输出流内容，以完成多次签章的过程
                outputStream = new FileOutputStream(new File(signFileTargetPath));
                outputStream.write(tempArrayOutputStream.toByteArray());
            }
        } catch (Exception e) {
            logger.error("====>>>> pdfFilePath = " + pdfFilePath);
            logger.error("====>>>> sealWatermarImg = " + signatureEntity.getSealWatermarImg());
            logger.error("====>>>> ownerpwd = " + signatureEntity.getOwnerpwd());
            e.printStackTrace();
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.flush();
                    outputStream.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != tempArrayOutputStream) {
                    tempArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 取证书的详细信息
     *
     * @param pkPath     证书文件地址
     * @param ksPassword 证书密码
     * @return
     */
    public static CertEntity getCertInfo(String pkPath, String ksPassword) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        char[] ksp = ksPassword.toCharArray();    //keystore密码
        CertEntity certEntity = new CertEntity();
        // 将证书文件放入指定路径，并读取keystore ，获得私钥和证书链
        KeyStore keyStore = null;
        keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream(pkPath), ksp);

        String alias = keyStore.aliases().nextElement();
        // 获取私钥
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, ksp);
        // 得到证书链
        Certificate[] certificateChain = keyStore.getCertificateChain(alias);
        // 将数据封装成实体
        certEntity.setPrivateKey(privateKey);
        certEntity.setCertificateChain(certificateChain);
        return certEntity;
    }

    private static int i = 0;
    static List<float[]> arrays = new ArrayList<float[]>();
    static String sb;

    private static List<float[]> getKeyWords(PdfReader pdfReader, String keyWord) {
        try {
            int pageNum = pdfReader.getNumberOfPages();
            PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);
            for (i = 1; i <= pdfReader.getNumberOfPages(); i++) {
                pdfReaderContentParser.processContent(i, new RenderListener() {
                    @Override
                    public void renderText(TextRenderInfo textRenderInfo) {
                        String text = textRenderInfo.getText(); // 整页内容

                        if (null != text && text.contains(keyWord)) {
                            Float boundingRectange = textRenderInfo.getBaseline().getBoundingRectange();
                            sb = boundingRectange.x + "--" + boundingRectange.y + "---";

                            float[] resu = new float[3];
                            resu[0] = boundingRectange.x;
                            resu[1] = boundingRectange.y;
                            resu[2] = i;
                            arrays.add(resu);
                        }
                    }

                    @Override
                    public void renderImage(ImageRenderInfo arg0) {
                    }

                    @Override
                    public void endTextBlock() {
                    }

                    @Override
                    public void beginTextBlock() {
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrays;
    }
}

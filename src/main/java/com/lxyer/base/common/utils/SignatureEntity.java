package com.lxyer.base.common.utils;

import com.itextpdf.text.pdf.PdfSignatureAppearance;

import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Calendar;

/**
 * 签名信息实体
 */
public class SignatureEntity {

    private int page = 0;                   // 签章页码
    private String ownerpwd;                // 所有者密码
    private String reason;                  // 签名的原因，显示在pdf签名属性中
    private String location;                // 签名的地点，显示在pdf签名属性中
    private String digestAlgorithm;         // 摘要算法名称，例如SHA-1

    // 图章水印用于显示章，图章占位图片用于实际签章，两个图片叠加在一起实现签章功能
    private String sealAreaImg = "";        // 图章占位图片，要求全透明，用于电子签章的操作
    private String sealWatermarImg = "";    // 图章水印图片，用于视觉上的图章的显示

    private String postKeyword = "";        // 查找签章位置的关键字，如：〇
    private int offsetX = 0;                // 图章图片右下角坐标点向右的偏移量，值越大越往右
    private int offsetY = 0;                // 图章图片右下角坐标点向上的偏移量，值越大越往下
    private int width = 0;                  // 图章的宽度
    private int height = 0;                 // 图章的高度

    private String fieldName;               // 表单域名称
    private Certificate[] chain;            // 证书链
    private PrivateKey pk;                  // 签名私钥
    private int certificationLevel = 0;     // 批准签章
    private PdfSignatureAppearance.RenderingMode renderingMode; //表现形式：仅描述，仅图片，图片和描述，签章者和描述

    private String provider = null;
    private Calendar signDate = null;

    // 图章属性
    private float rectllx = 0;//图章左下角x
    private float rectlly = 0;//图章左下角y
    private float recturx = 0;//图章右上角x
    private float rectury = 0;//图章右上角y

    public String getPostKeyword() {
        return postKeyword;
    }

    public void setPostKeyword(String postKeyword) {
        this.postKeyword = postKeyword;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSealAreaImg() {
        return sealAreaImg;
    }

    public void setSealAreaImg(String sealAreaImg) {
        this.sealAreaImg = sealAreaImg;
    }

    public String getSealWatermarImg() {
        return sealWatermarImg;
    }

    public void setSealWatermarImg(String sealWatermarImg) {
        this.sealWatermarImg = sealWatermarImg;
    }

    public Calendar getSignDate() {
        return signDate;
    }

    public void setSignDate(Calendar signDate) {
        this.signDate = signDate;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getOwnerpwd() {
        return ownerpwd;
    }

    public void setOwnerpwd(String ownerpwd) {
        this.ownerpwd = ownerpwd;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDigestAlgorithm() {
        return digestAlgorithm;
    }

    public void setDigestAlgorithm(String digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Certificate[] getChain() {
        return chain;
    }

    public void setChain(Certificate[] chain) {
        this.chain = chain;
    }

    public PrivateKey getPk() {
        return pk;
    }

    public void setPk(PrivateKey pk) {
        this.pk = pk;
    }

    public int getCertificationLevel() {
        return certificationLevel;
    }

    public void setCertificationLevel(int certificationLevel) {
        this.certificationLevel = certificationLevel;
    }

    public PdfSignatureAppearance.RenderingMode getRenderingMode() {
        return renderingMode;
    }

    /**
     * //表现形式：仅描述，仅图片，图片和描述，签章者和描述
     *
     * @param renderingMode
     */
    public void setRenderingMode(PdfSignatureAppearance.RenderingMode renderingMode) {
        this.renderingMode = renderingMode;
    }

    public float getRectllx() {
        return rectllx;
    }

    public void setRectllx(float rectllx) {
        this.rectllx = rectllx;
    }

    public float getRectlly() {
        return rectlly;
    }

    public void setRectlly(float rectlly) {
        this.rectlly = rectlly;
    }

    public float getRecturx() {
        return recturx;
    }

    public void setRecturx(float recturx) {
        this.recturx = recturx;
    }

    public float getRectury() {
        return rectury;
    }

    public void setRectury(float rectury) {
        this.rectury = rectury;
    }
}

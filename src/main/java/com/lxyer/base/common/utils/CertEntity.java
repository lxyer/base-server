package com.lxyer.base.common.utils;

import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 * 签名证书信息实体
 */
public class CertEntity {

    // 获取私钥
    private PrivateKey privateKey = null;
    // 得到证书链
    private Certificate[] certificateChain = null;

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public Certificate[] getCertificateChain() {
        return certificateChain;
    }

    public void setCertificateChain(Certificate[] certificateChain) {
        this.certificateChain = certificateChain;
    }
}

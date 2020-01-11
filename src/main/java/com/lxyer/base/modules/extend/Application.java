package com.lxyer.base.modules.extend;

import lombok.Data;

@Data
public class Application {
    /**
     * 商户代码
     */
    private String merchantCode;
    /**
     * 业务类型
     */
    private String businType;
    /**
     * 放款渠道
     */
    private String loanChannel;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 贷款人
     */
    private String lenderName;
    /**
     * 统一社会信用代码
     */
    private String lenderUSCC;
    /**
     * 营业场所
     */
    private String lenderBusiGround;
    /**
     * 负责人
     */
    private String lenderChargeMan;
    /**
     * 职务
     */
    private String lenderTitle;
    /**
     * 授权代理人
     */
    private String authorizedAgent;
    /**
     * 合同文本MD5值
     */
    private String contractMD5;
    /**
     * 借款人姓名
     */
    private String borrowerName;
    /**
     * 性别
     */
    private String borrowerSex;
    /**
     * 出生年月
     */
    private String borrowerBirth;
    /**
     * 证件类型
     */
    private String borrowerIdType;
    /**
     * 证件号码
     */
    private String 证件类型;
    /**
     * 送达地址
     */
    private String borrowerDelvAddr;
    /**
     * 邮编
     */
    private String borrowerPostCode;
    /**
     * 其它送达方式
     */
    private String borrowerOtherDelv;
    /**
     * 传真
     */
    private String borrowerFax;
    /**
     * 电话
     */
    private String borrowerPhone;
    /**
     * 住址
     */
    private String borrowerAddr;
    /**
     * 手机号
     */
    private String borrowerMobile;
    /**
     * 微信号
     */
    private String borrowerWechat;
    /**
     * 电子邮箱
     */
    private String borrowerMailBox;
    /**
     * 其他联系方式
     */
    private String borrowerOthrCont;
    /**
     * 合同签订地
     */
    private String contractSignAddr;
    /**
     * 合同签署日期
     */
    private String contractSignDate;
    /**
     * 公证签署日期
     */
    private String authActSignDate;
    /**
     * 借款金额人民币
     */
    private String loanAmount;
    /**
     * 借款金额人民币（大写）
     */
    private String loanAmountWord;
    /**
     * 借款利率浮动方式
     */
    private String rateFloatMethod;
    /**
     * 借款利率浮动值
     */
    private String rateFloatValue;
    /**
     * 借款期限
     */
    private String loanTerm;
    /**
     * 逾期罚息利率
     */
    private String overdueInterest;
    /**
     * 用途不符罚息利率
     */
    private String inconsUseInterest;
    /**
     * 收款户名
     */
    private String recvAccName;
    /**
     * 收款账号
     */
    private String recvAccNo;
    /**
     * 收款账号开户行
     */
    private String recvAccOpenBank;
    /**
     * 还款户名
     */
    private String payAccName;
    /**
     * 还款账号
     */
    private String payAccNo;
    /**
     * 还款账号开户行
     */
    private String payAccOpenBank;
    /**
     * 姓名
     */
    private String faceDetName;
    /**
     * 性别
     */
    private String faceDetSex;
    /**
     * 民族
     */
    private String facdDetEthnic;
    /**
     * 出生日期
     */
    private String faceDetBirth;
    /**
     * 住址
     */
    private String faceDetAddr;
    /**
     * 身份证号码
     */
    private String faceDetIdNo;
    /**
     * 签发机关
     */
    private String faceDetAuthority;
    /**
     * 有效期限
     */
    private String faceDetValid;
    /**
     * 验证时间
     */
    private String faceDetVeryTime;
    /**
     * 验证结果
     */
    private String faceDetVeryRes;
    /**
     * 现场照片
     */
    private String faceDetPhoto;
    /**
     * 身份证照片
     */
    private String faceDetIdPic;
    /**
     * 公安局传递的照片
     */
    private String faceDetPolicPic;
    /**
     * 电子版借款合同
     */
    private String elecContract;
    /**
     * 当事人手写签名图片
     */
    private String borrowerHandSign;
    /**
     * 公证员编号
     */
    private String greffierNo;
    /**
     * 公证员名称
     */
    private String greffierName;
}
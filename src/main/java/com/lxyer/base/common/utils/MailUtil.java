package com.lxyer.base.common.utils;

import javax.mail.internet.MimeMessage;

import com.lxyer.base.modules.execution.vo.SendEmaiVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
/**
* @ClassName: MailUtil
* @Description: 发送邮件
* @author pc
* @date 2019年4月29日
*
*/
@Component
@Log4j2
public class MailUtil {
	
	@Autowired
	private JavaMailSender mailSender;

	private static String mailStyle = "<style type='text/css'>"
			+ "body,ol,ul,h1,h2,h3,h4,h5,h6,p,th,td,dl,dd,form,fieldset,legend,input,textarea,select{margin:0;padding:0}"
			+ "body{font:12px '微软雅黑','Arial Narrow',HELVETICA;background:#fff;-webkit-text-size-adjust:100%}"
			+ "a{color:#172c45;text-decoration:none}"
			+ "a:hover{text-decoration:underline}"
			+ "em{font-style:normal}"
			+ "li{list-style:none}"
			+ "img{border:0;vertical-align:middle}"
			+ "table{border-collapse:collapse;border-spacing:0}"
			+ "p{word-wrap:break-word}"
			+ ".clearfix:after{content:''; display:block; visibility:hidden; height:0; clear:both;}" 
			+ ".clearfix{zoom:1;}" 
			+ "body{ }" 
			+ ".ht_box{ width:80%; margin:0 auto; background:#fff; padding:20px 40px; box-sizing:border-box;}" 
			+ ".ht_box h1{ height:40px; line-height:40px; font-size:24px; text-align:center;}" 
			+ ".ht_box p{line-height:30px; background:#fff; color:#000; font-size:16px;}" 
			+ "p.textIndex{ text-indent:2em;}" 
			+ "p.textright{ text-align:right;}" 
			+ "p.mgtop{ margin-top:20px;}" 
			+ "p.mgbtm2{ margin-bottom:20px;}" 
			+ "p.mgbtm6{ margin-bottom:60px;}" 
			+ "</style>";
	
	static {
        System.setProperty("mail.mime.splitlongparameters","false");
   }
	
	public boolean send(SendEmaiVo req) {
		log.info("------------------------SendMailThread  begin send ------------------------");
		boolean flag = false;
		try {
			log.info("------------------------SendMailThread  sendTo：【"
					+ req.getReceUserMail() + "】  userName：【"
					+ req.getReceUserName()
					+ "】      begin------------------------");
			sendMail(req);
			flag = true;
			log.info("------------------------SendMailThread end success------------------------");
		} catch (Exception e) {
			log.error("------------------------SendMailThread error ------------------------",e);
		}
		return flag;
	}
	
	public void sendMail(SendEmaiVo sendEmaiVo){
		try {
			log.info("------------------------sendMail方法开始执行 ------------------------");
			MimeMessage message = mailSender.createMimeMessage();
	    	MimeMessageHelper helper=new MimeMessageHelper(message,true,"UTF-8");
	        helper.setFrom("");
	        helper.setTo(sendEmaiVo.getReceUserMail());
	        helper.setSubject(sendEmaiVo.getSubject());
	        helper.setText(getMailContent(sendEmaiVo).toString(), true);
	        mailSender.send(message);
		}catch(Exception e){
			log.error(sendEmaiVo.getReceUserMail()+"邮箱发送失败",e);
			e.printStackTrace();
		}
	}

	public static String getMailContent(SendEmaiVo req) {
		return verifyEmail(req).toString();
	}
	
	/**
	 * 邮件内容
	 * @param 
	 * @return
	 */
	public static StringBuffer verifyEmail(SendEmaiVo sendEmaiVo){
		StringBuffer sendContent = new StringBuffer();
		sendContent.append("<html><head>")
							.append(mailStyle)
							.append("</head><body><div class='ht_box'>")
							.append("<h1>"+sendEmaiVo.getSubject()+"</h1>")
							.append(" <p class='textIndex mgtop mgbtm6'>")
							.append("核实项目编号为:")
							.append(sendEmaiVo.getProjectCode())
							.append("的贷款用户记录。")
							.append(sendContent+"</p>")
							.append("<p class='textright'>北京市中信公证处</p>")
							.append("<p class='textright mgbtm6'>")
							.append(DateUtils.getcurrentYYYYMMDD())
							.append("</p>")
							.append("<p>地址：中国•北京市西城区阜成门外大街2号，万通金融中心4层和5层</p>")
							.append("<p>邮政编码：100037</p>")
							.append("<p>电话：010-68442299</p>")
							.append("<p>公证员").append(sendEmaiVo.getLecerNo().equals("1")?"霍妍":sendEmaiVo.getLecerNo().equals("2")?"吕东梅":sendEmaiVo.getLecerNo().equals("3")?"施扬":sendEmaiVo.getLecerNo().equals("4")?"王艳艳":"张伟")
							.append(" 电话:")
							.append(sendEmaiVo.getLecerNo().equals("1")?"010-81136567":sendEmaiVo.getLecerNo().equals("2")?"010-81136548":sendEmaiVo.getLecerNo().equals("3")?"010-81138901":sendEmaiVo.getLecerNo().equals("4")?"010-81138900":"010-81136549")
							.append("</p>")
							.append("<p>传真：010-68442200</p>")
							.append("<p>电子邮箱：bjszxgzc@163.com</p>")
							.append("<p>网址（Http）：www.bjgzc.com</p>")
							.append("</div></body></html>");
		return sendContent;
	}
	
}

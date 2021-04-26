package com.zh.ch.bigdata.base.util.mail;

import com.sun.mail.util.MailSSLSocketFactory;
import com.zh.ch.bigdata.base.util.exception.ProjectException;
import com.zh.ch.bigdata.base.util.properties.PropertiesAnalyzeUtil;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * 发送复杂邮件
 *
 * @author xiaozhch5
 * @date 20210426
 */

public class ComplexEmail {

    public Properties configs;

    public Session session;

    public MimeMessage mimeMessage;

    public String userEmail;

    public String userAuthorizationCode;

    public String targetEmail;

    public String mailConfigsFilePath;

    public void setMailConfigs(String mailConfigsFilePath) throws ProjectException, IOException, ClassNotFoundException, GeneralSecurityException {
        this.mailConfigsFilePath = mailConfigsFilePath;
        // 1. 读取配置信息
        this.configs = PropertiesAnalyzeUtil.loadProperties(this.mailConfigsFilePath, Class.forName("com.zh.ch.bigdata.base.util.mail.MailConstantConfigs"));
        MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
        mailSSLSocketFactory.setTrustAllHosts(true);
        this.configs.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);
        this.session = Session.getDefaultInstance(this.configs, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //传入发件人的姓名和授权码
                return new PasswordAuthentication(userEmail, userAuthorizationCode);
            }
        });
    }

    public void setUserConfigs(String userConfigsFilePath) throws ProjectException, IOException {
        this.userEmail = PropertiesAnalyzeUtil.getProperty(userConfigsFilePath, "userEmail");
        this.userAuthorizationCode = PropertiesAnalyzeUtil.getProperty(userConfigsFilePath, "userAuthorizationCode");
        this.targetEmail = PropertiesAnalyzeUtil.getProperty(userConfigsFilePath, "targetEmail");
    }


    public void send() throws MessagingException, ProjectException, IOException {

        // 2. 通过session获取transport对象
        Transport transport = session.getTransport();

        //3. 通过transport对象邮箱用户名和授权码连接邮箱服务器
        transport.connect(PropertiesAnalyzeUtil.getProperty(this.mailConfigsFilePath, MailConstantConfigs.mailHost), userEmail, userAuthorizationCode);

        //4. 创建邮件,传入session对象
        setMessage(session);

        //5. 发送邮件
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());

        //6. 关闭连接
        transport.close();
    }

    public void setMessage(Session session) throws MessagingException {
        //消息的固定信息
        this.mimeMessage = new MimeMessage(session);

        //发件人
        mimeMessage.setFrom(new InternetAddress(userEmail));
        //收件人
        mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(targetEmail));
        //邮件标题
        mimeMessage.setSubject("带图片和附件的邮件");

        //邮件内容
        //准备图片数据
        MimeBodyPart image = new MimeBodyPart();
        DataHandler handler = new DataHandler(new FileDataSource("/home/xiaozhch5/Downloads/cover.png"));
        image.setDataHandler(handler);
        image.setContentID("test.png"); //设置图片id

        //准备文本
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("这是一段文本<img src='cid:test.png'>","text/html;charset=utf-8");

        //附件
        MimeBodyPart appendix = new MimeBodyPart();
        appendix.setDataHandler(new DataHandler(new FileDataSource("/home/xiaozhch5/Downloads/cover.png")));
        appendix.setFileName("test.txt");

        //拼装邮件正文
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(image);
        mimeMultipart.addBodyPart(text);
        mimeMultipart.setSubType("related");//文本和图片内嵌成功

        //将拼装好的正文内容设置为主体
        MimeBodyPart contentText = new MimeBodyPart();
        contentText.setContent(mimeMultipart);

        //拼接附件
        MimeMultipart allFile = new MimeMultipart();
        allFile.addBodyPart(appendix);//附件
        allFile.addBodyPart(contentText);//正文
        allFile.setSubType("mixed"); //正文和附件都存在邮件中，所有类型设置为mixed


        //放到Message消息中
        mimeMessage.setContent(allFile);
        mimeMessage.saveChanges();//保存修改
    }


    public static void main(String[] args) throws ProjectException, MessagingException, GeneralSecurityException, IOException, ClassNotFoundException {
        ComplexEmail complexEmail = new ComplexEmail();
        complexEmail.setMailConfigs("/data/bigdata/bigdata-tools/base/src/main/resources/testMailConfigs.properties");
        complexEmail.setUserConfigs("/data/bigdata/bigdata-tools/base/src/main/resources/userConfigs.properties");
        complexEmail.send();
    }





}

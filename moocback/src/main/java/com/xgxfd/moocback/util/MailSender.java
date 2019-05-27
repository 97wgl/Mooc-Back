package com.xgxfd.moocback.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Service
public class MailSender implements InitializingBean {


    private JavaMailSender mailSender;

    @Autowired
    private Configuration freeMarkerConfiguration;

    /**
     * @Author SZ02496
     * @Description 发送(异步实现)邮件函数  此处基本功能已实现
     * 待优化
     * @Date 14:29 2019/4/1
     * @param to 接收对象
     * @param subject 标题
     * @param template 模板  主要内容
     * @param model  模板所需参数 此处为验证码
     * @return boolean 发送状态
     **/

    public   boolean sendWithHTMLTemplate(String to, String subject, String templateName, Map<String,Object> model){

        try{
            String nick = MimeUtility.encodeText("MoocBack");
            InternetAddress from = new InternetAddress(nick+"<2310765090@qq.com>");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            try {
                Template template = freeMarkerConfiguration.getTemplate(templateName);
                try {
                    String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                    mimeMessageHelper.setText(text, true);
                    mailSender.send(mimeMessage);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;

        }catch (Exception e){
            log.error("发送邮件失败"+e.getMessage());
            return false;
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //在初始化 bean 中设置的发送信息 这些配置 java main方法需要单独写 才能测
        mailSender = new JavaMailSenderImpl();
        ((JavaMailSenderImpl) mailSender).setUsername("2310765090@qq.com");
        ((JavaMailSenderImpl) mailSender).setPassword("gwshumyvhvkkecec");  //qq 邮箱此处是授权码
        //如果开源练习的代码 请友好对待此处账号
        ((JavaMailSenderImpl) mailSender).setHost("smtp.qq.com");
        ((JavaMailSenderImpl) mailSender).setPort(465);
        ((JavaMailSenderImpl) mailSender).setProtocol("smtps");
        ((JavaMailSenderImpl) mailSender).setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable",true);
        ((JavaMailSenderImpl) mailSender).setJavaMailProperties(javaMailProperties);
    }
}

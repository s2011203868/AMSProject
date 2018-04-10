package com.purple.ams.ssm.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.Session;

import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
/**
 * @ClassName: MailUtil 
 * @Description: 邮件工具类
 * @author: PurpleSoft@一禅
 * @date: 2018年4月4日 下午2:47:34
 */
public class MailUtil {

    public static void send(String message) throws IOException, javax.mail.MessagingException{
        Properties props = new Properties();
        props.load(MailUtil.class.getClassLoader().getResourceAsStream("email_template.properties"));
        String host = props.getProperty("host");//获取服务器主机
        String uname = props.getProperty("uname");//获取用户名
        String pwd = props.getProperty("pwd");//获取密码
        String from = props.getProperty("from");//获取发件人
        String to = props.getProperty("to");//获取收件人
        String subject = props.getProperty("subject");//获取主题
        String content = props.getProperty("content");//获取邮件内容
        content = MessageFormat.format(content, message);//替换{0}
        
        Session session = MailUtils.createSession(host, uname, pwd);//得到session
        Mail mail = new Mail(from, to, subject, content);//创建邮件对象
        MailUtils.send(session, mail);//发邮件！
    }
}

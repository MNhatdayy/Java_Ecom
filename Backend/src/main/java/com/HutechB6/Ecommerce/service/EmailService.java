package com.HutechB6.Ecommerce.service;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
public class EmailService {
    public EmailService(){

    }
    public static void sendMail(String toEmail, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Server
        props.put("mail.smtp.port", "587"); // Cổng TLS
        props.put("mail.smtp.auth", "true"); // Xác thực
        props.put("mail.smtp.starttls.enable", "true"); // TLS
        final String fromEmail = "nhat23894@gmail.com";
        final String password = "kntx afwi ueot ixbq";
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });
        try {
            // Tạo email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // Gửi email
            Transport.send(message);
            System.out.println("Email đã được gửi thành công!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

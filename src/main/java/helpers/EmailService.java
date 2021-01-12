package helpers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class EmailService {
    
    private String host = "in-v3.mailjet.com";
    private String port = "2525";
    private String accountName = "projekt.to.habeja@gmail.com";
    private String username = "c53b49ee0fdab007daaac19e231d789f";
    private String password = "ef4d38f419845e37039bed8949ada3c7";

    public void sendMail(String to, String subject, String content) {
        Properties prop = new Properties();
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(accountName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setText(content);
            mimeBodyPart.setHeader("Content-Type", "text/plain; charset=UTF-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Send successfully");
    }
}

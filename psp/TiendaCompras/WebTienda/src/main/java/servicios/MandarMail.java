/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;


import lombok.extern.log4j.Log4j2;
//import org.apache.commons.mail.Email;
//import org.apache.commons.mail.EmailException;
//import org.apache.commons.mail.SimpleEmail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 *
 * @author oscar
 */
@Log4j2
public class MandarMail {


//    public void mandarMail(String to, String msg, String subject) {
//        try {
//            Email email = new SimpleEmail();
//
//            email.setHostName("smtp.gmail.com");
//            email.setSmtpPort(Integer.parseInt("587"));
//            email.setAuthentication("alumnosdamquevedo@gmail.com", "quevedo2020");
//            //email.setSSLOnConnect(true);
//            email.setStartTLSEnabled(true);
//            email.setFrom("alumnosDamQuevedo@gmail.com");
//            email.setSubject(subject);
//            email.setContent(msg,"text/html");
//            email.addTo(to);
//
//            email.send();
//        } catch (EmailException ex) {
//
//            Logger.getLogger(MandarMail.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }


    public void generateAndSendEmail(String to, String msg, String subject) throws AddressException, MessagingException {
        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage generateMailMessage;

        // Step1

        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", Integer.parseInt("587"));
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");

        // Step2

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        generateMailMessage.setSubject(subject);
        String emailBody = msg;
        generateMailMessage.setContent(emailBody, "text/html");


        // Step3

        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com",
                "alumnosdamquevedo@gmail.com",
                "quevedo2020");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}

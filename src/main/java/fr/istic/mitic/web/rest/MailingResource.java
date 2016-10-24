package fr.istic.mitic.web.rest;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

/**
 * Created by Yoan on 24/10/16.
 */

@RestController
@RequestMapping("/api")
public class MailingResource {

    private final Logger log = LoggerFactory.getLogger(FormResource.class);

    @RequestMapping(value = "/mailing/{data}",
        method = RequestMethod.GET)
    @Timed
    public void requetePerso(@PathVariable String[] data, Pageable pageable) {
    	
    	String host = "smtp.gmail.com";

    	Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", host);
    	
        Session session = Session.getInstance(props,
              new javax.mail.Authenticator() {
                 protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(data[0], data[1]);
                }
        });
        for (int i = 4; data.length > 3 && i < data.length; i++) {
        	try {
                // Create a default MimeMessage object.
                Message message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(data[0]));

                // Set To: header field of the header.
                message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(data[i]));

                // Set Subject: header field
                message.setSubject(data[2]);

                // Now set the actual message
                message.setText(data[3]);

                // Send message
                Transport.send(message);

                System.out.println("Sent message successfully from " + data[0] + " to " + data[i]);

             } catch (MessagingException e) {
                   throw new RuntimeException(e);
             }
        }
        
    }
}

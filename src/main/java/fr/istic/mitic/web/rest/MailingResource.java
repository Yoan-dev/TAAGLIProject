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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

@RestController
@RequestMapping("/api")
public class MailingResource {

    private final Logger log = LoggerFactory.getLogger(FormResource.class);

    @RequestMapping(value = "/mailing/{data}",
        method = RequestMethod.GET)
    @Timed
    public void requetePerso(@PathVariable String[] data, Pageable pageable) {

    	/* Ici, on souhaiterait utiliser le serveur SMTP de l'université (univ-rennes1)
    	 * cependant, il faut une autorisation pour permettre l'accès à l'adresse par
    	 * des applications moins sécurisés.
    	 * Nous avons donc décidé de faire nos tests avec gmail, qui permets une
    	 * activation/désactivation de l'accès par des applications moins sécurisés
    	 * très simple à réaliser
    	 */
    	String host = "smtp.gmail.com";

    	Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", host);

        // création d'une session avec authentification
        Session session = Session.getInstance(props,
              new javax.mail.Authenticator() {
                 protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(data[0], data[1]);
                }
	        });

        // pour chaque destinataire
        for (int i = 4; i < data.length - 1; i++) {
	       	try {
	       		Message message = new MimeMessage(session);
	       		message.setFrom(new InternetAddress(data[0]));

	       		// récupération du destinataire
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(data[i]));

	            // récupération du sujet
	            message.setSubject(data[2]);

	            // récupération du message texte
	            message.setText(data[3]);

	            // envoi
	            Transport.send(message);

	            System.out.println("Sent message successfully from " + data[0] + " to " + data[i]);

	        } catch (MessagingException e) {
	              throw new RuntimeException(e);
	        }
        }
    }
}

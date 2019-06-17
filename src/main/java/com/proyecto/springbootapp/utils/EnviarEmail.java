package com.proyecto.springbootapp.utils;

import org.springframework.stereotype.Component;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

@Component
public class EnviarEmail{


public void mailRegistro(String email, String password) {


    String host="smtp.gmail.com";
    final String user="d15juan2009@gmail.com";
    final String pass="sifnpymzvcskruiq";
    String to=email;

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");


    Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user,pass);
                }
            });

    //Compose the message
    try {


        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(user));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Mail Subject");

        String msg = "Gracias por realizar su registro \n" +
                "A continuación le recordamos sus datos de acceso: \n" +
                "Email: " + email + "\n" +
                "Contraseña: " + password + "\n \n" +
                "Le deseamos una agradable estancia en nuestro hotel.";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);

    } catch (MessagingException e) {e.printStackTrace();}
}
}
package com.proyecto.springbootapp.controller;

import com.proyecto.springbootapp.entity.UsuariosEntity;
import com.proyecto.springbootapp.repository.UsuariosRepository;
import com.proyecto.springbootapp.utils.EnviarEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    UsuariosRepository usuariosRepository;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Iterable<UsuariosEntity> getAllUsers() {

        return usuariosRepository.findAll();
    }
    @RequestMapping(value = "/{idUsuario}", method = RequestMethod.GET)
    @ResponseBody
    public  UsuariosEntity getUsuarioByIdUsuario(@PathVariable("idUsuario") int idUsuario) {
        return usuariosRepository.findByIdUsuario(idUsuario);
    }
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public String createUsuario(@RequestParam String nombre, @RequestParam String apellidos,@RequestParam String email,@RequestParam String password) throws MessagingException {
        email = email.toLowerCase();

        if (usuariosRepository.existsUsuarioByEmail(email)){
            return "";
        }
        else{
            usuariosRepository.createUsuario(nombre, apellidos, email, password);
          mailRegistro(email, password);
            return "ok";
        }
    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public void deleteUsuario(@RequestParam int idUsuario){
        usuariosRepository.deleteUsuario(idUsuario);

    }

    @RequestMapping(value ="/login", method = RequestMethod.GET)
    public UsuariosEntity login(@RequestParam String email,@RequestParam String password){
        return usuariosRepository.findByEmailAndPassword(email, password);
    }

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
            message.setSubject("Su registro se ha completado");

            String msg = "Gracias por realizar su registro <br>" +
                    "A continuación le recordamos sus datos de acceso: <br>" +
                    "Email: " + email + "<br>" +
                    "Contraseña: " + password + "<br><br>" +
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

package com.proyecto.springbootapp.controller;

import com.proyecto.springbootapp.entity.UsuariosEntity;
import com.proyecto.springbootapp.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * clase controladora de los usuarios, incluye un mapeo por defecto que la identifica
 */
@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    UsuariosRepository usuariosRepository;

    /**
     * función que devuelve un listado de usuarios según su tipo
     * @param tipoUsuario para filtar el resultado
     * @return lista de usuarios según su tipo
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Iterable<UsuariosEntity> getAllUsers(@RequestParam char tipoUsuario) {

        return usuariosRepository.findUsuariosByTipoUsuario(tipoUsuario);
    }

    /**
     * función que devuelve un usuario según su id
     * @param idUsuario id del usuario
     * @return usuario
     */
    @RequestMapping(value = "/usuario", method = RequestMethod.GET)
    @ResponseBody
    public  UsuariosEntity getUsuarioByIdUsuario(@RequestParam int idUsuario) {
        return usuariosRepository.findByIdUsuario(idUsuario);
    }

    /**
     * función que crea un usuario
     * @param nombre
     * @param apellidos
     * @param email
     * @param password
     * @param tipoUsuario por defecto será de tipo usuario
     * @return respuesta "" en caso negativo, "ok" en caso positivo
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public String createUsuario(@RequestParam String nombre, @RequestParam String apellidos,@RequestParam String email,@RequestParam String password, Character tipoUsuario) {

        tipoUsuario = new Character('U');
        email = email.toLowerCase();

        if (usuariosRepository.existsUsuarioByEmail(email)){
            return "";
        }
        else{
            usuariosRepository.createUsuario(nombre, apellidos, email, password, tipoUsuario);
          mailRegistro(email, password);
            return "ok";
        }
    }

    /**
     * función que elimina un usuario según su id
     * @param idUsuario
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public void deleteUsuario(@RequestParam int idUsuario){
        usuariosRepository.deleteUsuario(idUsuario);

    }

    /**
     * función que valida que los campos incluidos estén en la base de datos para hacer login
     * @param email
     * @param password
     * @return usuario en caso de que exista, valor vacío en caso negativo
     */
    @RequestMapping(value ="/login", method = RequestMethod.GET)
    public UsuariosEntity login(@RequestParam String email,@RequestParam String password){
        return usuariosRepository.findByEmailAndPassword(email, password);
    }

    /**
     * función que modifica un usuario según su id
     * @param nombre
     * @param apellidos
     * @param email
     * @param idUsuario id del usuario a modificar
     */
    @RequestMapping(value = "/modificar", method = RequestMethod.GET)
    @ResponseBody
    public  void updateUsuario(@RequestParam String nombre, @RequestParam String apellidos, @RequestParam String email, @RequestParam int idUsuario) {
         usuariosRepository.updateUsuario(nombre, apellidos, email, idUsuario);
    }

    /**
     * clase que envía un mail al usuario cuando realiza un registro, incluye las propiedades para acceder al
     * servicio SMTP y el correo usado para el envío de mails
     * @param email al que se envía el mail, además de ser un dato de acceso
     * @param password dato de acceso al aplicativo
     */
    public void mailRegistro(String email, String password) {


        final String user="d15juan2009@gmail.com";
        final String pass="sifnpymzvcskruiq";

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

        try {


            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Su registro se ha completado");

            String msg = "<meta charset=\"utf-8\">  " +
                    "Gracias por realizar su registro <br>" +
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

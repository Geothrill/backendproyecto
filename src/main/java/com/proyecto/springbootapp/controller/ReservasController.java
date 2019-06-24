package com.proyecto.springbootapp.controller;

import com.proyecto.springbootapp.entity.ReservasEntity;
import com.proyecto.springbootapp.repository.ReservasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * clase controladora de las reservas, incluye un mapeo por defecto que la identifica
 */
@RestController
@RequestMapping("/reservas")
public class ReservasController {

    @Autowired
    ReservasRepository reservasRepository;

    /**
     * función que devuelve todas las reservas de habitaciones
     * @return lista de reservas
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<ReservasEntity> getAllReservas() {

        return reservasRepository.findAll();
    }

    /**
     * función que crea una reserva de habitación, una vez se completa recoge dicha reserva y envía un email al usuario
     * @param fechaReserva
     * @param fechaEntrada
     * @param fechaSalida
     * @param email
     * @param idHabitaciones
     * @param idPension
     * @param idReservaCompartida id de la reserva completa
     * @throws ParseException
     */

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public @ResponseBody
    void addReserva
            (@RequestParam String fechaReserva, @RequestParam String fechaEntrada, @RequestParam String fechaSalida,
             @RequestParam String email, @RequestParam int idHabitaciones, @RequestParam int idPension, @RequestParam int idReservaCompartida) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date fechaEntradaDate = format.parse(fechaEntrada);
        Date fechaSalidaDate = format.parse(fechaSalida);
        Date fechaReservaDate = format.parse(fechaReserva);

        reservasRepository.addReserva(fechaReservaDate, fechaEntradaDate, fechaSalidaDate, email, idHabitaciones, idPension, idReservaCompartida);

        ReservasEntity reserva = reservasRepository.getLastReservaByUser();

        mailReserva(reserva);

    }

    /**
     * función que elimina una reserva
     * @param idReservas id de la reserva a eliminar
     */
    @RequestMapping(value = "/deleteone", method = RequestMethod.GET)
    public @ResponseBody
    void delete1Reserva(@RequestParam int idReservas) {

         reservasRepository.delete1Reserva(idReservas);
    }

    /**
     * función que añade una valoración a la reserva de una habitación
     * @param idReservas id de la reserva a la que añadimos la valoración
     */
    @RequestMapping(value = "/addValoracion", method = RequestMethod.GET)
    public @ResponseBody
    void addValoracion(@RequestParam int idReservas) {

        reservasRepository.addValoracionToReserva(idReservas);
    }

    /**
     * función que elimina toda una reserva
     * @param idReservaCompartida id de la reserva que engloba todas las habitaciones
     */
    @RequestMapping(value = "/deletegroup", method = RequestMethod.GET)
    public @ResponseBody
    void deleteGroupReserva(@RequestParam int idReservaCompartida) {

        reservasRepository.deleteGroupReserva(idReservaCompartida);
    }

    /**
     * función que devuelve la suma del coste de todas las habitaciones
     * @param email necesario para saber el usuario
     * @param idReservaCompartida reserva afectada
     * @return el sumatorio del precio de todas las habitaciones reservadas
     */
    @RequestMapping(value = "/precioTotal", method = RequestMethod.GET)
    public @ResponseBody
    int sumPrecioReservaCompleta(@RequestParam String email,@RequestParam int idReservaCompartida) {

        return reservasRepository.sumPrecioReservaCompleta(email, idReservaCompartida);

    }

    /**
     * función que retorna todas las habitaciones incluidas en una reserva concreta
     * @param email necesario para saber el usuario
     * @param idReservaCompartida reserva afectada
     * @return todas las reservas de habitaciones incluidas en una reserva
     */
    @RequestMapping(value = "/totalReservaCompartida", method = RequestMethod.GET)
    public @ResponseBody
    List<ReservasEntity> totalReservasPorReservaCompartida(@RequestParam String email, @RequestParam int idReservaCompartida) {

        return reservasRepository.getReservas(email, idReservaCompartida);

    }

    /**
     * función que devuelve todas las reservas de habitaciones de un usuario
     * @param email necesario para saber el usuario
     * @return lista de habitaciones reservadas
     */
    @RequestMapping(value = "/usuario", method = RequestMethod.GET)
    public @ResponseBody
    List<Integer> getReservasUsuario(@RequestParam String email) {

        return reservasRepository.reservasUsuario( email);

    }

    /**
     * función que devuelve el valor máximo de idReservaCompartida
     * @return valor máximo de idReservaCompartida
     */
    @RequestMapping(value = "/max", method = RequestMethod.GET)
    public @ResponseBody
    int getMaxIdReservaCompartida() {

        return reservasRepository.getMaxIdReservaCompartida();

    }

    /**
     * clase que envía un mail al usuario cuando realiza una reserva, incluye las propiedades para acceder al
     * servicio SMTP y el correo usado para el envío de mails
     * @param reserva datos de la reserva para mostrarlos en el mail
     */
    public void mailReserva(ReservasEntity reserva) {


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
                    Message.RecipientType.TO, InternetAddress.parse(reserva.getUsuario().email));
            message.setSubject("Su reserva ha sido añadida");

            String msg = "<meta charset=\"utf-8\">" +
                    "Gracias por reservar con nosotros <br>" +
                    "A continuación le recordamos los detalles de su reserva: <br>" +
                    "Fecha de entrada: " + reserva.fechaEntrada + "<br>" +
                    "Fecha de salida: " + reserva.fechaSalida + "<br>" +
                    "Número de la habitación: " + reserva.getHabitaciones().numHabitacion + "<br>" +
                    "Pensión seleccionada: " + reserva.getPensiones().tipo + "<br>" +
                    "Precio total: " + reserva.precio + "€<br><br>" +
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

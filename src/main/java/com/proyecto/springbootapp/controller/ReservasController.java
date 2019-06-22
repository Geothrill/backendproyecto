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

@RestController
@RequestMapping("/reservas")
public class ReservasController {

    @Autowired
    ReservasRepository reservasRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<ReservasEntity> getAllReservas() {

        return reservasRepository.findAll();
    }

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

    @RequestMapping(value = "/deleteone", method = RequestMethod.GET)
    public @ResponseBody
    void delete1Reserva(@RequestParam int idReservas) {

         reservasRepository.delete1Reserva(idReservas);
    }

    @RequestMapping(value = "/deletegroup", method = RequestMethod.GET)
    public @ResponseBody
    void deleteGroupReserva(@RequestParam int idReservaCompartida) {

        reservasRepository.deleteGroupReserva(idReservaCompartida);
    }


    @RequestMapping(value = "/precioTotal", method = RequestMethod.GET)
    public @ResponseBody
    int sumPrecioReservaCompleta(@RequestParam String email,@RequestParam int idReservaCompartida) {

        return reservasRepository.sumPrecioReservaCompleta(email, idReservaCompartida);

    }

    @RequestMapping(value = "/totalReservaCompartida", method = RequestMethod.GET)
    public @ResponseBody
    List<ReservasEntity> totalReservasPorReservaCompartida(@RequestParam String email, @RequestParam int idReservaCompartida) {

        return reservasRepository.getReservas(email, idReservaCompartida);

    }

    @RequestMapping(value = "/totalReservaCompartida/habitaciones", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<ReservasEntity> habitacionesIncluidasEnReserva(@RequestParam String email) {

        return reservasRepository.findReservasByEmail( email);

    }
    @RequestMapping(value = "/usuario", method = RequestMethod.GET)
    public @ResponseBody
    List<Integer> getReservasUsuario(@RequestParam String email) {

        return reservasRepository.reservasUsuario( email);

    }
    @RequestMapping(value = "/max", method = RequestMethod.GET)
    public @ResponseBody
    int getMaxIdReservaCompartida() {

        return reservasRepository.getMaxIdReservaCompartida();

    }

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

        //Compose the message
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

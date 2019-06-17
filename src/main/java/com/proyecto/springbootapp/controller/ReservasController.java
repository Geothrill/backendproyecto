package com.proyecto.springbootapp.controller;

import com.proyecto.springbootapp.entity.ReservasEntity;
import com.proyecto.springbootapp.repository.ReservasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @RequestMapping(value = "/habitacion/precio", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<ReservasEntity> getReservasByHabitacionesPrecio(@RequestParam Double precio1, @RequestParam Double precio2) {

        return reservasRepository.findReservasByHabitacionesPrecio(precio1, precio2);
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public @ResponseBody
    ReservasEntity newReserva
            (@RequestParam String fechaReserva, @RequestParam String fechaEntrada, @RequestParam String fechaSalida,
             @RequestParam String email, @RequestParam int idHabitaciones, @RequestParam int idPension) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date fechaEntradaDate = format.parse(fechaEntrada);
        Date fechaSalidaDate = format.parse(fechaSalida);
        Date fechaReservaDate = format.parse(fechaReserva);

        return reservasRepository.newReserva(fechaReservaDate, fechaEntradaDate, fechaSalidaDate, email, idHabitaciones, idPension);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody
    ReservasEntity deleteReserva(@RequestParam int idReservas) {

        return reservasRepository.deleteReserva(idReservas);
    }

    @RequestMapping(value = "/usuario", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<ReservasEntity> getReservaByIdUsuario(@RequestParam int idUsuario) {

        return reservasRepository.findReservasByIdUsuario(idUsuario);

    }
}

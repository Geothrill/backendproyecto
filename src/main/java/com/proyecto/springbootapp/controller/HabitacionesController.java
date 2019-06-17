package com.proyecto.springbootapp.controller;

import com.proyecto.springbootapp.entity.HabitacionesEntity;
import com.proyecto.springbootapp.repository.HabitacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/habitaciones")
public class HabitacionesController {

    @Autowired
    HabitacionesRepository habitacionesRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<HabitacionesEntity> getAllHabitaciones() {

        return habitacionesRepository.findAll();
    }
    @RequestMapping(value = "/reservar/sdf", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<HabitacionesEntity> getHabitacionesLibres(@RequestParam String fechaEntrada, @RequestParam String fechaSalida, @RequestParam Double precio1, @RequestParam Double precio2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date   fechaEntradaDate       = format.parse ( fechaEntrada );
        Date fechaSalidaDate = format.parse(fechaSalida);

        return habitacionesRepository.findHabitacionesLibresPrecioBetween(fechaEntradaDate, fechaSalidaDate, precio1, precio2);
    }

    @RequestMapping(value = "/reservar", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<HabitacionesEntity> getHabitacionesLibresWithOcupantes(@RequestParam String fechaEntrada, @RequestParam String fechaSalida, @RequestParam Double precio1, @RequestParam Double precio2, @RequestParam int ocupantes) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date fechaEntradaDate = format.parse(fechaEntrada);
        Date fechaSalidaDate = format.parse(fechaSalida);
        Iterable<HabitacionesEntity> respuesta = habitacionesRepository.findHabitacionesLibresPrecioBetweenOcupantes(fechaEntradaDate, fechaSalidaDate, precio1, precio2, ocupantes);

        return respuesta;
    }
    @RequestMapping(value = "/idHabitaciones", method = RequestMethod.GET)
    public @ResponseBody
    HabitacionesEntity getHabitacionesByIdHabitaciones(@RequestParam int idHabitaciones) {

        return habitacionesRepository.findByIdHabitaciones(idHabitaciones);
    }
    @RequestMapping(value = "/precio", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<HabitacionesEntity> GETabitacionesByPrecioBetween(@RequestParam Double precio1, @RequestParam Double precio2) {

        return habitacionesRepository.findByPrecioBetween(precio1, precio2);
    }
    @RequestMapping(value = "/precio/asc", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<HabitacionesEntity> getHabitacionesByPrecioBetweenOrderByPrecioDesc(@RequestParam Double precio1, @RequestParam Double precio2) {

        return habitacionesRepository.findByPrecioBetweenOrderByPrecioDesc(precio1, precio2);
    }

    @RequestMapping(value= "libres", method = RequestMethod.GET)
    public @ResponseBody Iterable<HabitacionesEntity> getHabitacionesLibres(@RequestParam String fechaEntrada, @RequestParam String fechaSalida) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaEntradaDate = format.parse(fechaEntrada);
        Date fechaSalidaDate = format.parse(fechaSalida);


        return habitacionesRepository.findHabitacionesLibres(fechaEntradaDate,fechaSalidaDate);

    }


    @RequestMapping(value = "/ocupantes", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<HabitacionesEntity> findByOcupantes(@RequestParam int ocupantes) {

        return habitacionesRepository.findByOcupantes(ocupantes);
    }

    @RequestMapping(value = "/precio/ocupantes", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<HabitacionesEntity> getHabitacionByTipoAndPrecioBetween(@RequestParam int ocupantes ,@RequestParam Double precio1, @RequestParam Double precio2) {

        return habitacionesRepository.findByOcupantesAndPrecioBetween(ocupantes, precio1, precio2);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    HabitacionesEntity createHabitacion
            (@RequestParam String descripcion,@RequestParam int numHabitacion,@RequestParam String pathImg,@RequestParam String tipo,@RequestParam Double precio,@RequestParam int ocupantes) {
        return habitacionesRepository.newHabitacion(descripcion,numHabitacion, pathImg, tipo, precio, ocupantes);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    HabitacionesEntity deleteHabitacion(@RequestParam int idHabitaciones) {
        return habitacionesRepository.deleteHabitacion(idHabitaciones);
    }



}

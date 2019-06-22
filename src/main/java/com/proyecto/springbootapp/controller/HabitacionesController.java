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
    @RequestMapping(value = "/habitacion", method = RequestMethod.GET)
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

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public @ResponseBody
    void createHabitacion
            ( @RequestParam int numHabitacion,@RequestParam String tipo,@RequestParam Double precio,@RequestParam int ocupantes) {

        String descripcion ="";
         String pathImg = "";

        if (ocupantes == 1){
            if(tipo.equals("Simple")){
                pathImg = "www.algo.es";
                descripcion = "prueba 1";


            }else if(tipo.equals("Doble")){
                pathImg = "www.algo.es";
                descripcion = "prueba 1";

            }

        }else if (ocupantes == 2){
            pathImg = "www.algo.es";
            descripcion = "prueba 1";

        }else if(ocupantes == 3){
            pathImg = "www.algo.es";
            descripcion = "prueba 1";

        }else if(ocupantes == 4){
            pathImg = "www.algo.es";
            descripcion = "prueba 1";

        }
        habitacionesRepository.newHabitacion(descripcion,numHabitacion, pathImg, tipo, precio, ocupantes);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody
    void deleteHabitacion(@RequestParam int idHabitaciones) {
        habitacionesRepository.deleteHabitacion(idHabitaciones);
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.GET)
    public @ResponseBody
    void updateHabitacion(@RequestParam int numHabitacion, @RequestParam String tipo,
                          @RequestParam Double precio, @RequestParam int ocupantes, @RequestParam int idHabitaciones) {

        String descripcion ="";
        String pathImg = "";

        if (ocupantes == 1){
            if(tipo.equals("Simple")){
                pathImg = "www.algo.es";
                descripcion = "prueba 1";


            }else if(tipo.equals("Doble")){
                pathImg = "www.algo.es";
                descripcion = "prueba 1";

            }

        }else if (ocupantes == 2){
            pathImg = "www.algo.es";
            descripcion = "prueba 1";

        }else if(ocupantes == 3){
            pathImg = "www.algo.es";
            descripcion = "prueba 1";

        }else if(ocupantes == 4){
            pathImg = "www.algo.es";
            descripcion = "prueba 1";

        }
        habitacionesRepository.updateHabitacion(descripcion,numHabitacion, pathImg, tipo, precio, ocupantes, idHabitaciones);
    }






}

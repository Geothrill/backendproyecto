package com.proyecto.springbootapp.controller;

import com.proyecto.springbootapp.entity.HabitacionesEntity;
import com.proyecto.springbootapp.repository.HabitacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * clase controladora de las habitaciones, incluye un mapeo por defecto que la identifica
 */
@RestController
    @RequestMapping("/habitaciones")
public class HabitacionesController {

    @Autowired
    HabitacionesRepository habitacionesRepository;

    /**
     * función que retorna una lista de habitaciones
     * @return lista de habitaciones
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<HabitacionesEntity> getAllHabitaciones() {

        return habitacionesRepository.findAll();
    }

    /**
     * función que retorna las habitaciones disponibles para realizar una reserva
     * @param fechaEntrada
     * @param fechaSalida
     * @param precio1
     * @param precio2
     * @param ocupantes
     * @return lista de habitaciones disponibles
     * @throws ParseException en caso de no poder parsear las fechas
     */
    @RequestMapping(value = "/reservar", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<HabitacionesEntity> getHabitacionesLibresWithOcupantes(@RequestParam String fechaEntrada, @RequestParam String fechaSalida,
                                                                    @RequestParam Double precio1, @RequestParam Double precio2, @RequestParam int ocupantes) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date fechaEntradaDate = format.parse(fechaEntrada);
        Date fechaSalidaDate = format.parse(fechaSalida);
        Iterable<HabitacionesEntity> respuesta = habitacionesRepository.findHabitacionesLibresPrecioBetweenOcupantes(fechaEntradaDate, fechaSalidaDate, precio1, precio2, ocupantes);

        return respuesta;
    }

    /**
     * función que retorna una habitación
     * @param idHabitaciones id de la habitación
     * @return habitación según su id
     */
    @RequestMapping(value = "/habitacion", method = RequestMethod.GET)
    public @ResponseBody
    HabitacionesEntity getHabitacionesByIdHabitaciones(@RequestParam int idHabitaciones) {

        return habitacionesRepository.findByIdHabitaciones(idHabitaciones);
    }

    /**
     * función que crea una habitación a falta de 2 parametros de entrada, que se autogeneran en función del tipo y de los ocupantes
     * @param numHabitacion
     * @param tipo
     * @param precio
     * @param ocupantes
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public @ResponseBody
    void createHabitacion
            ( @RequestParam int numHabitacion,@RequestParam String tipo,@RequestParam Double precio,@RequestParam int ocupantes) {

        String descripcion ="";
         String pathImg = "";

        if (ocupantes == 1){
            if(tipo.equals("Simple")){
                pathImg = "../image/room4.jpg";
                descripcion = "La habitación individual de nuestro establecimiento es perfecta para una persona. Nuestras habitaciones individuales gozan de vistas a la calle o al patio y " +
                        "disponen de ventanas de doble acristalamiento. " +
                        "Cuentan con una cama de 120 cm, baño con bañera e inodoro privado. " +
                        "Si viaja con niños menores de dos años, puede solicitar una cuna al efectuar la reserva.";


            }else if(tipo.equals("Doble")){
                pathImg = "../image/room2.jpg";
                descripcion = "La habitación doble " +
                        " perfecta para una o dos personas, ofrece vistas al patio o a la calle " +
                        "y cuenta con ventanas de doble acristalamiento para que disfrute de un ambiente tranquilo y relajante. " +
                        "Dispone de una cama de 140 cm de ancho, baño con bañera e inodoro privado.";

            }

        }else if (ocupantes == 2){
            pathImg = "../image/facilites_bg.jpg";
            descripcion = "La habitación doble " +
                    " perfecta para una o dos personas, ofrece vistas al patio o a la calle " +
                    "y cuenta con ventanas de doble acristalamiento para que disfrute de un ambiente tranquilo y relajante. " +
                    "Dispone de una cama de 140 cm de ancho, baño con bañera e inodoro privado.";

        }else if(ocupantes == 3){
            pathImg = "../image/room1.jpg";
            descripcion = "La habitación triple tiene capacidad para tres personas en una cama doble de 140 cm y una cama individual de 90 cm. " +
                    "Nuestras habitaciones triples gozan de vistas a la calle y " +
                    "cuentan con ventanas de doble acristalamiento que garantizan calma y tranquilidad. Todas nuestras habitaciones triples disponen de baño con bañera e inodoro privado.";

        }else if(ocupantes == 4){
            pathImg = "../image/room2.jpg";
            descripcion = "La habitación perfecta para familias con 2 hijos, ofrece vistas al patio o a la calle y cuenta con ventanas de \n" +
                    "doble acristalamiento para que disfrute de un ambiente tranquilo y relajante. Dispone de una cama de 140 cm de ancho y 2 camas supletorias, baño con bañera e inodoro privado.";

        }
        habitacionesRepository.newHabitacion(descripcion,numHabitacion, pathImg, tipo, precio, ocupantes);
    }

    /**
     * función que elimina una habitación según su id
     * @param idHabitaciones id de la habitación a eliminar
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody
    void deleteHabitacion(@RequestParam int idHabitaciones) {
        habitacionesRepository.deleteHabitacion(idHabitaciones);
    }

    /**
     * función que modifica una habitación según su id a falta de 2 parametros de entrada, que se autogeneran en función del tipo y de los ocupantes
     * @param numHabitacion
     * @param tipo
     * @param precio
     * @param ocupantes
     * @param idHabitaciones id de la habitación a modificar
     */
    @RequestMapping(value = "/modificar", method = RequestMethod.GET)
    public @ResponseBody
    void updateHabitacion(@RequestParam int numHabitacion, @RequestParam String tipo,
                          @RequestParam Double precio, @RequestParam int ocupantes, @RequestParam int idHabitaciones) {

        String descripcion ="";
        String pathImg = "";

        if (ocupantes == 1){
            if(tipo.equals("Simple")){
                pathImg = "../image/room4.jpg";
                descripcion = "La habitación individual de nuestro establecimiento es perfecta para una persona. Nuestras habitaciones individuales gozan de vistas a la calle o al patio y " +
                        "disponen de ventanas de doble acristalamiento. " +
                        "Cuentan con una cama de 120 cm, baño con bañera e inodoro privado. " +
                        "Si viaja con niños menores de dos años, puede solicitar una cuna al efectuar la reserva.";


            }else if(tipo.equals("Doble")){
                pathImg = "../image/room2.jpg";
                descripcion = "La habitación doble " +
                        " perfecta para una o dos personas, ofrece vistas al patio o a la calle " +
                        "y cuenta con ventanas de doble acristalamiento para que disfrute de un ambiente tranquilo y relajante. " +
                        "Dispone de una cama de 140 cm de ancho, baño con bañera e inodoro privado.";

            }

        }else if (ocupantes == 2){
            pathImg = "../image/facilites_bg.jpg";
            descripcion = "La habitación doble " +
                    " perfecta para una o dos personas, ofrece vistas al patio o a la calle " +
                    "y cuenta con ventanas de doble acristalamiento para que disfrute de un ambiente tranquilo y relajante. " +
                    "Dispone de una cama de 140 cm de ancho, baño con bañera e inodoro privado.";

        }else if(ocupantes == 3){
            pathImg = "../image/room1.jpg";
            descripcion = "La habitación triple tiene capacidad para tres personas en una cama doble de 140 cm y una cama individual de 90 cm. " +
                    "Nuestras habitaciones triples gozan de vistas a la calle y " +
                    "cuentan con ventanas de doble acristalamiento que garantizan calma y tranquilidad. Todas nuestras habitaciones triples disponen de baño con bañera e inodoro privado.";

        }else if(ocupantes == 4){
            pathImg = "../image/room2.jpg";
            descripcion = "La habitación perfecta para familias con 2 hijos, ofrece vistas al patio o a la calle y cuenta con ventanas de \n" +
                    "doble acristalamiento para que disfrute de un ambiente tranquilo y relajante. Dispone de una cama de 140 cm de ancho y 2 camas supletorias, baño con bañera e inodoro privado.";

        }
        habitacionesRepository.updateHabitacion(descripcion,numHabitacion, pathImg, tipo, precio, ocupantes, idHabitaciones);
    }






}

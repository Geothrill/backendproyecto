package com.proyecto.springbootapp.controller;

import com.proyecto.springbootapp.entity.ValoracionesEntity;
import com.proyecto.springbootapp.repository.ValoracionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/valoraciones")
public class ValoracionesController {

    @Autowired
    ValoracionesRepository valoracionesRepository;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public @ResponseBody void createValoracion
            ( @RequestParam  String fecha,@RequestParam String email,@RequestParam int idReserva,@RequestParam String comentarios,@RequestParam  int valor) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date fechaDate = format.parse(fecha);
         valoracionesRepository.createValoracion(fechaDate, email, idReserva, comentarios, valor);
    }


}

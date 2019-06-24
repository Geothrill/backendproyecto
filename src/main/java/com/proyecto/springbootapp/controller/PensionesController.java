package com.proyecto.springbootapp.controller;


import com.proyecto.springbootapp.entity.PensionesEntity;
import com.proyecto.springbootapp.repository.PensionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * clase controladora de las pensiones, incluye un mapeo por defecto que la identifica
 */
@RestController
@RequestMapping("/pensiones")
public class PensionesController {

    @Autowired
    PensionesRepository pensionesRepository;

    /**
     * función que devuelve todas las funciones
     * @return lista de pensiones
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<PensionesEntity> getAllPensiones() {

        return pensionesRepository.findAll();
    }

    /**
     * función que devuelve una pensión según su tipo
     * @param tipo
     * @return pensión
     */
    @RequestMapping(value = "/idPension", method = RequestMethod.GET)
    public @ResponseBody
    PensionesEntity getIdPensionByTipo(@RequestParam String tipo) {
        return pensionesRepository.findIdPensionesByTipo(tipo);


    }
}

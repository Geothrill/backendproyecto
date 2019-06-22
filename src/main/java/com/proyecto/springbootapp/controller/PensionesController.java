package com.proyecto.springbootapp.controller;


import com.proyecto.springbootapp.entity.PensionesEntity;
import com.proyecto.springbootapp.repository.PensionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pensiones")
public class PensionesController {

    @Autowired
    PensionesRepository pensionesRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<PensionesEntity> getAllPensiones() {

        return pensionesRepository.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public @ResponseBody
    void createPension(@RequestParam String descripcion, @RequestParam String tipo, @RequestParam Double precio) {

    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody
    void deletePension(@RequestParam int idPension) {

    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public @ResponseBody
    void updatePension(@RequestParam String descripcion, @RequestParam String tipo, @RequestParam Double precio, @RequestParam int idPension) {

    }

    @RequestMapping(value = "/idPension", method = RequestMethod.GET)
    public @ResponseBody
    PensionesEntity getIdPensionByTipo(@RequestParam String tipo) {
        return pensionesRepository.findIdPensionesByTipo(tipo);


    }
}

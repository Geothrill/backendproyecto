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
    PensionesEntity createPension(@RequestParam String descripcion, @RequestParam String tipo, @RequestParam Double precio) {
        return pensionesRepository.createPension(descripcion, tipo, precio);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public @ResponseBody
    PensionesEntity deletePension(@RequestParam int idPension) {
        return pensionesRepository.deletePension(idPension);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public @ResponseBody
    PensionesEntity updatePension(@RequestParam String descripcion, @RequestParam String tipo, @RequestParam Double precio, @RequestParam int idPension) {
        return pensionesRepository.updatePension(descripcion, tipo, precio, idPension);
    }

    @RequestMapping(value = "/idPension", method = RequestMethod.GET)
    public @ResponseBody
    PensionesEntity getIdPensionByTipo(@RequestParam String tipo) {
        return pensionesRepository.findIdPensionesByTipo(tipo);


    }
}

package com.proyecto.springbootapp.repository;

import com.proyecto.springbootapp.entity.PensionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repositorio de pensiones, aquí se incluyen todas las consultas a realizar referentes a las pensiones
 */
@Repository
public interface PensionesRepository extends JpaRepository<PensionesEntity, Integer> {
    /**
     * función que devuelve todas las pensiones
     * @return todas las pensiones
     */
    List<PensionesEntity> findAll();

    /**
     * función que devuelve la id de una pensión según el tipo de pensión
     * @param tipo de la pensión
     * @return id de la pensión
     */
    PensionesEntity findIdPensionesByTipo(String tipo);

}

package com.proyecto.springbootapp.repository;

import com.proyecto.springbootapp.entity.ValoracionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Repositorio de valoraciones, aquí se incluyen todas las consultas a realizar referentes a las valoraciones
 */
@Repository
public interface ValoracionesRepository extends JpaRepository<ValoracionesEntity, Integer> {
    /**
     * función que retorna todas las valoraciones
     * @return todas las valoraciones
     */
    List<ValoracionesEntity> findAll();

    /**
     * función que crea una valoración
     * @param fecha
     * @param email
     * @param idReserva
     * @param comentarios
     * @param valor
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO valoraciones ( fecha, idUsuario, idReserva, comentarios, valor) values (?1,(select idUsuario from usuarios where email like ?2),?3,?4,?5)", nativeQuery = true)
    void createValoracion(Date fecha, String email, int idReserva, String comentarios, int valor );


}

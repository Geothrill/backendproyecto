package com.proyecto.springbootapp.repository;

import com.proyecto.springbootapp.entity.HabitacionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Repositorio de habitaciones, aquí se incluyen todas las consultas a realizar referentes a las habitaciones
 */
@Repository
public interface HabitacionesRepository extends JpaRepository<HabitacionesEntity, Integer> {
    /**
     * función que retorna todas las habitaciones
     * @return lista de habitaciones
     */
    List<HabitacionesEntity> findAll();

    /**
     * función que retorna una habitación en función de su id
     * @param idHabitaciones id de la habitación
     * @return una habitación
     */
    HabitacionesEntity findByIdHabitaciones(int idHabitaciones);

    /**
     * función que retorna todas las habitaciones disponibles para realizar una reserva
     * @param fechaEntrada fecha en la que se quiere acceder
     * @param fechaSalida fecha de finalización de la reserva
     * @param precio1 cantidad mínima a pagar
     * @param precio2 cantidad máxima a pagar
     * @param ocupantes
     * @return una lista de habitaciones disponibles para reservar
     */
    @Query(value = "select distinct habitaciones.* from habitaciones, reservas where  not exists (select 1" +
            "                  from reservas" +
            "                  where reservas.idHabitaciones = habitaciones.idHabitaciones and" +
            "                        reservas.fechaEntrada <= ?1 and" +
            "                        reservas.fechaSalida >= ?2" +
            "                 ) and habitaciones.precio BETWEEN ?3 AND ?4 and habitaciones.ocupantes = ?5", nativeQuery = true)
    Iterable<HabitacionesEntity> findHabitacionesLibresPrecioBetweenOcupantes (Date fechaEntrada, Date fechaSalida, Double precio1, Double precio2, int ocupantes);

    /**
     * función que permite crear una habitación
     * @param descripcion
     * @param numHabitacion
     * @param pathImg
     * @param tipo
     * @param precio
     * @param ocupantes
     */
    @Transactional
    @Modifying
    @Query(value = "Insert into habitaciones (descripcion, numHabitacion, pathImg, tipo, precio, ocupantes) values(?1,?2,?3,?4,?5,?6)", nativeQuery = true)
    void newHabitacion(String descripcion,int numHabitacion,String pathImg,String tipo, Double precio, int ocupantes);

    /**
     * función que modifica una habitación ya existente según su id
     * @param descripcion
     * @param numHabitacion
     * @param pathImg
     * @param tipo
     * @param precio
     * @param ocupantes
     * @param idHabitaciones id de la habitación a modificar
     */
    @Transactional
    @Modifying
    @Query(value = "update habitaciones set descripcion = ?1, numHabitacion = ?2," +
            " pathImg = ?3, tipo = ?4, precio = ?5, ocupantes = ?6 where habitaciones.idHabitaciones = ?7", nativeQuery = true)
    void updateHabitacion(String descripcion,int numHabitacion,String pathImg,String tipo, Double precio, int ocupantes, int idHabitaciones);

    /**
     * función que elimina una habitación
     * @param idHabitaciones id de la habitación a eliminar
     */
    @Transactional
    @Modifying
    @Query(value = "Delete from habitaciones where idHabitaciones = ?1", nativeQuery = true)
    void deleteHabitacion(int idHabitaciones);



}

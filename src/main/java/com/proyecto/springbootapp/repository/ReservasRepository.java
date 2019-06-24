package com.proyecto.springbootapp.repository;

import com.proyecto.springbootapp.entity.ReservasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Repositorio de reservas, aquí se incluyen todas las consultas a realizar referentes a las reservas
 */
@Repository
public interface ReservasRepository extends JpaRepository<ReservasEntity, Integer> {
    /**
     * funcion que retorna todas las reservas
     * @return lista de reservas
     */
    List<ReservasEntity> findAll();

    /**
     * función que retorna el numero máximo usado para el campo idReservaCompartida
     * @return el número máximo del campo idReservaCompartida
     */
    @Query(value = "select max(reservas.idReservaCompartida) from reservas", nativeQuery = true)
    int getMaxIdReservaCompartida();

    /**
     * función que retorna la ultima reserva realizada
     * @return una reserva
     */
    @Query(value = "SELECT *  FROM reservas ORDER BY idReservas DESC LIMIT 1", nativeQuery = true)
    ReservasEntity getLastReservaByUser();

    /**
     * función que devuelve todas las id de reserva compartida de un usuario
     * @param email necesario para obtener el idUsuario
     * @return lista de idReservaCompartida de un usuario
     */
    @Query(value = "select distinct idReservaCompartida from reservas where idUsuario = (select idUsuario from usuarios where email like ?1) order by idReservaCompartida", nativeQuery = true)
    List<Integer> reservasUsuario(String email);

    /**
     * función que retorna todas las reservas dentro de un id de reserva compartida
     * @param email necesario para obtener el idUsuario
     * @param idReservaCompartida necesario para obtener todas las habitaciones reservadas con este id
     * @return todas las habitaciones asociadas a un idReservaCompartida de un usuario
     */
    @Query(value = "select * from reservas where idUsuario in (SELECT idUsuario FROM usuarios WHERE email like ?1) and idReservaCompartida = ?2", nativeQuery = true)
    List<ReservasEntity> getReservas(String email, int idReservaCompartida);

    /**
     * función que retorna el precio total de una reserva completa
     * @param email necesario para obtener el idUsuario
     * @param idReservaCompartida necesario para obtener todas las habitaciones reservadas con este id
     * @return la suma de los precios de todas las habitaciones de una reserva completa
     */
    @Query(value = "select sum(precio) from reservas where idUsuario in (SELECT idUsuario FROM usuarios WHERE email like ?1) and idReservaCompartida = ?2", nativeQuery = true)
    int sumPrecioReservaCompleta(String email, int idReservaCompartida);

    /**
     * función que añade una reserva de habitación
     * @param fechaReserva
     * @param fechaEntrada
     * @param fechaSalida
     * @param email necesario para obtener el idUsuario
     * @param idHabitaciones
     * @param idPension
     * @param idReservaCompartida asocia la reserva de una habitación a una reserva completa
     */
    @Transactional
    @Modifying
    @Query(value = "insert into reservas (fechaReserva, fechaEntrada,fechaSalida,precio,idUsuario,idHabitaciones,idPension, idReservaCompartida)" +
            "values (?1, ?2,?3,  (datediff(?3,?2)*(select precio from habitaciones where idHabitaciones = ?5)) +" +
            "((datediff(?3,?2))*((select ocupantes from habitaciones where idHabitaciones = ?5) * (select precio from pension where idPension = ?6))) ," +
            " (select idUsuario from usuarios where email like ?4),?5,?6, ?7)", nativeQuery = true)
    void addReserva(Date fechaReserva,Date fechaEntrada,Date fechaSalida,String email,int idHabitaciones, int idPension, int idReservaCompartida);

    /**
     * elimina una reserva de habitación
     * @param idReserva id de la reserva a eliminar
     */
    @Transactional
    @Modifying
    @Query(value = "delete from reservas where idReservas = ?1", nativeQuery = true)
    void delete1Reserva(int idReserva);

    /**
     * elimina una reserva completa
     * @param idReservaCompartida id compartido que incluye todas las habitaciones de la reserva
     */
    @Transactional
    @Modifying
    @Query(value = "delete from reservas where idReservaCompartida = ?1", nativeQuery = true)
    void deleteGroupReserva(int idReservaCompartida);

    /**
     * función que añade una valoración a la reserva de una habitación
     * @param idReservas reserva de habitación a la que se le añade la valoración
     */
    @Transactional
    @Modifying
    @Query(value = "update reservas set idValoraciones = (select idReserva from valoraciones  order by idValoraciones desc limit 1) where idReservas = ?1", nativeQuery = true)
    void addValoracionToReserva(int idReservas);




}

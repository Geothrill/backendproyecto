package com.proyecto.springbootapp.repository;

import com.proyecto.springbootapp.entity.ReservasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservasRepository extends JpaRepository<ReservasEntity, Integer> {
    List<ReservasEntity> findAll();

    @Query(value = "select max(reservas.idReservaCompartida) from reservas", nativeQuery = true)
    int getMaxIdReservaCompartida();

    @Query(value = "SELECT *  FROM reservas ORDER BY idReservas DESC LIMIT 1", nativeQuery = true)
    ReservasEntity getLastReservaByUser();

    @Query(value = "select distinct idReservaCompartida from reservas where idUsuario = (select idUsuario from usuarios where email like ?1) order by idReservaCompartida", nativeQuery = true)
    List<Integer> reservasUsuario(String email);

    @Query(value = "select * from reservas where idUsuario in (SELECT idUsuario FROM usuarios WHERE email like ?1) and idReservaCompartida = ?2", nativeQuery = true)
    List<ReservasEntity> getReservas(String email, int idReservaCompartida);

    @Query(value = "select sum(precio) from reservas where idUsuario in (SELECT idUsuario FROM usuarios WHERE email like ?1) and idReservaCompartida = ?2", nativeQuery = true)
    int sumPrecioReservaCompleta(String email, int idReservaCompartida);

    @Query(value = "select * from reservas where idUsuario in (SELECT idUsuario FROM usuarios WHERE email like ?1)", nativeQuery = true)
    List<ReservasEntity> findReservasByEmail( String email);


    @Transactional
    @Modifying
    @Query(value = "insert into reservas (fechaReserva, fechaEntrada,fechaSalida,precio,idUsuario,idHabitaciones,idPension)" +
            "    values (?1, ?2,?3,  (datediff(?3,?2)*(select precio from habitaciones where idHabitaciones = ?5)) +" +
            "            ((datediff(?3,?2))*((select ocupantes from habitaciones where idHabitaciones = ?5) * (select precio from pension where idPension = ?6))) , (select idUsuario from usuarios where email like ?4),?5,?6, ?7)", nativeQuery = true)
    void newReserva(Date fechaReserva,Date fechaEntrada,Date fechaSalida,String email,int idHabitaciones, int idPension, int idReservaCompartida);

    @Transactional
    @Modifying
    @Query(value = "insert into reservas (fechaReserva, fechaEntrada,fechaSalida,precio,idUsuario,idHabitaciones,idPension, idReservaCompartida)" +
            "values (?1, ?2,?3,  (datediff(?3,?2)*(select precio from habitaciones where idHabitaciones = ?5)) +" +
            "((datediff(?3,?2))*((select ocupantes from habitaciones where idHabitaciones = ?5) * (select precio from pension where idPension = ?6))) ," +
            " (select idUsuario from usuarios where email like ?4),?5,?6, ?7)", nativeQuery = true)
    void addReserva(Date fechaReserva,Date fechaEntrada,Date fechaSalida,String email,int idHabitaciones, int idPension, int idReservaCompartida);

    @Transactional
    @Modifying
    @Query(value = "delete from reservas where idReservas = ?1", nativeQuery = true)
    void delete1Reserva(int idReserva);

    @Transactional
    @Modifying
    @Query(value = "delete from reservas where idReservaCompartida = ?1", nativeQuery = true)
    void deleteGroupReserva(int idReservaCompartida);



}

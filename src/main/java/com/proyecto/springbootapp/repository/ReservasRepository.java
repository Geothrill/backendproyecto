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

    List<ReservasEntity> findReservasByIdUsuario(int idUsuario);


    @Query(value = "select * from reservas where id_habitaciones in (SELECT idHabitaciones FROM habitaciones WHERE precio BETWEEN ?1 AND ?2)", nativeQuery = true)
    List<ReservasEntity> findReservasByHabitacionesPrecio(Double precio1, Double precio2);


    @Transactional
    @Modifying
    @Query(value = "insert into reservas set (fechaReserva, fechaEntrada,fechaSalida,precio,idUsuario,idHabitaciones,idPension)" +
            "    values (?1, ?2,?3,  (select precio from habitaciones where idHabitaciones = ?5) +" +
            "            ((select ocupantes from habitaciones where idHabitaciones = ?5) * (select precio from pension where idPension = ?6)) , (select idUsuario where email like ?4),?5,?6)", nativeQuery = true)
    ReservasEntity newReserva(Date fechaReserva,Date fechaEntrada,Date fechaSalida,String email,int idHabitaciones, int idPension);

    @Transactional
    @Modifying
    @Query(value = "delete from reservas where idReserva = ?1", nativeQuery = true)
    ReservasEntity deleteReserva(int idReserva);



}

package com.proyecto.springbootapp.repository;

import com.proyecto.springbootapp.entity.ValoracionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface ValoracionesRepository extends JpaRepository<ValoracionesEntity, Integer> {
    List<ValoracionesEntity> findAll();

    ValoracionesEntity findByIdValoraciones(int idValoraciones);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO valoraciones ( fecha, idUsuario, idReserva, comentarios, valor) values (?1,(select idUsuario from usuarios where email like ?2),?3,?4,?5)", nativeQuery = true)
    void createValoracion(Date fecha, String email, int idReserva, String comentarios, int valor );

    @Query(value = "Select * from valoraciones where idUsuario = ?1", nativeQuery = true)
    Iterable<ValoracionesEntity> findByIdUsuario (int idUsuario);

    @Transactional
    @Modifying
    @Query(value =" delete from valoraciones where idValoraciones = ?1", nativeQuery = true)
    ValoracionesEntity deleteValoracion(int idValoraciones);

}

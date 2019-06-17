package com.proyecto.springbootapp.repository;

import com.proyecto.springbootapp.entity.PensionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PensionesRepository extends JpaRepository<PensionesEntity, Integer> {
    List<PensionesEntity> findAll();

    @Transactional
    @Modifying
    @Query(value = "delete from pension where idPension= ?1", nativeQuery = true)
    PensionesEntity deletePension(int idPension);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO pension ( descripcion, tipo, precio) values (?1,?2,?3)", nativeQuery = true)
    PensionesEntity createPension (String descripcion, String tipo, Double precio);


    @Transactional
    @Modifying
    @Query(value = "update pension set ( descripcion, tipo, precio) = (?1,?2,?3) where idPension = ?4", nativeQuery = true)
    PensionesEntity updatePension (String descripcion, String tipo, Double precio, int idPension);

    PensionesEntity findIdPensionesByTipo(String tipo);

}

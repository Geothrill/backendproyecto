package com.proyecto.springbootapp.repository;

import com.proyecto.springbootapp.entity.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Integer> {
    List<UsuariosEntity> findAll();

    List<UsuariosEntity> findUsuariosByTipoUsuario(char tipoUsuario);

    UsuariosEntity findByIdUsuario(int idUsuario);

    boolean existsUsuarioByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO usuarios ( nombre, apellidos, email, password, tipoUsuario) values (?1,?2,?3,?4,?5)", nativeQuery = true)
    void createUsuario(String nombre, String apellidos, String email, String password,Character tipoUsuario);

    @Transactional
    @Modifying
    @Query(value =" delete from usuarios where idUsuario = ?1", nativeQuery = true)
    void deleteUsuario(int idUsuario);

    UsuariosEntity findByEmailAndPassword(String email, String password);

    @Transactional
    @Modifying
    @Query(value = "update usuarios set nombre = ?1, apellidos = ?2, email = ?3 where idUsuario = ?4", nativeQuery = true)
    void updateUsuario(String nombre, String apellidos, String email, int idUsuario);

}

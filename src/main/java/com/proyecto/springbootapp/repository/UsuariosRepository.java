package com.proyecto.springbootapp.repository;

import com.proyecto.springbootapp.entity.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repositorio de usuarios, aquí se incluyen todas las consultas a realizar referentes a los usuarios
 */
@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Integer> {
    /**
     * función que retorna todos los usuarios
     * @return todos los usuarios
     */
    List<UsuariosEntity> findAll();

    /**
     * función que retorna todos los usuarios
     * @param tipoUsuario el tipo de usuario a retornar
     * @return lista de usuarios
     */
    List<UsuariosEntity> findUsuariosByTipoUsuario(char tipoUsuario);

    /**
     * función que retorna un usuario en concreto
     * @param idUsuario id del usuario a buscar
     * @return un usuario
     */
    UsuariosEntity findByIdUsuario(int idUsuario);

    /**
     * función que comprueba si el mail está en uso
     * @param email email a consultar
     * @return true si el mail existe en BBDD, false si no existe
     */
    boolean existsUsuarioByEmail(String email);

    /**
     * función que crea un usuario con los parámetros necesarios
     * @param nombre
     * @param apellidos
     * @param email
     * @param password
     * @param tipoUsuario
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO usuarios ( nombre, apellidos, email, password, tipoUsuario) values (?1,?2,?3,?4,?5)", nativeQuery = true)
    void createUsuario(String nombre, String apellidos, String email, String password,Character tipoUsuario);

    /**
     * función que elimina un usuario
     * @param idUsuario el usuario a eliminar
     */
    @Transactional
    @Modifying
    @Query(value =" delete from usuarios where idUsuario = ?1", nativeQuery = true)
    void deleteUsuario(int idUsuario);

    UsuariosEntity findByEmailAndPassword(String email, String password);

    /**
     * función que modifica a un usuario
     * @param nombre nombre a modificar
     * @param apellidos apellidos a modificar
     * @param email email a modificar
     * @param idUsuario usuario que se modificará
     */
    @Transactional
    @Modifying
    @Query(value = "update usuarios set nombre = ?1, apellidos = ?2, email = ?3 where idUsuario = ?4", nativeQuery = true)
    void updateUsuario(String nombre, String apellidos, String email, int idUsuario);

}

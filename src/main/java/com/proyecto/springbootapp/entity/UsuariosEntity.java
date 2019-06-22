package com.proyecto.springbootapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "usuarios")
@Table(name = "usuarios")
public class UsuariosEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public int idUsuario;
    public String nombre;
    public String apellidos;
    public String email;
    public String password;
    public char tipoUsuario;


    @OneToMany(mappedBy = "idUsuario")
    @JsonBackReference
     private List<ReservasEntity> reservas;

    @OneToMany(mappedBy = "idUsuario")
    @JsonBackReference
    private List<ValoracionesEntity> valoraciones;

    public UsuariosEntity() {
    }

    public UsuariosEntity(int idUsuario, String nombre, String apellidos, String email, String password, char tipoUsuario) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(char tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public List<ReservasEntity> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservasEntity> reservas) {
        this.reservas = reservas;
    }

    public List<ValoracionesEntity> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<ValoracionesEntity> valoraciones) {
        this.valoraciones = valoraciones;
    }

    @Override
    public String toString() {
        return "UsuariosEntity{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

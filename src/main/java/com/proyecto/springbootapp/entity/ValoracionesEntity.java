package com.proyecto.springbootapp.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
/**
 * entidad de las valoraciones, su nombre y la tabla a la que refiere
 */
@Entity(name = "valoraciones")
@Table(name = "valoraciones")
public class ValoracionesEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int idValoraciones;
    @DateTimeFormat
    public Date fecha;
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    @JsonBackReference
    public UsuariosEntity idUsuario;
    @OneToOne(mappedBy = "idValoraciones", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    public ReservasEntity idReserva;
    public String comentarios;
    public int valor;

    /**
     * constructor por defecto
     */
    public ValoracionesEntity() {
    }

    /**
     * constructor completo
     * @param idValoraciones
     * @param fecha
     * @param idUsuario
     * @param idReserva
     * @param comentarios
     * @param valor
     */
    public ValoracionesEntity(int idValoraciones, Date fecha, UsuariosEntity idUsuario, ReservasEntity idReserva, String comentarios, int valor) {
        this.idValoraciones = idValoraciones;
        this.fecha = fecha;
        this.idUsuario = idUsuario;
        this.idReserva = idReserva;
        this.comentarios = comentarios;
        this.valor = valor;
    }

    public int getIdValoraciones() {
        return idValoraciones;
    }

    public void setIdValoraciones(int idValoraciones) {
        this.idValoraciones = idValoraciones;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public UsuariosEntity getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UsuariosEntity idUsuario) {
        this.idUsuario = idUsuario;
    }

    public ReservasEntity getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(ReservasEntity idReserva) {
        this.idReserva = idReserva;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "ValoracionesRepository{" +
                "idValoraciones=" + idValoraciones +
                ", fecha=" + fecha +
                ", idUsuario=" + idUsuario +
                ", idReservas=" + idReserva +
                ", comentarios='" + comentarios + '\'' +
                ", valor=" + valor +
                '}';
    }
}

package com.proyecto.springbootapp.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
/**
 * entidad de las reservas, su nombre y la tabla a la que refiere
 */
@Entity(name = "Reservas")
@Table(name = "reservas")
public class ReservasEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int idReservas;
    @DateTimeFormat
    public Date fechaReserva;
    @DateTimeFormat
    public Date fechaEntrada;
    @DateTimeFormat
    public Date fechaSalida;
    public Double precio;
    public int idReservaCompartida;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable=false)
    @JsonBackReference
    public UsuariosEntity idUsuario;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idReservas")
    @JsonBackReference
    private ValoracionesEntity idValoraciones;
    @ManyToOne
    @JoinColumn(name = "idHabitaciones", nullable=false)
    @JsonBackReference
    public HabitacionesEntity idHabitaciones;
    @ManyToOne
    @JoinColumn(name = "idPension", nullable=false)
    @JsonBackReference
    public PensionesEntity idPension;


    /**
     * constructor por defecto
     */
    public ReservasEntity() {
    }

    /**
     * constructor completo
     * @param idReservas
     * @param fechaReserva
     * @param fechaEntrada
     * @param fechaSalida
     * @param precio
     * @param usuario
     * @param idValoraciones
     * @param habitaciones
     * @param pensiones
     * @param idReservaCompartida
     */
    public ReservasEntity(int idReservas, Date fechaReserva, Date fechaEntrada, Date fechaSalida, double precio,
                          UsuariosEntity usuario, ValoracionesEntity idValoraciones, HabitacionesEntity habitaciones, PensionesEntity pensiones, int idReservaCompartida) {
        this.idReservas = idReservas;
        this.fechaReserva = fechaReserva;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.precio = precio;
        this.idUsuario = usuario;
        this.idValoraciones = idValoraciones;
        this.idHabitaciones = habitaciones;
        this.idPension = pensiones;
        this.idReservaCompartida = idReservaCompartida;
    }

    public int getIdReservas() {
        return idReservas;
    }

    public void setIdReservas(int idReservas) {
        this.idReservas = idReservas;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }


    public UsuariosEntity getUsuario() {
        return idUsuario;
    }

    public void setUsuario(UsuariosEntity usuario) {
        this.idUsuario = usuario;
    }

    public ValoracionesEntity getIdValoraciones() {
        return idValoraciones;
    }

    public void setIdValoraciones(ValoracionesEntity idValoraciones) {
        this.idValoraciones = idValoraciones;
    }

    public HabitacionesEntity getHabitaciones() {
        return idHabitaciones;
    }

    public void setHabitaciones(HabitacionesEntity habitaciones) {
        this.idHabitaciones = habitaciones;
    }

    public PensionesEntity getPensiones() {
        return idPension;
    }

    public void setPensiones(PensionesEntity pensiones) {
        this.idPension = pensiones;
    }

    public int getIdReservaCompartida() {
        return idReservaCompartida;
    }

    public void setIdReservaCompartida(int idReservaCompartida) {
        this.idReservaCompartida = idReservaCompartida;
    }
}


package com.proyecto.springbootapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "pensiones")
@Table(name = "pension")
public class PensionesEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int idPension;
    public String descripcion;
    public String tipo;
    public Double precio;

    @OneToMany(mappedBy = "idPension")
    @JsonBackReference
    private List<ReservasEntity> reservas;

    public PensionesEntity() {
    }

    public PensionesEntity(int idPension, String descripcion, String tipo, double precio, List<ReservasEntity> reservas) {
        this.idPension = idPension;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.reservas = reservas;
    }

    public int getIdPension() {
        return idPension;
    }

    public void setIdPension(int idPension) {
        this.idPension = idPension;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public List<ReservasEntity> getReservas() {
        return reservas;
    }

    public void setReservas(List<ReservasEntity> reservas) {
        this.reservas = reservas;
    }

    @Override
    public String toString() {
        return "PensionesRepository{" +
                "idPension=" + idPension +
                ", descripcion='" + descripcion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", precio=" + precio +
                '}';
    }
}

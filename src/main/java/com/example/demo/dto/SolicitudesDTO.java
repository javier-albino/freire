package com.example.demo.dto;

import java.sql.Timestamp;

public class SolicitudesDTO {
    private String nombre;
    private long id;
    private Timestamp fecha_solicitud;

    /**
     * @return String return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return long return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return Timestamp return the fecha_solicitud
     */
    public Timestamp getFecha_solicitud() {
        return fecha_solicitud;
    }

    /**
     * @param fecha_solicitud the fecha_solicitud to set
     */
    public void setFecha_solicitud(Timestamp fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
    }

}
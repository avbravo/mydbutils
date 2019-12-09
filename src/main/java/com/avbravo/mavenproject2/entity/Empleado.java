/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.mavenproject2.entity;

import java.io.Serializable;
import java.util.Date;




/**
 *
 * @author avbravo
 */

public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String nombre;
  
    private Date fecha;

    public Empleado() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
    
    
   
}

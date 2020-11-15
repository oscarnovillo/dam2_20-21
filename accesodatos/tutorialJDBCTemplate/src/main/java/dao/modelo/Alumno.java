/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.modelo;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author oscar
 */
public class Alumno extends Miembro{

    private int id;
    private String nombre;
    private Date fecha_nacimiento;
    private Boolean mayor_edad;

    public Alumno(String nombre, Date fecha_nacimiento, Boolean mayor_edad) {
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.mayor_edad = mayor_edad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Boolean getMayor_edad() {
        return mayor_edad;
    }

    public void setMayor_edad(Boolean mayor_edad) {
        this.mayor_edad = mayor_edad;
    }

    public Alumno() {
    }

    @Override
    public String toString() {
        return "Alumno{" +
            "id=" + id +
            ", nombre='" + nombre + '\'' +
            ", fecha_nacimiento=" + fecha_nacimiento +
            ", mayor_edad=" + mayor_edad +
            "} " + super.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Alumno other = (Alumno) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.modelo;

import java.util.Objects;

/**
 *
 * @author daw
 */
public class Asignatura {

    private int id;
    private String nombre;
    private String curso;
    private String ciclo;

    public Asignatura(String nombre, String curso, String ciclo) {
        this.nombre = nombre;
        this.curso = curso;
        this.ciclo = ciclo;
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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public Asignatura() {
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Curso: " + curso + ", Ciclo: " + ciclo + '.';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Asignatura that = (Asignatura) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

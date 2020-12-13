package dao.modelo.test;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Espias {
    private int id;
    private String nombre;
    private String raza;
    private Collection<Batallas> batallasById;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "raza")
    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Espias espias = (Espias) o;
        return id == espias.id &&
                Objects.equals(nombre, espias.nombre) &&
                Objects.equals(raza, espias.raza);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, raza);
    }

    @OneToMany(mappedBy = "espiasByIdEspia")
    public Collection<Batallas> getBatallasById() {
        return batallasById;
    }

    public void setBatallasById(Collection<Batallas> batallasById) {
        this.batallasById = batallasById;
    }
}

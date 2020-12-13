package dao.modelo.test;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

//@Entity
public class Armas {
    private int id;
    private String nombre;
    private double precio;
    private Collection<ArmasFaccionesTest> armasFaccionesTestById;

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
    @Column(name = "precio")
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Armas armas = (Armas) o;
        return id == armas.id &&
                Double.compare(armas.precio, precio) == 0 &&
                Objects.equals(nombre, armas.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, precio);
    }

    @OneToMany(mappedBy = "armasByIdArma")
    public Collection<ArmasFaccionesTest> getArmasFaccionesById() {
        return armasFaccionesTestById;
    }

    public void setArmasFaccionesById(Collection<ArmasFaccionesTest> armasFaccionesTestById) {
        this.armasFaccionesTestById = armasFaccionesTestById;
    }
}

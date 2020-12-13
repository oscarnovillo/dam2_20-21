package dao.modelo.test;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Faccion {
    private String nombre;
    private String contacto;
    private String planetaBase;
    private int numeroSistemasControlados;
    private Date ultimaCompra;
    private Collection<ArmasFaccionesTest> armasFaccionesTestByNombre;
    private Collection<Batallas> batallasByNombre;
    private Collection<Batallas> batallasByNombre_0;

    @Id
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "contacto")
    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    @Basic
    @Column(name = "planeta_base")
    public String getPlanetaBase() {
        return planetaBase;
    }

    public void setPlanetaBase(String planetaBase) {
        this.planetaBase = planetaBase;
    }

    @Basic
    @Column(name = "numero_sistemas_controlados")
    public int getNumeroSistemasControlados() {
        return numeroSistemasControlados;
    }

    public void setNumeroSistemasControlados(int numeroSistemasControlados) {
        this.numeroSistemasControlados = numeroSistemasControlados;
    }

    @Basic
    @Column(name = "ultima_compra")
    public Date getUltimaCompra() {
        return ultimaCompra;
    }

    public void setUltimaCompra(Date ultimaCompra) {
        this.ultimaCompra = ultimaCompra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faccion faccion = (Faccion) o;
        return numeroSistemasControlados == faccion.numeroSistemasControlados &&
                Objects.equals(nombre, faccion.nombre) &&
                Objects.equals(contacto, faccion.contacto) &&
                Objects.equals(planetaBase, faccion.planetaBase) &&
                Objects.equals(ultimaCompra, faccion.ultimaCompra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, contacto, planetaBase, numeroSistemasControlados, ultimaCompra);
    }

    @OneToMany(mappedBy = "faccionByNombreFaccion")
    public Collection<ArmasFaccionesTest> getArmasFaccionesByNombre() {
        return armasFaccionesTestByNombre;
    }

    public void setArmasFaccionesByNombre(Collection<ArmasFaccionesTest> armasFaccionesTestByNombre) {
        this.armasFaccionesTestByNombre = armasFaccionesTestByNombre;
    }

    @OneToMany(mappedBy = "faccionByFaccionUno")
    public Collection<Batallas> getBatallasByNombre() {
        return batallasByNombre;
    }

    public void setBatallasByNombre(Collection<Batallas> batallasByNombre) {
        this.batallasByNombre = batallasByNombre;
    }

    @OneToMany(mappedBy = "faccionByFaccionDos")
    public Collection<Batallas> getBatallasByNombre_0() {
        return batallasByNombre_0;
    }

    public void setBatallasByNombre_0(Collection<Batallas> batallasByNombre_0) {
        this.batallasByNombre_0 = batallasByNombre_0;
    }
}

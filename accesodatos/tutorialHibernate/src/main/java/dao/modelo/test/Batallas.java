package dao.modelo.test;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Batallas {
    private int id;
    private String nombre;
    private String faccionUno;
    private String faccionDos;
    private String donde;
    private Date cuando;
    private int idEspia;
    private Faccion faccionByFaccionUno;
    private Faccion faccionByFaccionDos;
    private Espias espiasByIdEspia;

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
    @Column(name = "faccion_uno")
    public String getFaccionUno() {
        return faccionUno;
    }

    public void setFaccionUno(String faccionUno) {
        this.faccionUno = faccionUno;
    }

    @Basic
    @Column(name = "faccion_dos")
    public String getFaccionDos() {
        return faccionDos;
    }

    public void setFaccionDos(String faccionDos) {
        this.faccionDos = faccionDos;
    }

    @Basic
    @Column(name = "donde")
    public String getDonde() {
        return donde;
    }

    public void setDonde(String donde) {
        this.donde = donde;
    }

    @Basic
    @Column(name = "cuando")
    public Date getCuando() {
        return cuando;
    }

    public void setCuando(Date cuando) {
        this.cuando = cuando;
    }

    @Basic
    @Column(name = "id_espia")
    public int getIdEspia() {
        return idEspia;
    }

    public void setIdEspia(int idEspia) {
        this.idEspia = idEspia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Batallas batallas = (Batallas) o;
        return id == batallas.id &&
                idEspia == batallas.idEspia &&
                Objects.equals(nombre, batallas.nombre) &&
                Objects.equals(faccionUno, batallas.faccionUno) &&
                Objects.equals(faccionDos, batallas.faccionDos) &&
                Objects.equals(donde, batallas.donde) &&
                Objects.equals(cuando, batallas.cuando);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, faccionUno, faccionDos, donde, cuando, idEspia);
    }

    @ManyToOne
    @JoinColumn(name = "faccion_uno", referencedColumnName = "nombre", nullable = false)
    public Faccion getFaccionByFaccionUno() {
        return faccionByFaccionUno;
    }

    public void setFaccionByFaccionUno(Faccion faccionByFaccionUno) {
        this.faccionByFaccionUno = faccionByFaccionUno;
    }

    @ManyToOne
    @JoinColumn(name = "faccion_dos", referencedColumnName = "nombre", nullable = false)
    public Faccion getFaccionByFaccionDos() {
        return faccionByFaccionDos;
    }

    public void setFaccionByFaccionDos(Faccion faccionByFaccionDos) {
        this.faccionByFaccionDos = faccionByFaccionDos;
    }

    @ManyToOne
    @JoinColumn(name = "id_espia", referencedColumnName = "id", nullable = false)
    public Espias getEspiasByIdEspia() {
        return espiasByIdEspia;
    }

    public void setEspiasByIdEspia(Espias espiasByIdEspia) {
        this.espiasByIdEspia = espiasByIdEspia;
    }
}

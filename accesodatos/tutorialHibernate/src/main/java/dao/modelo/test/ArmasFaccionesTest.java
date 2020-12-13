package dao.modelo.test;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "armas_facciones", schema = "hibernate", catalog = "")
public class ArmasFaccionesTest {
    private int id;
    private String nombreFaccion;
    private int idArma;
    private Faccion faccionByNombreFaccion;
    private Armas armasByIdArma;
    private Collection<Compras> comprasById;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nombre_faccion")
    public String getNombreFaccion() {
        return nombreFaccion;
    }

    public void setNombreFaccion(String nombreFaccion) {
        this.nombreFaccion = nombreFaccion;
    }

    @Basic
    @Column(name = "id_arma")
    public int getIdArma() {
        return idArma;
    }

    public void setIdArma(int idArma) {
        this.idArma = idArma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArmasFaccionesTest that = (ArmasFaccionesTest) o;
        return id == that.id &&
                idArma == that.idArma &&
                Objects.equals(nombreFaccion, that.nombreFaccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreFaccion, idArma);
    }

    @ManyToOne
    @JoinColumn(name = "nombre_faccion", referencedColumnName = "nombre", nullable = false)
    public Faccion getFaccionByNombreFaccion() {
        return faccionByNombreFaccion;
    }

    public void setFaccionByNombreFaccion(Faccion faccionByNombreFaccion) {
        this.faccionByNombreFaccion = faccionByNombreFaccion;
    }

    @ManyToOne
    @JoinColumn(name = "id_arma", referencedColumnName = "id", nullable = false)
    public Armas getArmasByIdArma() {
        return armasByIdArma;
    }

    public void setArmasByIdArma(Armas armasByIdArma) {
        this.armasByIdArma = armasByIdArma;
    }

    @OneToMany(mappedBy = "armasFaccionesByIdArmasFaccion")
    public Collection<Compras> getComprasById() {
        return comprasById;
    }

    public void setComprasById(Collection<Compras> comprasById) {
        this.comprasById = comprasById;
    }
}

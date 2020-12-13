package dao.modelo.test;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

//@Entity
public class Compras {
    private int id;
    private int idArmasFaccion;
    private int cantidad;
    private Date fechaCompra;
    private ArmasFaccionesTest armasFaccionesByIdArmasFaccionTest;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_armas_faccion")
    public int getIdArmasFaccion() {
        return idArmasFaccion;
    }

    public void setIdArmasFaccion(int idArmasFaccion) {
        this.idArmasFaccion = idArmasFaccion;
    }

    @Basic
    @Column(name = "cantidad")
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Basic
    @Column(name = "fecha_compra")
    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compras compras = (Compras) o;
        return id == compras.id &&
                idArmasFaccion == compras.idArmasFaccion &&
                cantidad == compras.cantidad &&
                Objects.equals(fechaCompra, compras.fechaCompra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idArmasFaccion, cantidad, fechaCompra);
    }

    @ManyToOne
    @JoinColumn(name = "id_armas_faccion", referencedColumnName = "id", nullable = false)
    public ArmasFaccionesTest getArmasFaccionesByIdArmasFaccion() {
        return armasFaccionesByIdArmasFaccionTest;
    }

    public void setArmasFaccionesByIdArmasFaccion(ArmasFaccionesTest armasFaccionesByIdArmasFaccionTest) {
        this.armasFaccionesByIdArmasFaccionTest = armasFaccionesByIdArmasFaccionTest;
    }
}

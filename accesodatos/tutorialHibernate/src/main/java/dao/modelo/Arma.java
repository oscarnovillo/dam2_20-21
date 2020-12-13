package dao.modelo;


import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "armas")
public class Arma {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "nombre")
    @NaturalId
    private String nombre;

    @Basic
    @Column(name = "precio")
    private Double precio;


    @OneToMany(mappedBy="arma")
    private Collection<ArmasFacciones> armasFaccionesById;


    @Override
    public String toString() {
        return "Arma{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}

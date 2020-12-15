package dao.modelo;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name ="faccion")
public class Faccion {

    @Id
    private String nombre;

    @Basic
    @Column(name = "contacto")
    private String contacto;

    @Basic
    @Column(name = "planeta_base")
    private String planetaBase;

    @Basic
    @Column(name = "numero_sistemas_controlados")
    private int numeroSistemasControlados;

    @Basic
    @Column(name = "ultima_compra")
    private LocalDate ultimaCompra;

    @OneToMany(mappedBy="faccion")
    private List<ArmasFacciones> armasFacciones;

    @OneToMany(mappedBy ="faccionUno")
    private List<Batalla> batallas;



}

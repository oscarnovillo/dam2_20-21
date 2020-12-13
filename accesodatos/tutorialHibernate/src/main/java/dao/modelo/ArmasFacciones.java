package dao.modelo;


import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "armas_facciones")
public class ArmasFacciones {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

//    @Basic
//    @Column(name = "nombre_faccion")
//    private String nombreFaccion;

//    @Basic
//    @Column(name = "id_arma")
//    private int id_arma;

    @ManyToOne
    @JoinColumn(name = "nombre_faccion",referencedColumnName = "nombre",nullable = false)
    private Faccion faccion;

    @ManyToOne
    @JoinColumn(name = "id_arma",referencedColumnName = "id",nullable = false)
    private Arma arma;
}

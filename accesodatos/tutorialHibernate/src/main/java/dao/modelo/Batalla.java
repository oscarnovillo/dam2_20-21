package dao.modelo;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="batallas")
public class Batalla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn (name="faccion_uno",referencedColumnName = "nombre",nullable = false)
    private Faccion faccionUno;


    @ManyToOne
    @JoinColumn (name="faccion_dos",referencedColumnName = "nombre",nullable = false)
    private Faccion faccionDos;


    @Basic
    @Column
    private String donde;


    @Basic
    @Column
    private LocalDate cuando;


    @ManyToOne
    @JoinColumn (name="id_espia",referencedColumnName = "id",nullable = false)
    private Espia espiaBlas;



}

package dao.modelo;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name= "espias")
public class Espia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "nombre")
    private String nombre;

    @Basic
    @Column(name = "raza")
    private String raza;

    @OneToMany( mappedBy = "espiaBlas")
    private List<Batalla> batallas;


}

package dao.modelo;

import lombok.Data;

import javax.persistence.Entity;



@Data
public class Informe {

    private final Arma arma;
    private final int numeroSistemasControlados;
}

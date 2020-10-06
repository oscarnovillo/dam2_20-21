package dao.modelo;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
public class Fecha {

    private int id;
    private String name;
    private LocalDateTime fecha;
    private int numero;



}

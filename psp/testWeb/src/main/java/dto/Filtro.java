package dto;


import lombok.Data;

@Data
public class Filtro {
    private final String jjj;
    private final String[] cabeceras;
    private final int columnas;
    private final int inferior;
}

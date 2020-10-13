package servicios;


import dto.Filtro;
import dto.FiltroException;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Validador {


    public Filtro validarFiltro(String jjj, String [] cabeceras, String columnas,String inferior,String superior)
            throws FiltroException
    {
        String error = null;
        var inferiorInterno = 0;
        var superiorInterno=0;
        Filtro f = null;

        if ( (cabeceras==null) || (columnas==null) )
        {
            error =" jjj o cabecera o cllumnas no se admiten nulos";
        }
        if (inferior!= null && !inferior.isBlank())
        {
            if (!inferior.chars().allMatch(Character::isDigit))
                error = "inferior debe ser numero";
            else
                inferiorInterno = Integer.parseInt(inferior);
        }
        else
            inferiorInterno=0;

        if (superior!= null && !superior.isBlank())
        {
            if (!superior.chars().allMatch(Character::isDigit))
                error = "inferior debe ser numero";
            else
                superiorInterno = Integer.parseInt(superior);
        }
        if (error == null) {
            long numeroCabeceras = Arrays.stream(cabeceras)
                    .filter(s -> !s.isBlank()).count();
            int columna = Integer.parseInt(columnas);

            if (numeroCabeceras != columna) {
                error = "cabceras y columnas no son iguales";
            }
        }
        if (error == null)
        {

           f = new Filtro(jjj == null ? "":jjj,
                   Arrays.stream(cabeceras)
                   .filter(s -> !s.isBlank()).toArray(String[]::new),Integer.parseInt(columnas)
           ,inferiorInterno,superiorInterno);
        }
        else
        {
            throw new FiltroException(error);
        }
        return f;

    }
}

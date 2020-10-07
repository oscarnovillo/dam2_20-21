package servicios;


import dto.Filtro;
import dto.FiltroException;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Validador {


    public Filtro validarFiltro(String jjj, String [] cabeceras, String columnas) throws FiltroException
    {
        String error = null;
        Filtro f = null;

        if ( (cabeceras==null) || (columnas==null) )
        {
            error =" jjj o cabecera o cllumnas no se admiten nulos";
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
           f = new Filtro(jjj,Arrays.stream(cabeceras)
                   .filter(s -> !s.isBlank()).toArray(String[]::new),Integer.parseInt(columnas));
        }
        else
        {
            throw new FiltroException(error);
        }
        return f;

    }
}

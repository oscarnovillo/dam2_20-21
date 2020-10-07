package servicios;


import java.util.Arrays;

public class Validador {


    public String validarParametros(String jjj, String [] cabeceras,String columnas)
    {
        String error = null;

        if ( (jjj==null) || (cabeceras==null) || (columnas==null) )
        {
            error =" jjj o cabecera o cllumnas no se admiten nulos";
        }
        else if (jjj.isBlank())
        {
            error = "no blancos";
        }
        else if (!jjj.chars().allMatch(Character::isDigit))
        {
            error = "se requiere numero";
        }
        if (error == null) {
            long numeroCabeceras = Arrays.stream(cabeceras)
                    .filter(s -> !s.isBlank()).count();
            int columna = Integer.parseInt(columnas);

            if (numeroCabeceras != columna) {
                error = "cabceras y columnas no son iguales";
            }
        }
        return error;

    }
}

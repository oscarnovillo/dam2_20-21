package servicios;

public class Validador {


    public String validarParametros(String jjj)
    {
        String error = null;

        if (jjj==null)
        {
            error =" no se admiten nulos";
        }
        else if (jjj.isBlank())
        {
            error = "no blancos";
        }
        else if (!jjj.chars().allMatch(Character::isDigit))
        {
            error = "se requiere numero";
        }
        return error;

    }
}

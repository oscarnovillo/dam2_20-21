package servicios;

import dao.AlumnosDao;
import dto.Filtro;
import dto.FiltroException;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;

public class ServiciosAlumnos {


    public List getAlumnos(Filtro f)
    {
        AlumnosDao dao =  new AlumnosDao();


        return dao.getAlumnos(f);
    }


}

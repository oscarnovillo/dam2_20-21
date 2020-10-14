package dao;

import dao.modelo.Alumno;
import dto.Filtro;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AlumnosDao {

    private static List<Alumno> alumnos;


    public List<Alumno> getAlumnos(Filtro f) {

        var limite = alumnos.size();

        if (f.getSuperior()>0)
            limite= f.getSuperior();


        return alumnos.stream().skip(f.getInferior())
                .limit(limite-f.getInferior()+1).filter(alumno -> alumno.getNombre()
                .contains(f.getJjj())).collect(Collectors.toList());
    }


    public AlumnosDao() {
        alumnos = new ArrayList<>();
        alumnos.add(new Alumno("nombre",18, LocalDate.now()));
        alumnos.add(new Alumno("nombre1",18, LocalDate.now()));
        alumnos.add(new Alumno("nombre2",18, LocalDate.now()));
        alumnos.add(new Alumno("nombre3",18, LocalDate.now()));
        alumnos.add(new Alumno("nombre4",18, LocalDate.now()));
    }




}

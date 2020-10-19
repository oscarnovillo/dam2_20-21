package servicios;

import dao.DaoLectores;
import dao.modelo.Lector;

import java.util.List;

public class ServiciosLectores {


    public boolean addLector(Lector l)
    {
        // hasheo de password
        DaoLectores dao = new DaoLectores();
        return dao.addLector(l);
    }


    public List<Lector> getLectores()
    {
        DaoLectores dao = new DaoLectores();
        return dao.getLectores();
    }
}

package servicios;

import dao.DaoLectores;
import dao.modelo.Lector;

public class ServiciosLectores {


    public boolean addLector(Lector l)
    {
        DaoLectores dao = new DaoLectores();
        return dao.addLector(l);
    }
}

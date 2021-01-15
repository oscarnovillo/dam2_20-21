package servicios;

import dagger.Component;
import dao.ModuleDao;

@Component(modules = ModuleDao.class)
public interface ServiciosComponent {

    ServiciosMain getServiciosMain();
}

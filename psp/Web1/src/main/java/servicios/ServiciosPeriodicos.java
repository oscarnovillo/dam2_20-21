package servicios;

import dao.DaoPeriodicos;
import modelo.Periodico;

import javax.inject.Inject;
import java.util.List;

public class ServiciosPeriodicos {


    private DaoPeriodicos dao;

    @Inject
    public ServiciosPeriodicos(DaoPeriodicos dao) {
        this.dao = dao;
    }

    public List<Periodico> getPeriodicos(){
        return dao.getPeriodicos();
    }

    public boolean addPeriodico(Periodico p){
        return dao.addPeriodico(p);
    }
    public boolean delPeriodicos(Periodico p){
        return dao.delPeriodicos(p);
    }

    public boolean updatePeriodicos(Periodico p){
        return dao.updatePeriodicos(p);
    }

}

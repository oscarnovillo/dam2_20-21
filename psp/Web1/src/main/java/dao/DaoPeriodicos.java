package dao;

import config.Config;
import modelo.Periodico;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DaoPeriodicos {

    private Config config;

    @Inject
    public DaoPeriodicos(Config config) {
        this.config = config;
    }

    private static List<Periodico> periodicos = new ArrayList<>();

    static {
        Periodico p = new Periodico("marca",""+1.78);
        Periodico p1 = new Periodico("as",""+1.5);
        Periodico p2 = new Periodico("sport",""+1.5);

        periodicos.add(p);
        periodicos.add(p1);
        periodicos.add(p2);

    }

    public List<Periodico> getPeriodicos(){
        return periodicos;
    }

    public boolean addPeriodico(Periodico p){
        return periodicos.add(p);
    }
    public boolean delPeriodicos(Periodico p){
        return periodicos.remove(p);
    }

    public boolean updatePeriodicos(Periodico p){
        periodicos.remove(p);


        return periodicos.add(p);
    }

}

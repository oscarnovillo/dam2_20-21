package servicios;

import dao.DaoMain;

import javax.inject.Inject;

public class ServiciosMain {


    private DaoMain main;

    @Inject
    public ServiciosMain(DaoMain main) {
        this.main = main;
    }

    public int numeros() {
        return main.getNumeros();
    }


}

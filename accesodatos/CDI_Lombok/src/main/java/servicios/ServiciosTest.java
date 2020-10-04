package servicios;

import dao.DaoTest;
import dao.DaoTestImpl;

import javax.inject.Inject;

public class ServiciosTest {

  @Inject
  DaoTest d;

  public int dameNumero()
  {
   // DaoTest d = new DaoTest();
    return d.dameNumero();
  }
  public String dameNombre(int condicion)
  {
   // DaoTest d = new DaoTest();
    if (condicion ==1 || condicion==2)
      return d.dameNombre();
    else
      return "Condicion distinto de 2";
  }



}

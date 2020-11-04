package servicios;

import dao.*;

import javax.inject.Inject;
import javax.validation.Validator;

public class ServiciosTest {

  @Inject
  private DaoTest d;

  @Inject
  private Validator validator;


  public int dameNumero()
  {
   // DaoTest d = new DaoTestImpl();
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

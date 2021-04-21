package servicios;

import dao.*;

import javax.inject.Inject;
import javax.validation.Validator;

public class ServiciosTest {


  private DaoTest d;


  private Validator validator;



  @Inject
  public ServiciosTest(DaoTest d, Validator validator) {
    this.d = d;
    this.validator = validator;
  }

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

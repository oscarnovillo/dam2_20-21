package dao;

import config.Configuration;

public class DaoTestImpl implements DaoTest {

  public int dameNumero()
  {
    return 7;
  }

  public String dameNombre()
  {
    return "pepe "+ Configuration.getInstance().getRuta();
  }
}

package dao;

import config.Configuration;

import javax.inject.Inject;

public class DaoTestImpl implements DaoTest {


  @Inject
  Configuration config;

  public int dameNumero()
  {
    return 7;
  }

  public String dameNombre()
  {
    return "pepe "+ config.getRuta();
  }
}

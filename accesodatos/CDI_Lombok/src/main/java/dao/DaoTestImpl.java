package dao;

import config.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;



public class DaoTestImpl implements DaoTest {

  private final Configuration config;

  @Inject
  public DaoTestImpl(Configuration config) {
    this.config = config;
  }

  public int dameNumero()
  {
    return 7;
  }

  public String dameNombre()
  {
    return "pepe "+ config.getRuta();
  }

  @Override
  public Optional<Test> get(long id) {
    return Optional.empty();
  }

  @Override
  public List<Test> getAll() {
    return null;
  }

  @Override
  public void save(Test test) {

  }

  @Override
  public void update(Test test, String[] params) {

  }

  @Override
  public void delete(Test test) {

  }
}

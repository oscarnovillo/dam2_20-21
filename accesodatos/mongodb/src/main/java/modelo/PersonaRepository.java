package modelo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository @Component
public interface PersonaRepository extends MongoRepository<Persona,String> {


  List<Persona> findDistinctByName(String name);
  List<Persona> findAllByName();

  @Query("{'cosas.nombre': ?0,'cosas.cantidad' : ?1}")
  List<Persona> findByCosasNombre(String nombreCosa,int cantidad);

}

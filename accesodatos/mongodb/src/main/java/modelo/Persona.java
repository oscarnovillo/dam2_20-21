package modelo;



import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @AllArgsConstructor
@Builder @ToString
public class Persona {


  private ObjectId id;

  private String name;
  private LocalDate fecha;
  private ObjectId _idLector;

  private List<Things> cosas;

  public Persona() {
    cosas = new ArrayList<>();
    fecha = LocalDate.now();
    name = "no cargado";
  }
}

package modelo;



import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @AllArgsConstructor
@Builder @ToString
@Document(collection = "est")
public class Persona {


  @Id
  private ObjectId _id;

  private String name;
  private LocalDate fecha;
  private ObjectId _idLector;

  private List<Things> cosas;

  public Persona() {
    cosas = new ArrayList<>();
    fecha = LocalDate.now();
  }
}

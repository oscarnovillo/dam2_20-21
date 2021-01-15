package modelo;

import lombok.*;

@Getter @Setter @Builder @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Things {

  private String nombre;
  private int cantidad;

}

package dao.modelo;

import lombok.*;

import java.time.LocalDate;

@RequiredArgsConstructor

@Builder
@Getter
@Setter
public class ApiError {

  private  String message;
  private  LocalDate fecha;


}

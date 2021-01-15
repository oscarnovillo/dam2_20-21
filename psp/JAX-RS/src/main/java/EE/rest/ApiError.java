package EE.rest;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;

@RequiredArgsConstructor
@Data
public class ApiError {

  private final String message;
  private final LocalDate fecha;


}

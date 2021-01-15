package modelo;

import org.bson.Document;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersonaConverter {


  public Document convertPersonaDocument(Persona p) {
    Document d = new Document().append("name", p.getName())
        .append("fecha", Date.from(p.getFecha().atStartOfDay()
            .atZone(ZoneId.of("UTC"))
            .toInstant()));

    d.append("cosas", p.getCosas().stream()
        .map(things -> new Document().append("nombre", things.getNombre())
            .append("cantidad", things.getCantidad()))
        .collect(Collectors.toList()));
    return d;
  }

  public Persona convertDocumentPersona(Document d) {


    List<Document> lista = d.getList("cosas",Document.class);
    if (lista == null)
      lista = new ArrayList();

    return Persona.builder().name(d.getString("name"))
        .fecha(
            Optional.ofNullable(d.getDate("fecha"))
                .map(date -> date.toInstant().atZone(ZoneId.of("UTC")).toLocalDate())
                .orElse(null))
        ._id(d.getObjectId("_id"))
        .cosas(lista.stream().map(document ->
            Things.builder()
            .nombre(((Document)document).getString("nombre"))
            .cantidad(Optional.ofNullable(((Document)document).getInteger("cantidad")).orElse(0)).build()).collect(Collectors.toList()))
        .build();
  }
}

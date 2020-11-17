package main;

import dao.modelo.Asignatura;
import dao.utils.CreateModel;
import dao.utils.DBConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class TestUpdate {

    public static void main(String[] args) {
        CreateModel.createModel();
        DBConnection db = new DBConnection();
        JdbcTemplate jtm = new JdbcTemplate(
                db.getDataSource());
        Asignatura a = new Asignatura(100, "nombre", "cliclo", "curso");
        String updateQuery = "update asignaturas set NOMBRE = ?, CICLO=?, CURSO=? where id = ?";


        System.out.println(jtm.update(updateQuery, new Object[] {a.getNombre(), a.getCiclo(), a.getCurso(), a.getId()}));

        System.out.println(jtm.update(updateQuery, a.getNombre(), a.getCiclo(), a.getCurso(), a.getId()));

//         updateQuery = "update asignaturas set NOMBRE =:nombre, CICLO=:ciclo, CURSO=:curso where id = :id";
//
//        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(a);
//        System.out.println(jtm.update(updateQuery, namedParameters));

    }
}

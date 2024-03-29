package main;


import dao.modelo.Asignatura;
import dao.utils.CreateModel;
import dao.utils.DBConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TestInsert {

    public static void main(String[] args) {
        CreateModel.createModel();
        DBConnection db = new DBConnection();

        Asignatura a = new Asignatura();
        a.setCiclo("ciclo");
        a.setCurso("curso");
        a.setNombre("damn");
        a.setId(200);
        try {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(
                    db.getDataSource())
                    .withTableName("asignaturas")
                    .usingGeneratedKeyColumns("ID");
//            Map<String, Object> parameters = new HashMap<>();
//
//            parameters.put("NOMBRE", a.getNombre());
//            parameters.put("CICLO", a.getCiclo());
//            parameters.put("CURSO", a.getCurso());
//            a.setId((int) jdbcInsert.executeAndReturnKey(parameters).longValue());

            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(a);
            a.setId((int) jdbcInsert.executeAndReturnKey(namedParameters).longValue());
            System.out.println(a);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }


        KeyHolder keyHolder = new GeneratedKeyHolder();

        JdbcTemplate jtm = new JdbcTemplate(
                db.getDataSource());
        jtm.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into asignaturas (NOMBRE,CICLO,CURSO) VALUES (?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "a.getNombre()");
            ps.setString(2, "a.getCiclo()");
            ps.setString(3, "a.getCurso()");

            return ps;
        }, keyHolder);

        System.out.println(Optional.ofNullable(keyHolder.getKey()).orElse(null));

    }
}

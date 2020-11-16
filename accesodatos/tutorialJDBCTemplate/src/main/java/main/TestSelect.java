package main;

import dao.modelo.Asignatura;
import dao.utils.CreateModel;
import dao.utils.DBConnection;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@Log4j2

public class TestSelect {

    @SneakyThrows
    public static void main(String[] args) {
        CreateModel.createModel();
        DBConnection db = new DBConnection();

        JdbcTemplate jtm = new JdbcTemplate(
                db.getDataSource());
        jtm.query("Select * from asignaturas",
                BeanPropertyRowMapper.newInstance(Asignatura.class))
                .stream().forEach(System.out::println);

        jtm.query

        log.info("kkk");


    }
}

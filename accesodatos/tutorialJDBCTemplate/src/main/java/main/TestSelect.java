package main;

import dao.modelo.Asignatura;
import dao.utils.CreateModel;
import dao.utils.DBConnection;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
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


        // select devuelve LIST
        jtm.query("Select * from asignaturas",
                BeanPropertyRowMapper.newInstance(Asignatura.class))
                .stream().forEach(System.out::println);

        // select con parametros
        jtm.query("Select * from asignaturas where id=? and nombre like ?",
                BeanPropertyRowMapper.newInstance(Asignatura.class),
                new Object[] {100,"%a%"} )
                .stream().forEach(System.out::println);

        System.out.println();
        // select con parametros
        String sql = "Select * from asignaturas where id IN ( ?";
        for (int i=0; i<3;i++)
            sql += ",?";
        sql += ")";

        Object [] params = new Object[4];
        params[0]  = 100;
        params[1]  = 111;
        params[2]  = 207;
        params[3]  = 208;

        jtm.query(sql,
                BeanPropertyRowMapper.newInstance(Asignatura.class),
                params )
                .stream().forEach(System.out::println);


        try
        {
            // si no existe salta exception
            Asignatura s = jtm.queryForObject("Select * from asignaturas where id=? and nombre like ?",
                    BeanPropertyRowMapper.newInstance(Asignatura.class),
                    new Object[] {100000,"%a%"} );

            System.out.println(s);
        }
        catch (EmptyResultDataAccessException e)
        {
            System.out.println("No encontrado");
            log.error(e.getMessage(),e);
        }
        catch (Exception e)
        {
            System.out.println("otra cosa");
        }

        db.cerrarPool();



    }
}

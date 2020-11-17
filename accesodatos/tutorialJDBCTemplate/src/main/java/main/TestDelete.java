package main;

import dao.utils.CreateModel;
import dao.utils.DBConnection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestDelete {

    public static void main(String[] args) {
        CreateModel.createModel();
        DBConnection db = new DBConnection();
        int filas = -1;

        try {
            JdbcTemplate jtm = new JdbcTemplate(
                    db.getDataSource());
            String updateQuery = "delete from asignaturas where id = ?";

            filas = jtm.update(updateQuery, 212);
        } catch (DataIntegrityViolationException e) {
            filas = -2;
        } catch (Exception ex) {
            System.out.println("error ");
           // filas = -1;
        }
        System.out.println(filas);
    }
}

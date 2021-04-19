package tutorial;

import dao.modelo.Fecha;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

@Log4j2
public class PrimeraConexion {


    @SneakyThrows
    public static void main(String[] args) {
        Connection connection = null;

        List<Fecha> fechas = new ArrayList();

        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(
                "jdbc:mysql://dam2.mysql.iesquevedo.es:3335/netflisssss",
                "root",
                "quevedo2020");

        //System.out.println("23434".chars().allMatch(value -> Character.isDigit(value)));
        Statement stmt = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        var name = sc.nextLine();
        try {

            stmt = connection.createStatement();

            pst = connection.prepareStatement(
                    "select * from table_fechas2 where name = ? " +
                            " and numero > ? and date > ?");
            LocalDateTime ldt = LocalDateTime.of(2000,10,10,10,10);

            pst.setString(1,name);
            pst.setInt(2,10);
            pst.setTimestamp(3,Timestamp.valueOf(ldt));

            //rs = stmt.executeQuery("select * from table_fechas2 where name='" + name + "'");

            rs= pst.executeQuery();
        /*

            select id as temarioAD from table_fechas2 where name='2'
            select * from table_fechas2 where name=' 2' OR 1=1 #'
            2' OR 1=1 #

             */


            while (rs.next()) {
                fechas.add( Fecha.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .fecha(rs.getTimestamp("date").toLocalDateTime())
                        .numero(rs.getInt("numero")).build());
            }

        } catch (Exception e) {
            log.info(e.getMessage(),e);
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }


        fechas.stream().forEach(System.out::println);
    }
}

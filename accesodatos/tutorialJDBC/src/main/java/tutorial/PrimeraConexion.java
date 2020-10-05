package tutorial;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Logger;

public class PrimeraConexion {

    @SneakyThrows
    public static void main(String[] args) {
        Connection connection = null;

        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(
                "jdbc:mysql://dam2.mysql.iesquevedo.es:3335/netflisssss",
                "root",
                "root");

        System.out.println("234.34".chars().allMatch(value -> Character.isDigit(value)));
        Statement stmt = null;
        ResultSet rs=null;
        Scanner sc = new Scanner(System.in);
        var name = sc.nextLine();
        try {

            stmt = connection.createStatement();

            rs = stmt.executeQuery("select * from table_fechas2 where name='"+name+"'");

            while (rs.next())
            {
                System.out.print(rs.getInt("id"));
                System.out.print(" "+rs.getString("name"));
                System.out.print(" "+rs.getTimestamp("date").toLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
                System.out.print(" "+rs.getInt("numero"));
                System.out.println();
            }

        } catch (Exception e) {
            Logger.getLogger("Main").info(e.getMessage());
        } finally {
            rs.close();
            stmt.close();
            connection.close();
        }
    }
}

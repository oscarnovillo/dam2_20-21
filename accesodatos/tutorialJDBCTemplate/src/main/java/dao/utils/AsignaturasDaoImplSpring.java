/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.utils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author oscar
 */
public class AsignaturasDaoImplSpring {

  //select JDBCTemplate
//
//
//  @Override
//  public List<Asignatura> getAllAsignaturasJDBCTemplate() {
//
//    JdbcTemplate jtm = new JdbcTemplate(
//        DBConnectionPool.getInstance().getDataSource());
//    return jtm.query("Select * from asignaturas",
//        BeanPropertyRowMapper.newInstance(Asignatura.class));
//  }
//
//
//
//
//  @Override
//  public Asignatura getAsignaturaJDBCTemplate(int id) {
//
//    JdbcTemplate jtm = new JdbcTemplate(
//        DBConnectionPool.getInstance().getDataSource());
//    List<Asignatura> asignatura =
//            jtm.query("Select * from asignaturas where ID = ?",
//       BeanPropertyRowMapper.newInstance(Asignatura.class),
//                    new Object[]{id});
//    return asignatura.isEmpty() ? null : asignatura.get(0);
//
//
//  }
//
//  //Select JDBCTemplate
//
//
//  @Override
//  public List<Asignatura> getAllAsignaturasNotasJDBCTemplate() {
//    JdbcTemplate jtm = new JdbcTemplate(
//        DBConnectionPool.getInstance().getDataSource());
//   return  jtm.query("SELECT * FROM asignaturas where id in(select distinct(ID_ASIGNATURAS) from notas)",
//            BeanPropertyRowMapper.newInstance(Asignatura.class));
//
//  }
//
//  //insert spring jdbc template
//
//
//  @Override
//  public Asignatura addAsignaturaJDBCTemplate(Asignatura a) {
//
//    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(
//        DBConnection.getInstance().getDataSource())
//            .withTableName("asignaturas")
//            .usingGeneratedKeyColumns("ID");
//    Map<String, Object> parameters = new HashMap<>();
//
//    parameters.put("NOMBRE", a.getNombre());
//    parameters.put("CICLO", a.getCiclo());
//    parameters.put("CURSO", a.getCurso());
//    a.setId((int) jdbcInsert.executeAndReturnKey(parameters).longValue());
//    return a;
//  }
//
//
//  @Override
//  public int insertJDBCTemplate(Asignatura a) {
//    KeyHolder keyHolder = new GeneratedKeyHolder();
//
//    JdbcTemplate jtm = new JdbcTemplate(
//            DBConnectionPool.getInstance().getDataSource());
//    jtm.update(connection -> {
//      PreparedStatement ps = connection
//              .prepareStatement("insert into asignaturas (NOMBRE,CICLO,CURSO) VALUES (?,?,?)",
//                      Statement.RETURN_GENERATED_KEYS);
//      ps.setString(1, a.getNombre());
//      ps.setString(2, a.getCiclo());
//      ps.setString(3, a.getCurso());
//
//      return ps;
//    },keyHolder);
//
//      a.setId((int)keyHolder.getKey());
//
//    return (int)keyHolder.getKey();
//  }
//
//
//
//  // update JDBCTemplate
//
//
//  @Override
//  public int updateJDBCTemplate(Asignatura a) {
//
//    JdbcTemplate jtm = new JdbcTemplate(
//        DBConnectionPool.getInstance().getDataSource());
//    String updateQuery = "update asignaturas set NOMBRE = ?, CICLO=?, CURSO=? where id = ?";
//    return jtm.update(updateQuery, a.getNombre(), a.getCiclo(), a.getCurso(), a.getId());
//  }
//
//  //delete JDBCTemplate
//
//
//  @Override
//  public int deleteJDBCTemplate(int id) {
//    int filas = -1;
//
//    try {
//      JdbcTemplate jtm = new JdbcTemplate(
//          DBConnectionPool.getInstance().getDataSource());
//      String updateQuery = "delete from asignaturas where id = ?";
//
//      filas = jtm.update(updateQuery, id);
//    } catch (DataIntegrityViolationException e) {
//      if (e.getMessage().contains("violación")) {
//        filas = -2;
//      }
//    } catch (Exception ex) {
//      Logger.getLogger(AsignaturasDaoImplSpring.class.getName()).log(Level.SEVERE, null, ex);
//    }
//    return filas;
//  }
//
//  // delete trannsaccion JDBCTemplate
//
//
//  @Override
//  public int deleteTransaccJDBCTemplate(Asignatura a) {
//    int filas = -1;
//    TransactionDefinition txDef = new DefaultTransactionDefinition();
//    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(
//    DBConnectionPool.getInstance().getDataSource());
//    TransactionStatus txStatus = transactionManager.getTransaction(txDef);
//
//    try {
//
//      JdbcTemplate jtm = new JdbcTemplate(
//          transactionManager.getDataSource());
//      String updateQuery = "delete from notas where ID_ASIGNATURAS = ?";
//      jtm.update(updateQuery, a.getId());
//
//      updateQuery = "delete from asignaturas where ID = ?";
//      filas = jtm.update(updateQuery, a.getId());
//
//      transactionManager.commit(txStatus);
//
//    } catch (Exception e) {
//
//      transactionManager.rollback(txStatus);
//
//      throw e;
//
//    }
//
//    return filas;
//  }

}

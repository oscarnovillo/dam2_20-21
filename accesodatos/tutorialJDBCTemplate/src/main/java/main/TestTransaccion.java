package main;

import dao.utils.CreateModel;
import dao.utils.DBConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TestTransaccion {

    public static void main(String[] args) {

        CreateModel.createModel();
        DBConnection db = new DBConnection();
        int filas = -1;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(
                db.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);

        try {

            JdbcTemplate jtm = new JdbcTemplate(
                    transactionManager.getDataSource());
            String updateQuery = "delete from notas where ID_ASIGNATURAS = ?";
            jtm.update(updateQuery, 100);

            updateQuery = "delete from asignaturas where ID = ?";
            filas = jtm.update(updateQuery, 100);

            transactionManager.commit(txStatus);

        } catch (Exception e) {

            transactionManager.rollback(txStatus);

            throw e;

        }

        System.out.println(filas);
//  }
    }
}

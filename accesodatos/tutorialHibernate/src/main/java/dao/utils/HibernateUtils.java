package dao.utils;

import config.ConfigurationSingleton;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtils {

    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            configuration.configure()
                    .setProperty("hibernate.connection.url", ConfigurationSingleton.getInstance().getRuta())
                    .setProperty("hibernate.connection.username", ConfigurationSingleton.getInstance().getUser())
                    .setProperty("hibernate.connection.password",ConfigurationSingleton.getInstance().getPassword())
                    .setProperty("hibernate.connection.driver_class",ConfigurationSingleton.getInstance().getDriver())
            ;

            //configuration.setProperty()
            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

}

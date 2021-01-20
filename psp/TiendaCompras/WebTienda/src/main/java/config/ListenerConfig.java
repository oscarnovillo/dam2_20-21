package config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class ListenerConfig implements ServletContextListener
        {

    // Public constructor is required by servlet spec
    public ListenerConfig() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed).
         You can initialize servlet context related data here.
      */

//        ConfigurationSingleton.cargarInstance(sce.getServletContext().getResourceAsStream("/WEB-INF/config/config.yaml")
//                ,sce.getServletContext().getRealPath("/WEB-INF/"));
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context
         (the Web application) is undeployed or
         Application Server shuts down.
      */
//        hibernateUtils.close();
//        .getInstance().close();
    }


}

/*
 * Isomorphic SmartGWT web presentation layer
 * Copyright 2000 and beyond Isomorphic Software, Inc.
 *
 * OWNERSHIP NOTICE
 * Isomorphic Software owns and reserves all rights not expressly granted in this source code,
 * including all intellectual property rights to the structure, sequence, and format of this code
 * and to all designs, interfaces, algorithms, schema, protocols, and inventions expressed herein.
 *
 *  If you have any questions, please email <sourcecode@isomorphic.com>.
 *
 *  This entire comment must accompany any portion of Isomorphic Software source code that is
 *  copied or moved from this file.
 */

package ${package}.server.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hsqldb.Server;

public class HSQLServletContextListener implements ServletContextListener {

    private static final String HSQLDB_DRIVER = "org.hsqldb.jdbcDriver";

    private static final String URL_PROPERTY = "jdbc:hsqldb:hsql://localhost/isomorphic";
    private static final String DATA_DIR_PROPERTY = "WEB-INF/db/hsqldb";
    private static final String DATABASE_PROPERTY = "isomorphic";
    
    private static final String USER_PROPERTY = "sa";
    private static final String PASSWORD_PROPERTY = "";

    public void contextInitialized(ServletContextEvent sce) {

    	ServletContext context = sce.getServletContext();

        Server.main(new String[]{
            "-database.0", context.getRealPath(DATA_DIR_PROPERTY + "/" + DATABASE_PROPERTY),
            "-dbname.0", DATABASE_PROPERTY,
            "-no_system_exit",
            "true"
        });

    }

    public void contextDestroyed(ServletContextEvent sce) {
        
    	Connection connection = null;
        try {
        	
            Class.forName(HSQLDB_DRIVER);
            connection = DriverManager.getConnection(URL_PROPERTY, USER_PROPERTY, PASSWORD_PROPERTY);

            Statement stmt = connection.createStatement();
            stmt.executeUpdate("SHUTDOWN;");
            stmt.close();
            
        } catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
            try {
                connection.close();
            } catch (SQLException ignore) {
            }
		}
    }
}
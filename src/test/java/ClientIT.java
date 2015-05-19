import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

import org.junit.Test;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class ClientIT {
	
	@Test
	public void test1() throws Exception {    
		Properties properties = new Properties();
		properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        properties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        properties.put(Context.SECURITY_PRINCIPAL, "admin");  
        properties.put(Context.SECURITY_CREDENTIALS, "Passw0rd"); 
        InitialContext context = new InitialContext(properties);
        
        ConnectionPoolDataSource ds = (MysqlConnectionPoolDataSource) context.lookup("java:jboss/mysql");
        PooledConnection pConn = ds.getPooledConnection();
        Connection conn = pConn.getConnection();        
        Statement stmt = conn.createStatement();        
        ResultSet rs = stmt.executeQuery("select sex from student where id = 2");
        while (rs.next()) {
            System.out.println(rs.getString("sex"));
        }       
	}
}


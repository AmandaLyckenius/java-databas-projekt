package se.amanda;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = JDBCUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IOException("Unable to find application.properties");
            }
            properties.load(input);
        } catch(IOException e ){
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to load database properties");
        }
    }

    public static Connection getConnection() throws SQLException {
        Driver hsqlDriver=new org.hsqldb.jdbcDriver();
        DriverManager.registerDriver(hsqlDriver);
        String dbURL = properties.getProperty("db.url");
        String userId = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        Connection conn = DriverManager.getConnection(dbURL,userId,password);
        conn.setAutoCommit(false);
        return conn;
    }

    public static void closeConnection(Connection conn){
        try{
            if (conn != null) {
                conn.close();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void closeStatement(Statement stmt){
        try{
            if(stmt != null){
                stmt.close();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet rs){
        try{
            if (rs != null){
                rs.close();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void commit(Connection conn){
        try{
            if (conn != null){
                conn.commit();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void rollback(Connection conn){
        try{
            if(conn != null){
                conn.rollback();
            }
        }    catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getDatabaseProductName(Connection conn){
        try{
            if (conn != null){
                DatabaseMetaData metaData = conn.getMetaData();
                return metaData.getDatabaseProductName();
            }
        }   catch (SQLException e){
            e.printStackTrace();
        }

        return "unknown";
    }


}

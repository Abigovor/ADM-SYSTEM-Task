package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Single on 23.06.2015.
 */
public class JdbcUtils {
    private static volatile boolean initialized;

    private JdbcUtils() {
    }

    public static synchronized void initDriver(String driverClass) {
        if (!initialized) {
            try {
                Class.forName(driverClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Can't initialize driver: " + driverClass);
            }
            initialized = true;
        }
    }

    public static void rollbackQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                /* NOP */
            }
        }
    }

    public static void closeQuietly(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // NOP
            }
        }
    }

    public static void closeQuietly(Statement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                // NOP
            }
        }
    }

    public static void closeQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // NOP
            }
        }
    }
}

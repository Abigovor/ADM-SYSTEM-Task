package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Single on 23.06.2015.
 */
public class DaoImpl<T> implements Dao {

    private static volatile DaoImpl instance;

    static {
        JdbcUtils.initDriver("com.mysql.jdbc.Driver");
    }

    private String login;
    private String password;
    private String DBName;
    private String ipAddress;
    private String port;

    public static DaoImpl getInstance() {
        if (instance == null) {
            synchronized (DaoImpl.class) {
                if (instance == null) {
                    instance = new DaoImpl();
                }
            }
        }
        return instance;
    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" + ipAddress + ":" + port + "/" + DBName,
                login,
                password);
    }

    private DaoImpl() {
    }

    @Override
    public List selectAll() throws SQLException {
        Connection connection = getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM  user");
            ArrayList<String> result = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_user");
                String name = resultSet.getString("user_name");
                result.add(id + " " + name);
            }
            connection.commit();
            return result;
        } catch (SQLException e) {
            JdbcUtils.rollbackQuietly(connection);
            throw e;
        } finally {
            JdbcUtils.closeQuietly(resultSet);
            JdbcUtils.closeQuietly(statement);
            JdbcUtils.closeQuietly(connection);
        }
    }


    @Override
    public boolean insert(String userName, String timeStart, String timeEnd, byte[] data) throws SQLException {
        boolean isInsert = true;
        Connection connection = getConnection();
        CallableStatement callableStatement = null;
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        connection.setAutoCommit(false);
        try {
            callableStatement = connection.prepareCall("{call insert_data(?,?,?,?)}");

            callableStatement.setString(1, userName);
            callableStatement.setTimestamp(2, Timestamp.valueOf(timeStart));
            callableStatement.setTimestamp(3, Timestamp.valueOf(timeEnd));
            callableStatement.setBytes(4, data);

            callableStatement.executeQuery();
            connection.commit();
        } catch (SQLException e) {
            isInsert = false;
            e.printStackTrace();
        } finally {
            JdbcUtils.closeQuietly(callableStatement);
            JdbcUtils.closeQuietly(connection);
        }

        return isInsert;
    }

    @Override
    public void update(String userName, byte[] data) throws SQLException {
        Connection connection = getConnection();
        CallableStatement cs = null;
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        connection.setAutoCommit(false);
        try {
            cs = connection.prepareCall("{call update_data(?,?)}");

            cs.setString(1, userName);
            cs.setBytes(2, data);

            cs.executeQuery();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeQuietly(cs);
            JdbcUtils.closeQuietly(connection);
        }
    }

    @Override
    public void setParameters(String nameDb, String login, String password, String ipAddress, String port) {
        this.DBName = nameDb;
        this.login = login;
        this.password = password;
        this.ipAddress = ipAddress;
        this.port = port;
    }
}
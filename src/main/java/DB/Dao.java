package DB;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Single on 23.06.2015.
 */
public interface Dao<T> {

    public List<T> selectAll() throws SQLException;

    public boolean insert(String userName, String timeStart, String endStart, byte[] date) throws SQLException;

    public void update(String userName, byte[] data) throws SQLException;

    public void setParameters(String nameDb, String loginDB, String passwordDB, String ipAddress, String port);
}

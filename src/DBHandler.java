import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBHandler {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/gameinfo";
    private static final String USER = "root";
    private static final String PASS = "password";

    private Connection connection;

    public DBHandler() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}

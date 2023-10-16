package connection;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {
    private static ConnectionDB instance;
    private Connection conn;

    public ConnectionDB() {
        String dbname = "easybank_v3";
        String user = "postgres";
        String password = "sadinzar29";

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, password);
            System.out.println("The Connection is succefully");
        } catch (Exception e) {
            e.printStackTrace(); // Changed to printStackTrace() for better error handling
            System.out.println("Error Of Connection");
        }

    }

    public static synchronized ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    public Connection getConnection() {
        return conn;
    }
}

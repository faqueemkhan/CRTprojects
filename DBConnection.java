import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static final String URL =
            "jdbc:mysql://localhost:3306/hospitaldb";

    static final String USER =
            "root";

    static final String PASSWORD =
            "NewPassword123!";

    public static Connection getConnection() {

        try {

            Connection con =
                    DriverManager.getConnection(
                            URL,
                            USER,
                            PASSWORD
                    );

            System.out.println(
                    "Database Connected Successfully!"
            );

            return con;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}
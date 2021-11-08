package database;

import java.sql.*;

/**
 * Klasa odpowiedzialna za nawiązanie połączenia z DB
 * Parametry:
 * url - wskazuje adres bazy danych
 * user - użytkownik do którego się łączymy
 * password - hasło użytkownika
 */
public class DatabaseConnection {

    private String url= "jdbc:mysql://localhost:3306/bookstore";
    private String user = "root"; //tutaj dobrze jest użyć innego użytkownika niż root
    private String password = "root";

    private static DatabaseConnection instance;

    private DatabaseConnection(){

    }

    synchronized public static DatabaseConnection getInstance(){
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*Metoda która zwalnia obiekty Connection, PreparedStatement, ResultSet po stronie DB
    Jeżeli tego by nie było to nowe połączenia w bazie byłyby otwierane ale nie były by nigdy zamykane
    co prowadziłoby do wysycenia puli  połączeń po stronie DB

     */
    public void freeResources(Connection connection, PreparedStatement preparedStatement,
                               ResultSet resultSet){
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

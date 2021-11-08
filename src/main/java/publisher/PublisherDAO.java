package publisher;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import database.DatabaseConnection;

public class PublisherDAO {
    public PublisherDTO findPublisher(String publisherName) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

        String sql = "select id, name, description, email, dialing_code, phone_number from publishers " +
                     "where name=?";
        try {
            connection = databaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,publisherName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String email = resultSet.getString("email");
                String dialingCode = resultSet.getString("dialing_code");
                String phoneNumber = resultSet.getString("phone_number");
                return PublisherDTO.builder().id(id).name(name).email(email).description(description)
                            .dialingCode(dialingCode).phoneNumber(phoneNumber).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.freeResources(connection, preparedStatement, resultSet);
        }
        return null;
    }


    public boolean update(int id, String publisherName, String email, String dialingCode, String phoneNumber,
                          String description) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        StringBuilder sqlBuilder = new StringBuilder("update publishers ")
                .append("set name=?, email=?, dialing_code=?, phone_number=?, description=? ")
                .append("where id=?;");
        try {
            connection = databaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            preparedStatement.setString(1, publisherName);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, dialingCode);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5, description);
            preparedStatement.setInt(6, id);

            return preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.freeResources(connection,preparedStatement,null);
        }
        return false;
    }
}

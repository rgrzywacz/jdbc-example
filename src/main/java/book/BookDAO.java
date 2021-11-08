package book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;

public class BookDAO {
    public List<String> findBooksTitles() {
        //Pobranie instancji, która serwuje połączenia
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        //Nawiązanie połączenia z DB
        Connection connection = databaseConnection.getConnection();
        //Polecenie, które ma zostać wysłane do DB
        String sql = "select title from books;";
        //Obiekt, który komunikuje się z DB, inicjalizujemy go tutaj aby można było potem go zwolnić w finally
        PreparedStatement preparedStatement = null;
        //ResultSet reprezentuje tablicę z danymi, które zwróciła DB
        ResultSet resultSet = null;
        List<String> titles = new ArrayList<String>();

        try {
            //Zainicjalizowanie Prepared Statement wraz z przekazaniem mu polecenia SQL, które ma być uruchomione DB
            preparedStatement = connection.prepareStatement(sql);
            //Wywołanie polecenia po stronie DB i przypisanie wyników do ResultSet
            resultSet = preparedStatement.executeQuery();

            //Iteracja po wierszach, które nam zwróciła DB
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                titles.add(title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //Zwolnienie zasobów
          databaseConnection.freeResources(connection, preparedStatement, resultSet);
        }
        return titles;
    }

    public List<BookDTO> findBooks() {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        StringBuilder sqlBuilder = new StringBuilder()
                .append("select b.id as bookId, b.title as bookTitle, b.pages_number as pagesNumber, ")
                .append("c.name as categoryName, p.name as publisherName, ")
                .append("concat(a.first_name, a.last_name) as authorFullName ")
                .append("from books b join categories c on b.category_id = c.id ")
                .append("join publishers p on p.id = b.publisher_id ")
                .append("join authors_books ab on ab.book_id=b.id ")
                .append("join authors a on a.id=ab.author_id;");
        String sql = sqlBuilder.toString();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<BookDTO> books = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("bookId");
                String title = resultSet.getString("bookTitle");
                int pagesNumber = resultSet.getInt("pagesNumber");
                String categoryName = resultSet.getString("categoryName");
                String publisherName = resultSet.getString("publisherName");
                String author = resultSet.getString("authorFullName");
                BookDTO book = BookDTO.builder().id(id).title(title).pagesNumber(pagesNumber)
                                      .categoryName(categoryName).publisherName(publisherName)
                                      .author(author).build();

                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.freeResources(connection, preparedStatement, resultSet);
        }
        return books;
    }

    public BookDTO findByTitle(String bookTitle) {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        Connection connection = databaseConnection.getConnection();

        StringBuilder sqlBuilder = new StringBuilder()
                .append("select b.id as bookId, b.title as bookTitle, b.pages_number as pagesNumber, ")
                .append("c.name as categoryName, p.name as publisherName, ")
                .append("concat(a.first_name, a.last_name) as authorFullName ")
                .append("from books b join categories c on b.category_id = c.id ")
                .append("join publishers p on p.id = b.publisher_id ")
                .append("join authors_books ab on ab.book_id=b.id ")
                .append("join authors a on a.id=ab.author_id ")
                .append("where b.title=?;");
        String sql = sqlBuilder.toString();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,bookTitle);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("bookId");
                String title = resultSet.getString("bookTitle");
                int pagesNumber = resultSet.getInt("pagesNumber");
                String categoryName = resultSet.getString("categoryName");
                String publisherName = resultSet.getString("publisherName");
                String author = resultSet.getString("authorFullName");
                BookDTO book = BookDTO.builder().id(id).title(title).pagesNumber(pagesNumber)
                                      .categoryName(categoryName).publisherName(publisherName)
                                      .author(author).build();
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.freeResources(connection, preparedStatement, resultSet);
        }
        return null;
    }

}

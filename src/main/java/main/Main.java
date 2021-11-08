package main;

import java.util.List;

import book.BookDAO;
import book.BookDTO;
import category.CategoryDAO;
import publisher.PublisherDAO;
import publisher.PublisherDTO;

public class Main {
    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();

        List<String> titles = bookDAO.findBooksTitles();
        //titles.forEach(System.out::println);

        List<BookDTO> books = bookDAO.findBooks();
        //books.forEach(System.out::println);

        BookDTO bookDTO = bookDAO.findByTitle("Kaznodzieja");
        System.out.println(bookDTO);

        PublisherDAO publisherDAO = new PublisherDAO();
        PublisherDTO publisherDTO = publisherDAO.findPublisher("Wydawnictwo Zgierz");
        System.out.println(publisherDTO);

        publisherDTO.setDescription("Najstarsze wydawnictwo w Zgierzu");
        publisherDTO.setPhoneNumber("600600600");
        publisherDAO.update(publisherDTO.getId(),publisherDTO.getName(), publisherDTO.getEmail(),
                            publisherDTO.getDialingCode(), publisherDTO.getPhoneNumber(),
                            publisherDTO.getDescription());

        publisherDTO = publisherDAO.findPublisher("Wydawnictwo Zgierz");
        System.out.println(publisherDTO);

        CategoryDAO categoryDAO = new CategoryDAO();
        int result = categoryDAO.insert("Książka kucharska", "KITCHEN2");
        System.out.println(result);


    }
}

package book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDTO {

    private int id;
    private String title;
    private int pagesNumber;
    private String categoryName;
    private String author;
    private String publisherName;

}
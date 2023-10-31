package vn.sparkminds.services.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import vn.sparkminds.model.Author;
import vn.sparkminds.model.Category;
import vn.sparkminds.model.Publisher;
import vn.sparkminds.model.enums.BookStatus;

@Data
@AllArgsConstructor
public class BookResponse {
    private String title;
    private String description;
    private Category category;
    private double price;
    private BookStatus status;
    private Publisher publisher;
    private Author author;
    private String imageUrl;
    private double numRating;
}

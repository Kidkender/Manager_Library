package vn.sparkminds.services.dto.mapper;

import java.util.Objects;
import org.springframework.stereotype.Component;
import vn.sparkminds.model.Book;
import vn.sparkminds.model.User;
import vn.sparkminds.services.dto.response.BookResponse;
import vn.sparkminds.services.dto.response.BorrowBookResponse;

@Component
public class BookMapper {
    public BookResponse toBookResponseDTO(Book book) {
        if (Objects.isNull(book)) {
            return null;
        }
        return new BookResponse(book.getTitle(), book.getDescription(), book.getCategory(),
                book.getPrice(), book.getStatus(), book.getPublisher(), book.getAuthor(),
                book.getImageUrl(), book.getNumRatings());
    }



}

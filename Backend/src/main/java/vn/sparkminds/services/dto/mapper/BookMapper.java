package vn.sparkminds.services.dto.mapper;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.stereotype.Component;
import vn.sparkminds.model.Book;
import vn.sparkminds.services.dto.request.BookRequest;
import vn.sparkminds.services.dto.response.BookResponse;

@Component
public class BookMapper {
    public BookResponse toBookResponseDTO(Book book) {
        if (Objects.isNull(book)) {
            return null;
        }
        return new BookResponse(book.getTitle(), book.getDescription(), book.getCategory(),
                book.getPrice(), book.getStatus(), book.getPublisher(), book.getAuthor(), book.get,
                book.getImageUrl(), book.getNumRatings());
    }

}

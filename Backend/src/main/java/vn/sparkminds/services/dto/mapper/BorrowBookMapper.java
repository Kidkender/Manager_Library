package vn.sparkminds.services.dto.mapper;

import java.util.Objects;
import org.springframework.stereotype.Component;
import vn.sparkminds.model.BorrowedBook;
import vn.sparkminds.services.dto.response.BorrowBookResponse;

@Component
public class BorrowBookMapper {
    public BorrowBookResponse toBorrowBookResponse(BorrowedBook borrowedBook) {
        if (Objects.isNull(borrowedBook)) {
            return null;
        }
        return new BorrowBookResponse(borrowedBook.getBook().getTitle(),
                borrowedBook.getUser().getUserName(), borrowedBook.getBorrowDate());
    }
}

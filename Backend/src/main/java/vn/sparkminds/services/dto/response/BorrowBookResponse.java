package vn.sparkminds.services.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BorrowBookResponse {
    private String title;
    private String nameUser;
    private LocalDateTime timeBorrowed;
}

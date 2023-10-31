package vn.sparkminds.services.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorResponse {
    private String name;
    private String biography;
    private String national;
    private LocalDate born;
}

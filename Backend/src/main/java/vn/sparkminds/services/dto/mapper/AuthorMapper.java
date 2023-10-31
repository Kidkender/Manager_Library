package vn.sparkminds.services.dto.mapper;

import org.springframework.stereotype.Component;
import vn.sparkminds.model.Author;
import vn.sparkminds.services.dto.response.AuthorResponse;

@Component
public class AuthorMapper {
    public AuthorResponse toAuthorResponse(Author author) {
        if (author == null) {
            return null;
        }
        return new AuthorResponse(author.getName(), author.getBiography(), author.getNational(),
                author.getBirth());
    }
}

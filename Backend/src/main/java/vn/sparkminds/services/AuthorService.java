package vn.sparkminds.services;

import java.util.List;
import vn.sparkminds.exceptions.AuthorException;
import vn.sparkminds.model.Author;
import vn.sparkminds.services.dto.request.AuthorRequest;
import vn.sparkminds.services.dto.response.AuthorResponse;

public interface AuthorService {
    public Author createAuthor(AuthorRequest author);

    public AuthorResponse updateAuthor(AuthorRequest req);

    public void deleteAuthor(Long authorId) throws AuthorException;

    public Author findAuthorById(Long id) throws AuthorException;

    public List<AuthorResponse> findAllAuthor();

    public List<AuthorResponse> findAuthorsByKeyword(String keyword);
}

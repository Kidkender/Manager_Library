package vn.sparkminds.services;

import java.util.List;
import vn.sparkminds.exceptions.AuthorException;
import vn.sparkminds.model.Author;
import vn.sparkminds.services.dto.request.AuthorRequest;
import vn.sparkminds.services.dto.response.AuthorResponse;

public interface AuthorService {
    public Author createAuthor(Author author);

    public AuthorResponse updateAuthor(Long authorId, AuthorRequest req) throws AuthorException;

    public List<Author> createMultipleAuthor(Author[] reqList);

    public void deleteAuthor(Long authorId) throws AuthorException;

    public Author findAuthorById(Long id) throws AuthorException;

    public List<AuthorResponse> findAllAuthor();

    public List<AuthorResponse> findAuthorsByKeyword(String keyword);
}

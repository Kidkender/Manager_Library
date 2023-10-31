package vn.sparkminds.services;

import java.util.List;
import vn.sparkminds.exceptions.AuthorException;
import vn.sparkminds.model.Author;
import vn.sparkminds.services.dto.request.AuthorRequest;

public interface AuthorService {
    public Author createAuthor(Author author);

    public Author updateAuthor(AuthorRequest req);

    public String deleteAuthor(Long authorId) throws AuthorException;

    public Author findAuthorById(Long id) throws AuthorException;

    public List<Author> findAllAuthor();
}

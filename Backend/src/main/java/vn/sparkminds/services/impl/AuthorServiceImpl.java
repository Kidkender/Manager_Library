package vn.sparkminds.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.sparkminds.exceptions.AuthorException;
import vn.sparkminds.model.Author;
import vn.sparkminds.repositories.AuthorRepository;
import vn.sparkminds.services.AuthorService;
import vn.sparkminds.services.dto.mapper.AuthorMapper;
import vn.sparkminds.services.dto.request.AuthorRequest;
import vn.sparkminds.services.dto.response.AuthorResponse;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;



    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    @Transactional
    public Author createAuthor(Author req) {
        Author author = new Author();
        author.setName(req.getName());
        author.setEmail(req.getEmail());
        author.setNational(req.getNational());
        author.setBiography(req.getBiography());
        author.setBirth(req.getBirth());
        author.setCreatedAt(LocalDateTime.now());
        return authorRepository.save(author);

    }

    @Override
    @Transactional
    public void deleteAuthor(Long authorId) throws AuthorException {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorException("Author not found with id " + authorId));

        authorRepository.deleteById(author.getId());

    }

    @Override
    public List<AuthorResponse> findAllAuthor() {
        List<AuthorResponse> result = authorRepository.findAll().stream()
                .map(author -> authorMapper.toAuthorResponse(author)).toList();
        return result;
    }

    @Override
    public Author findAuthorById(Long id) throws AuthorException {
        Optional<Author> opt = authorRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new AuthorException("Author not found with id " + id);
    }

    @Override
    @Transactional
    public AuthorResponse updateAuthor(Long authorId, AuthorRequest req) throws AuthorException {
        Author author = findAuthorById(authorId);
        if (req.getName() != null) {

            author.setName(req.getName());
        }

        if (req.getBio() != null) {

            author.setBiography(req.getBio());
        }
        if (req.getNational() != null) {

            author.setNational(req.getNational());
        }

        author.setUpdatedAt(LocalDateTime.now());
        Author updatedAuthor = authorRepository.save(author);

        return authorMapper.toAuthorResponse(updatedAuthor);

    }

    @Override
    public List<AuthorResponse> findAuthorsByKeyword(String keyword) {
        List<AuthorResponse> list = authorRepository.findAuthorsByNameContainingKeyword(keyword)
                .stream().map(author -> authorMapper.toAuthorResponse(author)).toList();
        return list;
    }



}

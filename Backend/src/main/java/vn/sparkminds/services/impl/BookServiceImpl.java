package vn.sparkminds.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.sparkminds.exceptions.AuthorException;
import vn.sparkminds.exceptions.BookException;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.model.Author;
import vn.sparkminds.model.Book;
import vn.sparkminds.model.Category;
import vn.sparkminds.model.enums.BookStatus;
import vn.sparkminds.repositories.AuthorRepository;
import vn.sparkminds.repositories.BookRepository;
import vn.sparkminds.repositories.CategoryRepository;
import vn.sparkminds.services.BookService;
import vn.sparkminds.services.dto.mapper.BookMapper;
import vn.sparkminds.services.dto.request.BookRequest;
import vn.sparkminds.services.dto.response.BookResponse;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository,
            AuthorRepository authorRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional
    public Book createBook(Book req) {
        Book book = new Book();
        book.setTitle(req.getTitle());
        book.setPrice(req.getPrice());
        book.setAuthor(req.getAuthor());
        book.setDescription(req.getDescription());
        book.setCategory(req.getCategory());
        book.setStatus(BookStatus.Inventory);
        book.setPublisher(req.getPublisher());
        book.setImageUrl(req.getImageUrl());
        book.setCreateAt(LocalDateTime.now());
        book.setNumRatings(0);
        book.setDiscountPersent(req.getDiscountPersent());
        book.setDiscountedPrice(req.getDiscountedPrice());
        return bookRepository.save(book);
    }



    @Override
    public List<BookResponse> getAllBook() {
        List<BookResponse> bookResponse = bookRepository.findAll().stream()
                .map(book -> bookMapper.toBookResponseDTO(book)).toList();
        return bookResponse;
    }

    @Override
    public Book getBookById(Long id) throws BookException {
        Optional<Book> opt = bookRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new BookException("Book is not found with id " + id);
    }

    @Override
    @Transactional
    public BookResponse updateBook(Long bookId, BookRequest req) throws BookException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found: " + bookId));
        book.setTitle(req.getTitle());
        book.setDescription(req.getDescription());
        book.setImageUrl(req.getImageUrl());
        book.setQuantity(req.getQuantity());
        book.setPrice(req.getPrice());
        book.setUpdateAt(LocalDateTime.now());
        return bookMapper.toBookResponseDTO(book);

    }

    @Override
    public List<BookResponse> findBookByAuthor(Long authorId) throws AuthorException {
        Optional<Author> author = authorRepository.findById(authorId);
        if (author == null) {
            throw new AuthorException("Author not found with Id " + authorId);
        }
        List<BookResponse> books = author.get().getBooks().stream()
                .map(book -> bookMapper.toBookResponseDTO(book)).toList();
        return books;
    }

    @Override
    public List<BookResponse> findBookByCategory(Long categoryId)
            throws BookException, CategoryException {
        Optional<Category> opt = categoryRepository.findById(categoryId);
        if (opt.isPresent() == false) {
            throw new CategoryException("Category not found with id: " + categoryId);
        }
        List<BookResponse> bookList = bookRepository.findBookByCategory(categoryId).stream()
                .map(book -> bookMapper.toBookResponseDTO(book)).toList();

        return bookList;
    }



    @Override
    public void deleteBook(Long id) throws BookException {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

    @Override
    public List<BookResponse> findBookByCategoryAuthor(Long category, Long AuthorId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Book> importMutilBooks() {
        // TODO Auto-generated method stub
        return null;
    }

}

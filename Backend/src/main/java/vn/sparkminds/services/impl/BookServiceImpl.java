package vn.sparkminds.services.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.sparkminds.exceptions.AuthorException;
import vn.sparkminds.exceptions.BookException;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.exceptions.PublisherException;
import vn.sparkminds.model.Author;
import vn.sparkminds.model.Book;
import vn.sparkminds.model.Category;
import vn.sparkminds.model.Publisher;
import vn.sparkminds.model.enums.BookStatus;
import vn.sparkminds.repositories.AuthorRepository;
import vn.sparkminds.repositories.BookRepository;
import vn.sparkminds.repositories.CategoryRepository;
import vn.sparkminds.services.AuthorService;
import vn.sparkminds.services.BookService;
import vn.sparkminds.services.CategoryService;
import vn.sparkminds.services.PublisherService;
import vn.sparkminds.services.dto.mapper.BookMapper;
import vn.sparkminds.services.dto.request.AddBookRequest;
import vn.sparkminds.services.dto.request.BookRequest;
import vn.sparkminds.services.dto.response.BookResponse;
import vn.sparkminds.utils.CSVHelper;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final PublisherService publisherService;



    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository,
            AuthorRepository authorRepository, BookMapper bookMapper, AuthorService authorService,
            CategoryService categoryService, PublisherService publisherService) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.publisherService = publisherService;
    }



    @Override
    @Transactional
    public Book createBook(AddBookRequest req)
            throws AuthorException, CategoryException, PublisherException {
        Book book = new Book();

        Author author = authorService.findAuthorById(req.getAuthorId());
        Category category = categoryService.findCategoryById(req.getCategoryId());
        Publisher publisher = publisherService.findPublisherById(req.getPublisherId());

        book.setQuantity(req.getTotalBook());
        book.setTitle(req.getTitle());
        book.setPrice(req.getPrice());
        book.setAuthor(author);
        book.setDescription(req.getDescription());
        book.setCategory(category);
        book.setPublisher(publisher);
        book.setImageUrl(req.getImageUrl());
        book.setDiscountPersent(req.getDiscountPersent());
        book.setDiscountedPrice(req.getDiscountedPrice());
        book.setStatus(BookStatus.Inventory);
        book.setNumRatings(0);
        book.setCreateAt(LocalDateTime.now());

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
        if (req.getTitle() != null) {

            book.setTitle(req.getTitle());
        }
        if (req.getDescription() != null) {

            book.setDescription(req.getDescription());
        }
        if (req.getImageUrl() != null) {

            book.setImageUrl(req.getImageUrl());
        }
        if (req.getQuantity() != 0) {

            book.setQuantity(req.getQuantity());
        }
        if (req.getPrice() != 0) {

            book.setPrice(req.getPrice());
        }
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
        return null;
    }

    @Override
    public List<BookResponse> searchBooks(String query) throws BookException {
        List<Book> books = bookRepository.findBookByTitle(query);
        if (books.size() == 0) {
            throw new BookException("No books found");
        }
        return books.stream().map(book -> bookMapper.toBookResponseDTO(book)).toList();
    }

    @Override
    public void importMutilBookFromCsv(MultipartFile file)
            throws AuthorException, CategoryException, PublisherException {
        try {
            List<AddBookRequest> books = CSVHelper.csvBooks(file.getInputStream());
            for (AddBookRequest request : books) {
                createBook(request);
            }
            // return bookRepository.saveAll(books);
        } catch (IOException e) {
            throw new RuntimeException("Fail to store csv data :" + e.getMessage());
        }
    }



}

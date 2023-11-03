package vn.sparkminds.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.sparkminds.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Modifying
    @Query("SELECT b FROM Book b WHERE b.category.id = :id ORDER BY b.createAt Desc")
    public List<Book> findBookByCategory(@Param("id") Long id);

    @Modifying
    @Query("Select DISTINCT b FROM Book b WHERE b.title LIKE %:query% ORDER BY b.createAt Desc")
    public List<Book> findBookByTitle(@Param("query") String query);
}

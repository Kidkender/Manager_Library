package vn.sparkminds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.sparkminds.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}

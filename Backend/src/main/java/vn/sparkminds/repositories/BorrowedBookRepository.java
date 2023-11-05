package vn.sparkminds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.sparkminds.model.Book;
import vn.sparkminds.model.BorrowedBook;
import vn.sparkminds.model.User;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    @Query("SELECT bb FROM BorrowedBook bb WHERE bb.user = :user AND bb.book = :book AND bb.returnDate IS NULL")
    BorrowedBook findNotReturnedByUserAndBook(@Param("user") User user, @Param("book") Book book);
}

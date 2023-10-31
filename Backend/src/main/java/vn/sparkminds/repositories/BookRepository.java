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
    @Query("Select b From Book Where b.category.id IN =:id Order by b.createdAt Desc")
    public List<Book> findBookByCategory(@Param("id") Long id);
}

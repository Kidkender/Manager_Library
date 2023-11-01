package vn.sparkminds.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.sparkminds.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("Select a From Author a Where Lower(a.name) Like %:keyword%")
    List<Author> findAuthorsByNameContainingKeyword(@Param("keyword") String keyword);
}

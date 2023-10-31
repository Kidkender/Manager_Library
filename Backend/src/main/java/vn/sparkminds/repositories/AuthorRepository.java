package vn.sparkminds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.sparkminds.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}

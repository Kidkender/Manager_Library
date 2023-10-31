package vn.sparkminds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.sparkminds.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}

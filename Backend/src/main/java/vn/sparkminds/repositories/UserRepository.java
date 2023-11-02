package vn.sparkminds.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.sparkminds.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    public Optional<User> findByUserName(String userName);

    @Modifying
    @Query("Select DISTINCT u From User u WHERE u.userName LIKE %:query% Or u.email LIKE %:query%")
    public List<User> findByQuery(@Param("query") String query);
}

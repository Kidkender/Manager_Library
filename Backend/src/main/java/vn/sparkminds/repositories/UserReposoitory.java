package vn.sparkminds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.sparkminds.model.User;

@Repository
public interface UserReposoitory extends JpaRepository<User, Long> {

}

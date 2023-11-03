package vn.sparkminds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.sparkminds.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}

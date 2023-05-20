package fpt.com.vn.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fpt.com.vn.demo.model.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
    @Query(value = "select * from customer where id_user = :id", nativeQuery = true)
    Optional<Customer> findById(@Param("id") Integer id);
    
}

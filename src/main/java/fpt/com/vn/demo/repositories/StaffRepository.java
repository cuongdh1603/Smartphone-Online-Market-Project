package fpt.com.vn.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fpt.com.vn.demo.model.Staff;
@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer>{
    @Query(value = "select * from staff where id_user = :id", nativeQuery = true)
    Optional<Staff> findById(@Param("id") Integer id);
}

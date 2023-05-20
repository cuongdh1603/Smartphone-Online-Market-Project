package fpt.com.vn.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fpt.com.vn.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByUsernameAndPassword(String username, String password);
    
}

package fpt.com.vn.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fpt.com.vn.demo.model.Color;

public interface ColorRepository extends JpaRepository<Color, Integer>{
    Optional<Color> findById(Integer id);
}

package fpt.com.vn.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fpt.com.vn.demo.model.ColorItem;
public interface ColorItemRepository extends JpaRepository<ColorItem, Integer>{
    
}

package fpt.com.vn.demo.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fpt.com.vn.demo.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{
    @Query(value = "select * from item where"
			+ " id_item = (select max(id_item) from item)",nativeQuery = true)
    Item getLastestItem();

    Optional<Item> findById(Integer id);
}

package fpt.com.vn.demo.repositories;

// import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import fpt.com.vn.demo.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer>{
    @Query(value = "select * from brand "
            + "where id_brand = (select max(id_brand) from brand)",nativeQuery = true)
	Brand getLastestBrand();

    Brand findByName(String name);
    Optional<Brand> findById(Integer id);
    @Query(value = "select * from brand "
    + "where id_brand != :id and namebrand = :name",nativeQuery = true)
    Brand findByNameAndNotId(@Param("id") Integer id, @Param("name") String name);
}

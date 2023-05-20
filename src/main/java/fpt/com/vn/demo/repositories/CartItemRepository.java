package fpt.com.vn.demo.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fpt.com.vn.demo.model.CartItem;
public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
    @Query(value = "select count(*) from cartitem", nativeQuery = true)
    Integer findAmountOfCartItem();

    @Query(value = "select * from cartitem where id_user = :id", nativeQuery = true)
    List<CartItem> findByCustomerId(@Param("id") Integer id);

    Optional<CartItem> findById(Integer id);

    @Query(value = "select * from cartitem where id_user = :id_user and id_item = :id_item and status = 0", nativeQuery = true)
    Optional<CartItem> findByCustomerIdAndItemId(@Param("id_user") Integer idUser, @Param("id_item") Integer idItem);

    @Query(value = "select * from cartitem where id_user = :id and status = 0", nativeQuery = true)
    List<CartItem> findInconfimredCartItemByCustomerId(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cartitem WHERE id_cartitem = :id",nativeQuery = true)
    void deleteCartItem(@Param("id") Integer id);

    @Query(value = "select * from cartitem where status = 1", nativeQuery = true)
    List<CartItem> findAllUnpaidCartItem();

}

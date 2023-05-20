package fpt.com.vn.demo.service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpt.com.vn.demo.model.CartItem;
import fpt.com.vn.demo.model.Customer;
import fpt.com.vn.demo.model.Item;
import fpt.com.vn.demo.repositories.CartItemRepository;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    public boolean checkCartItemExist(CartItem cartItem){
        return true;
    }

    public void addCartItem(CartItem cartItem){
        cartItem.setCode(generateRandomCode(4));
        cartItem.setStatus(0);

        log.info("Ma gio hang: " + cartItem.getCode());
        log.info("Trang thai: " + cartItem.getStatus());
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItemByCustomerId(Customer customer){
        return cartItemRepository.findByCustomerId(customer.getId());
    }

    public CartItem getCartItemById(Integer id){
        Optional<CartItem> op = cartItemRepository.findById(id);
        if(op.isPresent()) return op.get();
        else return null;  
    }

    public CartItem getInconfirmedCartItem(Customer customer, Item item){
        Optional<CartItem> op = cartItemRepository.findByCustomerIdAndItemId(customer.getId(), item.getId());
        if(op.isPresent()) return op.get();
        else return null;
    }
    public List<CartItem> getInconfirmedCartItemByCustomer(Customer customer){
        return cartItemRepository.findInconfimredCartItemByCustomerId(customer.getId());
    }
    public void confirmCartItem(CartItem cartItem){
        if(cartItem.getCode()==null)
            cartItem.setCode(generateRandomCode(4));
        cartItem.setCreateTime(new Timestamp(System.currentTimeMillis()));
        cartItem.setStatus(1);
        log.info("Thong tin don hang : "+cartItem.getCode() + " " + cartItem.getQuantity() + " " + cartItem.getAddress() + " " + cartItem.getCreateTime() + " " + cartItem.getStatus());
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> getAllUnpaidCartItem(){
        return cartItemRepository.findAllUnpaidCartItem();
    }

    public void confirmBill(CartItem cartItem){
        cartItem.setPayTime(new Timestamp(System.currentTimeMillis()));
        cartItem.setStatus(2);
        cartItemRepository.save(cartItem);
        // cartItemRepository.confirmCartItem(id);
    }

    public void deleteCartItem(Integer id){
        cartItemRepository.deleteCartItem(id);
    }
    public static String generateRandomCode(int len)
    {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String numbers = "0123456789";
 
        SecureRandom random = new SecureRandom();
        StringBuilder char_sb = new StringBuilder();
        StringBuilder num_sb = new StringBuilder();
 
        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance
 
        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            char_sb.append(chars.charAt(randomIndex));
        }
        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(numbers.length());
            num_sb.append(numbers.charAt(randomIndex));
        }
        char_sb.append('-');
        char_sb.append(num_sb);
        return char_sb.toString();
    }
}

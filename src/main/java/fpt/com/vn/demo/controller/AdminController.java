package fpt.com.vn.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fpt.com.vn.demo.model.CartItem;
import fpt.com.vn.demo.model.Staff;
import fpt.com.vn.demo.service.CartItemService;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private CartItemService cartItemService;
    @GetMapping
    public String adminHome(Model model, HttpSession session){  
        Staff staff = (Staff)session.getAttribute("staff");
        model.addAttribute("username", staff.getUsername());
        return "admin/index";
    }
    @GetMapping("/unpaid")
    public String unpaidList(Model model, HttpSession session){
        Staff staff = (Staff)session.getAttribute("staff");
        model.addAttribute("username", staff.getUsername());
        
        List<CartItem> cartItems = cartItemService.getAllUnpaidCartItem();
        for (CartItem cartItem : cartItems) {
            if(cartItem.getCreateTime()==null)
            System.out.println(cartItem.getCreateTime());
            else 
            System.out.println(cartItem.getCreateTime());
        }
        model.addAttribute("cartItems", cartItems);
        return "admin/bills";
    }
    @GetMapping("/confirm/{id}")
    public String confirmBill(@PathVariable("id") Integer id,
        RedirectAttributes ra){
        CartItem cartItem = cartItemService.getCartItemById(id);
        cartItemService.confirmBill(cartItem);
        // cartItemService.confirmBill(id);
        ra.addFlashAttribute("successConfirm", "Hoàn tất xác nhận thanh toán cho đơn hàng");
        return "redirect:/admin/unpaid";
    }

}

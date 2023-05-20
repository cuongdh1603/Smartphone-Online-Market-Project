package fpt.com.vn.demo.controller;


import java.text.NumberFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fpt.com.vn.demo.model.Brand;
import fpt.com.vn.demo.model.CartItem;
import fpt.com.vn.demo.model.Customer;
import fpt.com.vn.demo.model.Item;
import fpt.com.vn.demo.service.BrandService;
import fpt.com.vn.demo.service.CartItemService;
import fpt.com.vn.demo.service.ItemService;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/home")
public class ClientHomeController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public String clientHome(Model model, HttpSession session){
        Customer customer = (Customer)session.getAttribute("customer");
        model.addAttribute("username", customer.getUsername());

        // log.info("Thông tin khách hàng: "+ customer.getId()+ " " +  customer.toString());
        List<Item> items = itemService.getAllItem();
        List<Brand> brands = brandService.getAllBrands();
        model.addAttribute("items", items);
        model.addAttribute("brands", brands);
        return "client/index";
    }

    @GetMapping("/item/{id}")
    public String detailProduct(Model model, HttpSession session, @PathVariable("id") Integer id){
        Customer customer = (Customer)session.getAttribute("customer");
        
        model.addAttribute("username", customer.getUsername());
        Item item = itemService.getItemById(id); 
        session.setAttribute("item", item);
        model.addAttribute("item", item);
        return "client/item";
    }

    @GetMapping("/addcart/{id}")
    public String addItemIntoCart(Model model, HttpSession session, @PathVariable("id") Integer id){
        Customer customer = (Customer)session.getAttribute("customer");
        Item item = (Item)session.getAttribute("item");
        CartItem savedCartItem = cartItemService.getInconfirmedCartItem(customer, item);
        if(savedCartItem != null){
            model.addAttribute("addCartError", "Sản phẩm này đã được thêm vào giỏ hàng");
        } 
        else{
            CartItem cartItem = new CartItem();
            cartItem.setCustomer(customer);
            cartItem.setItem(item);  
            cartItemService.addCartItem(cartItem);
        }
        /*
         * XỬ LÝ TRƯỜNG HỢP XẢY RA LỖI KHI THÊM VÀO GIỎ HÀNG
         */
        model.addAttribute("item", item);
        return "client/item";
    }
    
    @GetMapping("/cart")
    public String showCartItem(Model model, HttpSession session){
        Customer customer = (Customer)session.getAttribute("customer");
        model.addAttribute("username", customer.getUsername());

        List<CartItem> cartItems = cartItemService.getInconfirmedCartItemByCustomer(customer);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("numItems", cartItems.size());
        return "client/cart";
    }
    @GetMapping("/bill/{id}")
    public String showBill(Model model, HttpSession session, @PathVariable("id") Integer id){
        Customer customer = (Customer)session.getAttribute("customer");
        model.addAttribute("username", customer.getUsername());

        CartItem cartItem = cartItemService.getCartItemById(id);
        model.addAttribute("cartItem", cartItem);

        log.info("Thong tin don hang: "+cartItem.getItem().getName()+" "+cartItem.getCustomer().getName());
        return "client/update_bill";
    }
    @GetMapping("/receipt/{id}")
    public String showReceipt(Model model, HttpSession session, @PathVariable("id") Integer id){
        Customer customer = (Customer)session.getAttribute("customer");
        model.addAttribute("username", customer.getUsername());
        Item item = itemService.getItemById(id);

        CartItem newCartItem = new CartItem();
        newCartItem.setCustomer(customer);
        newCartItem.setItem(item);
        model.addAttribute("cartItem", newCartItem);
        return "client/update_bill";
    }
    @PostMapping("/confirm")
    public String confirmBill(Model model, 
        @ModelAttribute("cartItem") CartItem cartItem,
        @RequestParam("id_item") Integer idItem,
        HttpSession session){
        Customer customer = (Customer)session.getAttribute("customer");
        model.addAttribute("username", customer.getUsername());
        Item item = itemService.getItemById(idItem);

        CartItem incomfirmedCartItem = cartItemService.getInconfirmedCartItem(customer, item);
        if(incomfirmedCartItem==null){
            CartItem newCartItem = new CartItem();
            newCartItem.setCustomer(customer);
            newCartItem.setItem(item);
            newCartItem.setQuantity(cartItem.getQuantity());
            newCartItem.setAddress(cartItem.getAddress());
            model.addAttribute("cartItem", newCartItem);

            Double itemPrice = newCartItem.getItem().getPrice()*newCartItem.getQuantity();
            model.addAttribute("itemPrice", getFormatPrice(itemPrice));
            model.addAttribute("shipFee", getFormatPrice(itemPrice*0.01));
            model.addAttribute("totalPrice", getFormatPrice(itemPrice*1.01));
            cartItemService.confirmCartItem(newCartItem);
        }
        else{
            incomfirmedCartItem.setQuantity(cartItem.getQuantity());
            incomfirmedCartItem.setAddress(cartItem.getAddress());
            model.addAttribute("cartItem", incomfirmedCartItem);

            Double itemPrice = incomfirmedCartItem.getItem().getPrice()*incomfirmedCartItem.getQuantity();
            model.addAttribute("itemPrice", getFormatPrice(itemPrice));
            model.addAttribute("shipFee", getFormatPrice(itemPrice*0.01));
            model.addAttribute("totalPrice", getFormatPrice(itemPrice*1.01));
            cartItemService.confirmCartItem(incomfirmedCartItem);
        }    

        return "client/success";
    }
    @GetMapping("/cart/delete/{id}")
    public String deleteCartItem(Model model, 
        RedirectAttributes ra,
        @PathVariable("id") Integer id){
        // Customer customer = (Customer)session.getAttribute("customer");
        // model.addAttribute("username", customer.getUsername());
        cartItemService.deleteCartItem(id);
        ra.addFlashAttribute("deleteSuccess", "Đã xóa sản phẩm khỏi giỏ hàng");
        return "redirect:/home/cart";
        
    }
    public static String getFormatPrice(Double d){
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		return myFormat.format(d);
	}
}

package fpt.com.vn.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fpt.com.vn.demo.model.Customer;
import fpt.com.vn.demo.model.Staff;
import fpt.com.vn.demo.service.CustomerService;
import fpt.com.vn.demo.service.StaffService;
import fpt.com.vn.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class LoginController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;
    @Autowired
    private StaffService staffService;
    @GetMapping({"/login","/"})
    public String login(Model model){
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "login";
    } 

    @PostMapping("/post_login")
    public String postLogin(Model model, 
        @ModelAttribute("customer") Customer customer,
        HttpSession session){
        Customer loggedCustomer = customerService.getCustomerById(customer);
        log.info("Username: "+customer.getUsername() +" Password: "+customer.getPassword());
        Staff loggedStaff = staffService.getCustomerById(customer);
        if(loggedCustomer!=null){
            session.setAttribute("customer", loggedCustomer);
            // log.info(loggedCustomer.toString());
            return "redirect:/home";
        }
        if(loggedStaff!=null){
            session.setAttribute("staff", loggedStaff);
            // log.info(loggedCustomer.toString());
            return "redirect:/admin";
        }   
        model.addAttribute("failedLogin", "Đăng nhập thất bại");
        log.info("Null Object");
        return "login";
    }
    @GetMapping("/signup")
    public String signup(Model model){
        Customer customer = new Customer();
        model.addAttribute("newCustomer", customer);
        return "signup";
    }

    @PostMapping("/post_signup")
    public String register(Model model, 
        @Valid @ModelAttribute("newCustomer") Customer customer,
        Errors errors){
        if(errors.hasErrors()){
            model.addAttribute("newCustomer", customer);
            return "signup";
        }
        if(userService.checkUsernameExist(customer.getUsername())){
            // log.info("Ton tai ");
            model.addAttribute("usernameError", "Tên đăng nhập này đã được sử dụng. Yêu cầu chọn tên đăng nhập khác.");
            model.addAttribute("newCustomer", customer);
            return "signup";
        }
        customerService.saveCustomer(customer);

        // model.addAttribute("newCustomer", customer);
        model.addAttribute("successSignup", "Đăng kí thành công");
        log.info(customer.toString());
        return "signup";
    }
    @GetMapping("/logout")
	public String logout(Model model, HttpSession session) {
		model.addAttribute("customer", new Customer());
		// log.info(session.getAttribute("username"));
		session.removeAttribute("customer");
        session.removeAttribute("staff");
        Customer customer = (Customer)session.getAttribute("customer");
        if(customer == null)
            log.info("Khong co phien giao dich ");
        else
            log.info("Thong tin khach hang: "+customer.getId());
		return "login";
	}

}

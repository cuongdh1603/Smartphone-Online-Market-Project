package fpt.com.vn.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String showIndex(){
        return "combo_box";
    }
    @GetMapping("/addproduct")
    public String addProduct(){
        return "admin/add_product";
    }
    @GetMapping("/clientHome")
    public String clientHome(){
        return "client/index";
    }
}

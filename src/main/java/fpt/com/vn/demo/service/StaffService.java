package fpt.com.vn.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpt.com.vn.demo.model.Customer;
import fpt.com.vn.demo.model.Staff;
import fpt.com.vn.demo.model.User;
import fpt.com.vn.demo.repositories.StaffRepository;

@Service
public class StaffService {
    @Autowired
    private UserService userService;
    @Autowired
    private StaffRepository staffRepository;
    public Staff getCustomerById(Customer customer){
        
        User user = userService.getUserByUsernameAndPassword(customer);
        if(user==null) return null;
        else{
            System.out.println("SO id: "+user.getId());
            Optional<Staff> op = staffRepository.findById(user.getId());
           
            if(op.isPresent()){
                System.out.println("Khong null - Username: "+user.getUsername()+" password: "+user.getPassword());
                return op.get();
            } 
            else return null;
        }
    }
}

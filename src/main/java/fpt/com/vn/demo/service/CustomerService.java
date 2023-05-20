package fpt.com.vn.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpt.com.vn.demo.model.Customer;
import fpt.com.vn.demo.model.User;
import fpt.com.vn.demo.repositories.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserService userService;
    
    public void saveCustomer(Customer customer){
        customerRepository.save(customer);
    }

    public Customer getCustomerById(Customer customer){
        User user = userService.getUserByUsernameAndPassword(customer);
        if(user==null) return null;
        else{
            Optional<Customer> op = customerRepository.findById(user.getId());
            if(op.isPresent()) return op.get();
            else return null;
        }
    }

    
}

package fpt.com.vn.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpt.com.vn.demo.model.Customer;
import fpt.com.vn.demo.model.User;
import fpt.com.vn.demo.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByUsernameAndPassword(Customer customer){
        Optional<User> op = userRepository.findByUsernameAndPassword(customer.getUsername(), customer.getPassword());
        if(op.isPresent()) return op.get();
        else return null;
        // return user;
    }
    public boolean checkUsernameExist(String username){
        List<String> usernameList = getAllUser().stream().map(User::getUsername).collect(Collectors.toList());
        return usernameList.contains(username);
    }


    public List<User> getAllUser(){
        return userRepository.findAll();
    }
}

package org.example.wishlistapp.service;

import org.example.wishlistapp.model.User;
import org.example.wishlistapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void addNewUserToDatabase(User newUserToAdd){
        userRepository.addToDatabase(newUserToAdd);
    }

    public void deleteFromDatabase(User userToDelete){
        userRepository.deleteFromDatabase(userToDelete);
    }

    public User findById(int id){
        return userRepository.findById(id);
    }

    public Boolean editUser(User newUser){
        return userRepository.edit(newUser);
    }

    public User findByEmailAndPassword(String email, String password){
        return userRepository.findByEmailAndPassword(email, password);
    }

    public Boolean doesEmailExist(String email){
        return userRepository.doesEmailExist(email);
    }
}

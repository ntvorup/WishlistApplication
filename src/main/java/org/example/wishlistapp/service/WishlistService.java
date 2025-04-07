package org.example.wishlistapp.service;

import org.example.wishlistapp.repository.UserRepository;

public class WishlistService {
    private UserRepository userRepository;

    public WishlistService(){
        userRepository = new UserRepository();
    }
}

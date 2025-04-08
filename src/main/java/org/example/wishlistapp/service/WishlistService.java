package org.example.wishlistapp.service;

import org.example.wishlistapp.model.Wish;
import org.example.wishlistapp.model.Wishlist;
import org.example.wishlistapp.repository.UserRepository;
import org.example.wishlistapp.repository.WishlistRepository;

import java.util.Map;

public class WishlistService {
    private WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository){
        this.wishlistRepository = wishlistRepository;
    }

    public void addWishlistToDatabase(Wishlist newWishlistToAdd){
        wishlistRepository.addToDatabase(newWishlistToAdd);
    }

    public void deleteWishlistFromDatabase(Wishlist wishlistToDelete){
        wishlistRepository.deleteFromDatabase(wishlistToDelete);
    }

    public Wishlist findById(int wishlistId){
        return wishlistRepository.findById(wishlistId);
    }

    public Boolean editWishlist(Wishlist newWishlist){
        return wishlistRepository.edit(newWishlist);
    }

    public Map<Integer, Wishlist> getAllWishlists(){
        return wishlistRepository.getAllWishlists();
    }

    //Wish methods

    public void addWishToWishlist(Wish newWishToAdd, int wishlistId){
        wishlistRepository.addWishToWishlist(newWishToAdd, wishlistId);
    }

    public void deleteWishFromWishlist(Wish wishToDelte){
        wishlistRepository.deleteWishFromWishlist(wishToDelte);
    }

    public boolean updateWish(Wish newWish){
        return wishlistRepository.updateWish(newWish);
    }

    public Map<Integer, Wish> getWishesByWishlistId(int wishlistId){
        return wishlistRepository.getWishesByWishlistId(wishlistId);
    }

}

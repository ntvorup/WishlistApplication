package org.example.wishlistapp.model;

import java.util.ArrayList;
import java.util.List;

public class Wishlist {

    private int wishlistId;
    private int userId;
    private String wishlistTitle;
    private String wishlistDescription;
    private List<Wish> listOfWishes;

    public Wishlist(int wishlistId, int userId, String wishlistTitle, String wishlistDescription) {
        this.wishlistId = wishlistId;
        this.userId = userId;
        this.wishlistTitle = wishlistTitle;
        this.wishlistDescription = wishlistDescription;
        this.listOfWishes = new ArrayList<>();
    }

    public Wishlist() {
    }


    // Getters og setters

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getWishlistTitle() {
        return wishlistTitle;
    }

    public void setWishlistTitle(String wishlistTitle) {
        this.wishlistTitle = wishlistTitle;
    }

    public String getWishlistDescription() {
        return wishlistDescription;
    }

    public void setWishlistDescription(String wishlistDescription) {
        this.wishlistDescription = wishlistDescription;
    }

    public List<Wish> getListOfWishes() {
        return listOfWishes;
    }

    public void setListOfWishes(List<Wish> listOfWishes) {
        this.listOfWishes = listOfWishes;
    }
}
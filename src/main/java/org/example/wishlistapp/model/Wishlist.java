package org.example.wishlistapp.model;

import java.util.ArrayList;
import java.util.List;

public class Wishlist {

    private int wishlistId;
    private int userId;
    private String title;
    private String description;
    private List<Wish> wishes;

    public Wishlist(int wishlistId, int userId, String title, String description) {
        this.wishlistId = wishlistId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.wishes = new ArrayList<>();
    }

    public Wishlist(){
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }


    //Metoder til at administrere ønsker

    public void addWish (Wish wish){
        wishes.add(wish);
    }

    //Fjerner et specifikt Wish-objekt fra listen
    public void removeWish(Wish wish) {
        wishes.remove(wish);
    }

    //Fjerne et ønske udfra dets ID - Kan vi bruge når vi kun kender til ID'et
    public void removeWish(int wishId) {
        wishes.removeIf(wish -> wish.getWishId() == wishId);
    }
}

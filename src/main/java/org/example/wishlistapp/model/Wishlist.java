package org.example.wishlistapp.model;

import org.example.wishlistapp.exceptions.wishlist.WishAlreadyExistsException;
import org.example.wishlistapp.exceptions.wishlist.WishNotFoundException;

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


    //Metoder til at administrere ønsker

    public Wish getWishById(int wishId) {
        for (Wish wish : listOfWishes) {
            if (wish.getWishId() == wishId) {
                return wish;
            }
        }
        throw new WishNotFoundException(String.valueOf(wishlistId), String.valueOf(wishId));
    }

    public void addWish(Wish wish) {
        for (Wish existingWish : listOfWishes) {
            if (existingWish.getWishId() == wish.getWishId()) {
                throw new WishAlreadyExistsException("Wish with ID " + wish.getWishId() + " already exists in wishlist");
            }
        }
        listOfWishes.add(wish);
    }

    // Fjerner et specifikt Wish-objekt fra listen
    public void removeWish(Wish wish) {
        if (wish == null) {
            throw new NullPointerException("Wish cannot be null");
        }

        boolean removed = listOfWishes.remove(wish);
        if (!removed) {
            throw new WishNotFoundException(String.valueOf(wishlistId), String.valueOf(wish.getWishId()));
        }
    }

    // Fjerne et ønske udfra dets ID - Kan vi bruge når vi kun kender til ID'et
    public void removeWish(int wishId) {
        int initialSize = listOfWishes.size();
        listOfWishes.removeIf(wish -> wish.getWishId() == wishId);

        if (listOfWishes.size() == initialSize) {
            throw new WishNotFoundException(String.valueOf(wishlistId), String.valueOf(wishId));
        }
    }
}
package org.example.wishlistapp.exceptions.wishlist;

public class WishListNotFoundException extends WishlistException {

    private final String wishlistId;

    public WishListNotFoundException(String wishlistId) {
        super("Wishlist with ID " + wishlistId + " not found");
        this.wishlistId = wishlistId;
    }

    public String getWishlistId() {
        return wishlistId;
    }
}

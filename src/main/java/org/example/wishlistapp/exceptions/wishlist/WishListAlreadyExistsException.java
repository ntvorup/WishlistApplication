package org.example.wishlistapp.exceptions.wishlist;

public class WishListAlreadyExistsException extends WishlistException {

    private final String identifier;

    public WishListAlreadyExistsException(String identifier) {
        super("Wishlist with identifier " + identifier + " already exists");
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}

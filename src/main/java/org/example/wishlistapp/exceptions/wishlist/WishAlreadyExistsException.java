package org.example.wishlistapp.exceptions.wishlist;

public class WishAlreadyExistsException extends WishlistException {

    private final String identifier;

    public WishAlreadyExistsException(String identifier) {
        super("Wishlist with identifier " + identifier + " already exists");
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}

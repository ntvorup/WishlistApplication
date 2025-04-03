package org.example.wishlistapp.exceptions.wishlist;

public class WishNotFoundException extends WishlistException {

    private final String wishlistId;
    private final String itemId;

    public WishNotFoundException(String wishlistId, String itemId) {
        super("Item with ID " + itemId + " not found in wishlist " + wishlistId);
        this.wishlistId = wishlistId;
        this.itemId = itemId;
    }

    public String getWishlistId() {
        return wishlistId;
    }

    public String getItemId() {
        return itemId;
    }
}

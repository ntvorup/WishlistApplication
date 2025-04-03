package org.example.wishlistapp.exceptions.wishlist;

import org.example.wishlistapp.exceptions.base.BaseException;

public abstract class WishlistException extends BaseException {

    public WishlistException(String message) {
        super(message);
    }
}

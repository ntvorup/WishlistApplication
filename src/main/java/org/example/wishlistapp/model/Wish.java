package org.example.wishlistapp.model;

import java.math.BigDecimal;

public class Wish {
    private String wishTitle;
    private String wishDescription;
    private int wishId;
    private BigDecimal wishPrice;
    private int wishlistId;
    private String wishURL;
    private String wishImageUrl;

    public Wish() {
    }

    public Wish(String wishTitle, String wishDescription, int wishId, BigDecimal wishPrice, int wishlistId, String wishImageUrl, String wishURL) {
        this.wishTitle = wishTitle;
        this.wishDescription = wishDescription;
        this.wishId = wishId;
        this.wishPrice = wishPrice;
        this.wishlistId = wishlistId;
        this.wishURL = wishURL;
        this.wishImageUrl = wishImageUrl;
    }

    public String getWishTitle() {
        return wishTitle;
    }

    public void setWishTitle(String wishTitle) {
        this.wishTitle = wishTitle;
    }

    public String getWishDescription() {
        return wishDescription;
    }

    public void setWishDescription(String wishDescription) {
        this.wishDescription = wishDescription;
    }

    public int getWishId() {
        return wishId;
    }

    public void setWishId(int wishId) {
        this.wishId = wishId;
    }

    public BigDecimal getWishPrice() {
        return wishPrice;
    }

    public void setWishPrice(BigDecimal wishPrice) {
        this.wishPrice = wishPrice;
    }

    @Override
    public String toString() {
        return wishTitle + wishDescription + wishId + wishPrice;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getWishURL() {
        return wishURL;
    }

    public void setWishURL(String wishURL) {
        this.wishURL = wishURL;
    }

    public String getWishImageUrl() {
        return wishImageUrl;
    }
}
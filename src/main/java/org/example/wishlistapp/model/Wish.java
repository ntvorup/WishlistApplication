package org.example.wishlistapp.model;

import java.math.BigDecimal;

public class Wish {
    private String wishTitle;
    private String wishDescription;
    private int wishId;
    private BigDecimal wishPrice;
    private String productUrl;
    private String imageUrl;
    private int wishlistId;

    public Wish() {
    }

    public Wish(String wishTitle, String wishDescription, int wishId, BigDecimal wishPrice, String productUrl, String imageUrl, int wishlistId) {
        this.wishTitle = wishTitle;
        this.wishDescription = wishDescription;
        this.wishId = wishId;
        this.wishPrice = wishPrice;
        this.productUrl = productUrl;
        this.imageUrl = imageUrl;
        this.wishlistId = wishlistId;
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

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return wishTitle + wishDescription + wishId + wishPrice +
                (productUrl != null ? " " + productUrl : "") +
                (imageUrl != null ? " " + imageUrl : "");
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }
}
package org.example.wishlistapp.model;

import java.math.BigDecimal;

public class Wish {
    private String wishTitle;
    private String wishDescription;
    private int wishId;
    private BigDecimal wishPrice;

    public Wish() {
    }

    public Wish(String wishTitle, String wishDescription, int wishId, BigDecimal wishPrice) {
        this.wishTitle = wishTitle;
        this.wishDescription = wishDescription;
        this.wishId = wishId;
        this.wishPrice = wishPrice;
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
}
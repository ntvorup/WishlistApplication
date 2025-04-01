package org.example.wishlistapp.Model;

import java.math.BigDecimal;

public class Wish {
    private String wishName;
    private String wishDescription;
    private int wishId;
    private BigDecimal wishPrice;

    public Wish() {
    }

    public Wish(String wishName, String wishDescription, int wishId, BigDecimal wishPrice) {
        this.wishName = wishName;
        this.wishDescription = wishDescription;
        this.wishId = wishId;
        this.wishPrice = wishPrice;
    }

    public String getWishName() {
        return wishName;
    }

    public void setWishName(String wishName) {
        this.wishName = wishName;
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
        return wishName + wishDescription + wishId + wishPrice;
    }
}
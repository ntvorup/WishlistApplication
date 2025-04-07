package org.example.wishlistapp.model;

import org.example.wishlistapp.exceptions.wishlist.WishAlreadyExistsException;
import org.example.wishlistapp.exceptions.wishlist.WishNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class WishlistTest {

    private Wishlist wishlist;
    private Wish wish1;
    private Wish wish2;

    @BeforeEach
    void setUp() {

        wishlist = new Wishlist(1, 101, "Fødselsdagsønsker", "Min ønskeliste til fødselsdag");

        wish1 = new Wish(
                "Bog",
                "En god bog om programmering",
                1,
                new BigDecimal("299.95"),
                "https://example.com/bog",
                "https://example.com/images/bog.jpg"
        );

        wish2 = new Wish(
                "Headset",
                "Trådløst headset",
                2,
                new BigDecimal("899.00"),
                "https://example.com/headset",
                "https://example.com/images/headset.jpg"
        );
    }

    @Test
    void testGetWishById() {
        wishlist.addWish(wish1);
        wishlist.addWish(wish2);

        Wish foundWish = wishlist.getWishById(1);

        assertEquals(wish1, foundWish);
    }

    @Test
    void testGetWishByIdNotFound() {
        wishlist.addWish(wish1);

        assertThrows(WishNotFoundException.class, () -> {
            wishlist.getWishById(999);
        });
    }

    @Test
    void testAddWish() {
        assertEquals(0, wishlist.getListOfWishes().size());

        wishlist.addWish(wish1);

        assertEquals(1, wishlist.getListOfWishes().size());
        assertTrue(wishlist.getListOfWishes().contains(wish1));
    }

    @Test
    void testAddDuplicateWish() {
        wishlist.addWish(wish1);

        assertThrows(WishAlreadyExistsException.class, () -> {
            wishlist.addWish(wish1);
        });
    }

    @Test
    void testRemoveWishByObject() {
        wishlist.addWish(wish1);
        wishlist.addWish(wish2);

        wishlist.removeWish(wish1);

        assertEquals(1, wishlist.getListOfWishes().size());
        assertFalse(wishlist.getListOfWishes().contains(wish1));
        assertTrue(wishlist.getListOfWishes().contains(wish2));
    }

    @Test
    void testRemoveWishById() {
        wishlist.addWish(wish1);
        wishlist.addWish(wish2);

        wishlist.removeWish(1);

        assertEquals(1, wishlist.getListOfWishes().size());
        assertFalse(wishlist.getListOfWishes().contains(wish1));
        assertTrue(wishlist.getListOfWishes().contains(wish2));
    }

    @Test
    void testRemoveNonExistentWish() {
        wishlist.addWish(wish1);

        assertThrows(WishNotFoundException.class, () -> {
            wishlist.removeWish(wish2);
        });
    }

    @Test
    void testRemoveWishByNonExistentId() {
        wishlist.addWish(wish1);

        assertThrows(WishNotFoundException.class, () -> {
            wishlist.removeWish(999);
        });
    }


}

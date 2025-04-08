package org.example.wishlistapp;

import org.example.wishlistapp.model.Wishlist;
import org.example.wishlistapp.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Map;


@SpringBootTest

@Sql(scripts = "classpath:h2init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

public class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    public void addToDatabase() throws SQLException {
        //Arrange
        Wishlist wishlist = new Wishlist();
        wishlist.setUserId(1);
        wishlist.setWishlistTitle("Test Ønskeliste");

        //Act
        wishlistRepository.addToDatabase(wishlist);

        //Assert
        Map<Integer, Wishlist> wishlists = wishlistRepository.getAllWishlists();

        System.out.println("Antal ønskelister i databasen: " + wishlists.size());
        for (Map.Entry<Integer, Wishlist> entry : wishlists.entrySet()) {
            Wishlist w = entry.getValue();
            System.out.println("Wishlist ID: " + entry.getKey() +
                    ", Titel: '" + w.getWishlistTitle() + "'" +
                    ", UserID: " + w.getUserId());
        }

        boolean found = false;
        for (Wishlist wish : wishlists.values()) {
            if (wish.getWishlistTitle() != null &&
                    wish.getWishlistTitle().equals("Test Ønskeliste") &&
                    wish.getUserId() == 1) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Den tilføjede ønskeliste blev ikke fundet i databasen");
    }

    /*
    @Test
    public void deleteFromDatabase() {

    }

    @Test
    public void findById() {

    }

    @Test
    public void edit() {

    }

    @Test
    public void getAllWishlists() {

    }

    @Test
    public void addWishToWishlist() {

    }

    @Test
    public void deleteWishFromWishlist() {

    }

    @Test
    public void updateWish() {

    }

    @Test
    public void getWishesByWishlistId() {

    }


     */
}

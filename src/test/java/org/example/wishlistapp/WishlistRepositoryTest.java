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


    @Test
    public void deleteFromDatabase() throws SQLException {
        // Arrange
        int existingUserId = 1;
        String existingTitle = "Fødselsdag";

        // Find den eksisterende ønskeliste for at få dens ID
        Map<Integer, Wishlist> wishlists = wishlistRepository.getAllWishlists();
        Integer wishlistId = null;

        for (Map.Entry<Integer, Wishlist> entry : wishlists.entrySet()) {
            Wishlist w = entry.getValue();
            if (w.getWishlistTitle() != null &&
                    w.getWishlistTitle().equals(existingTitle) &&
                    w.getUserId() == existingUserId) {
                wishlistId = entry.getKey();
                break;
            }
        }

        assertNotNull(wishlistId, "Kunne ikke finde den eksisterende ønskeliste");

        // Act - Slet ønskelisten
        Wishlist wishlistToDelete = new Wishlist();
        wishlistToDelete.setWishlistId(wishlistId);
        wishlistRepository.deleteFromDatabase(wishlistToDelete);

        // Assert - Verificer at ønskelisten ikke længere findes i databasen
        wishlists = wishlistRepository.getAllWishlists();
        boolean found = wishlists.containsKey(wishlistId);

        assertFalse(found, "Ønskelisten blev ikke slettet fra databasen");
    }

    @Test
    public void findById() {
        // Arrange
        int existingUserId = 1;
        String existingTitle = "Fødselsdag";

        // Find den eksisterende ønskeliste for at få dens ID
        Map<Integer, Wishlist> wishlists = wishlistRepository.getAllWishlists();
        Integer wishlistId = null;

        for (Map.Entry<Integer, Wishlist> entry : wishlists.entrySet()) {
            Wishlist w = entry.getValue();
            if (w.getWishlistTitle() != null &&
                    w.getWishlistTitle().equals(existingTitle) &&
                    w.getUserId() == existingUserId) {
                wishlistId = entry.getKey();
                break;
            }
        }

        assertNotNull(wishlistId, "Kunne ikke finde den eksisterende ønskeliste");

        //Act
        Wishlist foundWishlist = wishlistRepository.findById(wishlistId);

        //Assert
        assertNotNull(foundWishlist, "Ønskelisten blev ikke fundet");
        assertEquals(existingTitle, foundWishlist.getWishlistTitle(), "Ønskelistens titel matcher ikke");
        assertEquals(existingUserId, foundWishlist.getUserId(), "Ønskelistens bruger-ID matcher ikke");
        assertEquals(wishlistId, foundWishlist.getWishlistId(), "Ønskelistens ID matcher ikke");
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



}

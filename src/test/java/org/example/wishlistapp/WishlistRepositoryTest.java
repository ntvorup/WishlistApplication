package org.example.wishlistapp;

import org.example.wishlistapp.model.Wish;
import org.example.wishlistapp.model.Wishlist;
import org.example.wishlistapp.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;


@SpringBootTest
@Sql(scripts = "classpath:h2init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

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
    public void findById() throws SQLException {
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
    public void edit() throws SQLException {
        // Arrange
        int existingUserId = 1;
        String existingTitle = "Fødselsdag";

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

        // Act - Opdater titlen
        String newTitle = "Opdateret Fødselsdag";
        Wishlist wishlistToUpdate = new Wishlist();
        wishlistToUpdate.setWishlistId(wishlistId);
        wishlistToUpdate.setUserId(existingUserId);
        wishlistToUpdate.setWishlistTitle(newTitle);

        wishlistRepository.edit(wishlistToUpdate);

        // Assert - Tjek at titlen blev opdateret
        Wishlist updatedWishlist = wishlistRepository.findById(wishlistId);
        assertNotNull(updatedWishlist, "Kunne ikke finde ønskelisten efter opdatering");
        assertEquals(newTitle, updatedWishlist.getWishlistTitle(), "Titlen blev ikke opdateret korrekt");
        assertEquals(existingUserId, updatedWishlist.getUserId(), "Bruger-ID blev ændret ved opdatering");
    }

    @Test
    public void getAllWishlists() throws SQLException {
        // Act
        Map<Integer, Wishlist> wishlists = wishlistRepository.getAllWishlists();

        // Assert
        assertNotNull(wishlists, "getAllWishlists returnerede null");
        assertFalse(wishlists.isEmpty(), "Ingen ønskelister blev fundet");

        // Vi forventer mindst 6 ønskelister baseret på h2init.sql
        assertTrue(wishlists.size() >= 6, "Forventede mindst 6 ønskelister, men fandt " + wishlists.size());

        // Test at vi kan få adgang til specifikke ønskelister i map'et
        boolean foundFødselsdag = false;
        boolean foundJul = false;

        for (Wishlist wishlist : wishlists.values()) {
            if (wishlist.getWishlistTitle() != null) {
                if (wishlist.getWishlistTitle().equals("Fødselsdag")) {
                    foundFødselsdag = true;
                } else if (wishlist.getWishlistTitle().equals("Jul")) {
                    foundJul = true;
                }
            }
        }

        assertTrue(foundFødselsdag, "Kunne ikke finde ønskelisten 'Fødselsdag'");
        assertTrue(foundJul, "Kunne ikke finde ønskelisten 'Jul'");
    }

    @Test
    public void addWishToWishlist() throws SQLException {
        // Arrange
        int wishlistId = 1;

        // Opret et nyt ønske
        Wish newWish = new Wish();
        newWish.setWishTitle("Nyt testønske");
        newWish.setWishDescription("Dette er et testønske");
        newWish.setWishPrice(new BigDecimal("199.95"));
        newWish.setProductUrl("https://example.com/test");
        newWish.setImageUrl("https://example.com/image.jpg");

        // Act
        wishlistRepository.addWishToWishlist(newWish, wishlistId);

        // Assert
        Map<Integer, Wish> wishes = wishlistRepository.getWishesByWishlistId(wishlistId);
        assertNotNull(wishes, "Kunne ikke hente ønsker efter tilføjelse");
        assertFalse(wishes.isEmpty(), "Ingen ønsker blev fundet efter tilføjelse");


        boolean found = false;
        for (Wish wish : wishes.values()) {
            if (wish.getWishTitle() != null && wish.getWishTitle().equals("Nyt testønske")) {
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("WARNING: Could not find wish with title 'Nyt testønske'");
        }

        assertTrue(found, "Det tilføjede ønske blev ikke fundet");
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

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
import java.util.List;
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
        List<Wishlist> wishlists = wishlistRepository.getAllWishlistsById(wishlist.getUserId());


        boolean found = false;
        for (Wishlist wish : wishlists) {
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
        List<Wishlist> wishlists = wishlistRepository.getAllWishlistsById(existingUserId);
        Integer wishlistId = null;

        for (Wishlist w : wishlists) {
            if (w.getWishlistTitle() != null &&
                    w.getWishlistTitle().equals(existingTitle) &&
                    w.getUserId() == existingUserId) {
                wishlistId = w.getWishlistId();
                break;
            }
        }

        assertNotNull(wishlistId, "Kunne ikke finde den eksisterende ønskeliste");

        // Act - Slet ønskelisten
        Wishlist wishlistToDelete = new Wishlist();
        wishlistToDelete.setWishlistId(wishlistId);
        wishlistRepository.deleteFromDatabase(wishlistToDelete);

        // Assert - Verificer at ønskelisten ikke længere findes i databasen
        wishlists = wishlistRepository.getAllWishlistsById(existingUserId);
        boolean found = false;
        for (Wishlist w : wishlists) {
            if (wishlistId.equals(w.getWishlistId())) {
                found = true;
                break;
            }
        }

        assertFalse(found, "Ønskelisten blev ikke slettet fra databasen");
    }


    @Test
    public void findById() throws SQLException {
        // Arrange
        int existingUserId = 1;
        String existingTitle = "Fødselsdag";

        // Find den eksisterende ønskeliste for at få dens ID
        List<Wishlist> wishlists = wishlistRepository.getAllWishlistsById(existingUserId);
        Integer wishlistId = null;

        for (Wishlist w : wishlists) {
            if (w.getWishlistTitle() != null &&
                    w.getWishlistTitle().equals(existingTitle) &&
                    w.getUserId() == existingUserId) {
                wishlistId = w.getWishlistId();
                break;
            }
        }

        assertNotNull(wishlistId, "Kunne ikke finde den eksisterende ønskeliste");

        // Act
        Wishlist foundWishlist = wishlistRepository.findById(wishlistId);

        // Assert
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

        List<Wishlist> wishlists = wishlistRepository.getAllWishlistsById(existingUserId);
        Integer wishlistId = null;

        for (Wishlist w : wishlists) {
            if (w.getWishlistTitle() != null &&
                    w.getWishlistTitle().equals(existingTitle) &&
                    w.getUserId() == existingUserId) {
                wishlistId = w.getWishlistId();
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
    public void deleteWishFromWishlist() throws SQLException {
        // Arrange
        int wishlistId = 1;

        // Først henter vi alle ønsker for denne ønskeliste
        Map<Integer, Wish> wishes = wishlistRepository.getWishesByWishlistId(wishlistId);
        assertFalse(wishes.isEmpty(), "Ingen ønsker fundet til test af sletning");

        // Vælg det første ønske til at slette
        Integer wishIdToDelete = wishes.keySet().iterator().next();
        Wish wishToDelete = wishes.get(wishIdToDelete);
        assertNotNull(wishToDelete, "Kunne ikke finde et ønske at slette");

        // Act
        wishlistRepository.deleteWishFromWishlist(wishToDelete);

        // Assert
        Map<Integer, Wish> updatedWishes = wishlistRepository.getWishesByWishlistId(wishlistId);
        assertFalse(updatedWishes.containsKey(wishIdToDelete),
                "Ønsket blev ikke slettet korrekt fra ønskelisten");
    }

    @Test
    public void updateWish() throws SQLException {
        // Arrange
        int wishlistId = 1;

        // Først henter vi alle ønsker for denne ønskeliste
        Map<Integer, Wish> wishes = wishlistRepository.getWishesByWishlistId(wishlistId);
        assertFalse(wishes.isEmpty(), "Ingen ønsker fundet til test af opdatering");

        // Vælg det første ønske til at opdatere
        Integer wishIdToUpdate = wishes.keySet().iterator().next();
        Wish originalWish = wishes.get(wishIdToUpdate);
        assertNotNull(originalWish, "Kunne ikke finde et ønske at opdatere");

        // Opret opdateret ønske
        Wish updatedWish = new Wish();
        updatedWish.setWishId(originalWish.getWishId());
        updatedWish.setWishlistId(wishlistId);
        updatedWish.setWishTitle("Opdateret ønsketitel");
        updatedWish.setWishDescription("Opdateret beskrivelse");
        updatedWish.setWishPrice(new BigDecimal("299.95"));
        updatedWish.setProductUrl("https://example.com/updated");
        updatedWish.setImageUrl("https://example.com/updated-image.jpg");

        // Act
        wishlistRepository.updateWish(updatedWish);

        // Assert
        Map<Integer, Wish> refreshedWishes = wishlistRepository.getWishesByWishlistId(wishlistId);
        Wish refreshedWish = refreshedWishes.get(wishIdToUpdate);

        assertNotNull(refreshedWish, "Kunne ikke finde det opdaterede ønske");
        assertEquals("Opdateret ønsketitel", refreshedWish.getWishTitle(), "Titlen blev ikke opdateret korrekt");
        assertEquals("Opdateret beskrivelse", refreshedWish.getWishDescription(), "Beskrivelsen blev ikke opdateret korrekt");
        assertEquals(0, new BigDecimal("299.95").compareTo(refreshedWish.getWishPrice()), "Prisen blev ikke opdateret korrekt");
        assertEquals("https://example.com/updated", refreshedWish.getProductUrl(), "Produkt URL blev ikke opdateret korrekt");
        assertEquals("https://example.com/updated-image.jpg", refreshedWish.getImageUrl(), "Billede URL blev ikke opdateret korrekt");
    }


    @Test
    public void getWishesByWishlistId() throws SQLException {
        // Arrange
        int wishlistId = 1; 

        // Act
        Map<Integer, Wish> wishes = wishlistRepository.getWishesByWishlistId(wishlistId);

        // Assert
        assertNotNull(wishes, "getWishesByWishlistId returnerede null");
        assertFalse(wishes.isEmpty(), "Ingen ønsker blev fundet for ønskelisten");

        // Verificer at alle ønsker i map'et har korrekte værdier
        for (Map.Entry<Integer, Wish> entry : wishes.entrySet()) {
            Integer wishId = entry.getKey();
            Wish wish = entry.getValue();

            assertNotNull(wishId, "Ønske ID er null");
            assertNotNull(wish, "Ønske objekt er null");
            assertEquals(wishId, wish.getWishId(), "Ønske ID i map-nøgle og ønske objekt stemmer ikke overens");
            assertNotNull(wish.getWishTitle(), "Ønske titel er null");

            // Tjek at nødvendige felter ikke er null eller tomme
            assertNotNull(wish.getWishDescription(), "Ønske beskrivelse er null");
            assertNotNull(wish.getWishPrice(), "Ønske pris er null");

            // Tjek at produkt URL og billede URL er gyldige URL'er (simpel validering)
            if (wish.getProductUrl() != null) {
                assertTrue(wish.getProductUrl().startsWith("http"),
                        "Produkt URL har ikke et gyldigt format: " + wish.getProductUrl());
            }

            if (wish.getImageUrl() != null) {
                assertTrue(wish.getImageUrl().startsWith("http"),
                        "Billede URL har ikke et gyldigt format: " + wish.getImageUrl());
            }
        }
    }
}
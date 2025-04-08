package org.example.wishlistapp.controller;

import org.example.wishlistapp.model.Wish;
import org.example.wishlistapp.service.WishlistService;
import org.springframework.ui.Model;
import org.example.wishlistapp.model.Wishlist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequestMapping("/wishlist")

public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }


    // Get all wishlists
    @GetMapping("")
    public String getAllWishlists(Model model) {
        model.addAttribute("wishlists", wishlistService.getAllWishlists());
        return "";
    }


    // Get wishlist by id
    @GetMapping("/{wishlistId}")
    public String findByID(@PathVariable int wishlistId, Model model) {
        Wishlist wishlist = wishlistService.findById(wishlistId);
        model.addAttribute("wishlist", wishlist);
        return "";
    }


    // Add a new wishlist
    //TODO Skal der tilføjes en User til den også?
    @PostMapping("/add/wishlist")
    public String addWishlistToDatabase(@ModelAttribute Wishlist wishlist){
        wishlistService.addWishlistToDatabase(wishlist);
        return "";
    }


    // Edit a wishlist
    @PostMapping("/edit/wishlist")
    public String editWishlist(@ModelAttribute Wishlist wishlist, Model model) {
        boolean success = wishlistService.editWishlist(wishlist);

        if (success) {
            model.addAttribute("updatedWishlist", wishlist);
            return "wishlistDetail";  //TODO Return somewhere we want it to
        } else {
            model.addAttribute("error", "Der opstod en fejl ved opdatering af ønskelisten");
            return "editWishlist";  //TODO Return somewhere we want it to
        }
    }


    //TODO How the fuck do I get the right wishlist to delete?!
    // Delete a wishlist
    @PostMapping("/delete/wishlist/{wishlistId}")
    public String deleteWishlist(@PathVariable int wishlistId, Model model) {
        Wishlist deletedWishlist = wishlistService.deleteWishlistFromDatabase();

        if (deletedWishlist == null) {
            throw new IllegalArgumentException("Ugyldigt input - Ønskelisten findes ikke");
        }
        model.addAttribute("deletedWishlist", deletedWishlist);

        return "redirect:/wishlist";
    }

//-----------------------------------  Wishes  ----------------------------------------------------

    // Get wishes by wishlist ID
    @GetMapping("/wishes/{wishlistId}")
    public String getWishesByWishlistId(@PathVariable int wishlistId, Model model){
        Map<Integer, Wish> wishes = wishlistService.getWishesByWishlistId(wishlistId);
        model.addAttribute("wishes",wishes);
        return "wishList";
    }

    // Add a wish to a wishlist
    @PostMapping("/{wishlistId}/add-wish")
    public String addWishToWishlist(@PathVariable int wishlistId, @ModelAttribute Wish newWishToAdd) {
        wishlistService.addWishToWishlist(newWishToAdd,wishlistId);
        return "redirect:/wishlist/" + wishlistId + "/wishes"; // Chats forslag
    }

    //TODO Eksperiment på om dette vil virke
    // Delete a wish from a wishlist
    @PostMapping("/wish/delete/{wishId}")
    public String deleteWishFromWishlist(@PathVariable int wishId) {
        Wish dummyWish = new Wish();
        dummyWish.setWishId(wishId);
    wishlistService.deleteWishFromWishlist(dummyWish);
    return ""; //TODO redirect burde være tilbage til specifikke liste eller?
    }


    // Update a wish
    @PostMapping("/wish/edit/{wishId}")
    public String updateWish(@PathVariable int wishId, @ModelAttribute Wish wish, Model model){
        boolean success = wishlistService.updateWish(wish);

        if (success) {
            model.addAttribute("updatedWishlist", wish);
            return "wishDetail";  // Return somewhere if we want it
        } else {
            model.addAttribute("error", "Der opstod en fejl ved opdatering af ønskelisten.");
            return "updateWish";  // Return somewhere if we want it
        }

    }







}

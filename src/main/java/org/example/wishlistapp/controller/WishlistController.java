package org.example.wishlistapp.controller;

import org.example.wishlistapp.model.Wish;
import org.springframework.ui.Model;
import org.example.wishlistapp.model.Wishlist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/wishlist")

public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }


    @GetMapping("")
    public String getWishlists(Model model) {
        model.addAttribute("wishlists", wishlistService.getWishlists());
        return "";
    }


    @GetMapping("/{wishlistTitle}")
    public String getWishlistByTitle(@PathVariable String wishlistTitle, Model model) {
        Wishlist wishlist = wishlistService.getWishlistByTitle(wishlistTitle);
        model.addAttribute("wishlist", wishlist);
        return "";
    }


    //Skal der tilføjes en User til den også?
    @GetMapping("/add")
    public String addWishlist(Model model){
        model.addAttribute("wishlist", new Wishlist());
        return "";
    }


    @PostMapping("/save")
    public String saveWishlist(@ModelAttribute Wishlist wishlist) {
        wishlistService.saveWish(wishlist);
        return "redirect:/wishlist";
    }


    @GetMapping("/wishlistTitle}/edit")
    public String editWishlist(@PathVariable String wishlistTitle, Model model) {
        Wishlist wishlist = wishlistService.getWishlistByTitle(wishlistTitle);
        if (wishlist == null) {
            throw new IllegalArgumentException("Ugyldig input");
        }
        model.addAttribute("wishlist", wishlist);
        return "";
    }


    @PostMapping("/update")
    public String updateWishlist(Model model, @ModelAttribute Wishlist wishlist) {
        wishlistService.updateWishlist(wishlist);
        model.addAttribute("updatedWishlist", wishlist);
        return "";
    }


    @PostMapping("/delete/{wishlistTitle}")
    public String deleteWishlist(@PathVariable String wishlistTitle, Model model) {
        Wishlist deletedWishlist = wishlistService.deleteWishlist(wishlistTitle);
        if (deletedWishlist == null) {
            throw new IllegalArgumentException("Ugyldigt input");
        }
        model.addAttribute("deletedWishlist", deletedWishlist);
        return "redirect:/wishlist";
    }










}

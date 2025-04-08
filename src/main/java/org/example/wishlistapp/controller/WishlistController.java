package org.example.wishlistapp.controller;

import jakarta.servlet.http.HttpSession;
import org.example.wishlistapp.model.Wish;
import org.example.wishlistapp.service.WishlistService;
import org.springframework.ui.Model;
import org.example.wishlistapp.model.Wishlist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;


@Controller
@RequestMapping("/wishlist")

public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }


    // Get all wishlists
    @GetMapping("/user/{id}")
    public String getAllWishlists(@PathVariable("id") int userId,HttpSession session,
                                  Model model) {
        Integer userSessionId = (Integer) session.getAttribute("userId");

        if (userSessionId == null) {
            return "redirect:/index";
        }

        model.addAttribute("wishlists", wishlistService.getAllWishlistsById(userSessionId));
        return "/user";
    }


    // Get wishlist by id
    @GetMapping("/wishlist/{id}")
    public String viewWishlist(@PathVariable("id") int wishlistID, Model model) {
        Map<Integer, Wish> wishes = wishlistService.getWishesByWishlistId(wishlistID);
        model.addAttribute("wishes", wishes);
        model.addAttribute("wishlistId", wishlistID);
        return "wishlist";
    }


    // Add a new wishlist
    @GetMapping("/addWishlist")
    public String addWishlistToDatabase(Model model) {
        Wishlist newWishlist = new Wishlist();

        model.addAttribute("newWishlist", newWishlist);
        return "addWishlist";
    }

    @PostMapping("/saveWishlist")
    public String saveWishlist(@ModelAttribute Wishlist newWishlist,
                               @RequestParam(value = "wishlistTitle") String wishlistTitle,
                               HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/index";
        }

        newWishlist.setUserId(userId);
        newWishlist.setWishlistTitle(wishlistTitle);

        wishlistService.addWishlistToDatabase(newWishlist);

        return "redirect:/user/" + userId;
    }

    @GetMapping("/wishlist/edit/{wishlistId}")
    public String editWishlist(@PathVariable int wishlistId, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/index";
        }

        Wishlist wishlist = wishlistService.findById(wishlistId);

        if (wishlist == null || wishlist.getUserId() != userId) {
            return "redirect:/user/" + userId;
        }

        model.addAttribute("wishlist", wishlist);
        return "editWishlist";
    }

    @PostMapping("/wishlist/edit")
    public String saveWishlist(@ModelAttribute Wishlist wishlist, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null || !userId.equals(wishlist.getUserId())) {
            return "redirect:/index";
        }

        wishlistService.editWishlist(wishlist);
        return "redirect:/user/" + userId;
    }

    @PostMapping("wishlist/{wishlistId}/delete")
    public String deleteWishlist(@PathVariable("wishlistId") int wishlistId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        wishlistService.deleteWishlistFromDatabase(wishlistService.findById(wishlistId));
        return "redirect:/user/" + userId;
    }


//    // Edit a wishlist
//    @PostMapping("/edit/wishlist")
//    public String editWishlist(@ModelAttribute Wishlist wishlist, Model model) {
//        boolean success = wishlistService.editWishlist(wishlist);
//
//        if (success) {
//            model.addAttribute("updatedWishlist", wishlist);
//            return "wishlistDetail";  //TODO Return somewhere we want it to
//        } else {
//            model.addAttribute("error", "Der opstod en fejl ved opdatering af ønskelisten");
//            return "editWishlist";  //TODO Return somewhere we want it to
//        }
//    }
//
//
//    //TODO How the fuck do I get the right wishlist to delete?!
//    // Delete a wishlist
//    @PostMapping("/delete/wishlist/{wishlistId}")
//    public String deleteWishlist(@PathVariable int wishlistId, Model model) {
//        Wishlist deletedWishlist = wishlistService.deleteWishlistFromDatabase();
//
//        if (deletedWishlist == null) {
//            throw new IllegalArgumentException("Ugyldigt input - Ønskelisten findes ikke");
//        }
//        model.addAttribute("deletedWishlist", deletedWishlist);
//
//        return "redirect:/wishlist";
//    }

//-----------------------------------  Wishes  ----------------------------------------------------

    @GetMapping("/wishlist/{wishlistId}/wish/{wishId}")
    public String viewWish(@PathVariable int wishlistId,
                           @PathVariable int wishId,
                           Model model) {
        Wish wish = wishlistService.getWishById(wishId);
        model.addAttribute("wish", wish);
        return "/wish";
    }

    @PostMapping("/wishlist/{wishlistId}/wish")
    public String addWish(@PathVariable int wishlistId,
                          @RequestParam("wishTitle") String wishTitle,
                          @RequestParam(value = "wishDescription", required = false) String wishDescription,
                          @RequestParam(value = "wishPrice", required = false) BigDecimal wishPrice,
                          @RequestParam(value = "wishUrl", required = false) String wishUrl,
                          @RequestParam(value = "wishImageUrl", required = false) String wishImageUrl) {

        Wish newWish = new Wish();
        newWish.setWishlistId(wishlistId);

        if (wishTitle != null && !wishTitle.trim().isEmpty()) {
            newWish.setWishTitle(wishTitle);
        }
        if (wishDescription != null && !wishDescription.trim().isEmpty()) {
            newWish.setWishDescription(wishDescription);
        }
        if (wishPrice != null && wishPrice.compareTo(BigDecimal.ZERO) > 0) {
            newWish.setWishPrice(wishPrice);
        }
        if (wishUrl != null && !wishUrl.trim().isEmpty()) {
            newWish.setProductUrl(wishUrl);
        }
        if (wishImageUrl != null && !wishImageUrl.isEmpty()) {
            newWish.setImageUrl(wishImageUrl);
        }

        wishlistService.addWishToWishlist(newWish, wishlistId);

        return "redirect:/wishlist/" + wishlistId;
    }

    @PostMapping("/wishlist/{wishlistId}/wish/{wishId}/delete")
    public String deleteWish(@PathVariable int wishlistId,
                             @PathVariable int wishId,
                             HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");

        wishlistService.deleteWishFromWishlist(wishlistService.getWishById(wishId));

        return "redirect:/wishlist/" + wishlistId;
    }

    @GetMapping("/wishlist/{wishlistId}/wish/{wishId}/edit")
    public String editWish(@PathVariable int wishId,
                           @PathVariable int wishlistId,
                           Model model,
                           HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/wishlist";
        }

        Wish wish = wishlistService.getWishById(wishId);

        if (wish == null) {
            return "redirect:/user/" + userId;
        }

        model.addAttribute("wish", wish);
        return "editWish";
    }

    @PostMapping("/wishlist/{wishlistId}/wish/{wishId}/edit")
    public String saveWish(@PathVariable int wishlistId,
                           @PathVariable int wishId,
                           @ModelAttribute Wish wish,
                           HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/index";
        }

        wish.setWishlistId(wishlistId);
        wish.setWishId(wishId);

        wishlistService.updateWish(wish);
        return "redirect:/wishlist/wishlist/" + wishlistId;
    }


//    // Get wishes by wishlist ID
//    @GetMapping("/wishes/{wishlistId}")
//    public String getWishesByWishlistId(@PathVariable int wishlistId, Model model){
//        Map<Integer, Wish> wishes = wishlistService.getWishesByWishlistId(wishlistId);
//        model.addAttribute("wishes",wishes);
//        return "wishList";
//    }
//
//    // Add a wish to a wishlist
//    @PostMapping("/{wishlistId}/add-wish")
//    public String addWishToWishlist(@PathVariable int wishlistId, @ModelAttribute Wish newWishToAdd) {
//        wishlistService.addWishToWishlist(newWishToAdd,wishlistId);
//        return "redirect:/wishlist/" + wishlistId + "/wishes"; // Chats forslag
//    }
//
//    //TODO Eksperiment på om dette vil virke
//    // Delete a wish from a wishlist
//    @PostMapping("/wish/delete/{wishId}")
//    public String deleteWishFromWishlist(@PathVariable int wishId) {
//        Wish dummyWish = new Wish();
//        dummyWish.setWishId(wishId);
//    wishlistService.deleteWishFromWishlist(dummyWish);
//    return ""; //TODO redirect burde være tilbage til specifikke liste eller?
//    }
//
//
//    // Update a wish
//    @PostMapping("/wish/edit/{wishId}")
//    public String updateWish(@PathVariable int wishId, @ModelAttribute Wish wish, Model model){
//        boolean success = wishlistService.updateWish(wish);
//
//        if (success) {
//            model.addAttribute("updatedWishlist", wish);
//            return "wishDetail";  // Return somewhere if we want it
//        } else {
//            model.addAttribute("error", "Der opstod en fejl ved opdatering af ønskelisten.");
//            return "updateWish";  // Return somewhere if we want it
//        }
//
//    }


}

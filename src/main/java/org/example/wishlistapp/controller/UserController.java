package org.example.wishlistapp.controller;


import org.example.wishlistapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getUser(Model model) {
        model.addAttribute("user", userService.getUser);
        return "";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "";
    }


    @GetMapping("/{userId}/edit")
    public String editUser(@PathVariable int userID, Model model) {
        User user = userService.getUserByID(userID);
        if (user == null) {
            throw new IllegalArgumentException("Udyldigt ID");
        }
        model.addAttribute("user", user);
        return "";
    }


    @PostMapping("/update")
    public String updateUser(Model model, @ModelAttribute User user) {
        userService.updateUser(user);
        model.addAttribute("updatedUser", user);
        return "";
    }

    @PostMapping("/delete/{userID}")
    public String deleteAttraction(@PathVariable int userID, Model model) {
        User deletedUser = userService.deleteUser(userID);
        if (deletedUser == null){
            throw new IllegalArgumentException("Ugyldigt ID");
        }
        model.addAttribute("deletedAttraction", deletedUser);
        return "";
    }
}

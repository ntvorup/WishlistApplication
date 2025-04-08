package org.example.wishlistapp.controller;


import org.example.wishlistapp.model.User;
import org.example.wishlistapp.service.UserService;
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


    // Get all users
    @GetMapping("")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "";
    }


    // Get user by id
    @GetMapping("/{userId}")
    public String findById(@PathVariable int userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "";
    }


    // Add a new user
    @PostMapping("/add/user")
    public String addUserToDatabase(@ModelAttribute User user) {
        userService.addToDatabase(user);
        return "redirect:/user";
    }


    // Edit a user
    @PostMapping("/edit/user")
    public String editUser(@ModelAttribute User user, Model model) {
        boolean success = userService.editUser(user);

        if (success) {
            model.addAttribute("updatedUser", user);
            return "userDetail";
        } else {
            model.addAttribute("error", "Der opstod en fejl ved opdatering af brugeren");
            return "editUser";
        }
    }


    // Delete a user
    @PostMapping("/delete/user/{userId}")
    public String deleteUser(@PathVariable int userId, Model model) {
        User userToDelete = userService.findById((userId));

        if (userToDelete == null) {
            throw new IllegalArgumentException("Ugyldigt input - Brugeren findes ikke");
        }

        userService.deleteFromDatabase(userToDelete);
        model.addAttribute("deletedUser", userToDelete);

        return "redirect:/user";
    }


    // Find user by email
    @GetMapping("/by-email")
    public String getUserByEmail(@RequestParam String email, Model model) {
        try {
            User user = userService.findByEmail(email);
            model.addAttribute("user", user);
            return "userDetail";
        } catch (Exception e) {
            model.addAttribute("error", "Brugeren med denne email findes ikke");
            return "userSearchError";
            //TODO return to another page please
        }
    }


    // Show add user form
    @GetMapping("/add")
    public String showAddusersForm(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }


    //Show edit user form
    @GetMapping("/edit/{userId}")
    public String showEditUserForm(@PathVariable int userId, Model model) {
        User user = userService.findById(userId);

        if (user == null) {
            throw new IllegalArgumentException("Ugyldigt input - Brugeren findes ikke");
        }

        model.addAttribute("user", user);
        return "editUser";
    }

}
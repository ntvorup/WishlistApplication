package org.example.wishlistapp.controller;


import jakarta.servlet.http.HttpSession;
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


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("newUser", new User());
        return "index"; //
    }

    @PostMapping("/login")
    public String loginWithEmailAndPassword(@RequestParam String email,
                                            @RequestParam String password,
                                            Model model,
                                            HttpSession session) {

        User userLoggedIn = userService.findByEmailAndPassword(email, password);

        if (userLoggedIn != null) {
            session.setAttribute("userId", userLoggedIn.getUserId());
            return "redirect:/wishlist/user/" + userLoggedIn.getUserId();
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "index";
        }
    }

    @GetMapping("/register")
    public String registerWithEmailAndPasswordAdd(Model model) {
        User newUser = new User();

        model.addAttribute("newUser", newUser);
        return "user";
    }

    @PostMapping("/registerSave")
    public String registerWithEmailAndPasswordSave(@ModelAttribute User newUser, Model model, HttpSession session) {

        if (!userService.doesEmailExist(newUser.getEmail())) {
            userService.addNewUserToDatabase(newUser);

            // Log brugeren ind direkte efter registrering
            User userLoggedIn = userService.findByEmailAndPassword(newUser.getEmail(), newUser.getPassword());
            session.setAttribute("userId", userLoggedIn.getUserId());

            return "redirect:/user/" + userLoggedIn.getUserId();
        } else {
            model.addAttribute("alreadyExist", "Email is already registered");
            return "redirect:/";
        }
    }


    @PostMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable int id) {
        userService.deleteFromDatabase(userService.findById(id));
        return "redirect:/";
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(@PathVariable int id, Model model) {
        User userToEdit = userService.findById(id);

        model.addAttribute("userToEdit", userToEdit);
        return "updateUser";
    }

    @PostMapping("/user/{id}/update")
    public String updateUser(@PathVariable int id,
                             @RequestParam(value = "firstName", required = false) String newFirstName,
                             @RequestParam(value = "lastName", required = false) String newLastName,
                             @RequestParam(value = "email", required = false) String newEmail,
                             @RequestParam(value = "password", required = false) String newPassword,
                             Model model) {
        User user = userService.findById(id);

        if (newFirstName != null && !newFirstName.trim().isEmpty()) {
            user.setFirstName(newFirstName);
        }
        if (newLastName != null && !newLastName.trim().isEmpty()) {
            user.setLastName(newLastName);
        }
        if (newEmail != null && !newEmail.trim().isEmpty()) {
            user.setEmail(newEmail);
        }
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            user.setPassword(newPassword);
        }

        if (userService.editUser(user)) {
            return "redirect:/user/" + user.getUserId();
        } else {
            model.addAttribute("shouldNeverBeHere", "If we arrive here something is not right");
            return "error";
        }
    }
}
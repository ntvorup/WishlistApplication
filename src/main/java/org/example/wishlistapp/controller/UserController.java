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

    @GetMapping("/user")
    public String showUserPage(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        User user = userService.findById(userId);
        model.addAttribute("currentUser", user);
        return "user";
    }

    @GetMapping("/user/{id}")
    public String showUserPage(@PathVariable int id, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null || userId != id) {
            return "redirect:/";
        }

        User user = userService.findById(userId);
        model.addAttribute("currentUser", user);
        return "user";
    }

    // Get all users
//    @GetMapping("")
//    public String getAllUsers(Model model) {
//        model.addAttribute("users", userService.getAllUsers());
//        return "";
//    }

    @PostMapping("/login")
    public String loginWithEmailAndPassword(@RequestParam String email,
                                            @RequestParam String password,
                                            Model model,
                                            HttpSession session){

        User userLoggedIn = userService.findByEmailAndPassword(email, password);

        if (userLoggedIn != null){
            session.setAttribute("userId", userLoggedIn.getUserId());
            return "redirect:/user/" + userLoggedIn.getUserId();
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "index";
        }
    }

    @GetMapping("/register")
    public String registerWithEmailAndPasswordAdd(Model model){
        User newUser = new User();

        model.addAttribute("newUser", newUser);
        return "/user";
    }

    /*@PostMapping("/registerSave")
    public String registerWithEmailAndPasswordSave(@ModelAttribute User newUser, Model model, HttpSession session){

        if(!userService.doesEmailExist(newUser.getEmail())){
            userService.addNewUserToDatabase(newUser);
            return loginWithEmailAndPassword(newUser.getEmail(), newUser.getPassword(), model, session);
        } else {
            model.addAttribute("alreadyExist", "Email is already registered");
            return "redirect:/user";
        }
    }
     */

    @PostMapping("/registerSave")
    public String registerWithEmailAndPasswordSave(@ModelAttribute User newUser, Model model, HttpSession session){

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
    public String deleteUser(@PathVariable int id){
        userService.deleteFromDatabase(userService.findById(id));
        return "redirect:/";
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(@PathVariable int id, Model model){
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
                             Model model){
        User user = userService.findById(id);

        if (newFirstName != null && !newFirstName.trim().isEmpty()){
            user.setFirstName(newFirstName);
        }
        if (newLastName != null && !newLastName.trim().isEmpty()){
            user.setLastName(newLastName);
        }
        if (newEmail != null && !newEmail.trim().isEmpty()){
            user.setEmail(newEmail);
        }
        if (newPassword != null && !newPassword.trim().isEmpty()){
            user.setPassword(newPassword);
        }

        if(userService.editUser(user)){
            return "redirect:/user/" + user.getUserId();
        } else {
            model.addAttribute("shouldNeverBeHere", "If we arrive here something is not right");
            return "/error";
        }
    }

//    // Get user by id
//    @GetMapping("/{userId}")
//    public String findById(@PathVariable int userId, Model model) {
//        User user = userService.findById(userId);
//        model.addAttribute("user", user);
//        return "";
//    }
//
//
//    // Add a new user
//    @PostMapping("/add/user")
//    public String addUserToDatabase(@ModelAttribute User user) {
//        userService.addToDatabase(user);
//        return "redirect:/user";
//    }
//
//
//    // Edit a user
//    @PostMapping("/edit/user")
//    public String editUser(@ModelAttribute User user, Model model) {
//        boolean success = userService.editUser(user);
//
//        if (success) {
//            model.addAttribute("updatedUser", user);
//            return "userDetail";
//        } else {
//            model.addAttribute("error", "Der opstod en fejl ved opdatering af brugeren");
//            return "editUser";
//        }
//    }
//
//
//    // Delete a user
//    @PostMapping("/delete/user/{userId}")
//    public String deleteUser(@PathVariable int userId, Model model) {
//        User userToDelete = userService.findById((userId));
//
//        if (userToDelete == null) {
//            throw new IllegalArgumentException("Ugyldigt input - Brugeren findes ikke");
//        }
//
//        userService.deleteFromDatabase(userToDelete);
//        model.addAttribute("deletedUser", userToDelete);
//
//        return "redirect:/user";
//    }
//
//
//    // Find user by email
//    @GetMapping("/by-email")
//    public String getUserByEmail(@RequestParam String email, Model model) {
//        try {
//            User user = userService.findByEmailAndPassword(email);
//            model.addAttribute("user", user);
//            return "userDetail";
//        } catch (Exception e) {
//            model.addAttribute("error", "Brugeren med denne email findes ikke");
//            return "userSearchError";
//            //TODO return to another page please
//        }
//    }
//
//
//    // Show add user form
//    @GetMapping("/add")
//    public String showAddUsersForm(Model model) {
//        model.addAttribute("user", new User());
//        return "addUser";
//    }
//
//
//    //Show edit user form
//    @GetMapping("/edit/{userId}")
//    public String showEditUserForm(@PathVariable int userId, Model model) {
//        User user = userService.findById(userId);
//
//        if (user == null) {
//            throw new IllegalArgumentException("Ugyldigt input - Brugeren findes ikke");
//        }
//
//        model.addAttribute("user", user);
//        return "editUser";
//    }

}
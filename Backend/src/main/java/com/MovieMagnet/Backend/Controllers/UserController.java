package com.MovieMagnet.Backend.Controllers;


import com.MovieMagnet.Backend.Classes.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.MovieMagnet.Backend.Repositories.UserRepository;
import static com.MovieMagnet.Backend.Classes.Encryption.Encrypt;

@Tag(name="User", description="Management APIs for Users")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    //---------------------------------//
    //          User Controller        //
    //---------------------------------//

    @Operation(summary = "Get all users",
            description = "Returns of all the users in a list.",
            tags = { "GET", "User" })
    @GetMapping("/users")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Operation(summary = "Find a user",
            description = "Finds and returns the user profile of the user with the provided email.",
            tags = { "User", "GET" })
    @GetMapping("/users/{email}")
    User getUserByEmail(@Parameter(description = "The email of the user") @PathVariable String email) {
        return userRepository.findByEmail(email);
    }

    @Operation(summary = "Add a user",
            description = "Adds the user provided in the RequestBody to the database.",
            tags = { "User", "POST" })
    @PostMapping("/users")
    String createUser(@Parameter(description = "The user to be added") @RequestBody User user) {
        if (user == null)
            return failure;
        userRepository.save(user);
        return success;
    }

    @Operation(summary = "Delete a user",
            description = "Delete the user with the email provided in the PathVariable",
            tags = { "User", "DELETE" })
    @DeleteMapping("/users/delete/{email}")
    String deleteUser(@Parameter(description = "The email of the user to be deleted") @PathVariable String email) {
        User user = userRepository.findByEmail(email);
        userRepository.deleteById(user.getId());
        return success;
    }

    //---------- Passwords and Authentication ----------//

    @Operation(summary = "Update password",
            description = "Updates the password for the user",
            tags = { "User", "PUT" })
    @PutMapping("/users/password/{email}")
    String setPassword(@Parameter(description = "The email of the user") @PathVariable String email, @Parameter(description = "The new password for the user") @RequestBody String password) {
        User user = userRepository.findByEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return success;
    }

    @Operation(summary = "Authenticates a user",
            description = "Checks the provided usernames password to see if it matches the stored password.",
            tags = { "User", "POST" })
    @PostMapping("/users/auth")
    public @ResponseBody User authenticatePassword(@Parameter(description = "The authenication request") @RequestBody AuthRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        String userHash = user.getPassword();
        if (userHash.equals(Encrypt(password))) {
            return user;
        }
        return null;
    }

    //---------- User Edit Account Info: Username and Email ----------//
    //----------------------------- Nick -----------------------------//

    @Operation(summary = "Update username for a user",
            description = "Updates the username of the requested user.",
            tags = { "User", "PUT" })
    @PutMapping("/users/updateUsername/{email}")
    String updateUsername(@Parameter(description = "The email of the user") @PathVariable String email, @Parameter(description = "The new username of the user") @RequestBody String newUsername) {

        // new user object
        User user = userRepository.findByEmail(email);

        // base case checking
        if (user == null) {
            return failure;
        }

        user.setName(newUsername); // sets the new userName with parameter
        userRepository.save(user); // saves
        return success;
    }

    @Operation(summary = "Update email for a user",
            description = "Updates the email address of the requested user.",
            tags = { "User", "PUT" })
    @PutMapping("/users/updateEmail/{currentEmail}")
    String updateEmail(@Parameter(description = "The current email of the user") @PathVariable String currentEmail, @Parameter(description = "The new email of the user") @RequestBody String newEmail) {

        // new user object
        User user = userRepository.findByEmail(currentEmail);

        // base case checking
        if (user == null) {
            return failure;
        }

        // checks if new email already exists in the database
        User existingUserWithNewEmail = userRepository.findByEmail(newEmail);
        if (existingUserWithNewEmail != null) {
            return "{\"message\":\"Email already exists\"}";
        }

        user.setEmail(newEmail); // sets new email with user.setEmail
        userRepository.save(user); // saves in userRepository
        return success;
    }

    @GetMapping("/users/friends/{email}")
    List<String> getFriends(@PathVariable String email) {
        User user = userRepository.findByEmail(email);
        return user.getFriendList();
    }

    @PutMapping("/users/friends/{email}")
    String addFriend(@PathVariable String email, @RequestBody String friend) {
        User user = userRepository.findByEmail(email);
        user.addFriend(friend);
        userRepository.save(user);
        return success;
    }

    @DeleteMapping("/users/friends/{email}/{exfriend}")
    String removeFriend(@PathVariable String email, @PathVariable String exfriend) {
        User user = userRepository.findByEmail(email);
        user.removeFriend(exfriend);
        userRepository.save(user);
        return success;
    }
}

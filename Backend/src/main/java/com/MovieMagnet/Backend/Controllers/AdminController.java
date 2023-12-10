package com.MovieMagnet.Backend.Controllers;

import com.MovieMagnet.Backend.Classes.AdminUser;
import com.MovieMagnet.Backend.Classes.AuthRequest;
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

import java.io.IOException;
import java.util.List;

import com.MovieMagnet.Backend.Classes.User;
import com.MovieMagnet.Backend.Repositories.UserRepository;

import static com.MovieMagnet.Backend.Classes.Encryption.Encrypt;
import static com.MovieMagnet.Backend.Classes.User.calcAgeGroup;
//import static com.MovieMagnet.Backend.Classes.User.findUser;

//---------------------------------//
//          Admin Controller       //
//---------------------------------//


@Tag(name="Admin", description="Controls the admin user functionality with CRUD operations")
@RestController
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

/*
    @Operation(summary = "Returns all users",
            description = "Retrieves all of the users in userRepository database",
            tags = { "Admin", "GET" })
    @GetMapping("/users/admin")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Operation(summary = "Returns a user by email",
            description = "Email is the string ID in our database, this method returns the user ID by email",
            tags = { "Admin", "GET" })
    @GetMapping("/users/admin/{email}")
    User getUserByEmail(@Parameter(description = "The email ID for the user to be retrieved") @PathVariable String email){
        List<User> userList = userRepository.findAll();
        return findUser(userList, email);
    }

    @Operation(summary = "Creates new user",
            description = "Uses @RequestBody to create a new user in userRepository database. Returns failure or success.",
            tags = { "Admin", "POST" })
    @PostMapping("/users/admin")
    String createUser(@Parameter(description = "The user being created") @RequestBody User user){
        if (user == null)
            return failure;
        userRepository.save(user);
        return success;
    }

    @Operation(summary = "Deletes existing user",
            description = "Deletes a user by String email ID and calls userRepository method deleteByID. Returns success if user deleted.",
            tags = { "Admin", "DELETE" })
    @DeleteMapping("/users/admin")
    String deleteUser(@Parameter(description = "The email ID for the user to be deleted") @RequestBody String email) {
        List<User> userList = userRepository.findAll();
        User user = findUser(userList, email);
        userRepository.deleteById(user.getId());
        return success;
    }
 */
    // ---------- updated below, on 10/31 ---------- //
    /* added admin Path PutMappings for updating a user's email and username */
/*
    @Operation(summary = "Updates a users email",
            description = "Passes a email string and calls userRepository method findByEmail(currentEmail) to find an existing user by email and then updates their email to the new email, saves in database. Returns failure or success.",
            tags = { "Admin", "PUT" })
    @PutMapping("/users/admin/updateEmail/{currentEmail}")
    String updateEmail(@Parameter(description = "The user's current email") @PathVariable String currentEmail, @Parameter(description = "The user's new email") @RequestBody String newEmail) {
        User user = userRepository.findByEmail(currentEmail);
        if(user == null) {
            return failure;
        }
        user.setEmail(newEmail);
        userRepository.save(user);
        return success;
    }

    @Operation(summary = "Updates a user's username",
            description = "Passes a newUsername string and and currentEmail string to call userRepository method findByEmail(currentEmail) and setName(newUsername) to find an existing user by email and then updates their username to the newUsername, saves in database. Returns failure or success.",
            tags = { "Admin", "PUT" })
    @PutMapping("/users/admin/updateUsername/{currentEmail}")
    String updateUsername(@Parameter(description = "The email string ID of the current user") @PathVariable String currentEmail, @Parameter(description = "The email ID string of the new username") @RequestBody String newUsername) {
        User user = userRepository.findByEmail(currentEmail);
        if(user == null) {
            return failure;
        }
        user.setName(newUsername);
        userRepository.save(user);
        return success;
    }
*/
    @Operation(summary = "Mutes a user",
            description = "Updates the user's mute state based on the provided email and mute state",
            tags = { "Admin", "PUT" })
    @PutMapping("/users/admin/mute/{email}")
    String muteUser(@Parameter(description = "The email ID of the user") @PathVariable String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return "{\"message\":\"failure: user not found\"}";
        }
        user.setMute(true);
        userRepository.save(user);
        return "{\"message\":\"success\"}";
    }

    @Operation(summary = "Unmutes a user",
            description = "Updates the user's mute state based on the provided email and mute state",
            tags = { "Admin", "PUT" })
    @PutMapping("/users/admin/unmute/{email}")
    String unmuteUser(@Parameter(description = "The email ID of the user") @PathVariable String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return "{\"message\":\"failure: user not found\"}";
        }
        user.setMute(false);
        userRepository.save(user);
        return "{\"message\":\"success\"}";
    }


}

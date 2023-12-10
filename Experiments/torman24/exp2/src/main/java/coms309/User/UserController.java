package coms309.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.HashMap;

import static coms309.Encryption.Encrypt;

/**
 * Controller used to showcase Create and Read from a LIST
 *
 * @author Vivek Bengre
 */

@RestController
public class UserController {

    // Note that there is only ONE instance of PeopleController in 
    // Springboot system.
    HashMap<String, User> userList = new  HashMap<>();



    @GetMapping("/user")
    public @ResponseBody HashMap<String,User> getAllPersons() {
        return userList;
    }


    @PostMapping("/user/{name}")
    public @ResponseBody String createPerson(@PathVariable String name) {
        User user = new User(name);
        userList.put(user.getName(), user);
        return "New person "+ user.getName() + " Saved";
    }

    @GetMapping("/user/{name}")
    public @ResponseBody User getUser(@PathVariable String name) {
        User u = userList.get(name);
        return u;
    }

    @GetMapping("/user/{name}/password")
    public @ResponseBody String getPassword(@PathVariable String name) {
        User u = userList.get(name);
        return u.getPassword();
    }


    @PutMapping("/user/{name}")
    public @ResponseBody String updatePerson(@PathVariable String name, @RequestBody String u) {
        User user = userList.get(u);
        user.setName(name);
        User newUser = new User(name);
        userList.replace(u, newUser);
        return "Name changed to " + name;
    }


    @PutMapping("/user/{name}/{password}")
    public @ResponseBody String setPassword(@PathVariable String name, @PathVariable String password) {
        User user = userList.get(name);
        user.setPassword(password);
        return "Password encryption complete";
    }

    @DeleteMapping("/user/{name}")
    public @ResponseBody HashMap<String, User> deleteUser(@PathVariable String name) {
        userList.remove(name);
        return userList;
    }


    @GetMapping("/user/{name}/auth")
    public @ResponseBody String authenicatePassword(@PathVariable String name, @RequestBody String password) {

        User user = userList.get(name);
        String userHash = user.getPassword();
        if (userHash.equals(Encrypt(password))) {

            return "User authenticated";
        }
        else {

            return "Incorrect password";
        }
    }
}


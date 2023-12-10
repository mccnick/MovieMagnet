package com.MovieMagnet.Backend.Classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import java.security.SecureRandom;

import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import static com.MovieMagnet.Backend.Classes.Encryption.Encrypt;

@Entity
public class AdminUser extends User{

    /* Default Constructor */
    public AdminUser() {}

    /* Super Constructor for extended User Class */
    public AdminUser(String name, String birthday, String password, String email) {
        super(name, birthday, password, email);
    }

    /* List All Users and Their Info*/
    public List<User> listAllUsers(List<User> userList) {
        return userList;
    }

    /* View a User's Details by email */
    /*public User viewUserDetails(List<User> userList, String userEmail) {
        return User.findUser(userList, userEmail);
    }*/

    /* Edit a User's Information */
    public void editUserInfo(User user, String name, String ageGroup) {
        user.setName(name);
        user.setAgeGroup(ageGroup);
    }

    /* Suspend or Deactivate a User's Account */
    public void suspendUserAccount(User user) {
        // Implement logic to suspend or deactivate the user account
    }

    /* Reset a User's Password using generateTemporaryPassword method */
    public void resetUserPasswordWithRandom(User user) {
        String newPassword = generateTemporaryPassword(12); // Change the length as needed
        user.setPassword(newPassword);


        /* Example test case:

        AdminUser adminUser = new AdminUser();
        User userToReset = adminUser.viewUserDetails(userList, "user@example.com"); // replace with the user's email
        adminUser.resetUserPasswordWithRandom(userToReset);

        */
    }


    /* Method to Generate Temporary Password for User */
    public String generateTemporaryPassword(int length) {
        String pwCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>?";

        StringBuilder passwordBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++)
        {
            int randomIndex = random.nextInt(pwCharacters.length());
            char randomChar = pwCharacters.charAt(randomIndex);
            passwordBuilder.append(randomChar);
        }
        return passwordBuilder.toString();
    }

}

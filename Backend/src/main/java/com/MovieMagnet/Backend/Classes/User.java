package com.MovieMagnet.Backend.Classes;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import static com.MovieMagnet.Backend.Classes.Encryption.Encrypt;

@Entity
public class User {

    private static int limitPG13 = 13;
    private static int limitR = 17;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "Name of the user")
    private String name;

    @Schema(description = "Age group the user belongs to, determined by the birthday")
    private String ageGroup;

    @Schema(description = "Encrypted password of the user")
    private String password;

    @Schema(description = "Email address of the user, used as a login identifier")
    private String email;

    @Column(name = "is_muted")
    @Schema(description = "Indicates if the user is muted")
    private boolean muteState;

    @Schema(description = "List of the user's friends")
    @ElementCollection
    private List<String> friendList;

    public User() {

    }

    public User(String name, String birthday, String password, String email) {
        this.name = name;
        this.ageGroup = calcAgeGroup(birthday);
        this.password = Encrypt(password);
        this.email = email;
        this.friendList = new ArrayList<>();
    }
/*
    public static User findUser(List<User> userList, String email) {

        User user = null;
        for (User u: userList)
            if (u.getEmail().equals(email)){ user = u; }
        return user;
    }
 */

    public static String calcAgeGroup(String bday) {

        if (bday.equals("admin")) { return "Admin"; }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate localDate = LocalDate.now();
        String currentDate = dtf.format(localDate);
        List<Integer> currentList = Arrays.stream(currentDate.split("/"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        int currentYear = currentList.get(0);
        int currentMonth = currentList.get(1);
        int currentDay = currentList.get(2);


        List<Integer> bdayList = Arrays.stream(bday.split("/"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        int birthYear = bdayList.get(2);
        int birthMonth = bdayList.get(0);
        int birthday = bdayList.get(1);

        String group = "Error";

        if ((currentYear - limitR) >= birthYear) {
            if ((currentYear - limitR > birthYear) || (currentMonth > birthMonth)) { group = "R"; }
            else if ((currentMonth == birthMonth) && (currentDay >= birthday)) { group = "R"; }
            else { group = "PG13"; }

        } else if ((currentYear - limitPG13) >= birthYear) {
            if ((currentYear - limitPG13 > birthYear) || (currentMonth > birthMonth)) { group = "PG13"; }
            else if ((currentMonth == birthMonth) && (currentDay >= birthday)) { group = "PG13"; }
            else { group = "PG"; }

        } else { group = "PG"; }

        return group;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgeGroup() {
        return this.ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = calcAgeGroup(ageGroup);
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = Encrypt(password);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for muting
    public boolean getMute() {
        return muteState;
    }

    // Setter for muting
    public void setMute(boolean muteState) {
        this.muteState = muteState;
    }

    public List<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<String> friendList) {
        this.friendList = friendList;
    }

    public void addFriend(String friend){
        this.friendList.add(friend);
    }

    public void removeFriend(String friend) {
        this.friendList.remove(friend);
    }

    public void clearFriendList() {this.friendList = new ArrayList<>(); }

    @Override
    public String toString() {
        return name + " " + ageGroup + " " + password + " " + email;
    }
}


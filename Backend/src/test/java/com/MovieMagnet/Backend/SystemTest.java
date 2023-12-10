package com.MovieMagnet.Backend;

import static com.MovieMagnet.Backend.Classes.User.calcAgeGroup;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.MovieMagnet.Backend.Classes.*;
import io.restassured.http.ContentType;
import org.apache.coyote.Request;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;	// SBv3

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class SystemTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    //--------------------//
    //     Tyler Tests    //
    //--------------------//

    // UserController
    @Test
    public void getAllUsersTEST() {
        // Send request and receive response
        Response response = RestAssured.with().request("GET", "/users");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(0);
            assertEquals("Admin", returnObj.get("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUserByEmailTEST() {
        // Send request and receive response
        Response response = RestAssured.with().pathParam("email", "admin@moviemagnet.com").request("GET", "/users/{email}");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertTrue(returnString.contains("name\":\"Admin"));
    }

    /*
    @Test
    public void createUserTEST() {
        User testUser = new User("TEST", "0000/00/00", "TEST", "TEST");

        // Send request and receive response
        Response response = RestAssured.with().contentType(ContentType.JSON).body(testUser).request("POST", "/users");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void deleteUserTEST() {
        // Send request and receive response
        Response response = RestAssured.with().pathParam("email", "TEST").request("DELETE", "/users/delete/{email}");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }
*/
    @Test
    public void setPasswordTEST() {
        // Send request and receive response
        Response response = RestAssured.with().pathParam("email", "admin@moviemagnet.com").body("admin").request("PUT", "/users/password/{email}");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void authenticatePasswordTEST() {

        AuthRequest request = new AuthRequest("admin@moviemagnet.com", "admin");

        // Send request and receive response
        Response response = RestAssured.with().contentType(ContentType.JSON).body(request).request("POST", "/users/auth");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertTrue(returnString.contains("name\":\"Admin"));
    }

    @Test
    public void updateUsernameTEST() {
        // Send request and receive response
        Response response = RestAssured.with().pathParam("email", "admin@moviemagnet.com").body("Admin").request("PUT", "/users/updateUsername/{email}");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void updateEmailTEST() {
        // Send request and receive response
        Response response = RestAssured.with().pathParam("currentEmail", "admin@moviemagnet.com").body("admin@moviemagnet.com").request("PUT", "/users/updateEmail/{currentEmail}");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"Email already exists\"}", returnString);
    }

    @Test
    public void getFriendsTEST() {
        // Send request and receive response
        Response response = RestAssured.with().pathParam("email", "admin@moviemagnet.com").request("GET", "/users/friends/{email}");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void addFriendTEST() {
        // Send request and receive response
        Response response = RestAssured.with().pathParam("email", "admin@moviemagnet.com").body("test").request("PUT", "/users/friends/{email}");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void removeFriendTEST() {
        // Send request and receive response
        Response response = RestAssured.with().pathParam("email", "admin@moviemagnet.com").pathParam("exfriend", "test").request("DELETE", "/users/friends/{email}/{exfriend}");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }


    //TheaterController
    @Test
    public void getAllTheatersTEST() {
        // Send request and receive response
        Response response = RestAssured.with().request("GET", "/theaters");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        assertTrue(returnString.contains("name\":\"Cinemark Movies 12"));
    }

    @Test
    public void getTheaterByIdTEST() {
        // Send request and receive response
        Response response = RestAssured.with().pathParam("theater", "Cinemark Movies 12").request("GET", "/theaters/search/{theater}");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertTrue(returnString.contains("name\":\"Cinemark Movies 12"));
    }

    @Test
    public void getShowtimesByTheaterTEST() {
        // Send request and receive response
        Response response = RestAssured.with().pathParam("theater", "Cinemark Movies 12").body("admin@moviemagnet.com").request("POST", "/theaters/showtimes/{theater}");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }


    //DatabaseController
    /*
    @Test
    public void refreshDBTEST() {
        // Send request and receive response
        Response response = RestAssured.with().request("POST", "/refresh");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }*/

    //User
    @Test
    public void calcAgeGroupTEST() {
        String response = calcAgeGroup("01/01/0001");
        assertEquals("R", response);

        String response2 = calcAgeGroup("01/01/2010");
        assertEquals("PG13", response2);

        String response3 = calcAgeGroup("01/01/2020");
        assertEquals("PG", response3);

        response = calcAgeGroup("admin");
        assertEquals("Admin", response);
    }


    //--------------------//
    //      Nick Tests    //
    //--------------------//

    // to do:
    // ReviewController, AdminController, MovieController
    // AdminUser, AuthRequest, Message(no tests), Review(no tests), Theater

    // write tests for delete and post but comment them out, put and get are fine


    // AdminUser.java tests
    @Test
    public void listAllUsersTEST() {
        // Send request and receive response
        Response response = RestAssured.with().request("GET", "/users");

        // Check status code
        assertEquals(200, response.getStatusCode());
    }
/*
    @Test
    public void viewUserDetailsTEST() {
        Response response = RestAssured.with().pathParam("email", "user@example.com").request("GET", "/admin/users/{email}");

        // Check status code and response content
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("user@example.com"));
    }
*/
    /* -- PUT -- nick figure out how to update to existing value so it doesn't "change" the database
    @Test
    public void editUserInfoTEST() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "New Name");
        requestParams.put("ageGroup", "Adult");

        Response response = RestAssured.with().contentType(ContentType.JSON).body(requestParams.toString()).pathParam("email", "user@example.com").request("PUT", "/admin/users/{email}");

        // Check status code
        assertEquals(200, response.getStatusCode());
    }
    */

    /* -- PUT -- nick figure out how to update to existing value
    @Test
    public void resetUserPasswordWithRandomTEST() {
        Response response = RestAssured.with().pathParam("email", "user@example.com").request("PUT", "/admin/users/resetPassword/{email}");

        // Check status code
        assertEquals(200, response.getStatusCode());
    }
    */

    // AuthRequest.java tests
    @Test
    public void getEmailFromAuthRequestTEST() {
        AuthRequest authRequest = new AuthRequest("test@example.com", "password123");

        // Check if getEmail() returns the correct email
        assertEquals("test@example.com", authRequest.getEmail());
    }

    @Test
    public void getPasswordFromAuthRequestTEST() {
        AuthRequest authRequest = new AuthRequest("test@example.com", "password123");

        // Check if getPassword() returns the correct password
        assertEquals("password123", authRequest.getPassword());
    }


    // Message.java and Review.java only have getters and setters, not doing tests


    // Theater.java
    @Test
    public void findTheaterTest() {
        // Create a list of theaters
        List<Theater> theaters = new ArrayList<>();
        Theater theater1 = new Theater("Theater1", "Address1", "1234567890", "12345", "City1");
        Theater theater2 = new Theater("Theater2", "Address2", "0987654321", "54321", "City2");
        theaters.add(theater1);
        theaters.add(theater2);

        // Find a theater by name
        Theater foundTheater = Theater.findTheater(theaters, "Theater1");

        // Check if the correct theater is returned
        assertNotNull(foundTheater);
        assertEquals("Theater1", foundTheater.getName());
    }
/*
    @Test
    public void addAndClearMovieListTest() {
        // Create a theater and a movie
        Theater theater = new Theater();
        Movie movie = new Movie(); // Assuming Movie class has an appropriate constructor or setters

        // Add movie to the theater's movie list
        theater.addMovie(movie);

        // Check if the movie is added
        assertFalse(theater.getMovieList().isEmpty());

        // Clear the movie list
        theater.clearMovieList();

        // Check if the movie list is cleared
        assertTrue(theater.getMovieList().isEmpty());
    }
*/
    /*
    @Test
    public void addShowtimeTest() {
        // Create a theater and a showtime
        Theater theater = new Theater();
        Showtime showtime = new Showtime(); // Assuming Showtime class has an appropriate constructor or setters

        // Add showtime to the theater's showtime list
        theater.addShowtime(showtime);

        // Check if the showtime is added
        assertFalse(theater.getShowtimeList().isEmpty());
    }
*/

//    // ReviewController
//    @Test
//    public void createReviewTest() {
//        int movieId = 1;
//        int userId = 1; // update to int
//        int rating = 5;
//        String comment = "I hated this movie!!!! This is not a test!";
//        String ageGroup = "PG13";
//
//        // Create a mock movie for the review (assuming Movie class has an appropriate constructor)
//        Movie mockMovie = new Movie(/* parameters to initialize the movie */);
//
//        // Create a new Review object with the mock data
//        Review newReview = new Review();
//        newReview.setMovie(mockMovie);
//        newReview.setUserId(userId);
//        newReview.setMovieId(movieId);
//        newReview.setRating(rating);
//        newReview.setComment(comment);
//        newReview.setAgeGroup(ageGroup);
//
//        // Send POST request to create a new review
//        Response response = RestAssured.with().contentType(ContentType.JSON).body(newReview).request("POST", "/reviews");
//
//        // Check status code and response content
//        assertEquals(200, response.getStatusCode());
//        assertEquals("{\"message\":\"success\"}", response.getBody().asString());
//    }


    @Test
    public void getReviewsByMovieTest() {
        int movieId = 1;

        // Send GET request to fetch reviews by movie
        Response response = RestAssured.with().pathParam("movieId", movieId).request("GET", "/reviews/movie/{movieId}");

        // Check status code
        assertEquals(200, response.getStatusCode());
    }
/*
    @Test
    public void deleteReviewTest() {
        int reviewId = 1;
        String adminEmail = "admin@moviemagnet.com";

        // Send DELETE request to delete a review
        Response response = RestAssured.with().pathParam("id", reviewId).pathParam("email", adminEmail).request("DELETE", "/reviews/{id}/{email}");

        // Check status code and response content
        assertEquals(200, response.getStatusCode());
        assertEquals("{\"message\":\"success\"}", response.getBody().asString());
    }
    */
/*
    @Test
    public void getReviewsByAgeGroupTest() {
        String userEmail = "griesman@gmail.com";

        // Send GET request to fetch reviews by user's age group
        Response response = RestAssured.with().pathParam("email", userEmail).request("GET", "/reviews/ageGroup/{email}");

        // Check status code
        assertEquals(200, response.getStatusCode());
    }
*/

    // AdminController.java tests
    @Test
    public void muteUserTest() {
        String userEmail = "griesman@gmail.com"; // ethan's example database email
        boolean muteState = true;

        // Send PUT request to update the user's mute state
        Response response = RestAssured.with().contentType(ContentType.JSON).body(muteState).pathParam("email", userEmail).request("PUT", "/users/admin/mute/{email}");

        // Check status code and response content
        assertEquals(200, response.getStatusCode());
        assertEquals("{\"message\":\"success\"}", response.getBody().asString());
    }

    // MovieController.java tests
    @Test
    public void getMoviesTest() {
        String userEmail = "r@moviemagnet.com";

        // Send POST request to get all movies for a user's age group
        Response response = RestAssured.with().contentType(ContentType.JSON).body(userEmail).request("POST", "/movies/all");

        // Check status code
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void searchMoviesTest() {
        String movieTitle = "Silent Night";
        String userEmail = "r@moviemagnet.com";

        // Send POST request to search for a specific movie
        Response response = RestAssured.with().contentType(ContentType.JSON).body(userEmail).pathParam("title", movieTitle).request("POST", "/movies/search/{title}");

        // Check status code
        assertEquals(200, response.getStatusCode());
    }
}

// nick -
// git push, then checkout to main, git pull, so main is updated,
// then checkout to backend-testbase and git merge main, then make minor changes




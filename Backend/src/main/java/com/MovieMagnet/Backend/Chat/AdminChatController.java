
/* AdminChatControllerR will need:
    - what would need to be different from ChatControllerR?
    - DM only functionality as compared to regular user
    - would not be a public chat
    - generate server with link (endpoint?) to use for frontend input
    - Admin editing (PUT) their account info in Settings
    - *** the USER will auto connect to a DM to the adminsuer ***
* */


// only DMs, not an open websocket chat


package com.MovieMagnet.Backend.Chat;

        import java.io.IOException;
        import java.util.Hashtable;
        import java.util.List;
        import java.util.Map;

        import javax.websocket.OnClose;
        import javax.websocket.OnError;
        import javax.websocket.OnMessage;
        import javax.websocket.OnOpen;
        import javax.websocket.Session;
        import javax.websocket.server.PathParam;
        import javax.websocket.server.ServerEndpoint;

        import com.MovieMagnet.Backend.Classes.User;
        import com.MovieMagnet.Backend.Repositories.UserRepository;
        import com.MovieMagnet.Backend.Classes.Message;
        import com.MovieMagnet.Backend.Repositories.MessageRepository;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;

@Controller
@ServerEndpoint(value = "/chat/admin/{username}")  // this is Websocket url
public class AdminChatController {

    private static UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository repo) {
        AdminChatController.userRepository = repo;
    }

    private static MessageRepository adminRepo;

    /*
     * Grabs the MessageRepository singleton from the Spring Application
     * Context.  This works because of the @Controller annotation on this
     * class and because the variable is declared as static.
     * There are other ways to set this. However, this approach is
     * easiest.
     */
    @Autowired
    public void setMessageRepository(MessageRepository repo) {
        adminRepo = repo;  // we are setting the static variable for Admin Repository chat
    }

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(ChatControllerR.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username)
            throws IOException {

        logger.info("Entered into Open");

        // store connecting user information
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        //Send chat history to the newly connected user
        sendMessageToPArticularUser(username, getChatHistory());

        // broadcast that new user joined
        String message = "User:" + username + " has Joined the Chat";
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        // Handle new messages
        logger.info("Entered into Message: Got Message:" + message);
        String username = sessionUsernameMap.get(session);

        // Check first if the user is muted, added by Nick on 11/28 for admin muting
        User user = userRepository.findByEmail(username);
        if (user != null && user.getMute()) {
            // User is muted, handle accordingly (e.g., send a notification to the user)
            sendMessageToPArticularUser(username, "You are muted and cannot send messages.");
            return;
        }

        // else not muted, proceed as normal below

        // If sender is Admin
        if (username.equals("Admin")) {
            if (message.startsWith("@")) {
                // Admin is sending a DM to a user
                String destUsername = message.split(" ")[0].substring(1);

                sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message);
                sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);
            }
            else {
                // Inform Admin they should use the DM format
                sendMessageToPArticularUser(username, "Please use the DM format '@username <message>'");
            }
        } else { // If sender isn't Admin, user is sending a DM to Admin
            sendMessageToPArticularUser("Admin", "[DM] " + username + ": " + message);
            sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);
        }

        // forcing DM here

//        else { // broadcast
//            broadcast(username + ": " + message);
//        }
        // Saving chat history to repository

        // Save chat history to repository
        adminRepo.save(new Message(username, message, "Help"));
    }


//
//    // Helper function to check if a user is an admin.
//    private boolean isUserAdmin(String email) {
//        User foundUser = userRepository.findByEmail(email);
//
//        // check if the user exists and if their ageGroup is set to "Admin"
//        return (foundUser != null && "Admin".equals(foundUser.getAgeGroup()));
//    }
//


    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        // remove the user connection information
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        // broadcast that the user disconnected
        String message = username + " disconnected";
        broadcast(message);
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
        throwable.printStackTrace();
    }


    private void sendMessageToPArticularUser(String username, String message) {
        if (isUserOnline(username)) {
            try {

                usernameSessionMap.get(username).getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("Exception: " + e.getMessage());
                e.printStackTrace();
                adminRepo.save(new Message(username, message, "Help"));
            }
        } else {
            adminRepo.save(new Message(username, message, "Help"));
        }
    }

    private boolean isUserOnline(String username) {
        Session session = usernameSessionMap.get(username);
        return session != null && session.isOpen();
    }




    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            }
            catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }

        });

    }


    // Gets the Chat history from the repository
    private String getChatHistory() {
        List<Message> messages = adminRepo.findByType("Help");

        // convert the list to a string
        StringBuilder sb = new StringBuilder();
        if(messages != null && messages.size() != 0) {
            for (Message message : messages) {
                sb.append(message.getUserName() + ": " + message.getContent() + "\n");
            }
        }
        return sb.toString();
    }
}



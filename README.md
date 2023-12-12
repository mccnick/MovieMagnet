# Movie Magnet Android App

Movie Magnet is an Android application dedicated to movie enthusiasts who enjoy socializing over cinema. It integrates features such as theater searching, curated age-group based suggestions (PG, PG13, R), and movie reviews, all wrapped in a user-friendly interface.

## Demo

- A demonstration video of Movie Magnet is available for viewing. It showcases the app's capabilities and user interface.
- [Watch the demo video here](https://drive.google.com/file/d/1y7Od1rRmp6tVQmu3a-cz0oVwLy3YiD5V/view?usp=drive_link). Make sure to select 4K resolution for the best viewing experience!

## Awards

- **Best Coder & Best Manager**: I (Nick) was recognized by the Computer Science department professor Simanta Mitra and my TA the entire semester, for outstanding contributions as a backend developer and team manager (only 27 awards were given out of 600+ students!).
- [View my awards here!](https://drive.google.com/file/d/102cRfdgZHHX7RKtiBY2ifmtN_wIMyMYG/view)


## Features

- **Theater Searching**: Allows users to find theaters and showtimes, streamlining the process of planning a movie night out.
- **Age Groups & Suggestions**: Curates movie suggestions based on age group preferences, enhancing personalization. This also restricts user's age group & chat with logic based on their date of birth.
- **Movie Reviews**: Users can read and write reviews, fostering a community of movie lovers.
- **Real-time Interaction**: Implemented WebSocket technology for real-time user interactions within the app.
- **RESTful APIs**: Utilized REST APIs for efficient backend data handling and integration.
- **Admin Features**: The app features in-depth Admin features, such as muting/unmuting users in chat, resetting their encrypted password, deleting a user's account and having their own websocket help-chat for all users to get help. 

## Tech Stack

### Backend Development:

- **Spring Boot & Maven**: Used Spring Boot and Maven for robust backend development, enabling scalable and maintainable code.
- **MySQL**: Integrated MySQL for designing a complex database schema capable of handling extensive data related to movies and user interactions.
- **Git & GitLab**: Managed version control and team collaboration through Git, with CI/CD pipelines set up on GitLab to streamline deployment processes.

### Real-time Features:

- **WebSockets**: Incorporated WebSocket technology to enable live interactions, such as chat and notifications.
- **Different Chats**: Every user is assigned a specific age group upon registering. This age group corresponds to movies that can be suggested/shown to them based on their age (PG/PG13/R). These age groups are also ONLY able to chat with each other to prevent any shenanigans.

### Testing:

- **Quality Assurance**: Conducted thorough testing to ensure a smooth user experience and reliable performance.

## Project Structure

- **GitLab Collaboration**: Worked collaboratively using GitLab for version control and project management, ensuring a seamless team development environment.
- **GitHub**: The final codebase and documentation are hosted on GitHub for public access and version tracking.

## Documentation

- Additional documentation, including system architecture diagrams and UI/UX design sketches, can be found in the `Documents` directory within the GitHub repository.




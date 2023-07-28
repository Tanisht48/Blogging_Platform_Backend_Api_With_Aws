
<h1 align = "center"> Blogging Platform</h1>

<p align="center">
<a href="Java url">
    <img alt="Java" src="https://img.shields.io/badge/Java->=8-darkblue.svg" />
</a>
<a href="Maven url" >
    <img alt="Maven" src="https://img.shields.io/badge/maven-3.8.1-brightgreen.svg" />
</a>
<a href="Spring Boot url" >
    <img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-3.1.1-brightgreen.svg" />
</a>

<a >
    <img alt="MySQL" src="https://img.shields.io/badge/MySQL-blue.svg">
</a>
</p>

Blogging Platform is a backend project that aims to provide a robust and scalable platform for users to create and manage blog posts, comment on posts, follow other users, and interact with the blogging community. The platform is designed to offer a seamless user experience and allow bloggers to showcase their writing skills and engage with a wider audience.

---
<br>

##  Framework Used
* Spring Boot

---
<br>

## Dependencies
The following dependencies are required to run the project:

* Spring Boot Dev Tools
* Spring Web
* Spring Data JPA
* MySQL Driver
* Lombok
* Validation
## Features

- User Registration and Authentication: Users can sign up, log in, and log out securely using email and password credentials.

- Create and Manage Posts: Authenticated users can create new blog posts, edit their existing posts, and delete their posts.

- Comment on Posts: Authenticated users can add comments to blog posts to share their thoughts and feedback.

- Follow Other Users: Users can follow other bloggers to receive updates and notifications about their new posts.

- Pagination: Posts and comments are paginated to enhance the user experience.

- User-Friendly API: The project provides a well-documented and user-friendly API for easy integration with front-end applications.

## Data Flow

1. The user at client side sends a request to the application through the API endpoints.
2. The API receives the request and sends it to the appropriate controller method.
3. The controller method makes a call to the method in service class.
4. The method in service class builds logic and retrieves or modifies data from the database, which is in turn given to controller class
5. The controller method returns a response to the API.
6. The API sends the response back to the user.


## Installation

1. Clone the repository from [GitHub link](https://github.com/Tanisht48/Blogging_Platform_Backend_Api_With_Aws/).
2. Install Java JDK and Maven on your machine.
3. Set up a MySQL database and configure the database connection in the application.properties file.
4. Run the Maven build to compile the project.
5. Start the application using the command.

## Usage

1. After starting the application, access the API documentation at [http://localhost:8080/swagger-ui.html](http://:8080/swagger-ui/index.html#/) for information on available endpoints and how to interact with the API.

2. Use tools like Postman or any API client to test the various API endpoints.


## Data Model
* Blogger
```
     Integer bloggerId;
     String bloggerName;
     String bloggerHandle;
     String bloggerBio;
     String bloggerEmail;
     String bloggerPassword;
     LocalDateTime bloggerAccountCreationDateTime;
     private Integer followerCount;
     private Integer followingCount;
     private Integer blogCount;
```
* Blog
```

    Integer blogId;
    BlogType blogType;
    BlogContentType blogContentType;
    String blogContent;
    private LocalDateTime blogCreatedTimeStamp;
    private Blogger blogOwner;
```
* Comment
```
     Integer commentId;
     String commentBody;
     LocalDateTime commentCreationTimeStamp;
     private Blog blogPost;
     private Blogger commenter;
```
* Follow
```
    Integer followId;
    Blogger currentBlogger;
    Blogger currentBloggerFollower;

```
* Authentication Token
```
    Long tokenId;
    String tokenValue;
    LocalDateTime tokenCreationDateTime;
    Blogger blogger;
```
## ApiEndPoints
```
    @PostMapping("SignUp")
    @PostMapping("SignIn")
    @PostMapping("blog")
    @PostMapping("blogs")
    @PostMapping("follow")
    @PostMapping("comment/Blog")
    @GetMapping("bloggers")
    @GetMapping("blogger/bloggerHandle")
    @GetMapping("blogs")
    @GetMapping("/MyProfile")
    @GetMapping("myBlogs")
    @GetMapping("blog/{blogId}/comments")
    @PutMapping("/blog/{blogId}")
    @DeleteMapping("blogger/signOut")
    @DeleteMapping("comment")
    @DeleteMapping("Blog/{blogId}")
  
```
* Swagger
```
    http://<Public Ip>:8080/swagger-ui/index.html#/
```
<br>

## DataBase Used
* SQL database
```
We have used Persistent database to implement CRUD Operations.

```
---
<br>

## Deployed
* AWS Ec2 Instance
```
We have used two Ec2 instance for Both Spring Boot Application and Mysql Database 
```

## Contributing

We welcome contributions from the community.


## Contact

For any questions or feedback related to this project, please reach out to [Me](tanishtgupta42@gmail.com).

    
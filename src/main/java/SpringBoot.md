# Spring Boot - High-Level Guide

> **For Experienced Developers**: This guide provides architectural overviews and deep-dive explanations of Spring Boot core concepts.

---

## Table of Contents

1. [IOC Container & Application Context](#1-ioc-container--application-context)
2. [Spring Data JPA](#2-spring-data-jpa)
3. [Spring Web (REST API)](#3-spring-web-rest-api)
4. [Spring Security](#4-spring-security)
5. [Spring Boot Internals](#5-spring-boot-internals)
6. [Microservices with Spring Cloud](#6-microservices-with-spring-cloud)
7. [Performance & Best Practices](#7-performance--best-practices)

---

# 1. IOC Container & Application Context

## 1.1 Overview

The **Inversion of Control (IoC) Container** is the heart of Spring Framework. It manages object creation, dependency injection, and the complete lifecycle of beans.

### Key Components:
- **ApplicationContext**: The central interface (extends BeanFactory)
- **BeanFactory**: Core container providing basic DI features
- **BeanDefinition**: Metadata about how to create beans
- **Bean Lifecycle Callbacks**: Initialization and destruction hooks

---

## 1.2 Starting Spring Boot from Zero

### Boot Sequence

```
Application Startup Flow
========================

1. main() method
   └─> SpringApplication.run(MainClass.class, args)
       │
       ├─> Create SpringApplication instance
       │   └─> Detect application type (Web/Reactive/None)
       │
       ├─> Prepare Environment
       │   ├─> Load application.properties/yml
       │   ├─> Profile activation
       │   └─> Environment variables
       │
       ├─> Create ApplicationContext
       │   ├─> AnnotationConfigApplicationContext (non-web)
       │   ├─> AnnotationConfigServletWebServerApplicationContext (web)
       │   └─> AnnotationConfigReactiveWebServerApplicationContext (reactive)
       │
       ├─> Component Scanning
       │   ├─> @ComponentScan from @SpringBootApplication
       │   ├─> Scan base package + sub-packages
       │   └─> Register BeanDefinitions
       │
       ├─> Auto-Configuration
       │   ├─> Load META-INF/spring.factories
       │   ├─> Process @Conditional annotations
       │   └─> Configure beans based on classpath
       │
       ├─> Bean Creation & DI
       │   ├─> Instantiate beans in order
       │   ├─> Resolve dependencies
       │   ├─> Inject dependencies
       │   └─> Call initialization callbacks
       │
       ├─> Start Embedded Server (if web app)
       │   ├─> Tomcat/Jetty/Undertow
       │   └─> Deploy DispatcherServlet
       │
       └─> Application Ready
           └─> Publish ApplicationReadyEvent
```

### Minimal Spring Boot Application

```java
// 1. Main Application Class
@SpringBootApplication  // Combines 3 annotations
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}

// @SpringBootApplication is equivalent to:
// @Configuration       - Marks class as configuration
// @EnableAutoConfiguration - Enable auto-config
// @ComponentScan       - Scan components in package
```

### What @SpringBootApplication Does

```
@SpringBootApplication Breakdown
================================

1. @Configuration
   └─> Marks class as source of bean definitions

2. @EnableAutoConfiguration
   ├─> Scans META-INF/spring.factories
   ├─> Conditional auto-configuration
   │   ├─> @ConditionalOnClass - if class present
   │   ├─> @ConditionalOnBean - if bean exists
   │   ├─> @ConditionalOnProperty - if property set
   │   └─> @ConditionalOnMissingBean - if bean missing
   │
   └─> Examples:
       ├─> DataSourceAutoConfiguration (if datasource on classpath)
       ├─> JpaAutoConfiguration (if JPA present)
       └─> SecurityAutoConfiguration (if security present)

3. @ComponentScan
   └─> Scans for @Component, @Service, @Repository, @Controller
```

---

## 1.3 IOC Container Architecture

### ApplicationContext Hierarchy

```
        ApplicationContext Interface
                   │
    ┌──────────────┼──────────────┐
    │              │              │
BeanFactory   MessageSource   ApplicationEventPublisher
    │              │              │
    │              │              │
    └──────────────┴──────────────┘
                   │
          ConfigurableApplicationContext
                   │
    ┌──────────────┼──────────────┐
    │                             │
AnnotationConfigApplicationContext  WebApplicationContext
(Standalone Apps)                   (Web Applications)
                                           │
                            AnnotationConfigServletWebServerApplicationContext
                            (Spring Boot Web Apps)
```

### Core IoC Operations

```java
// 1. Bean Definition Phase
@Component
public class UserService {
    @Autowired
    private UserRepository repository;
}

// 2. IoC Container Process
ApplicationContext → BeanDefinitionRegistry
                     ├─> Scan @Component classes
                     ├─> Parse @Bean methods
                     ├─> Process @Import
                     └─> Register BeanDefinition objects

// 3. Bean Creation Phase
BeanFactory → Instantiate beans
              ├─> Call constructor
              ├─> Resolve dependencies
              ├─> Inject via constructor/setter/field
              └─> Initialize bean

// 4. Post-Processing Phase
BeanPostProcessor → Modify/Enhance beans
                    ├─> @PostConstruct methods
                    ├─> InitializingBean.afterPropertiesSet()
                    └─> Custom init methods
```

---

## 1.4 Dependency Injection Mechanisms

### 1. Constructor Injection (Recommended)

```java
@Service
public class UserService {
    private final UserRepository repository;
    private final EmailService emailService;

    // Spring 4.3+: @Autowired optional for single constructor
    public UserService(UserRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }
}
```

**Why Constructor Injection?**
- Immutable dependencies (final fields)
- Makes dependencies explicit
- Easier to test
- Prevents circular dependencies at startup

### 2. Setter Injection

```java
@Service
public class UserService {
    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }
}
```

### 3. Field Injection (Not Recommended)

```java
@Service
public class UserService {
    @Autowired
    private UserRepository repository;  // Hard to test, breaks immutability
}
```

---

## 1.5 Bean Scopes

```
Bean Scopes in Spring
=====================

1. Singleton (Default)
   ├─> One instance per ApplicationContext
   ├─> Cached in container
   └─> Thread-safe for stateless beans

   @Component  // Default scope
   @Scope("singleton")
   public class UserService { }

2. Prototype
   ├─> New instance every time requested
   ├─> Not cached
   └─> Container doesn't manage destruction

   @Component
   @Scope("prototype")
   public class ReportGenerator { }

3. Request (Web only)
   ├─> One instance per HTTP request
   └─> Destroyed after request completes

   @Component
   @Scope(WebApplicationContext.SCOPE_REQUEST)
   public class ShoppingCart { }

4. Session (Web only)
   ├─> One instance per HTTP session
   └─> Destroyed when session expires

   @Component
   @Scope(WebApplicationContext.SCOPE_SESSION)
   public class UserPreferences { }

5. Application
   ├─> One instance per ServletContext
   └─> Similar to singleton but for ServletContext

6. WebSocket
   └─> One instance per WebSocket session
```

---

## 1.6 Bean Lifecycle

### Complete Lifecycle Flow

```
Bean Lifecycle Stages
=====================

1. Instantiation
   └─> Constructor called

2. Populate Properties
   └─> Dependency injection

3. BeanNameAware.setBeanName()
   └─> If bean implements BeanNameAware

4. BeanFactoryAware.setBeanFactory()
   └─> If bean implements BeanFactoryAware

5. ApplicationContextAware.setApplicationContext()
   └─> If bean implements ApplicationContextAware

6. BeanPostProcessor.postProcessBeforeInitialization()
   └─> Pre-initialization processing

7. @PostConstruct
   └─> Custom initialization method

8. InitializingBean.afterPropertiesSet()
   └─> If bean implements InitializingBean

9. Custom init-method
   └─> @Bean(initMethod="init")

10. BeanPostProcessor.postProcessAfterInitialization()
    └─> Post-initialization processing (AOP proxies created here)

11. Bean Ready to Use
    └─> Fully initialized bean

--- Container Shutdown ---

12. @PreDestroy
    └─> Custom cleanup method

13. DisposableBean.destroy()
    └─> If bean implements DisposableBean

14. Custom destroy-method
    └─> @Bean(destroyMethod="cleanup")
```

### Lifecycle Example

```java
@Component
public class DatabaseConnection implements InitializingBean, DisposableBean {

    private Connection connection;

    // 1. Constructor
    public DatabaseConnection() {
        System.out.println("1. Constructor called");
    }

    // 2. Dependency injection happens here

    // 3. @PostConstruct
    @PostConstruct
    public void init() {
        System.out.println("3. @PostConstruct - Opening connection");
        // Initialize connection
    }

    // 4. afterPropertiesSet (from InitializingBean)
    @Override
    public void afterPropertiesSet() {
        System.out.println("4. afterPropertiesSet called");
    }

    // Bean is now ready to use

    // Shutdown Phase

    // 5. @PreDestroy
    @PreDestroy
    public void cleanup() {
        System.out.println("5. @PreDestroy - Closing connection");
    }

    // 6. destroy (from DisposableBean)
    @Override
    public void destroy() {
        System.out.println("6. destroy called");
    }
}
```

---

## 1.7 Configuration Methods

### Java-Based Configuration

```java
@Configuration
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/mydb");
        ds.setUsername("root");
        ds.setPassword("password");
        return ds;
    }

    @Bean
    public UserRepository userRepository(DataSource dataSource) {
        return new UserRepositoryImpl(dataSource);
    }
}
```

### Property-Based Configuration

```yaml
# application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: root
    password: password
    hikari:
      maximum-pool-size: 10

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

```java
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String name;
    private String version;
    private Security security;

    public static class Security {
        private String secretKey;
        private int tokenExpiry;
    }
}
```

---

## 1.8 Component Stereotypes

```java
// @Component - Generic component
@Component
public class EmailValidator { }

// @Service - Business logic layer
@Service
public class UserService { }

// @Repository - Data access layer
// Adds automatic exception translation (SQLException → DataAccessException)
@Repository
public class UserRepository { }

// @Controller - Web MVC controller (returns views)
@Controller
public class HomeController { }

// @RestController - REST API controller (returns data)
@RestController  // = @Controller + @ResponseBody
public class UserApiController { }

// @Configuration - Java-based configuration
@Configuration
public class DatabaseConfig { }
```

---

# 2. Spring Data JPA

## 2.1 Overview

Spring Data JPA simplifies database access by providing:
- Repository abstraction
- Query derivation from method names
- Custom query support
- Transaction management
- Auditing support

### Architecture

```
Application Layer
       ↓
Service Layer (@Service)
       ↓
Repository Interface (extends JpaRepository)
       ↓
Spring Data JPA Implementation (Auto-generated)
       ↓
JPA Provider (Hibernate)
       ↓
JDBC Driver
       ↓
Database
```

---

## 2.2 Entity Mapping

### Basic Entity

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
```

---

## 2.3 Relationships

### One-to-Many & Many-to-One

```java
// One User has Many Posts
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    // Helper method
    public void addPost(Post post) {
        posts.add(post);
        post.setAuthor(this);
    }
}

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;
}
```

### Many-to-Many

```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();
}

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();
}
```

---

## 2.4 Repository Interface

### Built-in Methods

```java
public interface UserRepository extends JpaRepository<User, Long> {
    // Inherited methods:
    // - save(entity)
    // - findById(id)
    // - findAll()
    // - deleteById(id)
    // - count()
    // - existsById(id)
}
```

### Query Derivation

```java
public interface UserRepository extends JpaRepository<User, Long> {

    // Derived queries (Spring generates implementation)
    User findByUsername(String username);

    List<User> findByEmailContaining(String email);

    List<User> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<User> findByUsernameAndEmail(String username, String email);

    List<User> findByUsernameOrEmail(String username, String email);

    // With pagination
    Page<User> findByEmailContaining(String email, Pageable pageable);

    // Count queries
    long countByEmailContaining(String email);

    // Existence queries
    boolean existsByUsername(String username);

    // Delete queries
    void deleteByUsername(String username);
}
```

### Custom Queries

```java
public interface UserRepository extends JpaRepository<User, Long> {

    // JPQL Query
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmailJPQL(@Param("email") String email);

    // Native SQL Query
    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    User findByEmailNative(@Param("email") String email);

    // Update Query
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.email = :email WHERE u.id = :id")
    int updateUserEmail(@Param("id") Long id, @Param("email") String email);

    // Complex Join Query
    @Query("SELECT u FROM User u JOIN u.posts p WHERE p.title LIKE %:keyword%")
    List<User> findUsersWithPostsContaining(@Param("keyword") String keyword);
}
```

---

## 2.5 Transaction Management

### @Transactional Annotation

```java
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    // Transaction boundaries
    @Transactional
    public User createUser(User user) {
        User saved = userRepository.save(user);
        emailService.sendWelcomeEmail(user.getEmail());
        return saved;
        // Transaction commits here if no exception
        // Rolls back if exception thrown
    }

    // Read-only optimization
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    // Custom transaction attributes
    @Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED,
        timeout = 30,
        rollbackFor = Exception.class
    )
    public void complexOperation() {
        // ...
    }
}
```

### Transaction Propagation

```
Propagation Types
=================

1. REQUIRED (Default)
   ├─> Use existing transaction
   └─> Create new if none exists

2. REQUIRES_NEW
   ├─> Always create new transaction
   └─> Suspend existing transaction

3. MANDATORY
   ├─> Must have existing transaction
   └─> Throw exception if none exists

4. SUPPORTS
   ├─> Use existing transaction if available
   └─> Execute non-transactionally if none

5. NOT_SUPPORTED
   ├─> Execute non-transactionally
   └─> Suspend existing transaction

6. NEVER
   ├─> Execute non-transactionally
   └─> Throw exception if transaction exists

7. NESTED
   └─> Create nested transaction (savepoint)
```

---

## 2.6 Solving N+1 Query Problem

### The Problem

```java
// This causes N+1 queries!
@GetMapping("/users")
public List<UserDTO> getAllUsers() {
    List<User> users = userRepository.findAll();  // 1 query

    return users.stream()
        .map(user -> new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getPosts().size()  // N queries (one per user)
        ))
        .collect(Collectors.toList());
}
```

### Solution 1: JOIN FETCH

```java
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.posts")
    List<User> findAllWithPosts();
}
```

### Solution 2: @EntityGraph

```java
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"posts"})
    List<User> findAll();

    @EntityGraph(attributePaths = {"posts", "posts.comments"})
    User findById(Long id);
}
```

### Solution 3: Batch Fetching

```java
@Entity
public class User {
    @OneToMany(mappedBy = "author")
    @BatchSize(size = 10)  // Fetch in batches of 10
    private List<Post> posts;
}
```

---

## 2.7 Pagination and Sorting

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Page<User> getUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return userRepository.findAll(pageable);
    }
}
```

---

# 3. Spring Web (REST API)

## 3.1 Overview

Spring Web MVC provides the infrastructure for building web applications and REST APIs. At its core is the **DispatcherServlet**, which acts as the Front Controller.

---

## 3.2 Complete Request Flow (Client to Server)

### High-Level Flow

```
Client Request → Web Server → DispatcherServlet → Controller → Service → Repository → Database
                                                       ↓
                                                  View/JSON Response
```

### Detailed Flow with Components

```
┌─────────────────────────────────────────────────────────────────┐
│                         CLIENT                                   │
│                 (Browser/Mobile/Postman)                         │
└────────────────────────────┬────────────────────────────────────┘
                             │
                    HTTP Request
                    GET /api/users/123
                    Headers: Accept: application/json
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                    EMBEDDED TOMCAT                               │
│                  (Servlet Container)                             │
│                                                                  │
│  ┌────────────────────────────────────────────────────┐        │
│  │            DispatcherServlet                        │        │
│  │         (Front Controller Pattern)                  │        │
│  └───────────────────────┬────────────────────────────┘        │
│                          │                                       │
│           ┌──────────────┴──────────────┐                       │
│           │                             │                       │
│    ┌──────▼──────┐              ┌──────▼──────┐               │
│    │   Filters   │              │ Interceptors │               │
│    │             │              │              │               │
│    │- Security   │              │- Pre-handle  │               │
│    │- CORS       │              │- Post-handle │               │
│    │- Logging    │              │- After-comp  │               │
│    └──────┬──────┘              └──────┬───────┘               │
│           │                            │                        │
│           └────────────┬───────────────┘                        │
│                        │                                        │
│              ┌─────────▼──────────┐                            │
│              │  Handler Mapping    │                            │
│              │                     │                            │
│              │ Maps URL pattern to │                            │
│              │ Controller method   │                            │
│              │                     │                            │
│              │ /api/users/{id}    │                            │
│              │    ↓                │                            │
│              │ UserController      │                            │
│              │ .getUserById()     │                            │
│              └─────────┬───────────┘                            │
│                        │                                        │
│              ┌─────────▼──────────┐                            │
│              │  Handler Adapter    │                            │
│              │                     │                            │
│              │ Invokes controller  │                            │
│              │ Resolves parameters │                            │
│              │ (@PathVariable, etc)│                            │
│              └─────────┬───────────┘                            │
└────────────────────────┼────────────────────────────────────────┘
                         │
              ┌──────────▼───────────┐
              │   @RestController     │
              │   UserController      │
              │                       │
              │   getUserById(123)    │
              └──────────┬────────────┘
                         │
              ┌──────────▼───────────┐
              │      @Service         │
              │     UserService       │
              │                       │
              │   getUserById(123)    │
              └──────────┬────────────┘
                         │
              ┌──────────▼───────────┐
              │    @Repository        │
              │   UserRepository      │
              │   extends JpaRepository│
              │                       │
              │   findById(123)       │
              └──────────┬────────────┘
                         │
              ┌──────────▼───────────┐
              │    Hibernate/JPA      │
              │                       │
              │  SELECT * FROM users  │
              │  WHERE id = 123       │
              └──────────┬────────────┘
                         │
              ┌──────────▼───────────┐
              │      Database         │
              │       (MySQL)         │
              └──────────┬────────────┘
                         │
                    User Object
                         │
┌────────────────────────┼────────────────────────────────────────┐
│                RESPONSE PROCESSING                               │
│                        │                                         │
│              ┌─────────▼──────────┐                             │
│              │ HttpMessageConverter│                             │
│              │                     │                             │
│              │ Converts Java Object│                             │
│              │ to JSON             │                             │
│              │                     │                             │
│              │ User → JSON         │                             │
│              └─────────┬───────────┘                             │
│                        │                                         │
│              HTTP Response                                       │
│              200 OK                                              │
│              Content-Type: application/json                      │
│              {                                                   │
│                "id": 123,                                        │
│                "name": "John",                                   │
│                "email": "john@example.com"                       │
│              }                                                   │
└─────────────────────────┬───────────────────────────────────────┘
                          │
                          ▼
                      CLIENT
```

---

## 3.3 DispatcherServlet Deep Dive

### Initialization

```java
// Spring Boot auto-configures DispatcherServlet
@Configuration
@AutoConfigureAfter(ServletWebServerFactoryAutoConfiguration.class)
public class DispatcherServletAutoConfiguration {

    @Bean
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet servlet = new DispatcherServlet();
        // Configured to handle "/"
        return servlet;
    }
}
```

### Request Processing Steps

```
DispatcherServlet.doDispatch() method:
=======================================

1. getHandler()
   └─> Find HandlerExecutionChain for this request
       ├─> RequestMappingHandlerMapping (for @RequestMapping)
       ├─> BeanNameUrlHandlerMapping
       └─> SimpleUrlHandlerMapping

2. getHandlerAdapter()
   └─> Find adapter that can invoke the handler
       ├─> RequestMappingHandlerAdapter (for @Controller)
       ├─> HttpRequestHandlerAdapter
       └─> SimpleControllerHandlerAdapter

3. handler.handle()
   └─> Invoke the actual controller method
       ├─> Resolve method arguments
       ├─> Execute method
       └─> Return ModelAndView or ResponseEntity

4. processDispatchResult()
   └─> Render view or convert to JSON
       ├─> ViewResolver (for @Controller)
       └─> HttpMessageConverter (for @RestController)
```

---

## 3.4 Building REST APIs

### REST Controller Structure

```java
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    // Constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET all users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserDTO> users = userService.getAllUsers(PageRequest.of(page, size));
        return ResponseEntity.ok(users.getContent());
    }

    // GET user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // POST create user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDTO created = userService.createUser(request);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();
        return ResponseEntity.created(location).body(created);
    }

    // PUT update user
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
        @PathVariable Long id,
        @Valid @RequestBody UpdateUserRequest request
    ) {
        UserDTO updated = userService.updateUser(id, request);
        return ResponseEntity.ok(updated);
    }

    // DELETE user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## 3.5 Request/Response Handling

### Request Binding

```java
@RestController
@RequestMapping("/api")
public class DemoController {

    // Path Variable
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) { }

    // Request Parameter
    @GetMapping("/search")
    public List<User> search(@RequestParam String query) { }

    // Request Body
    @PostMapping("/users")
    public User createUser(@RequestBody User user) { }

    // Request Header
    @GetMapping("/info")
    public String getInfo(@RequestHeader("User-Agent") String userAgent) { }

    // Multiple Path Variables
    @GetMapping("/users/{userId}/posts/{postId}")
    public Post getPost(
        @PathVariable Long userId,
        @PathVariable Long postId
    ) { }

    // Optional Parameters
    @GetMapping("/filter")
    public List<User> filter(
        @RequestParam Optional<String> name,
        @RequestParam(required = false) String email
    ) { }
}
```

### Response Types

```java
@RestController
public class ResponseController {

    // 1. Direct object return (200 OK)
    @GetMapping("/user")
    public User getUser() {
        return new User("John");
    }

    // 2. ResponseEntity with status
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // 3. Custom headers
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile() {
        byte[] content = ...;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "file.pdf");
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    // 4. Created resource (201 Created)
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.save(user);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();
        return ResponseEntity.created(location).body(created);
    }
}
```

---

## 3.6 Exception Handling

### Global Exception Handler

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle specific exception
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
        UserNotFoundException ex,
        WebRequest request
    ) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
        MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
        Exception ex,
        WebRequest request
    ) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal server error",
            request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@Data
@AllArgsConstructor
class ErrorResponse {
    private int status;
    private String message;
    private String path;
    private LocalDateTime timestamp = LocalDateTime.now();
}
```

---

## 3.7 Request Validation

```java
// DTO with validation
public class CreateUserRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Min(18)
    @Max(100)
    private Integer age;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String phone;
}

// Controller with validation
@PostMapping("/users")
public ResponseEntity<UserDTO> createUser(
    @Valid @RequestBody CreateUserRequest request  // @Valid triggers validation
) {
    UserDTO created = userService.createUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
}
```

---

## 3.8 Content Negotiation

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    // Produces JSON (default)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserJson() {
        return new User("John");
    }

    // Produces XML
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public User getUserXml() {
        return new User("John");
    }

    // Consumes JSON
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    // Multiple content types
    @GetMapping(produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE
    })
    public User getUser() {
        // Spring chooses based on Accept header
        return new User("John");
    }
}
```

---

## 3.9 Filters vs Interceptors

### Filter (Servlet Level)

```java
@Component
@Order(1)
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Before request processing
        log.info("Request: {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());

        chain.doFilter(request, response);

        // After request processing
        log.info("Response sent");
    }
}
```

### Interceptor (Spring MVC Level)

```java
@Component
public class AuthInterceptor implements HandlerInterceptor {

    // Before controller method
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;  // Stop processing
        }
        return true;  // Continue to controller
    }

    // After controller method, before view rendering
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                          Object handler, ModelAndView modelAndView) {
        // Modify model or view
    }

    // After view rendering
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                               Object handler, Exception ex) {
        // Cleanup resources
    }
}

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/**");
    }
}
```

### Filter vs Interceptor

```
Request Flow:
=============

Filter → DispatcherServlet → Interceptor.preHandle() → Controller →
Interceptor.postHandle() → View → Interceptor.afterCompletion() → Filter

Key Differences:
================

Filter:
├─> Servlet specification
├─> Works with all web frameworks
├─> Access to raw ServletRequest/Response
├─> Cannot access Spring beans easily
└─> Use for: CORS, compression, encryption

Interceptor:
├─> Spring MVC specific
├─> Access to Spring context
├─> Access to Handler (controller method)
├─> Can modify ModelAndView
└─> Use for: authentication, logging, auditing
```

---

# 4. Spring Security

## 4.1 Overview

Spring Security provides authentication, authorization, and protection against common attacks.

### Core Concepts

```
Security Flow:
==============

Request → SecurityFilterChain → AuthenticationManager →
ProviderManager → AuthenticationProvider →
UserDetailsService → Authentication → Success/Failure
```

---

## 4.2 Security Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    HTTP Request                          │
└───────────────────────┬─────────────────────────────────┘
                        │
┌───────────────────────▼─────────────────────────────────┐
│              Security Filter Chain                       │
│                                                          │
│  ┌────────────────────────────────────────────────┐   │
│  │ 1. UsernamePasswordAuthenticationFilter        │   │
│  │    (Handles /login POST)                       │   │
│  └────────────────────┬───────────────────────────┘   │
│                       │                                 │
│  ┌────────────────────▼───────────────────────────┐   │
│  │ 2. BasicAuthenticationFilter                   │   │
│  │    (Handles Basic Auth)                        │   │
│  └────────────────────┬───────────────────────────┘   │
│                       │                                 │
│  ┌────────────────────▼───────────────────────────┐   │
│  │ 3. JwtAuthenticationFilter                     │   │
│  │    (Custom JWT validation)                     │   │
│  └────────────────────┬───────────────────────────┘   │
│                       │                                 │
│  ┌────────────────────▼───────────────────────────┐   │
│  │ 4. FilterSecurityInterceptor                   │   │
│  │    (Authorization checks)                      │   │
│  └────────────────────┬───────────────────────────┘   │
│                       │                                 │
│  ┌────────────────────▼───────────────────────────┐   │
│  │ 5. ExceptionTranslationFilter                  │   │
│  │    (Handle security exceptions)                │   │
│  └────────────────────────────────────────────────┘   │
└──────────────────────────────────────────────────────────┘
                        │
                        ▼
                DispatcherServlet
```

---

## 4.3 Basic Configuration

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable for REST APIs
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

---

## 4.4 JWT Authentication

### JWT Filter

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getJwtFromRequest(request);

        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            String username = tokenProvider.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
```

### JWT Token Provider

```java
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
```

---

## 4.5 Method-Level Security

```java
@Configuration
@EnableMethodSecurity
public class MethodSecurityConfig {
}

@Service
public class UserService {

    // Only ADMIN can call this
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // User can only access their own data
    @PreAuthorize("#username == authentication.principal.username")
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Check after method execution
    @PostAuthorize("returnObject.username == authentication.principal.username")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    // Role-based
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public void moderateContent() {
        // ...
    }
}
```

---

# 5. Spring Boot Internals

## 5.1 Auto-Configuration

### How It Works

```
Auto-Configuration Process:
===========================

1. @SpringBootApplication contains @EnableAutoConfiguration

2. @EnableAutoConfiguration imports AutoConfigurationImportSelector

3. Selector reads META-INF/spring.factories from all JARs

4. Finds all AutoConfiguration classes:
   ├─> DataSourceAutoConfiguration
   ├─> JpaRepositoriesAutoConfiguration
   ├─> SecurityAutoConfiguration
   └─> ... hundreds more

5. Each @Configuration class has @Conditional annotations:
   @ConditionalOnClass - Only if class present in classpath
   @ConditionalOnBean - Only if bean exists
   @ConditionalOnProperty - Only if property set
   @ConditionalOnMissingBean - Only if bean doesn't exist

6. Spring evaluates conditions and loads matching configurations
```

### Example Auto-Configuration

```java
@Configuration
@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
@ConditionalOnMissingBean(DataSource.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "spring.datasource", name = "url")
    public DataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }
}
```

---

## 5.2 Embedded Server

### Tomcat Startup

```
Embedded Tomcat Initialization:
================================

1. ServletWebServerApplicationContext created

2. ServletWebServerFactory bean detected
   └─> TomcatServletWebServerFactory (default)

3. getWebServer() called
   ├─> Create Tomcat instance
   ├─> Configure connector (port, protocol)
   ├─> Add context (webapp context)
   └─> Register DispatcherServlet

4. Start Tomcat
   └─> Tomcat listens on configured port (8080 default)

5. Application ready
   └─> Log: "Tomcat started on port(s): 8080 (http)"
```

### Configuration

```yaml
server:
  port: 8080
  servlet:
    context-path: /api
  tomcat:
    threads:
      max: 200
      min-spare: 10
    connection-timeout: 20000
    max-connections: 8192
```

---

## 5.3 Multi-Threading Model

```
Request Handling Model:
=======================

Tomcat Thread Pool (default: 200 threads)
    ├─> Thread 1 → Request A → DispatcherServlet → Controller → Service → Repository
    ├─> Thread 2 → Request B → DispatcherServlet → Controller → Service → Repository
    ├─> Thread 3 → Request C → DispatcherServlet → Controller → Service → Repository
    └─> ...

Key Points:
-----------
1. One thread per request (thread-per-request model)
2. Thread is blocked during entire request processing
3. Singleton beans must be thread-safe
4. Request-scoped beans are thread-local
5. @Transactional uses ThreadLocal for transaction context
```

### Thread Safety

```java
// Thread-safe (Singleton, stateless)
@Service
public class UserService {
    private final UserRepository repository;  // Safe: immutable reference

    public User getUser(Long id) {
        return repository.findById(id).orElseThrow();
    }
}

// NOT thread-safe (Singleton with mutable state)
@Service
public class CounterService {
    private int count = 0;  // DANGER: shared mutable state

    public void increment() {
        count++;  // Race condition!
    }
}

// Fixed with AtomicInteger
@Service
public class CounterService {
    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet();  // Thread-safe
    }
}
```

---

# 6. Microservices with Spring Cloud

## 6.1 Service Discovery (Eureka)

### Eureka Server

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

```yaml
# Eureka Server application.yml
server:
  port: 8761

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

### Eureka Client

```java
@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
```

```yaml
# Eureka Client application.yml
spring:
  application:
    name: user-service

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

---

## 6.2 API Gateway

```java
@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
```

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/api/users/**
          filters:
            - StripPrefix=1

        - id: order-service
          uri: lb://order-service
          predicates:
            - Path=/api/orders/**
          filters:
            - StripPrefix=1
```

---

## 6.3 Circuit Breaker (Resilience4j)

```java
@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "userService", fallbackMethod = "getUserFallback")
    public User getUser(Long userId) {
        return restTemplate.getForObject(
            "http://user-service/users/" + userId,
            User.class
        );
    }

    public User getUserFallback(Long userId, Exception ex) {
        return new User(userId, "Unknown User");
    }
}
```

```yaml
resilience4j:
  circuitbreaker:
    instances:
      userService:
        sliding-window-size: 10
        failure-rate-threshold: 50
        wait-duration-in-open-state: 10000
        permitted-number-of-calls-in-half-open-state: 3
```

---

## 6.4 Distributed Tracing

```yaml
# Sleuth + Zipkin configuration
spring:
  zipkin:
    base-url: http://localhost:9411
  sleuth:
    sampler:
      probability: 1.0  # Sample 100% of requests
```

---

# 7. Performance & Best Practices

## 7.1 Performance Optimization

### Database Optimization

```java
// 1. Use pagination
Page<User> users = userRepository.findAll(PageRequest.of(0, 20));

// 2. Use projections
interface UserSummary {
    String getUsername();
    String getEmail();
}
List<UserSummary> findAllBy();  // Only select needed columns

// 3. Use @EntityGraph to avoid N+1
@EntityGraph(attributePaths = {"posts", "comments"})
List<User> findAll();

// 4. Use batch operations
userRepository.saveAll(users);  // Batch insert

// 5. Use native queries for complex operations
@Query(value = "UPDATE users SET status = 'active' WHERE created_at > ?1", nativeQuery = true)
@Modifying
void activateRecentUsers(LocalDateTime since);
```

### Caching

```java
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("users", "products");
    }
}

@Service
public class UserService {

    @Cacheable("users")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @CachePut(value = "users", key = "#user.id")
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
```

### Async Processing

```java
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("async-");
        executor.initialize();
        return executor;
    }
}

@Service
public class EmailService {

    @Async
    public CompletableFuture<Void> sendEmail(String to, String subject, String body) {
        // Send email asynchronously
        return CompletableFuture.completedFuture(null);
    }
}
```

---

## 7.2 Best Practices

### Layered Architecture

```
Presentation Layer (@RestController)
    ↓
Service Layer (@Service)
    ↓
Repository Layer (@Repository)
    ↓
Database
```

### DTO Pattern

```java
// Entity (internal)
@Entity
public class User {
    @Id
    private Long id;
    private String username;
    private String password;  // Never expose!
}

// DTO (external)
public class UserDTO {
    private Long id;
    private String username;
    // No password field
}

// Mapper
@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername());
    }
}

// Controller
@RestController
public class UserController {

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return userMapper.toDTO(user);  // Never return entity directly
    }
}
```

### Exception Handling

```java
// Custom exceptions
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

// Global handler
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(ex.getMessage()));
    }
}
```

### Configuration Properties

```java
@ConfigurationProperties(prefix = "app")
@Validated
public class AppProperties {

    @NotBlank
    private String name;

    @Min(1000)
    @Max(9999)
    private int port;

    private Security security;

    public static class Security {
        @NotBlank
        private String jwtSecret;

        @Min(60000)
        private long jwtExpiration;
    }
}
```

---

## 7.3 Common Mistakes to Avoid

```java
// ❌ BAD: Calling repository from controller
@RestController
public class UserController {
    @Autowired
    private UserRepository repository;  // BAD!
}

// ✅ GOOD: Use service layer
@RestController
public class UserController {
    @Autowired
    private UserService service;  // GOOD!
}

// ❌ BAD: Returning entities
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    return userRepository.findById(id).orElseThrow();
}

// ✅ GOOD: Return DTOs
@GetMapping("/users/{id}")
public UserDTO getUser(@PathVariable Long id) {
    return userService.getUserById(id);  // Service returns DTO
}

// ❌ BAD: Not using @Transactional for write operations
public void updateUser(User user) {
    userRepository.save(user);
    emailService.sendEmail(user.getEmail());
}

// ✅ GOOD: Use @Transactional
@Transactional
public void updateUser(User user) {
    userRepository.save(user);
    emailService.sendEmail(user.getEmail());
    // Rolls back if email fails
}

// ❌ BAD: Hardcoded values
String url = "http://localhost:8080/api";

// ✅ GOOD: Use properties
@Value("${app.api.url}")
private String apiUrl;
```

---

## Quick Reference

### Common Annotations

| Annotation | Purpose | Layer |
|-----------|---------|-------|
| `@SpringBootApplication` | Main application class | Application |
| `@RestController` | REST API controller | Web |
| `@Service` | Business logic | Service |
| `@Repository` | Data access | Persistence |
| `@Entity` | JPA entity | Domain |
| `@Transactional` | Transaction boundary | Service |
| `@Autowired` | Dependency injection | Any |
| `@Configuration` | Java configuration | Configuration |
| `@ConfigurationProperties` | Bind properties | Configuration |

### HTTP Status Codes

| Code | Meaning | Usage |
|------|---------|-------|
| 200 | OK | Successful GET, PUT |
| 201 | Created | Successful POST |
| 204 | No Content | Successful DELETE |
| 400 | Bad Request | Validation error |
| 401 | Unauthorized | Authentication required |
| 403 | Forbidden | No permission |
| 404 | Not Found | Resource not found |
| 500 | Internal Server Error | Server error |

---

---

# 8. How a Microservice Project Works — Complete Architecture

## 8.1 Monolith vs Microservices

```
MONOLITH (Single Application)              MICROSERVICES (Multiple Services)
┌─────────────────────────┐               ┌──────────┐  ┌──────────┐  ┌──────────┐
│     ONE BIG APP         │               │  User    │  │  Order   │  │  Payment │
│  ┌───────┐ ┌──────────┐│               │  Service │  │  Service │  │  Service │
│  │ User  │ │  Order   ││               │  (8081)  │  │  (8082)  │  │  (8083)  │
│  │Module │ │  Module  ││               └────┬─────┘  └────┬─────┘  └────┬─────┘
│  ├───────┤ ├──────────┤│                    │             │             │
│  │Payment│ │Inventory ││               ┌────┴─────────────┴─────────────┴────┐
│  │Module │ │  Module  ││               │           Message Broker (Kafka)    │
│  └───────┘ └──────────┘│               └─────────────────────────────────────┘
│     ONE Database        │
│     ONE Deployment      │               Each service has its OWN database
└─────────────────────────┘               Each service deploys INDEPENDENTLY
```

| Aspect | Monolith | Microservices |
|--------|----------|---------------|
| Deployment | All-or-nothing | Deploy services independently |
| Scaling | Scale entire app | Scale individual services |
| Tech Stack | One stack for all | Each service can use different tech |
| Database | Single shared DB | Database per service |
| Failure | One bug can crash everything | Failure isolated to one service |
| Team | One large team | Small teams own individual services |
| Complexity | Simple to start | Complex infrastructure needed |

---

## 8.2 Complete Microservice Architecture

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        CLIENT (Browser / Mobile)                         │
└─────────────────────────┬───────────────────────────────────────────────┘
                          │ HTTPS Request
                          ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                        API GATEWAY (Spring Cloud Gateway)                │
│  - Single entry point for all requests                                   │
│  - Routes requests to correct service                                    │
│  - Authentication/Authorization (JWT validation)                         │
│  - Rate limiting, Load balancing                                         │
│  - Port: 8080                                                            │
└────────┬──────────────────┬──────────────────┬──────────────────────────┘
         │                  │                  │
         ▼                  ▼                  ▼
┌────────────────┐ ┌────────────────┐ ┌────────────────┐
│  USER SERVICE  │ │ ORDER SERVICE  │ │PAYMENT SERVICE │
│  Port: 8081    │ │  Port: 8082    │ │  Port: 8083    │
│                │ │                │ │                │
│  - Register    │ │  - Place order │ │  - Process pay │
│  - Login       │ │  - Track order │ │  - Refunds     │
│  - Profile     │ │  - Cancel      │ │  - History     │
│                │ │                │ │                │
│  ┌──────────┐  │ │  ┌──────────┐  │ │  ┌──────────┐  │
│  │ User DB  │  │ │  │ Order DB │  │ │  │Payment DB│  │
│  │ (MySQL)  │  │ │  │(Postgres)│  │ │  │ (MySQL)  │  │
│  └──────────┘  │ │  └──────────┘  │ │  └──────────┘  │
└───────┬────────┘ └───────┬────────┘ └───────┬────────┘
        │                  │                  │
        └──────────────────┼──────────────────┘
                           │
              ┌────────────┴────────────┐
              │   SERVICE DISCOVERY     │
              │   (Eureka / Consul)     │
              │   Port: 8761            │
              │                         │
              │   Keeps track of all    │
              │   running services      │
              │   and their locations   │
              └────────────┬────────────┘
                           │
              ┌────────────┴────────────┐
              │    CONFIG SERVER        │
              │   (Spring Cloud Config) │
              │   Port: 8888            │
              │                         │
              │   Centralized config    │
              │   for all services      │
              └─────────────────────────┘
```

---

## 8.3 Why Each Service Is Needed

### API Gateway — Why?

**Problem without Gateway:**
```
Client must know every service URL:
  - http://192.168.1.10:8081/users       (User Service)
  - http://192.168.1.11:8082/orders      (Order Service)
  - http://192.168.1.12:8083/payments    (Payment Service)

What if service moves? Client breaks!
What about authentication? Each service must handle it!
What about rate limiting? Each service must implement it!
```

**Solution — API Gateway:**
```
Client only knows ONE URL: http://api.myapp.com

Gateway handles:
  /api/users/**    → routes to User Service
  /api/orders/**   → routes to Order Service
  /api/payments/** → routes to Payment Service
```

```yaml
# Gateway configuration (application.yml)
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://USER-SERVICE          # lb = load balanced via Eureka
          predicates:
            - Path=/api/users/**
          filters:
            - AuthFilter                  # Custom JWT validation filter

        - id: order-service
          uri: lb://ORDER-SERVICE
          predicates:
            - Path=/api/orders/**
```

**Gateway provides:**
- Single entry point (clients don't know internal URLs)
- Authentication/Authorization at the edge
- Rate limiting and throttling
- Request/Response logging
- Load balancing across service instances
- SSL termination

---

### Service Discovery (Eureka) — Why?

**Problem without Discovery:**
```
Order Service needs to call User Service.
User Service runs on 192.168.1.10:8081... or does it?

What if:
  - Service restarts on different port?
  - We scale to 3 instances?
  - Server IP changes?

Hardcoded URLs break!
```

**Solution — Service Discovery:**
```
Each service registers itself with Eureka on startup:
  "I am USER-SERVICE, running at 192.168.1.10:8081"
  "I am USER-SERVICE, running at 192.168.1.11:8081"  (2nd instance)
  "I am ORDER-SERVICE, running at 192.168.1.12:8082"

When Order Service needs User Service:
  1. Asks Eureka: "Where is USER-SERVICE?"
  2. Eureka returns: ["192.168.1.10:8081", "192.168.1.11:8081"]
  3. Load balancer picks one
```

```java
// No hardcoded URLs — use service name!
@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/users/{id}")
    UserDTO getUserById(@PathVariable Long id);
}
```

---

### Config Server — Why?

**Problem without Config Server:**
```
50 microservices × 3 environments (dev, staging, prod) = 150 config files!

Changing database password?
  → Update 50 application.properties files
  → Rebuild and redeploy 50 services
```

**Solution — Centralized Config:**
```
All configs stored in ONE place (Git repo or Vault):
  config-repo/
    ├── user-service-dev.yml
    ├── user-service-prod.yml
    ├── order-service-dev.yml
    └── order-service-prod.yml

Services fetch their config from Config Server on startup.
Change password? Update ONE file → all services pick it up.
```

---

## 8.4 JPA vs Hibernate — What and Why

```
┌─────────────────────────────────────────────────────────────────────┐
│                    JPA vs HIBERNATE                                   │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  JPA (Jakarta Persistence API)                                       │
│  ─────────────────────────────                                       │
│  - A SPECIFICATION (set of rules/interfaces)                         │
│  - Defines HOW ORM should work in Java                               │
│  - Like a contract: "Any ORM must support these features"            │
│  - Annotations: @Entity, @Table, @Id, @Column, @OneToMany           │
│  - Cannot run by itself — needs an implementation                    │
│                                                                      │
│  Hibernate                                                           │
│  ─────────                                                           │
│  - An IMPLEMENTATION of JPA                                          │
│  - Actually does the ORM work (converts objects ↔ SQL)               │
│  - Most popular JPA provider                                         │
│  - Has extra features beyond JPA spec                                │
│                                                                      │
│  Analogy:                                                            │
│  JPA = JDBC interface (rules)                                        │
│  Hibernate = MySQL Driver (implementation)                           │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

| Aspect | JPA | Hibernate |
|--------|-----|-----------|
| **What** | Specification (interfaces + annotations) | Implementation (actual code) |
| **Type** | API / Standard | ORM Framework |
| **Can run alone?** | No — needs provider | Yes |
| **Defined by** | Jakarta EE (formerly Java EE) | Red Hat / JBoss |
| **Key classes** | `EntityManager`, `@Entity` | `Session`, `SessionFactory` |
| **Query** | JPQL (JPA Query Language) | HQL (Hibernate Query Language) + JPQL |
| **Caching** | Not defined | 1st level (Session) + 2nd level (shared) |
| **Alternatives** | — | EclipseLink, OpenJPA (other JPA implementations) |

**How They Work Together in Spring Boot:**

```java
// You write JPA annotations (specification)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
}

// You use Spring Data JPA repository (specification)
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}

// Behind the scenes, Hibernate (implementation) converts this to SQL:
// SELECT * FROM users WHERE name = ?
// SELECT * FROM users WHERE email = ?
```

**Spring Boot auto-configures Hibernate as the JPA provider:**
```properties
# application.properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

---

## 8.5 Security Types — Authentication & Authorization

### Types of Security in Microservices

```
┌─────────────────────────────────────────────────────────────────────┐
│                   SECURITY APPROACHES                                │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  1. Basic Authentication     — Username:Password in every request    │
│  2. Session-Based Auth       — Server stores session, client has ID  │
│  3. JWT (JSON Web Token)     — Stateless token-based auth            │
│  4. OAuth 2.0                — Third-party auth (Google, GitHub)     │
│  5. API Key                  — Simple key for service-to-service     │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

---

**1. Basic Authentication**

```
Client sends credentials in EVERY request:
Authorization: Basic base64(username:password)

Example: Authorization: Basic am9objpzZWNyZXQ=  (john:secret)
```

| Pros | Cons |
|------|------|
| Simple to implement | Password sent with every request |
| No session management | Must use HTTPS (plain text otherwise) |
| | No logout mechanism |
| | Not suitable for browser apps |

**Use case:** Internal service-to-service calls, simple APIs

---

**2. Session-Based Authentication**

```
1. Client sends login credentials
2. Server creates session, stores in memory/Redis
3. Server sends back Session ID (cookie)
4. Client sends cookie with every request
5. Server validates session ID

Client                              Server
  │── POST /login (user, pass) ───>│
  │<── Set-Cookie: JSESSIONID=abc ─│  (session stored in server memory)
  │                                 │
  │── GET /profile                 │
  │   Cookie: JSESSIONID=abc ─────>│  (server looks up session)
  │<── 200 OK + user data ────────│
```

| Pros | Cons |
|------|------|
| Simple, well-understood | Server must store sessions (stateful) |
| Easy logout (delete session) | Doesn't scale well (sticky sessions needed) |
| | Not suitable for microservices (shared state) |

**Use case:** Traditional monolith web applications

---

**3. JWT (JSON Web Token) — Most Common in Microservices**

```
1. Client sends login credentials
2. Server validates and returns a JWT token
3. Client sends token in Authorization header
4. Any service can validate the token (stateless!)

Client                    Gateway              User Service
  │── POST /login ──────>│──────────────────>│
  │                       │                   │ validates credentials
  │<── JWT Token ────────│<── JWT Token ─────│ generates JWT
  │                       │                   │
  │── GET /orders         │                   │
  │   Authorization:      │                   │
  │   Bearer eyJhb... ──>│                   │
  │                       │ validates JWT     │
  │                       │── forward ──────>│ Order Service
  │<── 200 OK ───────────│<── response ─────│
```

**JWT Structure:**
```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huIiwicm9sZSI6IkFETUlOIiwiZXhwIjoxNzA5MjAwMDAwfQ.signature

Header.Payload.Signature

Header:  {"alg": "HS256", "typ": "JWT"}
Payload: {"sub": "john", "role": "ADMIN", "exp": 1709200000}
Signature: HMACSHA256(header + "." + payload, secret_key)
```

```java
// JWT generation
@Service
public class JwtService {

    private static final String SECRET = "my-secret-key-which-should-be-very-long";

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
```

| Pros | Cons |
|------|------|
| Stateless — no server-side storage | Cannot revoke token (until it expires) |
| Scales perfectly in microservices | Token size larger than session ID |
| Any service can validate independently | Sensitive data in payload (base64, not encrypted) |
| Works across domains (CORS-friendly) | Must handle token refresh |

**Use case:** Microservices, REST APIs, Mobile apps

---

**4. OAuth 2.0 — Third-Party Authentication**

```
"Login with Google" / "Login with GitHub"

User                    Your App               Google
  │── "Login with Google" ──>│                    │
  │                          │── redirect ───────>│
  │<── Google login page ────│                    │
  │── enter Google creds ────│──────────────────>│
  │                          │                    │ validates
  │                          │<── auth code ──────│
  │                          │── exchange code ──>│
  │                          │<── access token ───│
  │                          │── GET /userinfo ──>│
  │                          │<── user profile ───│
  │<── logged in! ──────────│                    │
```

**OAuth 2.0 Roles:**

| Role | What | Example |
|------|------|---------|
| **Resource Owner** | The user | You (the person) |
| **Client** | App requesting access | Your Spring Boot app |
| **Authorization Server** | Issues tokens | Google, GitHub, Keycloak |
| **Resource Server** | Has protected data | Google's user profile API |

```java
// Spring Boot OAuth2 login config
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/public/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
```

```yaml
# application.yml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: your-google-client-id
            client-secret: your-google-client-secret
            scope: openid, profile, email
```

---

**Security Type Comparison:**

| Type | Stateless? | Best For | Scalability |
|------|-----------|----------|-------------|
| Basic Auth | Yes | Internal APIs, simple scripts | Good |
| Session-Based | No (server stores session) | Monolith web apps | Poor (sticky sessions) |
| JWT | Yes | Microservices, REST APIs | Excellent |
| OAuth 2.0 | Yes | "Login with Google/GitHub" | Excellent |
| API Key | Yes | Service-to-service, public APIs | Good |

---

## 8.6 Common Microservice Problems & Solutions

### Problem 1: Service-to-Service Failure (Cascading Failure)

```
Order Service → calls User Service → calls Address Service
                                          ↓
                                     Address Service is DOWN!
                                          ↓
                                     User Service hangs waiting...
                                          ↓
                                     Order Service hangs waiting...
                                          ↓
                                     ALL services become unresponsive!
```

**Solution: Circuit Breaker Pattern (Resilience4j)**

```java
@Service
public class OrderService {

    @CircuitBreaker(name = "userService", fallbackMethod = "getUserFallback")
    @Retry(name = "userService", fallbackMethod = "getUserFallback")
    public UserDTO getUser(Long userId) {
        return userClient.getUserById(userId);
    }

    // Fallback when User Service is down
    public UserDTO getUserFallback(Long userId, Exception ex) {
        return new UserDTO(userId, "Unknown User", "N/A");
    }
}
```

```
Circuit Breaker States:
                                   failure threshold exceeded
CLOSED ──────────────────────────────────> OPEN
(normal, requests pass through)           (requests fail fast, return fallback)
                                               │
                                               │ wait duration expires
                                               ▼
                                          HALF-OPEN
                                          (allow few test requests)
                                          ┌────┴────┐
                                    success│         │failure
                                          ▼         ▼
                                       CLOSED     OPEN
```

---

### Problem 2: Distributed Transactions

```
Place Order Flow:
  1. Order Service  → Create order in Order DB        ✓
  2. Payment Service → Charge payment in Payment DB   ✓
  3. Inventory Service → Reduce stock in Inventory DB ✗ (FAILS!)

Problem: Order created, payment charged, but stock not updated!
         Data is INCONSISTENT across services!
```

**Solution: SAGA Pattern**

```
Choreography SAGA (event-based):

Order Service          Payment Service         Inventory Service
     │                      │                       │
     │── OrderCreated ─────>│                       │
     │                      │── PaymentCharged ────>│
     │                      │                       │── StockReduced ──> Done!
     │                      │                       │
     │                      │   If stock fails:     │
     │                      │<── StockFailed ───────│
     │<── RefundIssued ─────│                       │
     │── OrderCancelled     │                       │

Each step publishes an event. If a step fails,
compensating events undo previous steps.
```

```java
// Order Service listens for payment result
@KafkaListener(topics = "payment-events")
public void handlePaymentEvent(PaymentEvent event) {
    if (event.getStatus().equals("CHARGED")) {
        // Payment successful, proceed to inventory
        kafkaTemplate.send("inventory-events", new InventoryEvent(event.getOrderId(), "REDUCE"));
    } else if (event.getStatus().equals("FAILED")) {
        // Payment failed, cancel order
        orderRepository.updateStatus(event.getOrderId(), "CANCELLED");
    }
}

// Inventory Service listens and compensates if needed
@KafkaListener(topics = "inventory-events")
public void handleInventoryEvent(InventoryEvent event) {
    try {
        inventoryRepository.reduceStock(event.getProductId(), event.getQuantity());
        kafkaTemplate.send("order-events", new OrderEvent(event.getOrderId(), "COMPLETED"));
    } catch (InsufficientStockException e) {
        // Compensate: refund payment
        kafkaTemplate.send("payment-events", new PaymentEvent(event.getOrderId(), "REFUND"));
        kafkaTemplate.send("order-events", new OrderEvent(event.getOrderId(), "CANCELLED"));
    }
}
```

---

### Problem 3: N+1 Query Problem (JPA/Hibernate)

```
// BAD: Fetching 100 users → 1 query for users + 100 queries for orders = 101 queries!
@Entity
public class User {
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders;
}

// When you access user.getOrders() in a loop:
List<User> users = userRepository.findAll();     // 1 query
for (User u : users) {
    u.getOrders().size();                        // N queries (one per user!)
}
```

**Solutions:**

```java
// Solution 1: JOIN FETCH (JPQL)
@Query("SELECT u FROM User u JOIN FETCH u.orders")
List<User> findAllWithOrders();

// Solution 2: Entity Graph
@EntityGraph(attributePaths = {"orders"})
List<User> findAll();

// Solution 3: Batch fetching
@OneToMany(mappedBy = "user")
@BatchSize(size = 25)  // Fetches orders in batches of 25
private List<Order> orders;
```

---

### Problem 4: Data Consistency Across Services

```
User Service has user data.
Order Service needs user name for order display.

Problem: User changes name in User Service,
         but Order Service still has old name!
```

**Solutions:**
- **Event-driven sync** — User Service publishes `UserUpdated` event, Order Service consumes and updates its local copy
- **API call** — Order Service fetches user data from User Service when needed (adds latency)
- **CQRS** — Separate read/write models, update read model asynchronously

---

### Problem 5: Service Discovery Failure

```
Eureka Server goes down → services can't find each other!
```

**Solutions:**
- Run multiple Eureka instances (cluster)
- Client-side caching (Eureka client caches registry)
- Use DNS-based discovery as fallback
- Kubernetes provides built-in service discovery

---

### Problem 6: Configuration Management

```
50 services × 3 environments = 150 configs
Changing DB password requires redeploying 50 services!
```

**Solution: Spring Cloud Config + Bus**

```yaml
# Config Server fetches from Git repo
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/your-org/config-repo
```

```java
// Any service reads config from Config Server
@Value("${database.password}")
private String dbPassword;  // Fetched from Config Server, not local file
```

---

### Problem 7: Logging & Tracing Across Services

```
One user request flows through 5 services.
Error occurs in Service 4.
How do you find which request caused it?
```

**Solution: Distributed Tracing (Micrometer + Zipkin)**

```
Request hits Gateway:
  TraceId: abc-123, SpanId: span-1  →  User Service
  TraceId: abc-123, SpanId: span-2  →  Order Service
  TraceId: abc-123, SpanId: span-3  →  Payment Service

All logs share the same TraceId → easy to correlate!
```

```properties
# application.properties
management.tracing.sampling.probability=1.0
management.zipkin.tracing.endpoint=http://localhost:9411/api/v2/spans
```

---

### Problem 8: API Versioning

```
User Service v1 returns: { "name": "John" }
User Service v2 returns: { "firstName": "John", "lastName": "Doe" }

How to support both without breaking existing clients?
```

**Solutions:**

```java
// URL versioning (most common)
@GetMapping("/v1/users/{id}")
public UserV1DTO getUserV1(@PathVariable Long id) { }

@GetMapping("/v2/users/{id}")
public UserV2DTO getUserV2(@PathVariable Long id) { }

// Header versioning
@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
public UserV1DTO getUserV1(@PathVariable Long id) { }
```

---

### Common Problems Quick Reference

| Problem | Pattern/Solution |
|---------|-----------------|
| Service failure cascading | Circuit Breaker (Resilience4j) |
| Distributed transactions | SAGA Pattern (choreography/orchestration) |
| N+1 query | JOIN FETCH, Entity Graph, @BatchSize |
| Data consistency | Event-driven sync, CQRS |
| Config management | Spring Cloud Config Server |
| Service discovery failure | Eureka cluster, client-side caching |
| Distributed logging | Correlation ID, Zipkin, Micrometer |
| API versioning | URL versioning, Header versioning |
| Rate limiting | API Gateway (Spring Cloud Gateway) |
| Secret management | HashiCorp Vault, AWS Secrets Manager |
| Slow inter-service calls | Caching (Redis), Async messaging (Kafka) |

---

## Interview Questions

### Redis & Caching

**Q1: What is Redis? What are the core concepts of Redis?**

**Answer:**

**Redis (Remote Dictionary Server)** is an open-source, **in-memory data structure store** used as a database, cache, message broker, and queue.

**Core Concepts:**

| Concept | Description |
|---------|-------------|
| In-Memory Storage | All data stored in RAM for sub-millisecond access |
| Key-Value Store | Every piece of data is stored as a key-value pair |
| Data Structures | Supports Strings, Lists, Sets, Sorted Sets, Hashes, Streams |
| Persistence | RDB (snapshots) and AOF (append-only file) for durability |
| Replication | Master-Slave replication for high availability |
| Clustering | Horizontal scaling by partitioning data across nodes |
| TTL (Time To Live) | Keys can expire automatically after a set duration |
| Pub/Sub | Built-in publish/subscribe messaging |

**Common Data Structures:**

```
Strings  → "user:1:name" = "John"
Hashes   → "user:1" = { name: "John", age: 30 }
Lists    → "queue:emails" = ["email1", "email2", "email3"]
Sets     → "tags:post:1" = {"java", "redis", "spring"}
Sorted Sets → "leaderboard" = {("Alice", 100), ("Bob", 85)}
```

**Use Cases:**
- **Caching** — Store frequently accessed data (API responses, DB queries)
- **Session Management** — Store user sessions across distributed servers
- **Rate Limiting** — Track API call counts per user/IP
- **Real-time Leaderboards** — Sorted sets for ranking
- **Message Queues** — Lightweight pub/sub or stream-based queuing
- **Distributed Locks** — Coordinate access in microservices

---

**Q2: How do you implement caching in a Spring Boot application using Redis?**

**Answer:**

**Step 1: Add Dependencies (pom.xml)**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

**Step 2: Configure Redis (application.properties)**

```properties
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=yourpassword
spring.cache.type=redis
spring.cache.redis.time-to-live=3600000
```

**Step 3: Enable Caching**

```java
@SpringBootApplication
@EnableCaching
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

**Step 4: Use Cache Annotations in Service Layer**

```java
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Cache the result — next call with same id returns cached value
    @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {
        System.out.println("Fetching from DB...");
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Update cache when data changes
    @CachePut(value = "products", key = "#product.id")
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    // Remove from cache when data is deleted
    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Clear entire cache
    @CacheEvict(value = "products", allEntries = true)
    public void clearCache() {
        System.out.println("Cache cleared!");
    }
}
```

**Key Annotations:**

| Annotation | Purpose |
|------------|---------|
| `@EnableCaching` | Enables Spring's caching infrastructure |
| `@Cacheable` | Caches the return value; skips method if cached |
| `@CachePut` | Always executes method and updates cache |
| `@CacheEvict` | Removes entry from cache |

---

**Q3: What is the difference between @Cacheable and @CachePut?**

**Answer:**

| Aspect | @Cacheable | @CachePut |
|--------|-----------|-----------|
| Execution | Skips method if cache hit | Always executes the method |
| Purpose | Read/fetch operations | Update/write operations |
| Cache behavior | Returns cached value if exists | Updates cache with new return value |
| Use case | `getProductById()` | `updateProduct()` |

```java
// @Cacheable — method NOT called if cache has the key
@Cacheable(value = "users", key = "#id")
public User getUser(Long id) {
    // This runs ONLY on cache miss
    return userRepository.findById(id).orElseThrow();
}

// @CachePut — method ALWAYS called, cache updated with result
@CachePut(value = "users", key = "#user.id")
public User updateUser(User user) {
    // This ALWAYS runs, then result is cached
    return userRepository.save(user);
}
```

---

### Kafka

**Q4: What is Apache Kafka and why is it used?**

**Answer:**

**Apache Kafka** is a distributed **event streaming platform** used for building real-time data pipelines and streaming applications.

**Core Components:**

```
┌──────────┐     ┌─────────────────────────┐     ┌──────────┐
│ Producer │────>│        Kafka Broker       │────>│ Consumer │
│ (Sender) │     │  ┌─────────────────────┐ │     │(Receiver)│
└──────────┘     │  │   Topic: orders     │ │     └──────────┘
                 │  │  ┌───┬───┬───┬───┐  │ │
                 │  │  │P0 │P1 │P2 │P3 │  │ │
                 │  │  └───┴───┴───┴───┘  │ │
                 │  └─────────────────────┘ │
                 └─────────────────────────┘
```

| Component | Description |
|-----------|-------------|
| **Producer** | Publishes messages to topics |
| **Consumer** | Subscribes to topics and processes messages |
| **Broker** | Kafka server that stores and serves messages |
| **Topic** | Category/feed name to which messages are published |
| **Partition** | Topics are split into partitions for parallelism |
| **Consumer Group** | Group of consumers that share the work of reading a topic |
| **Offset** | Position of a message within a partition |
| **Zookeeper/KRaft** | Manages broker metadata and leader election |

**Why Use Kafka?**
- **Decoupling** — Producers and consumers are independent
- **Scalability** — Handles millions of messages per second
- **Durability** — Messages are persisted to disk and replicated
- **Real-time Processing** — Low-latency event streaming
- **Fault Tolerance** — Replication across brokers ensures no data loss

**Common Use Cases:**
- Order processing in e-commerce
- Real-time analytics and monitoring
- Log aggregation from microservices
- Event sourcing and CQRS patterns
- Data synchronization between services

---

**Q5: How do you implement a Kafka Producer in Spring Boot?**

**Answer:**

**Step 1: Add Dependency**

```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
```

**Step 2: Configure (application.properties)**

```properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
```

**Step 3: Create Producer Service**

```java
@Service
public class OrderProducerService {

    private static final String TOPIC = "order-events";

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendOrderEvent(OrderEvent event) {
        kafkaTemplate.send(TOPIC, event.getOrderId(), event);
        System.out.println("Sent event: " + event);
    }
}
```

**Step 4: Use in Controller**

```java
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderProducerService producerService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderEvent event) {
        producerService.sendOrderEvent(event);
        return ResponseEntity.ok("Order event published!");
    }
}
```

**OrderEvent DTO:**

```java
public class OrderEvent {
    private String orderId;
    private String product;
    private int quantity;
    private double price;
    private String status; // CREATED, SHIPPED, DELIVERED

    // constructors, getters, setters
}
```

---

**Q6: How do you implement a Kafka Consumer in Spring Boot?**

**Answer:**

**Step 1: Configure (application.properties)**

```properties
spring.kafka.consumer.group-id=order-service-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.properties.spring.json.trusted.packages=*
```

**Step 2: Create Consumer Service**

```java
@Service
public class OrderConsumerService {

    @KafkaListener(topics = "order-events", groupId = "order-service-group")
    public void consumeOrderEvent(OrderEvent event) {
        System.out.println("Received event: " + event);

        switch (event.getStatus()) {
            case "CREATED":
                processNewOrder(event);
                break;
            case "SHIPPED":
                updateShippingStatus(event);
                break;
            case "DELIVERED":
                completeOrder(event);
                break;
        }
    }

    private void processNewOrder(OrderEvent event) {
        // validate inventory, charge payment, etc.
        System.out.println("Processing new order: " + event.getOrderId());
    }

    private void updateShippingStatus(OrderEvent event) {
        System.out.println("Updating shipping for: " + event.getOrderId());
    }

    private void completeOrder(OrderEvent event) {
        System.out.println("Order completed: " + event.getOrderId());
    }
}
```

**Consumer with Manual Acknowledgment:**

```java
@KafkaListener(topics = "order-events", groupId = "order-service-group")
public void consume(OrderEvent event, Acknowledgment ack) {
    try {
        processNewOrder(event);
        ack.acknowledge(); // manually commit offset after successful processing
    } catch (Exception e) {
        // don't acknowledge — message will be redelivered
        System.err.println("Failed to process: " + e.getMessage());
    }
}
```

---

### Spring Annotations

**Q7: What is @Component in Spring? What are its specializations?**

**Answer:**

`@Component` is a **generic stereotype annotation** that marks a Java class as a Spring-managed bean. Spring auto-detects it during component scanning and registers it in the ApplicationContext.

**Specializations of @Component:**

```
              @Component (generic)
              /         |         \
    @Service      @Repository    @Controller
   (Business      (Data Access)  (Web Layer)
    Logic)
```

```java
// Generic component
@Component
public class EmailValidator {
    public boolean isValid(String email) {
        return email != null && email.contains("@");
    }
}

// Service layer
@Service
public class UserService {
    // business logic
}

// Data access layer
@Repository
public class UserRepository {
    // database operations — also enables exception translation
}

// Web layer
@Controller
public class UserController {
    // handles HTTP requests, returns views
}

// REST API layer
@RestController  // = @Controller + @ResponseBody
public class UserApiController {
    // handles HTTP requests, returns JSON/XML
}
```

| Annotation | Layer | Extra Behavior |
|-----------|-------|----------------|
| `@Component` | Any | Basic bean registration |
| `@Service` | Service/Business | Semantic clarity (no extra behavior) |
| `@Repository` | Persistence/DAO | Exception translation (DB exceptions → Spring's DataAccessException) |
| `@Controller` | Web/MVC | Enables request mapping, returns views |
| `@RestController` | Web/REST API | `@Controller` + `@ResponseBody` combined |

---

**Q8: What is the difference between @Component and @Bean?**

**Answer:**

| Aspect | @Component | @Bean |
|--------|-----------|-------|
| **Target** | Class-level annotation | Method-level annotation |
| **Detection** | Auto-detected via component scanning | Explicitly declared in @Configuration class |
| **Control** | Spring creates the bean automatically | You control instantiation logic |
| **Use case** | Your own classes | Third-party library classes you can't annotate |
| **Customization** | Limited | Full control over creation |

```java
// @Component — annotate YOUR class directly
@Component
public class MyEmailService {
    public void sendEmail(String to, String body) {
        // your implementation
    }
}

// @Bean — used for third-party classes or custom initialization
@Configuration
public class AppConfig {

    // You can't add @Component to RestTemplate (it's a Spring class)
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return template;
    }

    // Custom initialization with specific constructor args
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
```

**When to use which:**
- Use `@Component` (and its specializations) for **your own classes**
- Use `@Bean` when you need to **configure third-party objects** or need **complex initialization logic**

---

**Q9: What is @Service annotation? Is it different from @Component?**

**Answer:**

`@Service` is a **specialization of @Component** that indicates a class holds **business logic**. Functionally, it behaves identically to `@Component` — Spring treats them the same way during component scanning.

**Why use @Service instead of @Component?**

1. **Semantic Clarity** — Communicates the class's role in the architecture
2. **Convention** — Teams follow layered architecture conventions
3. **Future-proofing** — Spring may add service-specific behavior in future versions
4. **AOP Targeting** — You can write pointcuts targeting `@Service` beans specifically

```java
// These two are functionally identical:

@Component
public class OrderProcessor {
    public void process(Order order) { }
}

@Service
public class OrderProcessor {
    public void process(Order order) { }
}
```

**Best Practice — Use the right annotation for the right layer:**

```java
@RestController  // Web layer
public class OrderController {

    @Autowired
    private OrderService orderService;
}

@Service  // Business logic layer
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
}

@Repository  // Data access layer
public interface OrderRepository extends JpaRepository<Order, Long> {
}
```

---

**Q10: What is @Bean annotation and when do you use it?**

**Answer:**

`@Bean` is a **method-level annotation** used inside `@Configuration` classes. The method's return value is registered as a Spring bean in the ApplicationContext.

**Common Use Cases:**

**1. Configuring Third-Party Libraries**
```java
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/api/public/**").permitAll()
            .anyRequest().authenticated();
        return http.build();
    }
}
```

**2. Conditional Bean Creation**
```java
@Configuration
public class StorageConfig {

    @Bean
    @Profile("local")
    public StorageService localStorage() {
        return new LocalFileStorageService("/tmp/uploads");
    }

    @Bean
    @Profile("prod")
    public StorageService s3Storage() {
        return new S3StorageService("my-bucket");
    }
}
```

**3. Bean with Dependencies**
```java
@Configuration
public class AppConfig {

    @Bean
    public UserService userService(UserRepository userRepository,
                                    PasswordEncoder encoder) {
        return new UserService(userRepository, encoder);
    }
}
```

**Key Attributes:**

| Attribute | Purpose | Example |
|-----------|---------|---------|
| `name` | Custom bean name | `@Bean(name = "myBean")` |
| `initMethod` | Method called after construction | `@Bean(initMethod = "init")` |
| `destroyMethod` | Method called before destruction | `@Bean(destroyMethod = "cleanup")` |

---

**End of Spring Boot Guide**

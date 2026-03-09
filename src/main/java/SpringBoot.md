# Spring Boot - High-Level Guide

> **For Experienced Developers**: This guide provides architectural overviews and deep-dive explanations of Spring Boot core concepts.

---

## Table of Contents

### Core Concepts
1. [IOC Container & Application Context](#1-ioc-container--application-context)
2. [Spring Data JPA](#2-spring-data-jpa)
3. [Spring Web (REST API)](#3-spring-web-rest-api)
4. [Spring Security](#4-spring-security)
5. [Spring Boot Internals](#5-spring-boot-internals)
6. [Microservices with Spring Cloud](#6-microservices-with-spring-cloud)
7. [Performance & Best Practices](#7-performance--best-practices)
8. [Microservice Project Architecture](#8-how-a-microservice-project-works--complete-architecture)

### Interview Questions
9. [Core Interview Questions](#interview-questions) - Q1–Q10: Fundamentals
10. [Production Troubleshooting](#real-world-spring-boot-interview-questions--production-troubleshooting) - Q11–Q40: Deployment, Slowness, Config, Connection Pools, Circuit Breakers, @Transactional, Docker, Graceful Shutdown
11. [Spring Boot Internals Deep Dive](#spring-boot-internals--interview-deep-dive) - Q41–Q61: Auto-Configuration, Startup Flow, Properties Loading, Fat JAR, DataSource, REST Flow, Logging, Performance Mistakes

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

## Real-World Spring Boot Interview Questions — Production Troubleshooting

> These are scenario-based questions interviewers ask to test how you think under pressure. No memorization — only understanding.

---

**Q11: Your Spring Boot app runs fine locally but fails after deployment. What do you check first?**

```
Debugging Checklist (in order):
═══════════════════════════════

1. ENVIRONMENT VARIABLES / PROFILES
   → Which profile is active?
   → Local: application-dev.yml  |  Prod: application-prod.yml
   → Missing env vars? DB_HOST, DB_PASSWORD not set in prod

2. CONFIGURATION DIFFERENCES
   → DB URL: localhost:5432 vs prod-db.internal:5432
   → Redis, Kafka, external API URLs different?
   → Check: /actuator/env  (shows all resolved properties)

3. NETWORK / CONNECTIVITY
   → Can the app reach the database? Redis? Kafka?
   → Firewall rules blocking ports?
   → DNS resolution failing inside container/K8s?

4. JAVA VERSION MISMATCH
   → Local: Java 17  |  Server: Java 11  → incompatible bytecode
   → Check: java -version on the server

5. DEPENDENCY CONFLICTS
   → Local Maven cache has different JARs than CI/CD build
   → mvn dependency:tree → check for conflicting versions

6. FILE PATHS / PERMISSIONS
   → Hardcoded path: /Users/veer/config.json → doesn't exist on server
   → File permission denied in Linux container

7. MEMORY / RESOURCE LIMITS
   → Local: 16GB RAM  |  Docker: 512MB → OOM on startup
   → Check: -Xmx setting in Dockerfile or K8s limits
```

```yaml
# Common fix: Proper profile-based configuration
# application.yml (common)
spring:
  application:
    name: order-service

# application-dev.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb

# application-prod.yml
spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
    # Values come from environment variables in production
```

```bash
# Quick diagnosis commands on the server
# Check which profile is active
curl http://localhost:8080/actuator/env | grep active

# Check if DB is reachable
nc -zv prod-db.internal 5432

# Check Java version
java -version

# Check memory limits
cat /sys/fs/cgroup/memory/memory.limit_in_bytes   # In Docker
```

---

**Q12: APIs are fast locally but slow only in production. How do you debug this?**

```
Diagnosis Approach:
═══════════════════

1. CHECK NETWORK LATENCY
   → Local: DB is on localhost (0ms network)
   → Prod: DB is on another server/region (5-50ms per query)
   → 20 queries × 10ms = 200ms just in network overhead!

2. CHECK DATABASE
   → Local: Small test data (100 rows)
   → Prod: Real data (10M rows) → missing indexes → full table scans
   → Run EXPLAIN ANALYZE on slow queries

3. CHECK CONNECTION POOL
   → Default HikariCP pool: 10 connections
   → 200 concurrent requests competing for 10 connections
   → Requests wait in queue → response time increases

4. CHECK EXTERNAL SERVICE CALLS
   → Local: Mocked or fast test servers
   → Prod: Real services with real latency + timeouts

5. CHECK GC PRESSURE
   → Prod has higher traffic → more objects → more GC → pauses

6. CHECK DNS RESOLUTION
   → Every HTTP call resolves DNS first
   → In K8s: DNS lookup for service names can be slow
```

```java
// FIX: Add observability to identify the bottleneck
// 1. Enable Micrometer + Spring Boot Actuator
@Bean
public TimedAspect timedAspect(MeterRegistry registry) {
    return new TimedAspect(registry);
}

// 2. Add @Timed to suspect methods
@Timed(value = "order.create", description = "Time to create order")
public Order createOrder(OrderRequest req) {
    User user = userClient.getUser(req.getUserId());      // How long?
    Payment pay = paymentClient.charge(req.getAmount());   // How long?
    Order order = orderRepository.save(new Order(...));     // How long?
    return order;
}

// 3. Check metrics at /actuator/metrics/order.create
// Shows: count, total time, max time → pinpoints the slow part
```

```yaml
# Enable slow query logging
spring:
  jpa:
    properties:
      hibernate:
        generate_statistics: true
    show-sql: true
logging:
  level:
    org.hibernate.stat: DEBUG        # Query execution stats
    org.hibernate.SQL: DEBUG         # Show SQL
    org.hibernate.type.descriptor: TRACE  # Show bind parameters
```

---

**Q13: You changed application.properties but the changes didn't reflect. Why?**

```
Common Causes:
══════════════

1. WRONG PROFILE ACTIVE
   → You edited application-dev.yml
   → But app runs with: --spring.profiles.active=prod
   → Prod profile overrides your changes!

2. PROPERTIES CACHED AT STARTUP
   → Spring loads properties ONCE at startup
   → Changing the file after startup has NO effect
   → Must restart the app (or use Spring Cloud Config + @RefreshScope)

3. ENVIRONMENT VARIABLE OVERRIDES PROPERTY FILE
   → Property precedence in Spring Boot (highest to lowest):
     1. Command line args (--server.port=9090)
     2. OS environment variables (SERVER_PORT=9090)
     3. application-{profile}.yml
     4. application.yml
   → Env var DB_HOST overrides spring.datasource.host in .yml

4. EDITED WRONG FILE
   → src/main/resources/application.yml  ← source (what you edited)
   → target/classes/application.yml      ← compiled (what app reads)
   → If you didn't rebuild → app uses OLD compiled version!

5. SPRING CLOUD CONFIG SERVER OVERRIDES LOCAL
   → If Config Server is configured, remote config takes priority
   → Local application.yml is overridden by config server values

6. @Value INJECTED AT STARTUP, NOT REFRESHED
   → @Value("${api.url}") is set ONCE when bean is created
   → Changing the property later has no effect on running bean
```

```java
// FIX: Use @RefreshScope for dynamic properties (with Spring Cloud)
@RestController
@RefreshScope    // ← Bean is recreated when /actuator/refresh is called
public class ApiController {

    @Value("${api.url}")
    private String apiUrl;  // Will update on POST /actuator/refresh
}

// Without Spring Cloud: Use Environment directly
@RestController
public class ApiController {

    @Autowired
    private Environment env;

    @GetMapping("/config")
    public String getConfig() {
        return env.getProperty("api.url");  // Always reads current value
    }
}
```

---

**Q14: Under high traffic, the app crashes even though CPU usage is low. What could be the reason?**

```
Low CPU + Crash = Resource exhaustion (NOT computation)
═══════════════════════════════════════════════════════

Suspect #1: THREAD EXHAUSTION
  → Tomcat default: 200 threads
  → All 200 threads blocked waiting for DB/external API
  → New requests rejected → 503 errors
  → CPU is LOW because threads are WAITING, not computing

Suspect #2: CONNECTION POOL EXHAUSTION
  → HikariCP default: 10 connections
  → Connections not returned (leaked or slow queries)
  → New requests wait for connection → timeout → error

Suspect #3: FILE DESCRIPTOR LIMIT
  → Linux default: 1024 open files per process
  → Each socket/connection = 1 file descriptor
  → 1024 concurrent connections → "Too many open files" error

Suspect #4: MEMORY (OOM)
  → Traffic spike → more objects in heap
  → GC can't keep up → OutOfMemoryError
  → CPU might be low because GC overhead limit reached

Suspect #5: HEAP METASPACE EXHAUSTION
  → java.lang.OutOfMemoryError: Metaspace
  → Too many classes loaded (reflection, proxies)
```

```yaml
# FIX: Tune server resources for high traffic
server:
  tomcat:
    threads:
      max: 400            # Increase from 200 default
      min-spare: 50       # Keep 50 threads ready
    max-connections: 10000
    accept-count: 200     # Queue size when all threads busy

spring:
  datasource:
    hikari:
      maximum-pool-size: 30          # Increase from 10
      minimum-idle: 10
      connection-timeout: 5000       # Fail fast (5 sec)
      idle-timeout: 300000           # Release idle connections
      max-lifetime: 600000           # Recycle connections
      leak-detection-threshold: 30000 # Detect leaked connections (30 sec)
```

```bash
# Diagnosis
# Check thread count
jstack <PID> | grep "http-nio" | wc -l

# Check file descriptors
ls /proc/<PID>/fd | wc -l
ulimit -n                              # Show limit

# Check connection pool
curl http://localhost:8080/actuator/metrics/hikaricp.connections.active
curl http://localhost:8080/actuator/metrics/hikaricp.connections.pending
```

---

**Q15: Multiple beans of the same type exist and the application fails to start. How do you resolve it?**

```
Error: No qualifying bean of type 'PaymentService':
      expected single matching bean but found 2: stripePayment, razorpayPayment
```

```java
// Scenario: Two implementations of same interface
public interface PaymentService {
    void processPayment(double amount);
}

@Service
public class StripePaymentService implements PaymentService { ... }

@Service
public class RazorpayPaymentService implements PaymentService { ... }

// ✗ FAILS: Spring doesn't know which one to inject
@Autowired
private PaymentService paymentService;  // Which one?
```

```java
// ✓ FIX 1: @Primary — mark one as default
@Service
@Primary
public class StripePaymentService implements PaymentService { ... }

@Service
public class RazorpayPaymentService implements PaymentService { ... }

@Autowired
private PaymentService paymentService;  // Gets Stripe (primary)
```

```java
// ✓ FIX 2: @Qualifier — specify which one
@Autowired
@Qualifier("razorpayPaymentService")
private PaymentService paymentService;  // Gets Razorpay specifically
```

```java
// ✓ FIX 3: Inject all implementations (Strategy Pattern)
@Autowired
private List<PaymentService> allPaymentServices;  // Gets both!

// Or use Map to pick by name
@Autowired
private Map<String, PaymentService> paymentServiceMap;
// Key = bean name: {"stripePaymentService": Stripe, "razorpayPaymentService": Razorpay}

public void processPayment(String gateway, double amount) {
    paymentServiceMap.get(gateway + "PaymentService").processPayment(amount);
}
```

```java
// ✓ FIX 4: @ConditionalOnProperty — activate based on config
@Service
@ConditionalOnProperty(name = "payment.gateway", havingValue = "stripe")
public class StripePaymentService implements PaymentService { ... }

@Service
@ConditionalOnProperty(name = "payment.gateway", havingValue = "razorpay")
public class RazorpayPaymentService implements PaymentService { ... }

// application.yml: payment.gateway=stripe → only Stripe bean is created
```

| Approach | When to Use |
|----------|------------|
| `@Primary` | One clear default, others are exceptions |
| `@Qualifier` | Need specific bean at specific injection point |
| `List<>` / `Map<>` | Strategy pattern — pick at runtime |
| `@ConditionalOnProperty` | Only ONE should exist based on environment |

---

**Q16: Random 401/403 errors appear without any code change. What could cause this?**

```
Debugging 401 (Unauthorized) vs 403 (Forbidden):
═════════════════════════════════════════════════

401 = "Who are you?" (Authentication failed)
403 = "I know you, but you can't do this" (Authorization failed)

Common causes WITHOUT code changes:

1. JWT TOKEN EXPIRED
   → Token TTL: 1 hour
   → User logged in 2 hours ago → token expired → 401
   → Fix: Implement refresh token mechanism

2. SECRET KEY CHANGED / ROTATED
   → JWT signing key rotated on server
   → Old tokens signed with old key → signature mismatch → 401
   → Fix: Support multiple keys during rotation

3. CLOCK SKEW BETWEEN SERVERS
   → Server A issued token at 10:00 (its clock)
   → Server B validates token at 09:58 (its clock is behind)
   → Token "issued in future" → rejected → 401
   → Fix: NTP sync all servers, add clock skew tolerance

4. CORS PREFLIGHT FAILING
   → Browser sends OPTIONS request first
   → Spring Security blocks OPTIONS → 403
   → Actual POST never reaches the server

5. CSRF TOKEN MISMATCH
   → CSRF protection enabled (default for form-based)
   → API client doesn't send X-CSRF-TOKEN header → 403

6. SECURITY FILTER ORDER CHANGED
   → Spring dependency update changed filter ordering
   → Auth filter now runs AFTER the endpoint → 403

7. LOAD BALANCER STRIPPING HEADERS
   → Authorization header not forwarded by LB/Gateway
   → App receives request without token → 401
```

```java
// FIX: Disable CSRF for stateless APIs + configure CORS properly
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())   // Disable for stateless REST APIs
            .cors(cors -> cors.configurationSource(corsConfig()))
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/actuator/health").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }

    private CorsConfigurationSource corsConfig() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("https://myapp.com"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
```

---

**Q17: Database connection pool gets exhausted suddenly. How do you identify and fix it?**

```
Symptoms:
═════════
  → Requests hang for 30 seconds then timeout
  → Error: "HikariPool-1 - Connection is not available, request timed out after 30000ms"
  → But DB server shows only 10 active connections (pool size = 10)
```

```java
// CAUSE 1: Connection leak — opened but never returned to pool
// ═══════════════════════════════════════════════════════════════
public void updateUser(Long id) {
    Connection conn = dataSource.getConnection();
    // ... exception thrown here ...
    conn.close();  // Never reached! Connection LEAKED.
}

// ✓ FIX: try-with-resources
public void updateUser(Long id) {
    try (Connection conn = dataSource.getConnection()) {
        // auto-closed even on exception
    }
}

// CAUSE 2: Long-running transaction holding connection
// ════════════════════════════════════════════════════
@Transactional
public void generateReport() {
    List<Order> orders = orderRepo.findAll();       // Gets connection from pool
    String pdf = pdfService.generate(orders);       // Takes 30 seconds!
    emailService.send(pdf);                         // Takes 5 seconds!
    // Connection held for 35 seconds! Other requests starving.
}

// ✓ FIX: Move non-DB work outside transaction
@Transactional
public List<Order> getOrders() {
    return orderRepo.findAll();  // Connection held only for DB query (~100ms)
}

public void generateReport() {
    List<Order> orders = getOrders();         // Transaction ends here
    String pdf = pdfService.generate(orders); // No DB connection held
    emailService.send(pdf);                   // No DB connection held
}

// CAUSE 3: N+1 queries exhausting pool under load
// ════════════════════════════════════════════════
// 1 query for orders + N queries for order items = 101 queries for 100 orders
// Each query needs a connection → pool thrashed
```

```yaml
# FIX: HikariCP configuration with leak detection
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 5000          # Don't wait more than 5 sec
      leak-detection-threshold: 10000   # ← LOG WARNING if connection held > 10 sec
      idle-timeout: 300000
      max-lifetime: 600000

# When leak-detection-threshold is set, you'll see:
# WARN  HikariPool - Connection leak detection triggered for connection xyz
# → Shows the stack trace where the connection was obtained!
```

---

**Q18: One downstream service is slow and your service also starts failing. How do you protect your app?**

```
The Problem: Cascading Failure
══════════════════════════════

User → Your App → Payment Service (slow: 30 sec)
                       ↓
       All 200 Tomcat threads waiting for Payment Service
                       ↓
       New requests can't be served → YOUR app is now down too!
       One slow dependency took down YOUR entire service.
```

```java
// ✓ FIX 1: Circuit Breaker (Resilience4j)
@CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
public PaymentResponse processPayment(PaymentRequest req) {
    return paymentClient.charge(req);  // Calls slow service
}

public PaymentResponse paymentFallback(PaymentRequest req, Exception ex) {
    // Return graceful degradation instead of failure
    return PaymentResponse.pending("Payment queued for retry");
}

// Circuit Breaker states:
// CLOSED  → normal, requests pass through
// OPEN    → failure rate > 50% → ALL requests go to fallback (don't call downstream)
// HALF-OPEN → after wait, let 1 test request through to check if service recovered
```

```java
// ✓ FIX 2: Timeout — don't wait forever
@TimeLimiter(name = "paymentService")
public CompletableFuture<PaymentResponse> processPayment(PaymentRequest req) {
    return CompletableFuture.supplyAsync(() -> paymentClient.charge(req));
}

// ✓ FIX 3: Bulkhead — limit concurrent calls to downstream
@Bulkhead(name = "paymentService", type = Bulkhead.Type.THREADPOOL)
public PaymentResponse processPayment(PaymentRequest req) {
    return paymentClient.charge(req);
}
// Only 10 threads (configurable) can call payment service simultaneously
// Other requests get fallback immediately → protects YOUR thread pool
```

```yaml
# resilience4j configuration
resilience4j:
  circuitbreaker:
    instances:
      paymentService:
        failure-rate-threshold: 50          # Open when 50% requests fail
        wait-duration-in-open-state: 30s    # Stay open for 30 sec
        sliding-window-size: 10             # Evaluate last 10 calls
        permitted-number-of-calls-in-half-open-state: 3
  timelimiter:
    instances:
      paymentService:
        timeout-duration: 3s                # Timeout after 3 seconds
  bulkhead:
    instances:
      paymentService:
        max-concurrent-calls: 10            # Max 10 parallel calls
```

```
Defense patterns summary:
═════════════════════════
┌──────────────────┬────────────────────────────────────────┐
│ Pattern          │ What it does                           │
├──────────────────┼────────────────────────────────────────┤
│ Circuit Breaker  │ Stop calling if service is failing     │
│ Timeout          │ Don't wait forever for response        │
│ Bulkhead         │ Limit concurrent calls (thread limit)  │
│ Retry            │ Retry transient failures (with backoff)│
│ Fallback         │ Return default/cached response on fail │
│ Rate Limiter     │ Limit how many calls per second        │
└──────────────────┴────────────────────────────────────────┘
```

---

**Q19: Memory usage keeps increasing over time. What do you suspect?**

```
Same concept as Java Q37 but specific Spring Boot causes:
═════════════════════════════════════════════════════════

1. SESSION OBJECTS NOT EXPIRING
   → HTTP sessions stored in memory (default: 30 min timeout)
   → If session timeout is too long or misconfigured → sessions accumulate
   → Fix: Use stateless JWT or configure session timeout

2. SPRING CACHE WITHOUT EVICTION
   → @Cacheable stores results forever (default: no TTL)
   → Fix: Configure TTL and max size

3. APPLICATIONCONTEXT BEANS HOLDING DATA
   → Singleton beans with List/Map fields growing over time
   → Singleton lives for entire app lifecycle → data never released

4. ACTUATOR METRICS ACCUMULATION
   → Custom metrics with high-cardinality tags
   → micrometer.tag("userId", id) → one metric per user → millions of entries!
   → Fix: Use low-cardinality tags only

5. HIKARICP CONNECTION OBJECTS LEAKED
   → Connections obtained but not closed → HikariCP holds proxy objects

6. SCHEDULED TASKS ACCUMULATING DATA
   → @Scheduled method loads data into list, never clears it
```

```yaml
# FIX: Configure session timeout and cache TTL
server:
  servlet:
    session:
      timeout: 15m             # Sessions expire after 15 minutes

spring:
  cache:
    type: caffeine
    caffeine:
      spec: maximumSize=10000,expireAfterWrite=600s   # Max 10K entries, 10 min TTL
```

```bash
# Diagnosis
# Heap dump analysis
jmap -dump:live,format=b,file=heap.hprof <PID>
# Open in Eclipse MAT → Leak Suspects → shows what's consuming memory

# Monitor via Actuator
curl http://localhost:8080/actuator/metrics/jvm.memory.used
```

---

**Q20: @Transactional is present but rollback doesn't happen. Why?**

```
This is one of the most common Spring Boot bugs. Six reasons:
═════════════════════════════════════════════════════════════
```

```java
// REASON 1: Checked exception — @Transactional only rolls back on UNCHECKED exceptions
// ═══════════════════════════════════════════════════════════════════════════════════════
@Transactional
public void transfer(Long from, Long to, double amount) throws InsufficientFundsException {
    accountRepo.debit(from, amount);
    if (balance < 0) {
        throw new InsufficientFundsException("Not enough funds");  // CHECKED exception
        // ✗ NO ROLLBACK! debit() is committed!
    }
    accountRepo.credit(to, amount);
}

// ✓ FIX: Specify rollbackFor
@Transactional(rollbackFor = Exception.class)  // Now rolls back on ALL exceptions
public void transfer(...) throws InsufficientFundsException { ... }
```

```java
// REASON 2: Self-invocation — calling @Transactional method from same class
// ═════════════════════════════════════════════════════════════════════════
@Service
public class OrderService {

    public void createOrder(OrderRequest req) {
        saveOrder(req);  // ✗ Direct call — BYPASSES Spring proxy!
    }

    @Transactional
    public void saveOrder(OrderRequest req) {
        // Transaction NEVER starts because proxy was bypassed
        orderRepo.save(new Order(req));
        paymentService.charge(req);  // If this fails → NO rollback!
    }
}

// WHY? Spring AOP creates a PROXY around the bean:
//   Client → Proxy (starts transaction) → Real OrderService
//   But internal method call skips the proxy!

// ✓ FIX Option 1: Call from another bean
@Service
public class OrderController {
    @Autowired
    private OrderService orderService;

    public void create(OrderRequest req) {
        orderService.saveOrder(req);  // Goes through proxy ✓
    }
}

// ✓ FIX Option 2: Inject self
@Service
public class OrderService {
    @Autowired
    private OrderService self;  // Inject the PROXY

    public void createOrder(OrderRequest req) {
        self.saveOrder(req);  // Goes through proxy ✓
    }
}
```

```java
// REASON 3: Method is not public
@Transactional
private void saveOrder(OrderRequest req) {  // ✗ private!
    // Spring AOP can only intercept PUBLIC methods
    // @Transactional is IGNORED on private, protected, or package-private methods
}

// REASON 4: Exception caught INSIDE the method
@Transactional
public void processOrder(OrderRequest req) {
    try {
        orderRepo.save(new Order(req));
        paymentService.charge(req);  // Throws exception
    } catch (Exception e) {
        log.error("Failed", e);
        // ✗ Exception is CAUGHT — Spring never sees it → NO rollback!
    }
}

// ✓ FIX: Let exception propagate, or manually trigger rollback
@Transactional
public void processOrder(OrderRequest req) {
    try {
        orderRepo.save(new Order(req));
        paymentService.charge(req);
    } catch (Exception e) {
        log.error("Failed", e);
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  // Manual rollback
        throw e;  // OR re-throw
    }
}

// REASON 5: Wrong transaction manager (multiple DataSources)
// REASON 6: @Transactional on interface method but using CGLIB proxy (not interface proxy)
```

```
Summary — @Transactional rollback checklist:
┌──────────────────────────────────────┬──────────────────────────┐
│ Problem                              │ Fix                      │
├──────────────────────────────────────┼──────────────────────────┤
│ Checked exception thrown             │ rollbackFor=Exception.class│
│ Self-invocation (same class call)    │ Call from another bean    │
│ Method is private/protected          │ Make it public            │
│ Exception caught inside method       │ Re-throw or manual rollback│
│ Wrong transaction manager            │ @Transactional("txMgr")  │
│ Using final/static method            │ Remove final/static       │
└──────────────────────────────────────┴──────────────────────────┘
```

---

**Q21: Users still see old behavior after a new deployment. What went wrong?**

```
Causes:
═══════

1. BROWSER CACHE / CDN CACHE
   → Static resources (JS, CSS) cached by browser
   → User's browser serves old JavaScript
   → Fix: Cache-busting (add version hash to file names)

2. SPRING CACHE NOT CLEARED
   → @Cacheable results from old code still in Redis/in-memory
   → New code deployed but cache serves old data
   → Fix: Clear cache on deployment (or use versioned cache keys)

3. BLUE-GREEN / ROLLING DEPLOYMENT IN PROGRESS
   → Half the pods run v1, half run v2
   → Load balancer routes some users to old pods
   → Fix: Wait for full rollout, use sticky sessions

4. CONFIGURATION STILL POINTING TO OLD VERSION
   → Config Server serving old properties
   → Feature flag not toggled

5. DATABASE MIGRATION NOT RUN
   → New code expects new column/table
   → Flyway/Liquibase migration didn't execute
   → New code works but returns old data format
```

```java
// FIX: Clear cache on deployment
@Component
public class CacheClearOnStartup implements ApplicationRunner {
    @Autowired
    private CacheManager cacheManager;

    @Override
    public void run(ApplicationArguments args) {
        cacheManager.getCacheNames()
            .forEach(name -> cacheManager.getCache(name).clear());
        log.info("All caches cleared on startup");
    }
}
```

---

**Q22: Logs are missing in production but available locally. Where do you look?**

```
Causes:
═══════

1. LOG LEVEL TOO HIGH IN PRODUCTION
   → Local: logging.level.root=DEBUG
   → Prod: logging.level.root=WARN  → INFO and DEBUG suppressed!

2. LOGGING TO WRONG DESTINATION
   → Local: logs to console (stdout)
   → Prod: configured to log to file → file path doesn't exist in container!
   → Docker container has ephemeral filesystem → logs lost on restart

3. ASYNC LOGGING DROPPING MESSAGES
   → AsyncAppender queue full → discards logs silently
   → Fix: Increase queue size or set discardingThreshold=0

4. STDOUT NOT CAPTURED
   → Docker/K8s: logs go to stdout → kubectl logs <pod> shows them
   → If logging to file inside container → kubectl logs shows nothing!

5. LOG FRAMEWORK CONFLICT
   → Both log4j and logback on classpath → one overrides the other
   → Fix: Exclude unwanted logging dependency
```

```yaml
# FIX: Proper production logging config
logging:
  level:
    root: INFO
    com.myapp: DEBUG                      # Your app at DEBUG
    org.springframework.web: WARN         # Framework at WARN
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"

# For Docker/K8s: ALWAYS log to stdout (not files)
# Container orchestrator captures stdout
```

```bash
# Check what logging framework is active
curl http://localhost:8080/actuator/loggers

# Change log level at runtime (no restart!)
curl -X POST http://localhost:8080/actuator/loggers/com.myapp \
  -H "Content-Type: application/json" \
  -d '{"configuredLevel": "DEBUG"}'
```

---

**Q23: API returns correct data but response time is inconsistent. What could be the reason?**

```
Causes of inconsistent latency (sometimes fast, sometimes slow):
════════════════════════════════════════════════════════════════

1. GARBAGE COLLECTION PAUSES
   → 99% of requests: 5ms
   → During GC: 500ms (stop-the-world pause)
   → Fix: Tune GC, use ZGC for low-latency

2. DATABASE CONNECTION POOL CONTENTION
   → Sometimes connection available immediately → fast
   → Sometimes all connections busy → wait in queue → slow

3. COLD CACHE vs HOT CACHE
   → First request: cache miss → DB query → 200ms
   → Subsequent: cache hit → 2ms
   → After cache TTL expires → slow again

4. THREAD POOL SATURATION
   → If all Tomcat threads busy → new request waits in queue
   → P50: 10ms (thread available)
   → P99: 2000ms (waited for thread)

5. NETWORK JITTER
   → Microservice calls have variable network latency
   → DNS resolution sometimes slow

6. NOISY NEIGHBOR (shared infrastructure)
   → Another pod/VM on same host consuming resources
   → Your app gets less CPU → slower
```

```yaml
# FIX: Monitor percentile latencies (not just averages!)
management:
  metrics:
    distribution:
      percentiles-histogram:
        http.server.requests: true
      percentiles:
        http.server.requests: 0.5, 0.95, 0.99    # P50, P95, P99
```

---

**Q24: A scheduled job affects API performance. How do you isolate it?**

```java
// ✗ PROBLEM: Scheduled job runs on same thread pool as API requests
@Scheduled(fixedRate = 60000)
public void syncData() {
    // Heavy DB query + processing for 30 seconds
    // Blocks a Tomcat thread → one less thread for API requests
    List<Record> records = repo.findAll();  // Loads 1M records into memory!
    process(records);
}
```

```java
// ✓ FIX 1: Separate thread pool for scheduled tasks
@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        registrar.setScheduler(Executors.newScheduledThreadPool(5));
        // Scheduled tasks run on separate threads, NOT on Tomcat threads
    }
}
```

```java
// ✓ FIX 2: Use @Async with dedicated executor
@Async("schedulerExecutor")
@Scheduled(fixedRate = 60000)
public void syncData() {
    // Runs on dedicated async thread pool
    // API threads unaffected
}

@Bean("schedulerExecutor")
public Executor schedulerExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(3);
    executor.setMaxPoolSize(5);
    executor.setThreadNamePrefix("scheduler-");
    return executor;
}
```

```java
// ✓ FIX 3: Process in batches (don't load 1M records at once)
@Scheduled(fixedRate = 60000)
public void syncData() {
    int page = 0;
    Page<Record> batch;
    do {
        batch = repo.findAll(PageRequest.of(page++, 500));  // 500 at a time
        processBatch(batch.getContent());
    } while (batch.hasNext());
}
```

---

**Q25: Application behaves differently in Docker compared to local. Why?**

```
Common Docker-specific issues:
══════════════════════════════

1. JVM DOESN'T RESPECT CONTAINER MEMORY LIMITS (old JVMs)
   → Docker limit: 512MB
   → JVM (pre-Java 10): sees HOST memory (16GB) → sets -Xmx=4GB → OOM killed!
   → Fix: Use Java 17+ (container-aware) or set -Xmx explicitly

2. TIMEZONE DIFFERENCE
   → Local: Asia/Kolkata
   → Docker: UTC (default)
   → Scheduled jobs run at wrong time, timestamps wrong
   → Fix: -e TZ=Asia/Kolkata or ENV TZ=Asia/Kolkata in Dockerfile

3. FILE SYSTEM IS EPHEMERAL
   → Writing to /tmp/uploads → data lost on container restart
   → Fix: Use Docker volumes or cloud storage (S3)

4. LOCALHOST MEANS DIFFERENT THINGS
   → Local: localhost → your DB on same machine
   → Docker: localhost → container itself (DB is NOT inside!)
   → Fix: Use Docker network and service names

5. DNS RESOLUTION DIFFERENCES
   → Local: /etc/hosts or system DNS
   → Docker: Docker's embedded DNS
   → K8s: CoreDNS → service-name.namespace.svc.cluster.local

6. CPU CORE COUNT MISMATCH
   → Local: 8 cores → ForkJoinPool.commonPool() = 7 threads
   → Docker with 2 CPU limit → should be 1 thread
   → Java 17+ reads cgroup limits correctly
```

```dockerfile
# Proper Spring Boot Dockerfile
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

ENV TZ=Asia/Kolkata
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0"
# Uses 75% of container memory limit (not host memory)

COPY target/*.jar app.jar

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
```

---

**Q26: Lazy loading works locally but fails in production. What is missing?**

```
Error: LazyInitializationException: could not initialize proxy — no Session
═════════════════════════════════════════════════════════════════════════════

This happens when you access a lazy-loaded relationship OUTSIDE a Hibernate session.
```

```java
// ✗ PROBLEM:
@Entity
public class Order {
    @OneToMany(fetch = FetchType.LAZY)  // Loaded only when accessed
    private List<OrderItem> items;
}

@Service
public class OrderService {
    @Transactional
    public Order getOrder(Long id) {
        return orderRepo.findById(id).orElseThrow();
        // Transaction ends here → Hibernate session closes
    }
}

@RestController
public class OrderController {
    @GetMapping("/orders/{id}")
    public OrderDTO getOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id);
        order.getItems();  // ✗ LazyInitializationException!
        // Session already closed → can't load items
    }
}
```

```java
// WHY it works locally:
// spring.jpa.open-in-view=true  (DEFAULT!)
// This keeps Hibernate session open during entire HTTP request
// But it's an ANTI-PATTERN → holds DB connection for entire request lifecycle

// In production: You might have disabled it (or config is different)
// spring.jpa.open-in-view=false  → LazyInitializationException!
```

```java
// ✓ FIX 1: Fetch eagerly in the query (BEST approach)
@Query("SELECT o FROM Order o JOIN FETCH o.items WHERE o.id = :id")
Optional<Order> findByIdWithItems(@Param("id") Long id);

// ✓ FIX 2: Use @EntityGraph
@EntityGraph(attributePaths = {"items"})
Optional<Order> findById(Long id);

// ✓ FIX 3: Use DTO projection (load only what you need)
@Transactional(readOnly = true)
public OrderDTO getOrder(Long id) {
    Order order = orderRepo.findById(id).orElseThrow();
    // Map to DTO INSIDE transaction (session still open)
    return new OrderDTO(
        order.getId(),
        order.getItems().stream().map(this::toItemDTO).toList()  // ✓ Access within session
    );
}

// ✗ DON'T: Enable open-in-view in production
// spring.jpa.open-in-view=true  → holds DB connection for entire request → pool exhaustion!
```

---

**Q27: The app restarts automatically without clear errors. What could trigger this?**

```
Causes:
═══════

1. OutOfMemoryError → JVM CRASHES → K8s/Docker restarts container
   → Check: kubectl describe pod → OOMKilled
   → Fix: Increase memory limit or fix memory leak

2. KUBERNETES LIVENESS PROBE FAILING
   → App is alive but slow (GC pause, heavy load)
   → Liveness probe fails 3 times → K8s kills pod → restart
   → Fix: Increase probe timeout and failure threshold

3. DOCKER OOM KILLER
   → Container exceeds memory limit
   → Linux OOM killer terminates the process
   → No error in app logs (killed from outside)
   → Check: docker inspect → OOMKilled: true

4. DEVTOOLS AUTO-RESTART (if accidentally included in prod)
   → spring-boot-devtools triggers restart on classpath changes
   → Fix: Exclude devtools from production builds

5. ACTUATOR SHUTDOWN ENDPOINT EXPOSED
   → POST /actuator/shutdown → app shuts down
   → Fix: Disable or secure it
```

```yaml
# FIX: Proper K8s probe configuration
livenessProbe:
  httpGet:
    path: /actuator/health/liveness
    port: 8080
  initialDelaySeconds: 60        # Give app time to start
  periodSeconds: 15
  failureThreshold: 5            # Allow 5 failures before restart
  timeoutSeconds: 5              # Wait 5 sec for response

readinessProbe:
  httpGet:
    path: /actuator/health/readiness
    port: 8080
  initialDelaySeconds: 30
  periodSeconds: 10
```

```xml
<!-- Exclude devtools from production build -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
    <scope>runtime</scope>   <!-- Not included in final JAR -->
</dependency>
```

---

**Q28: API Gateway routes requests but headers are missing. Why?**

```
Common causes:
══════════════

1. GATEWAY DOESN'T FORWARD ALL HEADERS BY DEFAULT
   → Custom headers (X-Correlation-Id, X-User-Id) stripped
   → Only standard headers forwarded

2. SENSITIVE HEADERS FILTERED
   → Spring Cloud Gateway removes Cookie, Authorization by default
   → Config: spring.cloud.gateway.default-filters

3. CORS PREFLIGHT BLOCKS CUSTOM HEADERS
   → Browser won't send custom headers unless allowed in CORS config
```

```yaml
# Spring Cloud Gateway — preserve headers
spring:
  cloud:
    gateway:
      default-filters:
        - DedupeResponseHeader=Access-Control-Allow-Origin
      routes:
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/api/users/**
          filters:
            - PreserveHostHeader                            # Keep original Host
            - RemoveRequestHeader=Cookie                    # Remove only Cookie
            # Don't add RemoveRequestHeader=Authorization!  # Keep Auth header

      # Forward all headers (don't strip sensitive ones)
      httpclient:
        wiretap: true    # Log all requests for debugging
```

```java
// Custom filter to add headers
@Component
public class AddCorrelationIdFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String correlationId = exchange.getRequest().getHeaders()
            .getFirst("X-Correlation-Id");

        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
        }

        exchange.getRequest().mutate()
            .header("X-Correlation-Id", correlationId)
            .build();

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() { return -1; }  // Run early in filter chain
}
```

---

**Q29: Circuit breaker stays open even when the service is healthy. What's wrong?**

```
Causes:
═══════

1. SLIDING WINDOW STILL CONTAINS OLD FAILURES
   → Circuit opened due to failures
   → Service recovered after 10 sec
   → But wait-duration-in-open-state = 60s
   → Circuit stays open for 60 seconds regardless!
   → Fix: Reduce wait-duration-in-open-state

2. HEALTH CHECK PASSES BUT CIRCUIT BREAKER USES DIFFERENT ENDPOINT
   → /actuator/health → 200 OK  (simple health check)
   → But circuit breaker tracks actual API calls → still failing for business reasons
   → HTTP 200 with error body → application error → circuit sees it as failure

3. HALF-OPEN STATE FAILS IMMEDIATELY
   → After wait duration, circuit becomes HALF-OPEN
   → Allows N test requests
   → If those N requests also fail → back to OPEN
   → Service might need warm-up time

4. TIMEOUT COUNTED AS FAILURE
   → Service responds in 4 sec (slow but working)
   → Circuit breaker timeout = 3 sec → counts as failure
   → Enough "failures" → circuit opens even though service works!
   → Fix: Align timeout with SLA
```

```yaml
# FIX: Tune circuit breaker properly
resilience4j:
  circuitbreaker:
    instances:
      paymentService:
        sliding-window-size: 10
        failure-rate-threshold: 50
        wait-duration-in-open-state: 10s           # ← Reduce from 60s
        permitted-number-of-calls-in-half-open-state: 5  # ← More test requests
        slow-call-rate-threshold: 80               # Treat slow calls as failures
        slow-call-duration-threshold: 3s           # Define "slow"
        record-exceptions:                         # Only count these as failures
          - java.io.IOException
          - java.util.concurrent.TimeoutException
        ignore-exceptions:                         # Don't count these
          - com.myapp.BusinessException            # Business errors ≠ service down
```

---

**Q30: Increasing server resources didn't improve performance. Why?**

```
This is the same concept as Java Q44 but in Spring Boot context.
═══════════════════════════════════════════════════════════════

Adding more CPU/RAM doesn't help when the bottleneck is elsewhere:

1. BOTTLENECK IS DATABASE, NOT APP SERVER
   → 16 CPU cores on app server → but only 10 DB connections
   → App can only process 10 concurrent DB requests regardless of CPU
   → Fix: Increase connection pool, optimize queries, add read replicas

2. BOTTLENECK IS EXTERNAL SERVICE
   → Calling payment API with 2 sec response time
   → Doubling RAM won't make payment API faster
   → Fix: Async calls, circuit breaker, caching

3. SINGLE-THREADED BOTTLENECK
   → Synchronized method or single-threaded executor
   → Only 1 request processed at a time regardless of cores
   → Fix: Remove unnecessary synchronization, use concurrent data structures

4. LOCK CONTENTION
   → All threads fighting over same lock
   → More threads = more contention = WORSE performance
   → Fix: Reduce lock scope, use lock-free algorithms

5. GC OVERHEAD
   → More memory → longer GC pauses (Java Q44)
   → Fix: Tune GC, use ZGC, reduce object creation

6. NETWORK BANDWIDTH
   → Returning 5MB JSON responses
   → Network saturated at 1 Gbps
   → More CPU won't help
   → Fix: Pagination, compression, GraphQL
```

---

**Q31: Async processing was added but performance became worse. How?**

```java
// ✗ PROBLEM 1: @Async without custom executor — uses SimpleAsyncTaskExecutor
// Creates a NEW thread for every call (no thread reuse!)
@Async
public void sendEmail(String to) {
    // Every call = new Thread() → thread creation overhead
    // 10,000 emails = 10,000 threads → OOM
}

// ✓ FIX: Configure proper thread pool
@Bean("asyncExecutor")
public Executor asyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(25);
    executor.setQueueCapacity(100);
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    return executor;
}

@Async("asyncExecutor")
public void sendEmail(String to) { ... }
```

```java
// ✗ PROBLEM 2: Making CPU-bound work async (no benefit)
@Async
public CompletableFuture<BigDecimal> calculateTax(Order order) {
    // Pure CPU computation → switching threads adds overhead
    // Net result: SLOWER than synchronous
    return CompletableFuture.completedFuture(computeTax(order));
}
// Async helps for I/O-bound (waiting for DB, API) NOT CPU-bound work

// ✗ PROBLEM 3: Fire-and-forget without backpressure
for (Order order : orders) {  // 100,000 orders
    asyncService.processOrder(order);  // Queues 100,000 tasks
    // Queue grows to 100K → memory exhaustion → OOM
}

// ✓ FIX: Use bounded queue with CallerRunsPolicy
// When queue is full → caller thread processes the task itself (natural backpressure)
```

---

**Q32: Retry logic caused more failures instead of fixing them. What mistake was made?**

```
Same concept as Java Q43 applied to Spring Boot:
═════════════════════════════════════════════════
```

```java
// ✗ BAD: Retry on ALL exceptions including non-transient ones
@Retryable(maxAttempts = 5)
public void createUser(User user) {
    userRepo.save(user);  // Throws DataIntegrityViolationException (duplicate email)
    // Retries 5 times → SAME error every time → wasted resources + DB load
}

// ✓ FIX: Only retry on transient exceptions
@Retryable(
    retryFor = {TransientDataAccessException.class, TimeoutException.class},
    noRetryFor = {DataIntegrityViolationException.class, ValidationException.class},
    maxAttempts = 3,
    backoff = @Backoff(delay = 1000, multiplier = 2)  // 1s → 2s → 4s
)
public void createUser(User user) {
    userRepo.save(user);
}

@Recover
public void createUserFallback(Exception e, User user) {
    log.error("Failed to create user after retries: {}", user.getEmail(), e);
    deadLetterQueue.send(user);  // Send to DLQ for manual processing
}
```

```
Rules:
✓ Retry: Timeout, 503, Connection refused (transient)
✗ Don't retry: 400, 404, 409, validation errors (permanent)
✓ Always use exponential backoff
✓ Always set maxAttempts
✓ Always have a @Recover fallback
```

---

**Q33: Connection timeout issues appear only under load. Why?**

```
Under low load:
  → 10 connections in pool
  → 5 concurrent requests → each gets a connection immediately → fast

Under high load:
  → 10 connections in pool
  → 200 concurrent requests → 190 WAIT for connection
  → HikariCP connection-timeout: 30 sec
  → After 30 sec → "Connection is not available, request timed out"

  But WHY are connections held so long?
  → Slow queries under load (DB also overloaded)
  → N+1 queries (1 request holds connection for 50 queries)
  → @Transactional on controller method (holds connection for entire request)
```

```yaml
# FIX: Monitor and tune
spring:
  datasource:
    hikari:
      maximum-pool-size: 30           # Increase pool
      connection-timeout: 5000        # Fail fast (5 sec, not 30)
      leak-detection-threshold: 10000 # Detect leaks

management:
  metrics:
    enable:
      hikari: true    # Enable HikariCP metrics in Actuator

# Monitor: /actuator/metrics/hikaricp.connections.pending
# If pending > 0 consistently → pool too small or queries too slow
```

---

**Q34: Configuration changes broke the application without code changes. How?**

```
Scenarios:
══════════

1. PROPERTY TYPO
   → spring.datasource.url → spring.datasourse.url (typo)
   → No error at startup! Spring just ignores unknown properties
   → Uses default (H2 in-memory?) instead of your PostgreSQL
   → App starts, but data goes to wrong DB

2. PROFILE MISMATCH
   → Changed application-prod.yml
   → But app runs with --spring.profiles.active=production (not "prod")
   → Changes never loaded

3. SPRING BOOT VERSION UPGRADE CHANGED DEFAULTS
   → Spring Boot 2.x: server.servlet.context-path
   → Spring Boot 3.x: same but different auto-config behavior
   → Or: spring.redis.* → spring.data.redis.* (namespace changed!)

4. CONFIG SERVER RETURNED WRONG VALUES
   → Config Server serves config from Git
   → Someone pushed wrong values to Git → all services get bad config

5. ENVIRONMENT VARIABLE OVERRIDES PROPERTY
   → Added SERVER_PORT=9090 as env var for testing
   → Forgot to remove it → overrides application.yml port
```

```yaml
# Prevention: Enable config validation
@ConfigurationProperties(prefix = "app")
@Validated                                # ← Validates at startup
public class AppConfig {
    @NotBlank
    private String dbUrl;                 # Fails fast if missing

    @Min(1) @Max(100)
    private int threadPoolSize;           # Validates range
}
```

---

**Q35: Health checks pass but the service is unusable. How is that possible?**

```
Default health check: /actuator/health
Returns: {"status": "UP"} → just means JVM is running and endpoints are accessible

But the service could still be UNUSABLE:

1. DATABASE CONNECTION WORKS BUT QUERIES ARE SLOW
   → Health check: SELECT 1 → 1ms ✓
   → Real query: SELECT * FROM orders JOIN ... → 30 seconds!

2. EXTERNAL DEPENDENCY DOWN
   → Health check doesn't check external APIs
   → Payment service is down → orders fail → but health says "UP"

3. DISK FULL
   → Can't write logs or temp files
   → Requests fail with I/O errors
   → Health check doesn't check disk by default

4. MEMORY ALMOST FULL
   → Heap at 95% → GC pauses causing timeouts
   → Health check is lightweight → succeeds between GC pauses

5. THREAD POOL EXHAUSTED
   → All threads busy → health check uses its own thread → passes
   → But real requests can't get a thread → timeout
```

```java
// ✓ FIX: Custom health indicators that check REAL functionality
@Component
public class PaymentServiceHealthIndicator implements HealthIndicator {
    @Autowired
    private PaymentClient paymentClient;

    @Override
    public Health health() {
        try {
            paymentClient.healthCheck();  // Actually calls the dependency
            return Health.up().build();
        } catch (Exception e) {
            return Health.down()
                .withDetail("error", e.getMessage())
                .build();
        }
    }
}

// Custom health check for connection pool
@Component
public class DatabasePoolHealthIndicator implements HealthIndicator {
    @Autowired
    private HikariDataSource dataSource;

    @Override
    public Health health() {
        HikariPoolMXBean pool = dataSource.getHikariPoolMXBean();
        int active = pool.getActiveConnections();
        int total = pool.getTotalConnections();
        int pending = pool.getThreadsAwaitingConnection();

        if (pending > 5) {
            return Health.down()
                .withDetail("pending", pending)
                .withDetail("active", active)
                .build();
        }
        return Health.up()
            .withDetail("active", active)
            .withDetail("idle", total - active)
            .build();
    }
}
```

```yaml
# Enable detailed health checks
management:
  endpoint:
    health:
      show-details: always
  health:
    diskspace:
      enabled: true
      threshold: 1GB               # DOWN if < 1GB free
    db:
      enabled: true                # Check DB connection
```

---

**Q36: Cache improved performance initially but later degraded it. Why?**

```
1. CACHE GREW UNBOUNDED (in-memory)
   → @Cacheable stores everything, no max size
   → After 1 month: 5 million cached entries → heap pressure → GC pauses
   → Fix: Set maximumSize and TTL

2. CACHE STAMPEDE (thundering herd)
   → Cache TTL expires for a popular key
   → 1000 requests hit simultaneously → ALL get cache miss
   → 1000 identical DB queries at once → DB overloaded!
   → Fix: Use cache locking or "stale-while-revalidate"

3. STALE CACHE SERVING WRONG DATA
   → Cache TTL = 1 hour
   → Data updated in DB → cache still serves old data for up to 1 hour
   → Fix: Use @CacheEvict on update operations

4. SERIALIZATION OVERHEAD (Redis)
   → Caching large objects → serialize to Redis → deserialize on read
   → Serialization takes longer than the DB query it's supposed to save!
   → Fix: Cache only IDs or lightweight DTOs, not full entity graphs
```

```java
// ✓ Proper cache configuration
@Cacheable(value = "users", key = "#id", unless = "#result == null")
public User getUser(Long id) {
    return userRepo.findById(id).orElse(null);
}

@CachePut(value = "users", key = "#user.id")     // Update cache
public User updateUser(User user) {
    return userRepo.save(user);
}

@CacheEvict(value = "users", key = "#id")         // Remove from cache
public void deleteUser(Long id) {
    userRepo.deleteById(id);
}

@CacheEvict(value = "users", allEntries = true)    // Clear all on deployment
public void clearCache() {}
```

```yaml
# Caffeine cache with limits
spring:
  cache:
    type: caffeine
    caffeine:
      spec: maximumSize=50000,expireAfterWrite=300s,recordStats
      # Max 50K entries, 5 min TTL, stats for monitoring
```

---

**Q37: Thread pool size increase caused request failures. How?**

```
Counter-intuitive: More threads = more failures

1. DB CONNECTION POOL NOT INCREASED
   → Tomcat threads: 200 → 500
   → HikariCP pool: 10 connections (unchanged)
   → 500 threads compete for 10 connections → most timeout!

2. DOWNSTREAM SERVICE OVERLOADED
   → More threads → more concurrent calls to downstream
   → Downstream can't handle the load → starts failing
   → More retries → even more load → cascade failure

3. CONTEXT SWITCHING OVERHEAD
   → More threads than CPU cores → CPU spends time switching, not working
   → 1000 threads on 4 cores → 99.6% switching overhead

4. MEMORY EXHAUSTION
   → Each thread = ~1MB stack
   → 500 → 2000 threads = 1.5GB JUST for thread stacks
   → Less memory for heap → more GC → slower

Rule: Thread pool size should match the bottleneck capacity
  → If DB pool = 20 → Tomcat threads > 20 for DB calls doesn't help
  → If downstream allows 50 req/sec → more threads doesn't help
```

---

**Q38: Application shuts down and drops in-flight requests. How do you fix it?**

```java
// PROBLEM: Default shutdown kills in-flight requests immediately
// User submitting payment → shutdown signal → 503 → payment status unknown!
```

```yaml
# ✓ FIX: Enable graceful shutdown (Spring Boot 2.3+)
server:
  shutdown: graceful                     # Wait for in-flight requests to complete

spring:
  lifecycle:
    timeout-per-shutdown-phase: 30s      # Wait max 30 sec before force kill
```

```java
// ✓ For async tasks: Proper executor shutdown
@Bean
public Executor asyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(25);
    executor.setWaitForTasksToCompleteOnShutdown(true);    // ← Don't kill running tasks
    executor.setAwaitTerminationSeconds(30);                // ← Wait up to 30 sec
    return executor;
}
```

```
Graceful Shutdown Flow:
═══════════════════════
1. SIGTERM received (K8s sends this before killing pod)
2. Spring stops accepting NEW requests
3. Waits for in-flight requests to complete (up to timeout)
4. Closes all resources (DB connections, caches, etc.)
5. JVM shuts down

K8s configuration:
  terminationGracePeriodSeconds: 45     # Must be > Spring's timeout
  # Spring timeout: 30s  +  buffer: 15s = 45s total
```

---

**Q39: One small config change caused a major outage. What went wrong?**

```
Real-world scenarios:
═════════════════════

1. CHANGED DB CONNECTION POOL SIZE FROM 10 TO 1 (typo!)
   → All requests queue for single connection → timeout cascade

2. CHANGED server.port IN WRONG PROFILE
   → App starts on port 9090 → Load balancer checks port 8080 → health fails
   → All traffic removed → service appears down

3. SET spring.jpa.hibernate.ddl-auto=create IN PRODUCTION
   → Drops all tables and recreates them → ALL DATA LOST
   → This has actually happened in real companies!

4. REMOVED A "HARMLESS" DEPENDENCY
   → Removed unused library from pom.xml
   → That library had a transitive dependency your code actually used
   → ClassNotFoundException in production

5. CHANGED LOGGING LEVEL TO DEBUG
   → Disk fills up in hours → no more logs → can't diagnose other issues
   → App crashes when log file system is full

Prevention:
═══════════
✓ Configuration changes go through the same PR review as code changes
✓ Use @ConfigurationProperties with @Validated
✓ Test config changes in staging first
✓ NEVER use ddl-auto=create or ddl-auto=update in production
✓ Use feature flags for risky changes (can toggle without redeployment)
✓ Have rollback plan for every deployment (including config changes)
```

```yaml
# SAFE production JPA settings
spring:
  jpa:
    hibernate:
      ddl-auto: validate          # Only VALIDATE schema, never modify
    open-in-view: false           # Don't hold DB connection for entire request
    show-sql: false               # Don't log SQL in production
```

---

**Q40: What Spring Boot decision you made once caused a real production issue?**

```
SCENARIO 1: open-in-view=true (DEFAULT!) caused connection pool exhaustion
══════════════════════════════════════════════════════════════════════════

What happened:
  - Spring Boot default: spring.jpa.open-in-view=true
  - This keeps Hibernate session open for ENTIRE HTTP request
  - DB connection held from request start to response sent (including JSON serialization)
  - Under load: 200 threads × long response times = all connections held
  - Pool exhausted → every new request waits → timeout cascade

Fix: spring.jpa.open-in-view=false
     Use JOIN FETCH or @EntityGraph for lazy loading instead
```

```
SCENARIO 2: @Transactional on @Controller method
═════════════════════════════════════════════════

What happened:
  - @Transactional on a controller method that called external API
  - Transaction (and DB connection) held while waiting for API response (5 sec)
  - 200 concurrent users = 200 connections held for 5 sec each
  - Pool exhausted → 503 errors for everyone

Fix: @Transactional only on service methods, only around DB operations
     Not on controller methods, not around HTTP calls
```

```
SCENARIO 3: Forgot @Recover on @Retryable
═════════════════════════════════════════

What happened:
  - @Retryable(maxAttempts=3) on payment service call
  - No @Recover method defined
  - When all retries exhausted → ExhaustedRetryException propagated
  - Global exception handler returned 500 instead of meaningful error
  - Frontend showed "Internal Server Error" instead of "Payment failed, try later"

Fix: Always define @Recover fallback for @Retryable methods
```

```
SCENARIO 4: Used @Cacheable without TTL on frequently changing data
══════════════════════════════════════════════════════════════════

What happened:
  - Cached product prices with @Cacheable (no TTL)
  - Product manager updated prices in DB
  - Users still saw old prices for hours → wrong charges → customer complaints
  - Had to clear cache manually in production

Fix: Set appropriate TTL, use @CacheEvict on update operations
```

```
SCENARIO 5: ddl-auto=update accidentally enabled in production
═════════════════════════════════════════════════════════════

What happened:
  - Developer added a new entity with @Column(unique = true)
  - ddl-auto=update was ON → Hibernate added a unique constraint
  - Existing data had duplicates → constraint failed → app crashed
  - Rolling back was complex because constraint was already applied

Fix: ALWAYS use ddl-auto=validate in production
     Use Flyway/Liquibase for all schema changes
```

```
How to answer this in an interview:
════════════════════════════════════
1. Pick ONE scenario you can explain deeply
2. Be honest about the mistake (interviewers respect honesty)
3. Explain the root cause clearly
4. Show the fix AND the prevention
5. End with: "Now I always ___" (lesson learned)
```

---

## Spring Boot Internals — Interview Deep Dive

> These questions test how well you understand what Spring Boot does **behind the scenes**, not just how to use it.

---

**Q41. How does Spring Boot decide which auto-configuration to apply?**

**Answer:**

Spring Boot uses a **conditional registration** system. It doesn't blindly load everything — it checks conditions first.

```
The Decision Process:
═════════════════════

Step 1: @SpringBootApplication triggers @EnableAutoConfiguration

Step 2: @EnableAutoConfiguration uses @Import(AutoConfigurationImportSelector.class)

Step 3: AutoConfigurationImportSelector reads META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
        (In older versions: META-INF/spring.factories)

Step 4: This file lists ALL possible auto-configuration classes (150+):
        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
        org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
        org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration
        ... (150+ entries)

Step 5: Each class has @Conditional annotations that act as GUARDS:
```

```java
// Example: DataSourceAutoConfiguration
@AutoConfiguration
@ConditionalOnClass({ DataSource.class, EmbeddedDatabaseType.class })
// ↑ Only loads if these classes are on the classpath
@ConditionalOnMissingBean(type = "io.r2dbc.spi.ConnectionFactory")
// ↑ Only loads if R2DBC is NOT configured
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean  // ← Only creates if YOU didn't define your own DataSource
    public DataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }
}

// Example: WebMvcAutoConfiguration
@AutoConfiguration(after = DispatcherServletAutoConfiguration.class)
@ConditionalOnWebApplication(type = Type.SERVLET)
// ↑ Only loads if this is a web application (not a batch job)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
// ↑ Only loads if Servlet API is on classpath
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
// ↑ Backs off if you extend WebMvcConfigurationSupport yourself
public class WebMvcAutoConfiguration { ... }
```

```
The @Conditional Family:
════════════════════════
@ConditionalOnClass          → Class exists on classpath?
@ConditionalOnMissingClass   → Class does NOT exist?
@ConditionalOnBean           → Bean already registered in context?
@ConditionalOnMissingBean    → Bean NOT registered? (most important — backs off)
@ConditionalOnProperty       → Property set to specific value?
@ConditionalOnWebApplication → Is this a web app?
@ConditionalOnResource       → Does a resource file exist?
@ConditionalOnExpression     → SpEL expression evaluates to true?

Key Insight: YOUR beans always win.
  If you define a DataSource @Bean → @ConditionalOnMissingBean skips auto-config
  This is how you customize without fighting the framework
```

```
To see what was applied vs skipped:
  application.properties:
    debug=true

  Output shows:
  Positive matches:     (applied)
    DataSourceAutoConfiguration matched:
      - @ConditionalOnClass found: DataSource ✓
  Negative matches:     (skipped)
    MongoAutoConfiguration:
      - @ConditionalOnClass did not find: MongoClient ✗
```

---

**Q42. What happens internally when you add spring-boot-starter-web?**

**Answer:**

A starter is just a **dependency aggregator** — it pulls in a specific set of libraries and triggers auto-configurations.

```
spring-boot-starter-web pulls in:
═════════════════════════════════

spring-boot-starter-web
  ├── spring-boot-starter           (core: Spring Context, logging, YAML support)
  │     ├── spring-boot-autoconfigure (150+ auto-configuration classes)
  │     ├── spring-boot-starter-logging (Logback + SLF4J)
  │     └── spring-core, spring-context, spring-beans
  ├── spring-web                     (RestTemplate, @RequestMapping, HTTP abstractions)
  ├── spring-webmvc                  (DispatcherServlet, @Controller, view resolution)
  ├── spring-boot-starter-tomcat     (embedded Tomcat server)
  │     ├── tomcat-embed-core
  │     ├── tomcat-embed-el
  │     └── tomcat-embed-websocket
  └── spring-boot-starter-json       (Jackson JSON processing)
        ├── jackson-databind
        ├── jackson-datatype-jdk8
        └── jackson-datatype-jsr310  (Java 8 date/time support)
```

```
What Gets Auto-Configured (because classes are now on classpath):
════════════════════════════════════════════════════════════════

1. ServletWebServerFactoryAutoConfiguration
   → Detects Tomcat on classpath → creates embedded Tomcat on port 8080

2. DispatcherServletAutoConfiguration
   → Creates DispatcherServlet (front controller for all requests)
   → Maps it to "/" (handles all HTTP requests)

3. WebMvcAutoConfiguration
   → Configures default message converters (Jackson for JSON)
   → Sets up static resource handling (/static, /public, /resources)
   → Configures default error page (/error)
   → Registers default view resolvers

4. HttpMessageConvertersAutoConfiguration
   → Registers MappingJackson2HttpMessageConverter
   → Your @RestController can now return objects as JSON automatically

5. ErrorMvcAutoConfiguration
   → Creates /error endpoint (the default white label error page)
   → Returns JSON error for API calls, HTML for browser requests

So just ONE dependency gives you:
  ✓ Embedded Tomcat server
  ✓ Spring MVC fully configured
  ✓ JSON serialization/deserialization
  ✓ Error handling
  ✓ Static resource serving
  ✓ Logging (Logback)
```

```
If you swap Tomcat for Jetty:
═════════════════════════════
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>

→ @ConditionalOnClass(Tomcat.class) → FALSE (Tomcat removed)
→ @ConditionalOnClass(Server.class) → TRUE (Jetty on classpath)
→ Jetty auto-configured instead of Tomcat — ZERO code changes
```

---

**Q43. What is the exact startup flow of a Spring Boot application?**

**Answer:**

```java
// When you call:
@SpringBootApplication
public class MyApp {
    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);  // Everything starts here
    }
}
```

```
Complete Startup Flow:
═════════════════════

1. SpringApplication.run()
   │
   ├─ 2. Create SpringApplication instance
   │     ├─ Detect web application type (SERVLET, REACTIVE, NONE)
   │     ├─ Load ApplicationContextInitializers (from spring.factories)
   │     └─ Load ApplicationListeners (from spring.factories)
   │
   ├─ 3. Run SpringApplication
   │     │
   │     ├─ 4. Create and start StopWatch (for timing)
   │     │
   │     ├─ 5. Get SpringApplicationRunListeners
   │     │     └─ Notify: applicationStarting event
   │     │
   │     ├─ 6. Prepare Environment
   │     │     ├─ Create Environment (StandardServletEnvironment)
   │     │     ├─ Load properties in order:
   │     │     │   1. Command-line args (--server.port=9090)
   │     │     │   2. SPRING_APPLICATION_JSON
   │     │     │   3. Servlet init params
   │     │     │   4. OS Environment variables (SPRING_DATASOURCE_URL)
   │     │     │   5. application-{profile}.properties/yml
   │     │     │   6. application.properties/yml
   │     │     │   7. @PropertySource on @Configuration classes
   │     │     │   8. Default properties
   │     │     └─ Notify: environmentPrepared event
   │     │
   │     ├─ 7. Print Banner (the Spring logo)
   │     │
   │     ├─ 8. Create ApplicationContext
   │     │     └─ AnnotationConfigServletWebServerApplicationContext (for web apps)
   │     │
   │     ├─ 9. Prepare Context
   │     │     ├─ Set environment
   │     │     ├─ Run ApplicationContextInitializers
   │     │     ├─ Notify: contextPrepared event
   │     │     ├─ Register main class as bean definition
   │     │     └─ Notify: contextLoaded event
   │     │
   │     ├─ 10. Refresh Context (THE MAIN STEP)
   │     │      ├─ a. Process @ComponentScan → find all @Component classes
   │     │      ├─ b. Process @Import → load auto-configuration classes
   │     │      ├─ c. Evaluate @Conditional on each auto-config
   │     │      ├─ d. Create BeanDefinitions for all matched beans
   │     │      ├─ e. Instantiate beans (respecting @DependsOn, constructor order)
   │     │      ├─ f. Inject dependencies (@Autowired, constructor injection)
   │     │      ├─ g. Call @PostConstruct methods
   │     │      ├─ h. Call InitializingBean.afterPropertiesSet()
   │     │      ├─ i. Start embedded web server (Tomcat/Jetty/Netty)
   │     │      └─ j. Notify: contextRefreshed event
   │     │
   │     ├─ 11. Notify: applicationStarted event
   │     │
   │     ├─ 12. Call Runners
   │     │     ├─ CommandLineRunner.run(args)
   │     │     └─ ApplicationRunner.run(ApplicationArguments)
   │     │
   │     └─ 13. Notify: applicationReady event
   │           └─ App is now fully started and serving requests
   │
   └─ If any step fails:
        ├─ Notify: applicationFailed event
        └─ Run FailureAnalyzers (pretty-print error explanation)
```

```
Key Interview Points:
═════════════════════
• Bean creation happens in step 10 (Refresh Context)
• Embedded server starts DURING refresh, not after
• Properties are loaded BEFORE beans are created (step 6)
• @PostConstruct runs AFTER dependency injection
• CommandLineRunner runs AFTER everything is ready
• Auto-configurations are just @Configuration classes with @Conditional guards
```

---

**Q44. How does application.properties get loaded internally?**

**Answer:**

Spring Boot uses `ConfigDataEnvironmentPostProcessor` (since 2.4) to load configuration files.

```
Property Loading Order (highest priority wins):
════════════════════════════════════════════════

Priority 1 (HIGHEST): Command-line arguments
    java -jar app.jar --server.port=9090

Priority 2: SPRING_APPLICATION_JSON (inline JSON)
    SPRING_APPLICATION_JSON='{"server.port": 9090}'

Priority 3: ServletConfig/ServletContext init parameters

Priority 4: OS Environment variables
    export SPRING_DATASOURCE_URL=jdbc:mysql://localhost/db
    Note: Spring converts SPRING_DATASOURCE_URL → spring.datasource.url
    (Relaxed binding: uppercase + underscore → lowercase + dot)

Priority 5: application-{profile}.properties/yml
    application-prod.properties overrides application.properties

Priority 6: application.properties / application.yml
    (outside of jar → inside of jar)

Priority 7: @PropertySource annotations

Priority 8: Default properties (SpringApplication.setDefaultProperties())
```

```
File Location Priority (for same-named property):
══════════════════════════════════════════════════

1. config/ subdirectory of current directory    ./config/application.properties
2. Current directory                            ./application.properties
3. config/ in classpath                         classpath:/config/application.properties
4. Classpath root                               classpath:/application.properties

So a file in ./config/ overrides the one in your JAR.
This is how ops teams override settings without rebuilding.
```

```
What happens if both .yml and .properties exist?
═════════════════════════════════════════════════
Both are loaded! .properties takes HIGHER priority than .yml
(at the same location level)

application.properties:  server.port=8080
application.yml:         server.port: 9090

Result: port = 8080 (.properties wins)

Best practice: Pick ONE format for your project. Don't mix.
```

---

**Q45. What is the role of SpringFactoriesLoader?**

**Answer:**

`SpringFactoriesLoader` is the discovery mechanism that makes Spring Boot's auto-configuration work.

```java
// It reads META-INF/spring.factories files from ALL JARs on the classpath
// Each JAR can register its own auto-configurations, initializers, listeners

// spring.factories format (Spring Boot 2.x):
// org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
//   com.example.MyAutoConfiguration,\
//   com.example.AnotherAutoConfiguration

// Spring Boot 3.x moved to:
// META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
// (one class per line, simpler format)

// How it works internally:
public final class SpringFactoriesLoader {
    // Scans ALL META-INF/spring.factories across ALL JARs
    // Caches results in a MultiValueMap
    // Returns list of class names for a given key

    // Called during startup for:
    // 1. Auto-configuration classes (EnableAutoConfiguration key)
    // 2. ApplicationContextInitializer implementations
    // 3. ApplicationListener implementations
    // 4. FailureAnalyzer implementations
    // 5. EnvironmentPostProcessor implementations
}
```

```
Why It Matters:
═══════════════
This is how third-party libraries plug into Spring Boot automatically.

Example: You add spring-boot-starter-data-redis to pom.xml
  → Redis JAR contains META-INF/spring.factories
  → Lists RedisAutoConfiguration
  → SpringFactoriesLoader finds it during startup
  → @ConditionalOnClass(RedisClient.class) → TRUE (Redis on classpath)
  → Redis connection pool auto-configured

You didn't write ANY configuration code. SpringFactoriesLoader made it happen.
```

---

**Q46. @ComponentScan vs @SpringBootApplication — what's the difference?**

**Answer:**

`@SpringBootApplication` IS `@ComponentScan` plus more.

```java
// @SpringBootApplication is a shortcut for THREE annotations:
@SpringBootConfiguration      // = @Configuration (this class is a config source)
@EnableAutoConfiguration       // = load auto-configuration from spring.factories
@ComponentScan                 // = scan for @Component classes
public @interface SpringBootApplication { }

// So this:
@SpringBootApplication
public class MyApp { }

// Is identical to:
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MyApp { }
```

```
@ComponentScan Details:
═══════════════════════
• Scans the PACKAGE of the annotated class + all sub-packages
• Finds: @Component, @Service, @Repository, @Controller, @Configuration
• Does NOT find auto-configuration classes (that's @EnableAutoConfiguration's job)

MyApp.java in com.example.myapp
  ├── com.example.myapp.service      ← scanned ✓
  ├── com.example.myapp.controller   ← scanned ✓
  ├── com.example.myapp.repository   ← scanned ✓
  └── com.other.library.service      ← NOT scanned ✗

// To include external packages:
@SpringBootApplication(scanBasePackages = {
    "com.example.myapp",
    "com.other.library"
})

// Common mistake: Putting main class in a sub-package
// com.example.myapp.config.MyApp.java ← only scans .config sub-packages!
// Fix: Put main class in the ROOT package (com.example.myapp)
```

---

**Q47. How does Spring Boot detect and configure embedded Tomcat?**

**Answer:**

```
Detection Flow:
═══════════════

1. spring-boot-starter-web → pulls spring-boot-starter-tomcat
   → Puts Tomcat classes on classpath

2. ServletWebServerFactoryAutoConfiguration activates:
   @ConditionalOnClass(ServletRequest.class)  → TRUE (from spring-web)
   @ConditionalOnWebApplication(type = SERVLET) → TRUE

3. Inside, it @Imports:
   EmbeddedTomcat → @ConditionalOnClass(Tomcat.class) → TRUE ✓
   EmbeddedJetty  → @ConditionalOnClass(Server.class) → FALSE ✗
   EmbeddedUndertow → @ConditionalOnClass(Undertow.class) → FALSE ✗

4. EmbeddedTomcat creates TomcatServletWebServerFactory bean:
```

```java
// Simplified internal code:
@Configuration
@ConditionalOnClass({ Servlet.class, Tomcat.class })
static class EmbeddedTomcat {

    @Bean
    TomcatServletWebServerFactory tomcatFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        // Reads server.port, server.tomcat.* properties
        // Configures thread pool, connection timeout, max connections
        return factory;
    }
}

// During context refresh, Spring calls:
// factory.getWebServer(servletContext)
//   → Creates Tomcat instance
//   → Adds Connector on port 8080
//   → Registers DispatcherServlet
//   → Calls tomcat.start()
//   → Server is running!
```

```
Customization:
═══════════════
// Via properties:
server.port=8080
server.tomcat.threads.max=200
server.tomcat.threads.min-spare=10
server.tomcat.max-connections=8192
server.tomcat.accept-count=100
server.tomcat.connection-timeout=20000

// Via code (for advanced config):
@Bean
public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
    return factory -> {
        factory.addConnectorCustomizers(connector -> {
            connector.setProperty("maxKeepAliveRequests", "100");
        });
    };
}
```

---

**Q48. How does Spring Boot handle externalized configuration?**

**Answer:**

Spring Boot's externalized configuration lets you use the **same code** across all environments by changing only config.

```
Configuration Sources (ALL of these work, highest priority wins):
═════════════════════════════════════════════════════════════════

1. Command-line args:           --spring.datasource.url=jdbc:...
2. JVM System properties:       -Dspring.datasource.url=jdbc:...
3. OS Environment variables:     SPRING_DATASOURCE_URL=jdbc:...
4. Profile-specific files:       application-prod.yml
5. Application files:            application.yml / application.properties
6. @PropertySource:              @PropertySource("classpath:custom.properties")
7. Default properties:           SpringApplication.setDefaultProperties(...)
```

```java
// === Multiple ways to READ configuration ===

// Way 1: @Value (simple, single property)
@Value("${app.max-retries:3}")  // Default value after colon
private int maxRetries;

// Way 2: @ConfigurationProperties (type-safe, recommended for groups)
@ConfigurationProperties(prefix = "app.datasource")
public class DataSourceConfig {
    private String url;
    private String username;
    private int maxPoolSize = 10;  // Default value
    // Getters and setters
}

// application.yml:
// app:
//   datasource:
//     url: jdbc:mysql://localhost/mydb
//     username: root
//     max-pool-size: 20

// Way 3: Environment object (programmatic access)
@Autowired
private Environment env;

public void doSomething() {
    String url = env.getProperty("app.datasource.url");
    int port = env.getProperty("server.port", Integer.class, 8080);
}
```

```
Relaxed Binding (Spring Boot converts between naming conventions):
═════════════════════════════════════════════════════════════════

These ALL bind to the same property:
  app.max-retries           (kebab-case — recommended in .properties)
  app.maxRetries            (camelCase)
  app.max_retries           (underscore)
  APP_MAX_RETRIES           (uppercase — for environment variables)
  APP_MAXRETRIES            (uppercase no separator)

This is why you can set SPRING_DATASOURCE_URL as an OS env var
and it maps to spring.datasource.url in your code.
```

---

**Q49. What is the real use of CommandLineRunner?**

**Answer:**

`CommandLineRunner` runs code **after** the application context is fully initialized but **before** it starts serving requests (conceptually — Tomcat is already started but the runner executes right after).

```java
// === Common Use Cases ===

// 1. Load initial data
@Component
@Order(1)  // Run order when multiple runners exist
public class DataLoader implements CommandLineRunner {
    @Autowired
    private UserRepository userRepo;

    @Override
    public void run(String... args) {
        if (userRepo.count() == 0) {
            userRepo.save(new User("admin", "admin@example.com"));
            log.info("Default admin user created");
        }
    }
}

// 2. Validate configuration on startup
@Component
@Order(0)  // Run first
public class ConfigValidator implements CommandLineRunner {
    @Value("${app.api-key}")
    private String apiKey;

    @Override
    public void run(String... args) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                "app.api-key must be set! App cannot start without it.");
        }
        // App will FAIL TO START with clear error message
    }
}

// 3. Warm up caches
@Component
public class CacheWarmer implements CommandLineRunner {
    @Override
    public void run(String... args) {
        log.info("Warming up product cache...");
        productService.loadTopProducts();  // Pre-fill cache
        log.info("Cache warmed up. Ready to serve requests.");
    }
}

// 4. Process command-line arguments
@Component
public class MigrationRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        // java -jar app.jar --migrate --version=2.0
        if (Arrays.asList(args).contains("--migrate")) {
            migrationService.migrate();
            System.exit(0);  // Exit after migration
        }
    }
}
```

```
CommandLineRunner vs ApplicationRunner:
═══════════════════════════════════════
CommandLineRunner:  run(String... args)        — raw string arguments
ApplicationRunner:  run(ApplicationArguments)   — parsed arguments (--key=value)

// ApplicationRunner example:
public void run(ApplicationArguments args) {
    args.getOptionValues("version");    // Returns ["2.0"]
    args.getNonOptionArgs();            // Returns non -- arguments
    args.containsOption("migrate");     // true/false
}

Both run at the same time. Use ApplicationRunner when you need parsed arguments.
```

---

**Q50. What is the difference between fat JAR and normal JAR?**

**Answer:**

```
Normal JAR:
═══════════
myapp.jar
  ├── com/example/MyApp.class
  ├── com/example/service/UserService.class
  └── META-INF/MANIFEST.MF

→ Contains ONLY your compiled classes
→ Needs external libraries on classpath to run:
  java -cp "myapp.jar:lib/spring-core.jar:lib/jackson.jar:..." com.example.MyApp
→ Dependency management is YOUR problem
→ "It works on my machine" because your machine has the right JARs


Fat JAR (Spring Boot Executable JAR):
═════════════════════════════════════
myapp.jar (50-100 MB)
  ├── BOOT-INF/
  │   ├── classes/                    ← Your compiled classes
  │   │   └── com/example/MyApp.class
  │   └── lib/                        ← ALL dependency JARs (nested!)
  │       ├── spring-core-6.1.0.jar
  │       ├── spring-web-6.1.0.jar
  │       ├── jackson-databind-2.15.jar
  │       ├── tomcat-embed-core-10.1.jar
  │       └── ... (100+ JARs)
  ├── org/springframework/boot/loader/  ← Spring Boot's custom class loader
  │   ├── JarLauncher.class
  │   └── LaunchedURLClassLoader.class
  └── META-INF/
      └── MANIFEST.MF
          Main-Class: org.springframework.boot.loader.JarLauncher
          Start-Class: com.example.MyApp

→ Contains EVERYTHING needed to run
→ Single command: java -jar myapp.jar
→ Embedded server (Tomcat) included
→ No external dependencies needed
→ Deploy to any machine with just a JRE
```

```
How Fat JAR Works:
══════════════════
1. java -jar myapp.jar
2. JVM reads MANIFEST.MF → finds Main-Class: JarLauncher
3. JarLauncher creates LaunchedURLClassLoader
4. This custom classloader can read JARs INSIDE JARs (nested JARs)
   (Normal Java classloader cannot do this)
5. Loads all JARs from BOOT-INF/lib/
6. Finds Start-Class: com.example.MyApp
7. Calls MyApp.main() → SpringApplication.run()

Why custom classloader?
  Java's built-in classloader can't read JAR-inside-JAR
  Spring Boot's loader can → this is what makes "executable JAR" possible
```

```
Thin JAR (Alternative):
═══════════════════════
Some teams use "thin" JARs that download dependencies at startup.
→ Faster CI/CD (smaller artifact)
→ But slower startup (must download on first run)
→ Fat JAR is the default and recommended for most cases.
```

---

**Q51. How does Spring Boot reduce XML configuration completely?**

**Answer:**

Spring Boot replaced XML with **three mechanisms**:

```
XML Era (Spring 2.x):
═════════════════════
<!-- applicationContext.xml — hundreds of lines -->
<beans>
    <bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost/mydb"/>
        <property name="username" value="root"/>
    </bean>

    <bean id="entityManagerFactory"
          class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="packagesToScan" value="com.example.entity"/>
        <!-- 20 more properties... -->
    </bean>

    <bean id="transactionManager"
          class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
    </bean>

    <tx:annotation-driven/>
    <context:component-scan base-package="com.example"/>
    <!-- And so on for every single component... -->
</beans>


Spring Boot Era:
════════════════
application.properties (3 lines):
  spring.datasource.url=jdbc:mysql://localhost/mydb
  spring.datasource.username=root
  spring.jpa.hibernate.ddl-auto=update

That's it. Everything else is auto-configured.
```

```
Three Mechanisms That Replaced XML:
════════════════════════════════════

1. ANNOTATIONS (replaced bean declarations)
   XML:  <bean id="userService" class="com.example.UserService">
   Java: @Service public class UserService { }

   XML:  <property name="repo" ref="userRepo"/>
   Java: @Autowired private UserRepository repo;

2. AUTO-CONFIGURATION (replaced wiring)
   XML:  50 lines to configure DataSource + JPA + Transactions
   Boot: Add spring-boot-starter-data-jpa → done automatically

3. PROPERTIES FILES (replaced XML property values)
   XML:  <property name="url" value="${db.url}"/>
   Boot: spring.datasource.url=jdbc:mysql://...
         + @ConfigurationProperties for type-safe binding
```

---

**Q52. How does Spring Boot manage dependency versions automatically?**

**Answer:**

Through the **Bill of Materials (BOM)** pattern — `spring-boot-dependencies`.

```xml
<!-- Your pom.xml — notice NO version specified -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <!-- No version! Parent manages it -->
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <!-- No version! Parent manages it -->
    </dependency>
</dependencies>
```

```
How It Works:
═════════════

spring-boot-starter-parent (your pom's parent)
  └── inherits from: spring-boot-dependencies (the BOM)
        └── declares <dependencyManagement> with 400+ library versions:

            jackson-databind:      2.15.3
            hibernate-core:        6.3.1.Final
            tomcat-embed-core:     10.1.16
            logback-classic:       1.4.11
            snakeyaml:             2.2
            ... (400+ versions, ALL tested together)

When you add a dependency WITHOUT a version:
  → Maven checks <dependencyManagement> from parent
  → Finds the version Spring Boot tested with
  → Uses that version

Result:
  ✓ All libraries are compatible (tested together by Spring team)
  ✓ No version conflicts
  ✓ Upgrading Spring Boot version upgrades ALL managed dependencies
  ✓ You CAN override: just specify a version explicitly to override BOM
```

---

**Q53. What happens when you hit a REST endpoint? (Full request flow)**

**Answer:**

```
HTTP Request: GET /api/users/123
═════════════════════════════════

1. TOMCAT receives TCP connection
   → Thread pool assigns a thread (Thread-42)
   → Parses HTTP request (method, URL, headers, body)

2. SERVLET FILTER CHAIN (in order)
   → CharacterEncodingFilter (set UTF-8)
   → HiddenHttpMethodFilter (support PUT/DELETE from forms)
   → SecurityFilterChain (if Spring Security):
     → AuthenticationFilter → validate JWT/session
     → AuthorizationFilter → check roles/permissions
   → Your custom filters (@Order controls order)

3. DISPATCHER SERVLET (front controller)
   → Receives the filtered request
   → Calls HandlerMapping to find which method handles this URL

4. HANDLER MAPPING
   → Scans @RequestMapping annotations
   → Matches: GET /api/users/{id} → UserController.getUser(Long id)
   → Returns HandlerExecutionChain (handler + interceptors)

5. HANDLER INTERCEPTORS (preHandle)
   → Logging interceptor, rate limiting, etc.
   → If any returns false → request rejected (no controller call)

6. HANDLER ADAPTER
   → Resolves method arguments:
     @PathVariable("id") → extracts "123" from URL → converts to Long
     @RequestBody → reads request body → Jackson deserializes to object
     @RequestHeader → extracts specific header value
   → Calls UserController.getUser(123L)

7. YOUR CONTROLLER METHOD EXECUTES
   → Calls service → calls repository → returns User object

8. RETURN VALUE HANDLING
   → @RestController = @Controller + @ResponseBody
   → HttpMessageConverter (Jackson) converts User → JSON
   → Sets Content-Type: application/json

9. HANDLER INTERCEPTORS (postHandle)
   → Can modify the response

10. RESPONSE SENT
    → Tomcat writes HTTP response to socket
    → Thread-42 returned to pool
```

```
If EXCEPTION occurs at step 7:
═══════════════════════════════
→ @ExceptionHandler in controller? → use it
→ @ControllerAdvice with @ExceptionHandler? → use it
→ Neither? → Spring's DefaultHandlerExceptionResolver
→ Returns error response (JSON for REST, HTML for browser)
```

---

**Q54. How does Spring Boot handle logging by default?**

**Answer:**

```
Default Setup (zero configuration):
════════════════════════════════════
spring-boot-starter → spring-boot-starter-logging
  → Logback (implementation)
  → SLF4J (API/facade)
  → Bridges for JUL, Log4j, JCL (redirect all to SLF4J)

Your code uses SLF4J API:
  private static final Logger log = LoggerFactory.getLogger(MyClass.class);
  log.info("User {} logged in", userId);

SLF4J routes to Logback (the actual logging engine).
```

```
Default Log Format:
═══════════════════
2024-01-15T10:30:00.123+05:30  INFO 12345 --- [main] c.e.myapp.MyApp : Started MyApp in 2.5s
│                               │    │       │      │                  │
│                               │    │       │      │                  └─ Message
│                               │    │       │      └─ Logger name (abbreviated)
│                               │    │       └─ Thread name
│                               │    └─ Process ID
│                               └─ Log level
└─ Timestamp (ISO 8601)

Default level: INFO (DEBUG and TRACE are hidden)
```

```
Customization (via properties — no XML needed):
════════════════════════════════════════════════
# Change root log level
logging.level.root=WARN

# Change specific package level
logging.level.com.example.myapp=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG

# Log to file
logging.file.name=app.log
logging.file.path=/var/log/myapp/

# Custom format
logging.pattern.console=%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n

# File rotation
logging.logback.rollingpolicy.max-file-size=10MB
logging.logback.rollingpolicy.max-history=30
```

```
Switching to Log4j2:
═══════════════════
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

---

**Q55. How does Spring Boot decide server port priority?**

**Answer:**

```
Port Resolution Order (highest priority wins):
═══════════════════════════════════════════════

1. Command line:     java -jar app.jar --server.port=9090      → 9090
2. JVM property:     java -Dserver.port=9090 -jar app.jar      → 9090
3. Env variable:     SERVER_PORT=9090 java -jar app.jar         → 9090
4. Profile config:   application-prod.properties: server.port=9090
5. Default config:   application.properties: server.port=8080
6. Programmatic:     SpringApplication.setDefaultProperties()
7. Spring default:   (nothing configured)                       → 8080

Special values:
  server.port=0       → Random available port (useful for testing)
  server.port=-1      → Disable HTTP (just load context, no server)

// Get the actual port at runtime (especially when port=0):
@Value("${local.server.port}")
private int port;

// Or via event:
@EventListener
public void onReady(WebServerInitializedEvent event) {
    int port = event.getWebServer().getPort();
}
```

---

**Q56. How does Spring Boot create DataSource automatically?**

**Answer:**

```java
// Just add these to application.properties:
// spring.datasource.url=jdbc:mysql://localhost/mydb
// spring.datasource.username=root
// spring.datasource.password=secret
// spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver  (optional — auto-detected)

// And add the dependency:
// <dependency>
//     <groupId>com.mysql</groupId>
//     <artifactId>mysql-connector-j</artifactId>
// </dependency>
```

```
What happens internally:
════════════════════════

1. DataSourceAutoConfiguration activates:
   @ConditionalOnClass(DataSource.class) → TRUE (from JDBC starter)

2. Detects connection pool library on classpath:
   Priority: HikariCP (default) > Tomcat Pool > DBCP2 > Oracle UCP

   Spring Boot includes HikariCP by default with spring-boot-starter-data-jpa

3. DataSourceProperties reads your spring.datasource.* properties

4. Creates HikariDataSource with your URL/username/password:
   @Bean
   @ConditionalOnMissingBean(DataSource.class)  // Only if YOU didn't define one
   public HikariDataSource dataSource(DataSourceProperties properties) {
       HikariDataSource ds = properties.initializeDataSourceBuilder()
           .type(HikariDataSource.class)
           .build();
       // Default pool settings:
       //   maximumPoolSize = 10
       //   minimumIdle = 10
       //   connectionTimeout = 30000 (30s)
       //   idleTimeout = 600000 (10 min)
       //   maxLifetime = 1800000 (30 min)
       return ds;
   }

5. If driver-class-name not set:
   → Spring deduces from URL:
     jdbc:mysql://    → com.mysql.cj.jdbc.Driver
     jdbc:postgresql: → org.postgresql.Driver
     jdbc:h2:         → org.h2.Driver

6. If H2/HSQLDB on classpath but NO url configured:
   → Creates embedded in-memory database automatically!
   → jdbc:h2:mem:testdb (random name)
```

---

**Q57. What happens if you exclude an auto-configuration class?**

**Answer:**

```java
// Three ways to exclude:

// Way 1: On @SpringBootApplication
@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    SecurityAutoConfiguration.class
})
public class MyApp { }

// Way 2: In properties
// spring.autoconfigure.exclude=\
//   org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
//   org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration

// Way 3: @EnableAutoConfiguration (less common)
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
```

```
What happens when excluded:
═══════════════════════════
The class is completely SKIPPED during auto-configuration phase.
None of its @Bean methods execute. No beans it would have created exist.

Common use cases:
  ✗ Exclude DataSourceAutoConfiguration when you don't need a DB
    (e.g., a service that only calls APIs, no database)
  ✗ Exclude SecurityAutoConfiguration in dev/testing
  ✗ Exclude a third-party auto-config that conflicts with your custom config

Warning:
  If other auto-configs DEPEND on the excluded one, they may also fail.
  Example: Excluding DataSource → JPA auto-config fails too
  → You'll see: "Consider defining a bean of type DataSource in your configuration"
```

---

**Q58. Why is Spring Boot preferred for cloud-native applications?**

**Answer:**

```
Cloud-Native Requirements → Spring Boot Features:
═══════════════════════════════════════════════════

1. CONTAINERIZATION
   → Fat JAR = single artifact, runs anywhere with JRE
   → Buildpacks: mvn spring-boot:build-image (Docker image without Dockerfile)
   → Layered JARs for efficient Docker layer caching

2. EXTERNALIZED CONFIGURATION
   → 12-Factor App: config from environment variables
   → Spring Boot reads OS env vars, config maps, secrets
   → Profile-based config: -Dspring.profiles.active=prod

3. HEALTH & OBSERVABILITY
   → Actuator: /health, /metrics, /info endpoints out of box
   → Micrometer: metrics export to Prometheus, Datadog, etc.
   → Distributed tracing integration (Zipkin, Jaeger)
   → Kubernetes-ready probes: /health/liveness, /health/readiness

4. SERVICE DISCOVERY & COMMUNICATION
   → Spring Cloud: Eureka, Consul, Kubernetes-native discovery
   → OpenFeign: declarative REST clients
   → Load balancing: Spring Cloud LoadBalancer

5. RESILIENCE
   → Resilience4j: circuit breaker, retry, bulkhead, rate limiter
   → Spring Cloud Circuit Breaker abstraction

6. FAST STARTUP (GraalVM)
   → Native compilation: startup in 50ms instead of 5 seconds
   → Low memory footprint: perfect for serverless (AWS Lambda)

7. MICROSERVICE PATTERNS
   → Spring Cloud Gateway (API gateway)
   → Spring Cloud Config (centralized configuration)
   → Spring Cloud Stream (event-driven with Kafka/RabbitMQ)
```

---

**Q59. What are the most common Spring Boot performance mistakes?**

**Answer:**

```
Top 10 Performance Mistakes:
════════════════════════════

1. OPEN-IN-VIEW = TRUE (default!)
   ─────────────────────────────
   spring.jpa.open-in-view=true  ← DEFAULT, holds DB connection for entire request
   A Hibernate session stays open through the controller layer
   → DB connection held while rendering JSON → pool exhaustion under load
   Fix: spring.jpa.open-in-view=false (ALWAYS set this in production)

2. N+1 QUERY PROBLEM
   ──────────────────
   @OneToMany(fetch = FetchType.EAGER)  ← Loads ALL children with every parent
   100 orders → 1 query for orders + 100 queries for items = 101 queries!
   Fix: FetchType.LAZY + @EntityGraph or JOIN FETCH in JPQL

3. NOT SETTING CONNECTION POOL SIZE
   ─────────────────────────────────
   Default HikariCP: maximumPoolSize = 10
   200 Tomcat threads competing for 10 DB connections → massive queuing
   Fix: spring.datasource.hikari.maximum-pool-size=20 (tune per workload)

4. SYNCHRONOUS EXTERNAL CALLS
   ──────────────────────────
   Calling 3 external APIs sequentially: 200ms + 300ms + 500ms = 1 second
   Fix: CompletableFuture.allOf() or WebClient for parallel calls

5. NO CACHING
   ──────────
   Same DB query executed 1000 times/minute for rarely-changing data
   Fix: @Cacheable with Spring Cache + Redis/Caffeine

6. JACKSON SERIALIZATION OF ENTIRE ENTITY
   ──────────────────────────────────────
   @RestController returns JPA entity directly → serializes ALL fields
   → Lazy-loaded collections trigger N+1 (LazyInitializationException or extra queries)
   → Circular references → StackOverflowError
   Fix: Use DTOs, never return entities from controllers

7. LOGGING TOO MUCH
   ─────────────────
   log.debug("Processing: " + expensiveObject.toString());
   Even if DEBUG is off, the string concatenation STILL happens!
   Fix: log.debug("Processing: {}", expensiveObject);  // Lazy evaluation

8. NOT CONFIGURING THREAD POOLS
   ─────────────────────────────
   @Async uses SimpleAsyncTaskExecutor by default → creates new thread per task!
   Fix: Define ThreadPoolTaskExecutor bean

9. LARGE PAYLOAD WITHOUT PAGINATION
   ──────────────────────────────
   findAll() returns 100,000 rows → OOM or extreme latency
   Fix: Always use Pageable: findAll(PageRequest.of(0, 20))

10. MISSING INDEXES + NO QUERY MONITORING
    ────────────────────────────────────
    Fix: spring.jpa.properties.hibernate.generate_statistics=true
         spring.jpa.show-sql=true (dev only)
         Use slow query log in production
```

---

**Q60. What happens internally when @RestController vs @Controller is used?**

**Answer:**

```java
// @Controller — returns a VIEW NAME (for server-side rendering)
@Controller
public class PageController {
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("name", "Veer");
        return "home";  // → ViewResolver finds templates/home.html
        // Thymeleaf/FreeMarker renders HTML → sent to browser
    }
}

// @RestController = @Controller + @ResponseBody on EVERY method
@RestController  // This annotation...
public class ApiController {
    @GetMapping("/api/users")
    public List<User> getUsers() {
        return userService.findAll();
        // Return value → HttpMessageConverter (Jackson) → JSON response
        // No ViewResolver involved at all
    }
}

// Is identical to:
@Controller
public class ApiController {
    @GetMapping("/api/users")
    @ResponseBody  // ...putting this on every method
    public List<User> getUsers() {
        return userService.findAll();
    }
}
```

```
Internal Flow Difference:
═════════════════════════

@Controller (without @ResponseBody):
  Return "home" → DispatcherServlet → ViewResolver → Template engine → HTML

@RestController (or @ResponseBody):
  Return Object → DispatcherServlet → HttpMessageConverter → JSON/XML

How Spring decides:
  1. Method returns String + no @ResponseBody → treat as view name
  2. Method has @ResponseBody → use HttpMessageConverter
  3. @RestController → @ResponseBody is implicit on ALL methods
```

---

**Q61. How does Spring Boot support microservices so easily?**

**Answer:**

```
Spring Boot alone gives you:
════════════════════════════
✓ Standalone executable JAR (no external server needed)
✓ Embedded server (Tomcat/Jetty/Netty)
✓ REST API support (Jackson, validation, exception handling)
✓ Health endpoints (Actuator)
✓ Externalized config (env vars, profiles)
✓ Fast startup + Docker-friendly

Spring Cloud adds microservice patterns:
════════════════════════════════════════
┌──────────────────────────┬──────────────────────────────────┐
│ Pattern                  │ Spring Cloud Component           │
├──────────────────────────┼──────────────────────────────────┤
│ Service Discovery        │ Eureka, Consul, Kubernetes       │
│ API Gateway              │ Spring Cloud Gateway             │
│ Load Balancing           │ Spring Cloud LoadBalancer         │
│ Circuit Breaker          │ Resilience4j                     │
│ Centralized Config       │ Spring Cloud Config Server       │
│ Distributed Tracing      │ Micrometer Tracing + Zipkin      │
│ Event-Driven Messaging   │ Spring Cloud Stream (Kafka/RMQ)  │
│ Distributed Transactions │ Saga pattern (manual/Axon)       │
│ Security                 │ Spring Security + OAuth2/JWT     │
└──────────────────────────┴──────────────────────────────────┘

Each microservice is just a Spring Boot app with specific starters added.
```

---

**End of Spring Boot Guide**

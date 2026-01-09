# Spring Boot - Complete Interview Guide

## Table of Contents
1. [Spring Boot Fundamentals](#spring-boot-fundamentals)
2. [Spring Data JPA & Hibernate](#spring-data-jpa--hibernate)
3. [Spring Security](#spring-security)
4. [Microservices Architecture](#microservices-architecture)
5. [Testing](#testing)
6. [Performance & Best Practices](#performance--best-practices)

---

# Spring Boot Fundamentals

## Spring Boot IoC Container Architecture

### How IoC Container Works

```
┌─────────────────────────────────────────────────────────────────────┐
│                    Spring Boot Application Startup                   │
└───────────────────────────────┬─────────────────────────────────────┘
                                │
                    ┌───────────▼──────────┐
                    │ @SpringBootApplication│
                    │  - @Configuration     │
                    │  - @ComponentScan     │
                    │  - @EnableAutoConfig  │
                    └───────────┬───────────┘
                                │
        ┌───────────────────────┼───────────────────────┐
        │                       │                       │
        ▼                       ▼                       ▼
┌───────────────┐    ┌──────────────────┐    ┌──────────────────┐
│Component Scan │    │Auto-Configuration│    │Property Binding  │
│               │    │                  │    │                  │
│- @Component   │    │- Conditional     │    │- application.yml │
│- @Service     │    │- Starters        │    │- @Value          │
│- @Repository  │    │- Classpath based │    │- @ConfigProp     │
│- @Controller  │    └────────┬─────────┘    └──────────────────┘
└───────┬───────┘             │
        │                     │
        └─────────────┬───────┘
                      │
            ┌─────────▼──────────┐
            │  IoC Container     │
            │  (ApplicationContext)│
            └─────────┬──────────┘
                      │
        ┌─────────────┴─────────────┐
        │                           │
        ▼                           ▼
┌───────────────┐          ┌────────────────┐
│Bean Definition│          │Bean Instantiation│
│Registry       │          │                │
│               │          │1. Create object│
│- Bean names   │─────────▶│2. Inject deps  │
│- Bean classes │          │3. Initialize   │
│- Dependencies │          │4. Ready to use │
└───────────────┘          └────────┬───────┘
                                    │
                           ┌────────▼────────┐
                           │   Bean Pool     │
                           │                 │
                           │ Singleton Beans │
                           │ Prototype Beans │
                           └─────────────────┘
```

### IoC Container Workflow

```
Step 1: Component Scanning
┌─────────────────────────────────────────┐
│ Spring scans base package & sub-packages│
│ Looking for:                            │
│  - @Component                           │
│  - @Service                             │
│  - @Repository                          │
│  - @Controller                          │
│  - @Configuration                       │
└────────────────┬────────────────────────┘
                 │
                 ▼
Step 2: Bean Definition Registration
┌─────────────────────────────────────────┐
│ For each annotated class:               │
│  - Create BeanDefinition                │
│  - Store in BeanDefinitionRegistry      │
│  - Analyze dependencies                 │
└────────────────┬────────────────────────┘
                 │
                 ▼
Step 3: Dependency Resolution
┌─────────────────────────────────────────┐
│ For each bean:                          │
│  - Check constructor parameters         │
│  - Check @Autowired fields/setters      │
│  - Resolve dependencies from registry   │
└────────────────┬────────────────────────┘
                 │
                 ▼
Step 4: Bean Instantiation
┌─────────────────────────────────────────┐
│ Create beans in correct order:          │
│  1. No-dependency beans first           │
│  2. Then beans with resolved deps       │
│  3. Call constructors                   │
│  4. Inject dependencies                 │
└────────────────┬────────────────────────┘
                 │
                 ▼
Step 5: Bean Initialization
┌─────────────────────────────────────────┐
│ For each bean:                          │
│  1. Call @PostConstruct methods         │
│  2. Call afterPropertiesSet()           │
│  3. Call custom init methods            │
└────────────────┬────────────────────────┘
                 │
                 ▼
Step 6: Application Ready
┌─────────────────────────────────────────┐
│ All beans available in context          │
│ Application ready to serve requests     │
└─────────────────────────────────────────┘
```

### Example: Bean Creation Flow

```java
// Step 1: Define beans
@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}

@Repository
public class UserRepository {
    // No dependencies
}
```

```
IoC Container Process:
══════════════════════

1. Component Scan finds:
   ├── UserService (@Service)
   └── UserRepository (@Repository)

2. Create Bean Definitions:
   ├── UserService (depends on UserRepository)
   └── UserRepository (no dependencies)

3. Resolve Dependencies:
   └── UserService needs UserRepository
       └── UserRepository found in registry ✓

4. Instantiate Beans (correct order):
   ├── Step 1: Create UserRepository
   │          └── new UserRepository()
   │
   └── Step 2: Create UserService
              └── new UserService(userRepository)

5. Beans Ready:
   ├── userRepository bean → Singleton
   └── userService bean → Singleton
```

---

## Spring MVC - DispatcherServlet Architecture

### Complete Request Flow

```
┌─────────────────────────────────────────────────────────────────────┐
│                           CLIENT REQUEST                            │
│                    GET /api/users/123                               │
└───────────────────────────────┬─────────────────────────────────────┘
                                │
                    ┌───────────▼──────────┐
                    │  DispatcherServlet   │  ← Front Controller
                    │  (Entry Point)       │
                    └───────────┬──────────┘
                                │
                    ┌───────────▼──────────┐
                    │  1. Handler Mapping  │
                    │                      │
                    │  - Maps URL to       │
                    │    Controller method │
                    │  - Returns           │
                    │    HandlerExecutor   │
                    └───────────┬──────────┘
                                │
                    Found: UserController.getUserById(123)
                                │
                    ┌───────────▼──────────┐
                    │  2. Handler Adapter  │
                    │                      │
                    │  - Invokes actual    │
                    │    controller method │
                    │  - Handles arguments │
                    │  - Converts request  │
                    └───────────┬──────────┘
                                │
                    ┌───────────▼──────────┐
                    │  3. Controller       │
                    │  @RestController     │
                    │                      │
                    │  getUserById(123)    │
                    │    ├─ Call Service   │
                    │    ├─ Get Data       │
                    │    └─ Return User    │
                    └───────────┬──────────┘
                                │
                    Returns: User object
                                │
                    ┌───────────▼──────────┐
                    │  4. ViewResolver     │
                    │  (for @Controller)   │
                    │                      │
                    │  OR                  │
                    │                      │
                    │  HttpMessageConverter│
                    │  (for @RestController)│
                    │                      │
                    │  - Converts object   │
                    │    to JSON           │
                    └───────────┬──────────┘
                                │
                    ┌───────────▼──────────┐
                    │  5. Response         │
                    │                      │
                    │  HTTP 200 OK         │
                    │  Content-Type:       │
                    │    application/json  │
                    │                      │
                    │  {"id":123,          │
                    │   "name":"John"}     │
                    └───────────┬──────────┘
                                │
                                ▼
                    ┌──────────────────────┐
                    │   CLIENT RESPONSE    │
                    └──────────────────────┘
```

### Detailed DispatcherServlet Flow

```
┌────────────────────────────────────────────────────────────────────┐
│                     REQUEST PROCESSING FLOW                         │
└────────────────────────────────────────────────────────────────────┘

Request: GET /api/users/123
         ↓
┌──────────────────────────────────────────────────────────────┐
│ STEP 1: DispatcherServlet receives request                   │
│ ─────────────────────────────────────────────────────────── │
│ • Request URL: /api/users/123                                │
│ • HTTP Method: GET                                           │
│ • Headers: Accept: application/json                         │
└────────────────────────────┬─────────────────────────────────┘
                             │
         ┌───────────────────▼────────────────────┐
         │ STEP 2: Handler Mapping                │
         │ ────────────────────────────────────── │
         │                                        │
         │ RequestMappingHandlerMapping looks for:│
         │                                        │
         │ @GetMapping("/api/users/{id}")        │
         │ public User getUserById(@PathVariable) │
         │                                        │
         │ Found:                                 │
         │ • Controller: UserController           │
         │ • Method: getUserById                  │
         │ • Parameters: id=123                   │
         └────────────────┬───────────────────────┘
                          │
         ┌────────────────▼───────────────────────┐
         │ STEP 3: Handler Adapter                │
         │ ────────────────────────────────────── │
         │                                        │
         │ RequestMappingHandlerAdapter:          │
         │                                        │
         │ 1. Resolve method arguments:           │
         │    • @PathVariable id = 123            │
         │    • @RequestBody (if present)         │
         │    • @RequestParam (if present)        │
         │                                        │
         │ 2. Invoke controller method:           │
         │    userController.getUserById(123)     │
         └────────────────┬───────────────────────┘
                          │
         ┌────────────────▼───────────────────────┐
         │ STEP 4: Controller Execution            │
         │ ────────────────────────────────────── │
         │                                        │
         │ @RestController                        │
         │ @RequestMapping("/api/users")          │
         │ public class UserController {          │
         │                                        │
         │   @GetMapping("/{id}")                 │
         │   public User getUserById(             │
         │       @PathVariable Long id) {         │
         │                                        │
         │     // 1. Call service layer           │
         │     User user = userService            │
         │                   .getUserById(123);   │
         │                                        │
         │     // 2. Return user object           │
         │     return user;                       │
         │   }                                    │
         │ }                                      │
         │                                        │
         │ Returns: User{id=123, name="John"}    │
         └────────────────┬───────────────────────┘
                          │
         ┌────────────────▼───────────────────────┐
         │ STEP 5: Message Converter               │
         │ ────────────────────────────────────── │
         │                                        │
         │ @RestController uses:                  │
         │ HttpMessageConverter                   │
         │                                        │
         │ MappingJackson2HttpMessageConverter:   │
         │                                        │
         │ User object → JSON                     │
         │                                        │
         │ {                                      │
         │   "id": 123,                           │
         │   "name": "John",                      │
         │   "email": "john@example.com"          │
         │ }                                      │
         └────────────────┬───────────────────────┘
                          │
         ┌────────────────▼───────────────────────┐
         │ STEP 6: Response                        │
         │ ────────────────────────────────────── │
         │                                        │
         │ HTTP/1.1 200 OK                        │
         │ Content-Type: application/json         │
         │ Content-Length: 67                     │
         │                                        │
         │ {                                      │
         │   "id": 123,                           │
         │   "name": "John",                      │
         │   "email": "john@example.com"          │
         │ }                                      │
         └────────────────────────────────────────┘
```

### Handler Mapping Process

```
┌─────────────────────────────────────────────────────────────┐
│              Handler Mapping Registry                        │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  URL Pattern              Controller Method                 │
│  ──────────────────────────────────────────────────────    │
│                                                              │
│  GET /api/users        →  UserController.getAllUsers()      │
│  GET /api/users/{id}   →  UserController.getUserById()      │
│  POST /api/users       →  UserController.createUser()       │
│  PUT /api/users/{id}   →  UserController.updateUser()       │
│  DELETE /api/users/{id}→  UserController.deleteUser()       │
│                                                              │
└─────────────────────────────────────────────────────────────┘

Request: GET /api/users/123
                ↓
┌───────────────────────────────────┐
│ Handler Mapping Lookup:           │
│                                   │
│ 1. Parse URL: /api/users/123     │
│                                   │
│ 2. Match pattern:                 │
│    /api/users/{id} ✓              │
│                                   │
│ 3. Extract variables:             │
│    id = 123                       │
│                                   │
│ 4. Found handler:                 │
│    UserController.getUserById()  │
│                                   │
│ 5. Return HandlerExecutionChain:  │
│    - Handler method               │
│    - Interceptors (if any)        │
└───────────────────────────────────┘
```

### MVC vs REST Controller

```
┌──────────────────────────────────────────────────────────────┐
│                     @Controller (MVC)                         │
├──────────────────────────────────────────────────────────────┤
│                                                               │
│  Request → DispatcherServlet → Controller                    │
│               ↓                     ↓                         │
│          Returns View Name    (e.g., "home")                 │
│               ↓                                               │
│          ViewResolver                                         │
│               ↓                                               │
│          home.html / home.jsp                                │
│               ↓                                               │
│          Rendered HTML                                        │
│               ↓                                               │
│          Response (HTML page)                                 │
│                                                               │
└──────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│                  @RestController (REST API)                   │
├──────────────────────────────────────────────────────────────┤
│                                                               │
│  Request → DispatcherServlet → RestController                │
│               ↓                     ↓                         │
│          Returns Object       (e.g., User object)            │
│               ↓                                               │
│          HttpMessageConverter                                 │
│               ↓                                               │
│          Object → JSON                                        │
│               ↓                                               │
│          Response (JSON data)                                 │
│                                                               │
└──────────────────────────────────────────────────────────────┘
```

### Complete Example Flow

```java
// 1. Controller
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}

// 2. Service
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}

// 3. Repository
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA provides implementation
}
```

```
Flow Diagram:
════════════

Client
  │
  │ GET /api/users/123
  ▼
DispatcherServlet
  │
  │ 1. Find handler
  ▼
HandlerMapping
  │ Found: UserController.getUserById(123)
  ▼
HandlerAdapter
  │
  │ 2. Invoke method
  ▼
UserController.getUserById(123)
  │
  │ 3. Call service
  ▼
UserService.getUserById(123)
  │
  │ 4. Query database
  ▼
UserRepository.findById(123)
  │
  │ 5. Return entity
  ▼
UserService (return User object)
  │
  ▼
UserController (return ResponseEntity<User>)
  │
  │ 6. Convert to JSON
  ▼
HttpMessageConverter
  │ User object → JSON
  ▼
DispatcherServlet
  │
  │ 7. Send response
  ▼
Client
  │
  │ {"id":123,"name":"John","email":"john@example.com"}
  ▼
```

---

## Embedded Tomcat Architecture

### Where Tomcat Fits in Spring Boot

```
┌─────────────────────────────────────────────────────────────────────┐
│                   SPRING BOOT APPLICATION                            │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  ┌────────────────────────────────────────────────────────────┐   │
│  │              Spring Boot Auto-Configuration                 │   │
│  │  - Detects spring-boot-starter-web in classpath            │   │
│  │  - Auto-configures Embedded Tomcat                         │   │
│  │  - Creates DispatcherServlet bean                          │   │
│  │  - Registers servlet mappings                              │   │
│  └────────────────────────────────────────────────────────────┘   │
│                              ↓                                      │
│  ┌────────────────────────────────────────────────────────────┐   │
│  │                 EMBEDDED TOMCAT SERVER                      │   │
│  │                   (Servlet Container)                       │   │
│  ├────────────────────────────────────────────────────────────┤   │
│  │                                                             │   │
│  │  Port: 8080 (default)                                      │   │
│  │  Thread Pool: 200 threads (default)                        │   │
│  │  Max Connections: 8192 (default)                           │   │
│  │                                                             │   │
│  │  ┌──────────────────────────────────────────────────┐     │   │
│  │  │         Tomcat Connector (HTTP/1.1)              │     │   │
│  │  │  - Listens on port 8080                          │     │   │
│  │  │  - Accepts incoming connections                  │     │   │
│  │  │  - Manages connection pool                       │     │   │
│  │  └────────────────┬─────────────────────────────────┘     │   │
│  │                   │                                        │   │
│  │  ┌────────────────▼─────────────────────────────────┐     │   │
│  │  │         Tomcat Thread Pool (Executor)            │     │   │
│  │  │                                                   │     │   │
│  │  │  ┌─────────┐ ┌─────────┐ ┌─────────┐           │     │   │
│  │  │  │Thread-1 │ │Thread-2 │ │Thread-N │ ... (200) │     │   │
│  │  │  └─────────┘ └─────────┘ └─────────┘           │     │   │
│  │  │                                                   │     │   │
│  │  │  Each thread handles one request at a time      │     │   │
│  │  └────────────────┬─────────────────────────────────┘     │   │
│  │                   │                                        │   │
│  │  ┌────────────────▼─────────────────────────────────┐     │   │
│  │  │         Servlet Container (Tomcat Engine)        │     │   │
│  │  │  - Request parsing                               │     │   │
│  │  │  - Session management                            │     │   │
│  │  │  - Security handling                             │     │   │
│  │  └────────────────┬─────────────────────────────────┘     │   │
│  │                   │                                        │   │
│  │  ┌────────────────▼─────────────────────────────────┐     │   │
│  │  │         DispatcherServlet (registered)           │     │   │
│  │  │  - URL: /* (all requests)                        │     │   │
│  │  │  - Front Controller for Spring MVC               │     │   │
│  │  └──────────────────────────────────────────────────┘     │   │
│  │                                                             │   │
│  └─────────────────────────────────────────────────────────────┘  │
│                              ↓                                     │
│  ┌────────────────────────────────────────────────────────────┐   │
│  │              SPRING MVC COMPONENTS                          │   │
│  │  - Controllers                                              │   │
│  │  - Services                                                 │   │
│  │  - Repositories                                             │   │
│  └─────────────────────────────────────────────────────────────┘  │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

### Startup Sequence

```
Application Startup:
═══════════════════

1. SpringApplication.run(Application.class, args)
   │
   ▼
2. Spring Boot detects spring-boot-starter-web
   │
   ▼
3. ServletWebServerFactoryAutoConfiguration
   │
   ▼
4. Creates TomcatServletWebServerFactory
   │
   ▼
5. TomcatServletWebServerFactory.getWebServer()
   │
   ▼
6. Creates Tomcat instance
   ├─ Set port (8080)
   ├─ Configure thread pool (min: 10, max: 200)
   ├─ Set connection timeout
   └─ Create Connector
   │
   ▼
7. Tomcat.start()
   ├─ Start connector
   ├─ Bind to port 8080
   └─ Start accepting connections
   │
   ▼
8. Register DispatcherServlet
   ├─ Map to "/*" (all URLs)
   └─ Initialize Spring MVC
   │
   ▼
9. Application ready to serve requests
   │
   ▼
   Listening on http://localhost:8080
```

---

## Multi-Threading: How DispatcherServlet Handles Multiple Requests

### Thread-Per-Request Model

```
┌───────────────────────────────────────────────────────────────────┐
│                    MULTIPLE CLIENT REQUESTS                        │
└───────────────────────────────────────────────────────────────────┘
     │              │              │              │
     │ Request 1    │ Request 2    │ Request 3    │ Request N
     │ GET /users   │ POST /users  │ GET /orders  │ DELETE /users/5
     │              │              │              │
     ▼              ▼              ▼              ▼
┌─────────────────────────────────────────────────────────────────────┐
│                      TOMCAT CONNECTOR                               │
│                   (Accepts all connections)                         │
│                                                                     │
│  ┌──────────────────────────────────────────────────────────────┐ │
│  │              Connection Queue (Accept Queue)                  │ │
│  │  ┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐               │ │
│  │  │ Conn 1 │ │ Conn 2 │ │ Conn 3 │ │ Conn N │ ...           │ │
│  │  └────────┘ └────────┘ └────────┘ └────────┘               │ │
│  └──────────────────────────────────────────────────────────────┘ │
└──────────────────────────────┬──────────────────────────────────────┘
                               │
                               │ Assign to available threads
                               ▼
┌─────────────────────────────────────────────────────────────────────┐
│                    TOMCAT THREAD POOL (Executor)                    │
│                                                                     │
│  Configuration:                                                     │
│  ├─ Min threads: 10                                                │
│  ├─ Max threads: 200                                               │
│  └─ Queue size: Unlimited                                          │
│                                                                     │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐            │
│  │  Thread-1    │  │  Thread-2    │  │  Thread-3    │  ...       │
│  │  [BUSY]      │  │  [BUSY]      │  │  [BUSY]      │            │
│  │              │  │              │  │              │            │
│  │ Processing   │  │ Processing   │  │ Processing   │            │
│  │ Request 1    │  │ Request 2    │  │ Request 3    │            │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘            │
│         │                 │                 │                     │
└─────────┼─────────────────┼─────────────────┼─────────────────────┘
          │                 │                 │
          ▼                 ▼                 ▼
┌─────────────────────────────────────────────────────────────────────┐
│              DISPATCHER SERVLET (One instance, shared)              │
│                      (Thread-Safe Singleton)                        │
│                                                                     │
│  Each thread gets its own:                                          │
│  ├─ HttpServletRequest                                             │
│  ├─ HttpServletResponse                                            │
│  ├─ Thread-local variables                                         │
│  └─ Call stack                                                     │
│                                                                     │
│  Shared (thread-safe):                                             │
│  ├─ HandlerMapping                                                 │
│  ├─ HandlerAdapter                                                 │
│  └─ ViewResolver                                                   │
└──────────────┬──────────────┬──────────────┬─────────────────────────┘
               │              │              │
               ▼              ▼              ▼
     ┌─────────────┐  ┌─────────────┐  ┌─────────────┐
     │ Controller  │  │ Controller  │  │ Controller  │
     │ Thread-1    │  │ Thread-2    │  │ Thread-3    │
     └──────┬──────┘  └──────┬──────┘  └──────┬──────┘
            │                │                │
            ▼                ▼                ▼
     ┌─────────────┐  ┌─────────────┐  ┌─────────────┐
     │ Service     │  │ Service     │  │ Service     │
     │ Thread-1    │  │ Thread-2    │  │ Thread-3    │
     └──────┬──────┘  └──────┬──────┘  └──────┬──────┘
            │                │                │
            ▼                ▼                ▼
     ┌─────────────┐  ┌─────────────┐  ┌─────────────┐
     │ Repository  │  │ Repository  │  │ Repository  │
     │ (DB call)   │  │ (DB call)   │  │ (DB call)   │
     └──────┬──────┘  └──────┬──────┘  └──────┬──────┘
            │                │                │
            │ Response       │ Response       │ Response
            ▼                ▼                ▼
     ┌─────────────┐  ┌─────────────┐  ┌─────────────┐
     │  Client 1   │  │  Client 2   │  │  Client 3   │
     └─────────────┘  └─────────────┘  └─────────────┘
```

### Detailed Request Handling with Threads

```
Time: t0 - Three requests arrive simultaneously
═════════════════════════════════════════════

Request A: GET  /api/users/1
Request B: POST /api/users
Request C: GET  /api/users/2

┌────────────────────────────────────────────────────────────────┐
│                    TOMCAT THREAD POOL                          │
├────────────────────────────────────────────────────────────────┤
│                                                                │
│  ┌─────────────────────┐  ┌─────────────────────┐            │
│  │    Thread-1         │  │    Thread-2         │            │
│  │    [IDLE] → [BUSY]  │  │    [IDLE] → [BUSY]  │            │
│  │                     │  │                     │            │
│  │  Assigned Request A │  │  Assigned Request B │            │
│  └─────────────────────┘  └─────────────────────┘            │
│                                                                │
│  ┌─────────────────────┐  ┌─────────────────────┐            │
│  │    Thread-3         │  │    Thread-4         │            │
│  │    [IDLE] → [BUSY]  │  │    [IDLE]           │            │
│  │                     │  │                     │            │
│  │  Assigned Request C │  │  Waiting...         │            │
│  └─────────────────────┘  └─────────────────────┘            │
│                                                                │
│  ... (up to 200 threads)                                      │
│                                                                │
└────────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────────┐
│               CONCURRENT REQUEST PROCESSING                     │
├────────────────────────────────────────────────────────────────┤
│                                                                │
│  Thread-1 (Request A):        Thread-2 (Request B):           │
│  ═════════════════════        ═════════════════════           │
│                                                                │
│  1. DispatcherServlet         1. DispatcherServlet            │
│     receives request              receives request            │
│     ↓                             ↓                           │
│  2. HandlerMapping            2. HandlerMapping               │
│     finds: getUserById(1)        finds: createUser()          │
│     ↓                             ↓                           │
│  3. Invoke controller         3. Invoke controller            │
│     ↓                             ↓                           │
│  4. userService               4. userService                  │
│     .getUserById(1)              .createUser(user)            │
│     ↓                             ↓                           │
│  5. userRepository            5. userRepository               │
│     .findById(1)                 .save(user)                  │
│     ↓                             ↓                           │
│  6. Database query            6. Database insert              │
│     SELECT * FROM users          INSERT INTO users            │
│     WHERE id = 1                 VALUES (...)                 │
│     ↓                             ↓                           │
│  7. Return User object        7. Return saved User            │
│     ↓                             ↓                           │
│  8. Convert to JSON           8. Convert to JSON              │
│     ↓                             ↓                           │
│  9. Send response (200)       9. Send response (201)          │
│                                                                │
│  Thread-3 (Request C):                                        │
│  ═════════════════════                                        │
│                                                                │
│  1. DispatcherServlet receives request                        │
│  2. HandlerMapping finds: getUserById(2)                      │
│  3. Invoke controller                                         │
│  4. userService.getUserById(2)                                │
│  5. userRepository.findById(2)                                │
│  6. Database query: SELECT * FROM users WHERE id = 2          │
│  7. Return User object                                        │
│  8. Convert to JSON                                           │
│  9. Send response (200)                                       │
│                                                                │
└────────────────────────────────────────────────────────────────┘

All three requests processed CONCURRENTLY by different threads!
```

### Thread Pool Configuration

```java
// application.properties
server.tomcat.threads.min-spare=10      // Minimum threads always alive
server.tomcat.threads.max=200           // Maximum threads in pool
server.tomcat.max-connections=8192      // Maximum connections
server.tomcat.accept-count=100          // Queue size when all threads busy
server.tomcat.connection-timeout=20000  // Connection timeout (ms)
```

### What Happens When All Threads Are Busy?

```
Scenario: 200 concurrent requests (all threads busy)
═══════════════════════════════════════════════════

Request 201 arrives:
        ↓
┌────────────────────────────────────────┐
│   Tomcat Connector Accept Queue        │
│   (Queue size: 100)                    │
│                                        │
│   ┌─────────┐ ┌─────────┐            │
│   │Request  │ │Request  │  ...       │
│   │  201    │ │  202    │            │
│   └─────────┘ └─────────┘            │
│                                        │
│   Request waits here until a thread   │
│   becomes available                    │
└────────────────────────────────────────┘
        ↓
   Thread becomes available
        ↓
┌────────────────────────────────────────┐
│   Request 201 gets assigned to         │
│   newly available thread               │
└────────────────────────────────────────┘

If queue is also full (> 100):
        ↓
┌────────────────────────────────────────┐
│   Connection REFUSED                   │
│   Client receives:                     │
│   - Connection timeout                 │
│   - HTTP 503 (Service Unavailable)     │
└────────────────────────────────────────┘
```

### Thread Safety in Spring Beans

```
┌────────────────────────────────────────────────────────────────┐
│              SPRING BEANS (Singleton Scope - Default)          │
├────────────────────────────────────────────────────────────────┤
│                                                                │
│  ONE INSTANCE shared by ALL threads                           │
│                                                                │
│  ┌──────────────────────────────────────────────────────┐    │
│  │  @RestController (Singleton)                         │    │
│  │  public class UserController {                       │    │
│  │                                                       │    │
│  │      // ✅ SAFE: Injected dependency (immutable)     │    │
│  │      private final UserService userService;          │    │
│  │                                                       │    │
│  │      // ❌ UNSAFE: Instance variable (mutable state) │    │
│  │      private int requestCount; // THREAD-UNSAFE!     │    │
│  │                                                       │    │
│  │      @GetMapping("/{id}")                            │    │
│  │      public User getUser(@PathVariable Long id) {    │    │
│  │          // ✅ SAFE: Local variable (thread-local)   │    │
│  │          User user = userService.getUserById(id);    │    │
│  │          return user;                                │    │
│  │      }                                                │    │
│  │  }                                                    │    │
│  └──────────────────────────────────────────────────────┘    │
│                                                                │
└────────────────────────────────────────────────────────────────┘

Thread-Safe Rules:
═══════════════════

✅ SAFE (No shared mutable state):
   - Injected dependencies (final fields)
   - Method parameters
   - Local variables
   - Stateless beans

❌ UNSAFE (Shared mutable state):
   - Instance variables that change
   - Static mutable variables
   - Shared collections without synchronization

Example:

Thread-1:                      Thread-2:
requestCount = 0               requestCount = 0
requestCount++;  // = 1        requestCount++;  // = 1 (LOST UPDATE!)

Both threads read 0, increment to 1, but should be 2!
This is a RACE CONDITION.
```

### Complete Flow: Client to Database

```
┌─────────────┐
│   Client    │
│  (Browser)  │
└──────┬──────┘
       │ HTTP Request: GET /api/users/123
       ▼
┌─────────────────────────────────────────────────────┐
│            EMBEDDED TOMCAT SERVER                    │
│                                                      │
│  ┌────────────────────────────────────────────┐    │
│  │  Connector (Port 8080)                     │    │
│  │  - Accepts TCP connection                  │    │
│  └────────────────┬───────────────────────────┘    │
│                   │                                 │
│  ┌────────────────▼───────────────────────────┐    │
│  │  Thread Pool                               │    │
│  │  - Assigns Thread-5 to handle request     │    │
│  └────────────────┬───────────────────────────┘    │
│                   │                                 │
│  ┌────────────────▼───────────────────────────┐    │
│  │  DispatcherServlet (Thread-5)              │    │
│  │  - Parse request                           │    │
│  │  - Find handler                            │    │
│  └────────────────┬───────────────────────────┘    │
└───────────────────┼─────────────────────────────────┘
                    │
       ┌────────────▼────────────┐
       │  Spring MVC (Thread-5)  │
       └────────────┬────────────┘
                    │
       ┌────────────▼────────────────┐
       │  @RestController (Thread-5) │
       │  UserController             │
       │  .getUserById(123)          │
       └────────────┬────────────────┘
                    │
       ┌────────────▼────────────────┐
       │  @Service (Thread-5)        │
       │  UserService                │
       │  .getUserById(123)          │
       └────────────┬────────────────┘
                    │
       ┌────────────▼────────────────┐
       │  @Repository (Thread-5)     │
       │  UserRepository             │
       │  .findById(123)             │
       └────────────┬────────────────┘
                    │
       ┌────────────▼────────────────┐
       │  Spring Data JPA (Thread-5) │
       │  - Generate SQL             │
       │  - Execute query            │
       └────────────┬────────────────┘
                    │
       ┌────────────▼────────────────┐
       │  HikariCP (Thread-5)        │
       │  Connection Pool            │
       │  - Get DB connection        │
       └────────────┬────────────────┘
                    │
       ┌────────────▼────────────────┐
       │  DATABASE (Thread-5)        │
       │  MySQL/PostgreSQL           │
       │  SELECT * FROM users        │
       │  WHERE id = 123             │
       └────────────┬────────────────┘
                    │ Returns: User{id=123, name="John"}
                    │
       ┌────────────▼────────────────┐
       │  Response flows back        │
       │  through the same path      │
       │  (still Thread-5)           │
       └────────────┬────────────────┘
                    │
┌───────────────────▼─────────────────────────────────┐
│            EMBEDDED TOMCAT SERVER                    │
│                                                      │
│  ┌────────────────────────────────────────────┐    │
│  │  DispatcherServlet (Thread-5)              │    │
│  │  - Convert User object to JSON             │    │
│  └────────────────┬───────────────────────────┘    │
│                   │                                 │
│  ┌────────────────▼───────────────────────────┐    │
│  │  HTTP Response (Thread-5)                  │    │
│  │  Status: 200 OK                            │    │
│  │  Content-Type: application/json            │    │
│  │  Body: {"id":123,"name":"John"}            │    │
│  └────────────────┬───────────────────────────┘    │
│                   │                                 │
│  ┌────────────────▼───────────────────────────┐    │
│  │  Thread-5 returns to pool (IDLE)           │    │
│  └────────────────────────────────────────────┘    │
│                                                      │
└──────────────────┬───────────────────────────────────┘
                   │ HTTP Response
                   ▼
            ┌─────────────┐
            │   Client    │
            │  (Browser)  │
            └─────────────┘

KEY POINT: Same thread (Thread-5) handles the entire request from
           start to finish. This is the "Thread-per-Request" model.
```

---

## What is Spring Boot?

**Definition:** Spring Boot is a framework built on top of Spring Framework that simplifies Spring application development by providing:
- **Auto-configuration**: Automatically configures beans based on classpath
- **Embedded servers**: No need to deploy WAR files (Tomcat, Jetty embedded)
- **Starter dependencies**: Pre-configured dependency bundles
- **Production-ready features**: Actuator for monitoring, health checks
- **No XML configuration**: Pure Java configuration

---

## Spring Boot vs Spring Framework

| Feature | Spring Framework | Spring Boot |
|---------|------------------|-------------|
| Configuration | Manual XML/Java config | Auto-configuration |
| Dependency Management | Manual | Starter dependencies |
| Server | External server needed | Embedded server |
| Setup Time | Longer | Faster |
| Production Features | Manual setup | Built-in (Actuator) |
| Boilerplate | More | Less |

**Example:**
```java
// Spring Framework - Manual configuration
@Configuration
@EnableWebMvc
@ComponentScan("com.example")
public class WebConfig { }

// Spring Boot - Auto-configured
@SpringBootApplication
public class Application { }
```

---

## @SpringBootApplication Annotation

**@SpringBootApplication** is a combination of three annotations:

1. **@Configuration** - Marks class as configuration class
2. **@EnableAutoConfiguration** - Enables auto-configuration
3. **@ComponentScan** - Scans for components in current package and sub-packages

```java
@SpringBootApplication
// Equivalent to:
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

---

## Spring Boot Starters

Starters are pre-configured dependency descriptors that include all necessary dependencies for a specific functionality.

**Common Starters:**
- `spring-boot-starter-web` - Web applications (Spring MVC, Tomcat)
- `spring-boot-starter-data-jpa` - JPA with Hibernate
- `spring-boot-starter-security` - Spring Security
- `spring-boot-starter-test` - Testing (JUnit, Mockito)
- `spring-boot-starter-actuator` - Production monitoring
- `spring-boot-starter-validation` - Bean validation

**Example:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!-- This single dependency brings: Spring MVC, Jackson, Tomcat, etc. -->
```

---

## Auto-Configuration

Auto-configuration automatically configures Spring application based on dependencies in classpath.

**How it works:**
1. Spring Boot scans classpath for libraries
2. Applies conditional configuration based on what it finds
3. Uses `@Conditional` annotations to decide what to configure

**Example:**
```java
// If H2 database is in classpath, Spring Boot auto-configures:
// - DataSource
// - JPA EntityManager
// - Transaction Manager
// All without any manual configuration!

// You can disable auto-configuration:
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application { }
```

---

## Dependency Injection (DI)

Dependency Injection is a design pattern where objects receive dependencies from external source rather than creating them.

### Types of Dependency Injection

**1. Constructor Injection (Recommended)**
```java
@Service
public class UserService {
    private final UserRepository repository;

    // No @Autowired needed for single constructor
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```
**Benefits**: Immutability, mandatory dependencies, easy testing

**2. Setter Injection**
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

**3. Field Injection (Not recommended)**
```java
@Service
public class UserService {
    @Autowired
    private UserRepository repository;
}
```
**Drawbacks**: Cannot use final, hard to test, hidden dependencies

---

## Spring Stereotypes

### @Component, @Service, @Repository, @Controller

All are specializations of @Component for semantic clarity:

| Annotation | Layer | Purpose |
|-----------|-------|---------|
| @Component | Generic | Any Spring-managed component |
| @Service | Business | Business logic layer |
| @Repository | Persistence | Data access layer (exception translation) |
| @Controller | Presentation | MVC controller (returns views) |
| @RestController | Presentation | REST API (returns data) |

```java
@Service
public class UserService {
    // Business logic
}

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Data access
}

@RestController
@RequestMapping("/api/users")
public class UserController {
    // REST endpoints
}
```

---

## Bean Lifecycle

Spring Bean lifecycle consists of several phases:

**Lifecycle Phases:**
1. **Instantiation** - Container creates bean instance
2. **Populate Properties** - DI happens, dependencies injected
3. **BeanNameAware** - `setBeanName()` called if implemented
4. **BeanFactoryAware** - `setBeanFactory()` called if implemented
5. **ApplicationContextAware** - `setApplicationContext()` called if implemented
6. **Pre-Initialization** - `@PostConstruct` or `InitializingBean.afterPropertiesSet()`
7. **Custom Init Method** - Method specified in @Bean(initMethod)
8. **Bean Ready** - Bean is fully initialized and ready to use
9. **Pre-Destruction** - `@PreDestroy` or `DisposableBean.destroy()`
10. **Custom Destroy Method** - Method specified in @Bean(destroyMethod)

**Example:**
```java
@Component
public class MyBean implements InitializingBean, DisposableBean {

    public MyBean() {
        System.out.println("1. Constructor called");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("2. @PostConstruct called");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("3. afterPropertiesSet called");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("4. @PreDestroy called");
    }

    @Override
    public void destroy() {
        System.out.println("5. destroy called");
    }
}
```

---

## Bean Scopes

| Scope | Description | Use Case |
|-------|-------------|----------|
| singleton | One instance per container (default) | Stateless beans |
| prototype | New instance every time | Stateful beans |
| request | One instance per HTTP request | Web applications |
| session | One instance per HTTP session | User session data |
| application | One instance per ServletContext | Global state |

```java
@Service
@Scope("singleton")  // Default
public class UserService { }

@Component
@Scope("prototype")
public class UserPreferences { }

@Controller
@Scope("request")
public class UserController { }
```

---

## REST API Development

### Request Mapping Annotations

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    // GET all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // GET user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // POST - Create user
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // PUT - Update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    // DELETE user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
```

### Parameter Annotations

**@PathVariable** - Extract from URL path
```java
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) { }
// URL: /users/123 → id = 123
```

**@RequestParam** - Extract query parameters
```java
@GetMapping("/users")
public List<User> getUsers(
    @RequestParam(required = false) String name,
    @RequestParam(defaultValue = "0") int page) { }
// URL: /users?name=John&page=0
```

**@RequestBody** - Extract request body
```java
@PostMapping("/users")
public User createUser(@RequestBody User user) { }
// JSON body: {"name":"John","email":"john@example.com"}
```

**@RequestHeader** - Extract headers
```java
@GetMapping("/users")
public List<User> getUsers(@RequestHeader("Authorization") String token) { }
```

---

## Exception Handling

### @ControllerAdvice - Global Exception Handler

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle specific exception
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation failed",
            errors,
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal server error",
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@Data
@AllArgsConstructor
class ErrorResponse {
    private int status;
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;
}
```

---

## Validation

### Bean Validation Annotations

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be less than 100")
    private Integer age;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phone;
}
```

**Common Validation Annotations:**
- `@NotNull` - Cannot be null
- `@NotBlank` - Cannot be null, empty, or whitespace (String only)
- `@NotEmpty` - Cannot be null or empty (collections, arrays, strings)
- `@Size(min, max)` - Size constraint
- `@Min(value)` - Minimum value
- `@Max(value)` - Maximum value
- `@Email` - Valid email format
- `@Pattern(regexp)` - Matches regex pattern

**Using in Controller:**
```java
@PostMapping("/users")
public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    // @Valid triggers validation
    User createdUser = userService.createUser(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
}
```

---

## Configuration

### application.properties vs application.yml

**application.properties:**
```properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.jpa.show-sql=true
```

**application.yml:**
```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb
    username: root
  jpa:
    show-sql: true
```

### Spring Profiles

Profiles allow different configurations for different environments (dev, test, prod).

**Configuration files:**
- `application.properties` - Common properties
- `application-dev.properties` - Development
- `application-test.properties` - Testing
- `application-prod.properties` - Production

**application-dev.properties:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb_dev
logging.level.root=DEBUG
spring.jpa.show-sql=true
```

**application-prod.properties:**
```properties
spring.datasource.url=jdbc:mysql://prod-server:3306/mydb
logging.level.root=WARN
spring.jpa.show-sql=false
```

**Activate profile:**
```properties
# application.properties
spring.profiles.active=dev
```

**Or via command line:**
```bash
java -jar app.jar --spring.profiles.active=prod
```

---

## Spring Boot Actuator

Actuator provides production-ready features to monitor and manage application.

**Key Endpoints:**
- `/actuator/health` - Application health status
- `/actuator/info` - Application information
- `/actuator/metrics` - Application metrics
- `/actuator/env` - Environment properties
- `/actuator/loggers` - Logger configuration

**Configuration:**
```properties
# application.properties
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=always
```

**Custom Health Indicator:**
```java
@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean databaseUp = checkDatabase();
        if (databaseUp) {
            return Health.up()
                .withDetail("database", "Available")
                .build();
        }
        return Health.down()
            .withDetail("database", "Unavailable")
            .build();
    }
}
```

---

# Spring Data JPA & Hibernate

## 🎯 Why JPA/Hibernate? The JDBC Pain Story

**The Problem: Writing SQL in Java (JDBC Hell)**

Imagine building a simple e-commerce app without JPA:

```java
// Without JPA - Pure JDBC (OLD WAY)
public class UserService {

    public User findById(Long id) {
        User user = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 1. Get database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost/db", "user", "pass");

            // 2. Write SQL query
            String sql = "SELECT id, name, email, age, created_at, updated_at FROM users WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            // 3. Execute query
            rs = stmt.executeQuery();

            // 4. Manually map ResultSet to object
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setAge(rs.getInt("age"));
                user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
        } catch (SQLException e) {
            // Handle exception
            e.printStackTrace();
        } finally {
            // 5. Close resources (MUST do this!)
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

    public void save(User user) {
        // Write another 50 lines of JDBC code...
        // Check if user exists (SELECT query)
        // If exists: UPDATE query
        // If not: INSERT query
        // Handle all exceptions
        // Close all resources
        // Repeat for EVERY table!
    }
}
```

**Problems with JDBC:**
1. **Too much boilerplate** - 50 lines just to fetch one record!
2. **Manual mapping** - Convert ResultSet to objects yourself
3. **Resource management** - Must close connections, statements, resultsets
4. **Repetitive code** - Write same pattern for every table
5. **SQL in Java code** - Mix business logic with database queries
6. **No relationship handling** - Fetch related data manually with JOINs
7. **No caching** - Every query hits database
8. **Database-specific** - SQL differs across MySQL, PostgreSQL, Oracle

### The Solution: JPA/Hibernate

**With JPA - Object-Oriented Way**

```java
// With JPA - Simple & Clean
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;  // That's it!

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);  // One line!
    }

    public void save(User user) {
        userRepository.save(user);  // One line!
    }

    // Bonus: Custom queries made easy
    public List<User> findByName(String name) {
        return userRepository.findByName(name);  // No SQL needed!
    }
}

// Entity class - Just a POJO
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private Integer age;

    // Getters/setters
}

// Repository - Just an interface!
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);  // No implementation needed!
}
```

### What JPA/Hibernate Does for You

```
┌────────────────────────────────────────────────────────────┐
│              JPA/HIBERNATE MAGIC                            │
├────────────────────────────────────────────────────────────┤
│                                                             │
│  Your Code:                                                 │
│  userRepository.findById(1L);                              │
│                                                             │
│           ↓                                                │
│                                                             │
│  Hibernate automatically:                                  │
│  1. Opens database connection                              │
│  2. Generates SQL: SELECT * FROM users WHERE id = 1        │
│  3. Executes query                                         │
│  4. Maps ResultSet to User object                          │
│  5. Closes connection                                      │
│  6. Returns User object                                    │
│                                                             │
│  All in ONE LINE!                                          │
│                                                             │
└────────────────────────────────────────────────────────────┘
```

### When to Use JPA/Hibernate?

✅ **Use JPA when:**
- Building enterprise applications
- Working with complex object models
- Need relationships (one-to-many, many-to-many)
- Want automatic CRUD operations
- Need caching for performance
- Want database-independent code
- Building REST APIs with Spring Boot

❌ **Don't use JPA when:**
- Simple applications with few tables
- Need complex SQL with multiple joins
- Performance-critical queries (use native SQL)
- Reporting with aggregations (better to use native SQL)
- Batch processing large data (JPA can be slow)

### Real-World Analogy

**JPA/Hibernate = Automatic Transmission Car**

```
Manual JDBC = Manual Transmission Car
┌─────────────────────────────────────────┐
│ You must:                                │
│ - Press clutch                           │
│ - Change gear                            │
│ - Control everything manually            │
│                                          │
│ Powerful but tedious!                    │
└─────────────────────────────────────────┘

JPA/Hibernate = Automatic Transmission
┌─────────────────────────────────────────┐
│ You just:                                │
│ - Press accelerator                      │
│ - Car handles gear changes               │
│                                          │
│ Easy and convenient!                     │
└─────────────────────────────────────────┘
```

---

## What is JPA?

**JPA (Java Persistence API)** is a specification for object-relational mapping (ORM) in Java.

**Hibernate** is the most popular JPA implementation.

| Feature | JPA | Hibernate |
|---------|-----|-----------|
| Type | Specification | Implementation |
| Provider | Interface | Concrete classes |

---

## Entity Class

```java
@Entity
@Table(name = "users")
@Data  // Lombok
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private Integer age;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
```

---

## JPA Relationships

### @OneToOne - One-to-one relationship

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private UserProfile profile;
}

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bio;

    @OneToOne(mappedBy = "profile")
    private User user;
}
```

### @OneToMany and @ManyToOne - One-to-many relationship

```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();
}

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
```

### @ManyToMany - Many-to-many relationship

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

**Cascade Types:**
- **CascadeType.PERSIST** - Save child when parent is saved
- **CascadeType.MERGE** - Update child when parent is updated
- **CascadeType.REMOVE** - Delete child when parent is deleted
- **CascadeType.ALL** - All of the above

**Fetch Types:**
- **FetchType.LAZY** - Load on demand (default for collections)
- **FetchType.EAGER** - Load immediately (default for single entities)

---

## Repository Interface

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Method name queries
    List<User> findByName(String name);
    List<User> findByEmail(String email);
    List<User> findByAgeGreaterThan(int age);
    List<User> findByNameContaining(String keyword);

    // @Query annotation (JPQL)
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailCustom(@Param("email") String email);

    // Native query
    @Query(value = "SELECT * FROM users WHERE age > :age", nativeQuery = true)
    List<User> findUsersOlderThan(@Param("age") int age);

    // Update query
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name = :name WHERE u.id = :id")
    int updateUserName(@Param("id") Long id, @Param("name") String name);
}
```

---

## @Transactional

### 🎯 Why @Transactional? The Money Transfer Disaster Story

**The Problem: No Transaction Management**

Imagine a banking app without @Transactional:

```java
// WITHOUT @Transactional - DANGEROUS!
@Service
public class BankService {

    public void transferMoney(Long fromAccountId, Long toAccountId, double amount) {
        // Step 1: Deduct money from sender
        Account fromAccount = accountRepository.findById(fromAccountId).get();
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        accountRepository.save(fromAccount);  // Database updated!

        // 💥 CRASH HERE! (Server dies, network fails, etc.)
        // fromAccount lost $1000
        // toAccount never received money
        // MONEY DISAPPEARED!

        // Step 2: Add money to receiver (never executes!)
        Account toAccount = accountRepository.findById(toAccountId).get();
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountRepository.save(toAccount);
    }
}
```

**What goes wrong?**

```
Scenario: Transfer $1000 from Alice to Bob

Without @Transactional:
┌─────────────────────────────────────────────────────────┐
│  Time  │  Action                │  Database State        │
├─────────────────────────────────────────────────────────┤
│  T1    │ Alice balance: $5000   │ Alice: $5000, Bob: $2000│
│  T2    │ Deduct from Alice      │ Alice: $4000, Bob: $2000│
│  T3    │ 💥 SERVER CRASH!       │ Alice: $4000, Bob: $2000│
│  T4    │ (Never adds to Bob)    │ Alice: $4000, Bob: $2000│
│        │                        │                         │
│  Result: $1000 LOST!           │  DATA CORRUPTION!      │
└─────────────────────────────────────────────────────────┘
```

### The Solution: @Transactional

**ACID Properties:**
- **A**tomicity: All or nothing
- **C**onsistency: Data remains valid
- **I**solation: Concurrent transactions don't interfere
- **D**urability: Committed data survives crashes

```java
// WITH @Transactional - SAFE!
@Service
public class BankService {

    @Transactional  // Magic happens here!
    public void transferMoney(Long fromAccountId, Long toAccountId, double amount) {
        // Step 1: Deduct money from sender
        Account fromAccount = accountRepository.findById(fromAccountId).get();
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        accountRepository.save(fromAccount);

        // 💥 CRASH HERE!
        // @Transactional ROLLSBACK both operations!
        // Database returns to previous state
        // Alice keeps $5000, Bob keeps $2000 ✓

        // Step 2: Add money to receiver
        Account toAccount = accountRepository.findById(toAccountId).get();
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountRepository.save(toAccount);

        // If we reach here without exception:
        // COMMIT both operations together ✓
    }
}
```

**How @Transactional Works:**

```
┌────────────────────────────────────────────────────────────┐
│         @TRANSACTIONAL FLOW                                 │
├────────────────────────────────────────────────────────────┤
│                                                             │
│  Method called                                              │
│        ↓                                                   │
│  Spring opens transaction (BEGIN)                          │
│        ↓                                                   │
│  Execute: deduct from Alice                                │
│  (Changes in memory, NOT committed to DB)                  │
│        ↓                                                   │
│  Execute: add to Bob                                       │
│  (Changes in memory, NOT committed to DB)                  │
│        ↓                                                   │
│  ┌─────────────────────────────────┐                      │
│  │  Any exception?                 │                      │
│  └─────────────────────────────────┘                      │
│        │                     │                             │
│      YES                    NO                             │
│        │                     │                             │
│        ↓                     ↓                             │
│  ROLLBACK              COMMIT                              │
│  (Undo everything)     (Save everything)                   │
│  Alice: $5000 ✓        Alice: $4000 ✓                      │
│  Bob: $2000 ✓          Bob: $3000 ✓                        │
│                                                             │
└────────────────────────────────────────────────────────────┘
```

### Real-World Example: Order Processing

```java
@Service
public class OrderService {

    @Transactional
    public void placeOrder(OrderRequest request) {
        // 1. Create order
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setTotal(request.getTotal());
        orderRepository.save(order);

        // 2. Deduct from inventory
        for (OrderItem item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId()).get();
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        // 3. Charge payment
        paymentService.charge(request.getUserId(), request.getTotal());

        // 4. Send email
        emailService.sendOrderConfirmation(order);

        // If ANY step fails:
        // - Order deleted
        // - Inventory restored
        // - Payment refunded
        // - Email not sent
        // Everything ROLLBACK together! ✓
    }
}
```

### When to Use @Transactional?

✅ **Use @Transactional when:**
- Multiple database operations must succeed together (money transfer, order processing)
- Data consistency is critical (banking, e-commerce, healthcare)
- Operations involve multiple tables
- Need rollback on failure
- Any financial transaction

❌ **Don't use @Transactional when:**
- Single SELECT query (use `readOnly = true`)
- No database modifications (read-only operations)
- Long-running operations (keep transactions short!)

### Common Mistakes

```java
// ❌ WRONG: @Transactional on private method (doesn't work!)
@Transactional
private void transferMoney() { }

// ✅ CORRECT: @Transactional on public method
@Transactional
public void transferMoney() { }

// ❌ WRONG: Catching exception without rethrowing
@Transactional
public void transfer() {
    try {
        // ... operations
    } catch (Exception e) {
        // Swallowed! Transaction commits anyway!
    }
}

// ✅ CORRECT: Rethrow exception for rollback
@Transactional
public void transfer() {
    try {
        // ... operations
    } catch (Exception e) {
        throw new RuntimeException("Transfer failed", e);  // Rollback!
    }
}
```

### Real-World Analogy

**@Transactional = Shopping Cart Checkout**

```
Without @Transactional:
┌─────────────────────────────────────────┐
│ 1. Charge credit card → Success ✓       │
│ 2. Reserve product → Out of stock! ✗    │
│ 3. Send email → Not sent ✗              │
│                                          │
│ Result: Money charged, no product!      │
└─────────────────────────────────────────┘

With @Transactional:
┌─────────────────────────────────────────┐
│ 1. Charge credit card → Success ✓       │
│ 2. Reserve product → Out of stock! ✗    │
│                                          │
│ @Transactional rollback:                │
│ - Refund credit card ✓                  │
│ - Cancel reservation ✓                  │
│                                          │
│ Result: No charge, no product ✓         │
│ (Customer not affected!)                │
└─────────────────────────────────────────┘
```

---

Manages database transactions declaratively.

```java
@Service
public class UserService {

    @Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED,
        timeout = 5,
        rollbackFor = Exception.class
    )
    public void transferMoney(Long fromId, Long toId, double amount) {
        accountRepository.debit(fromId, amount);
        // If exception here, both operations rollback
        accountRepository.credit(toId, amount);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
```

**Transaction Propagation:**
- **REQUIRED** (default) - Use existing or create new
- **REQUIRES_NEW** - Always create new
- **MANDATORY** - Must have existing transaction
- **SUPPORTS** - Use existing if available

---

## N+1 Query Problem

### 🎯 Why N+1 is Bad? The Performance Killer Story

**The Silent Performance Disaster**

Imagine you're building a blog platform. Everything works fine in development with 5 posts. Then in production with 10,000 posts, the app crashes. Why?

```java
// Innocent looking code - PERFORMANCE DISASTER!
@GetMapping("/posts")
public List<PostDTO> getAllPosts() {
    List<Post> posts = postRepository.findAll();  // 1 query

    List<PostDTO> result = new ArrayList<>();
    for (Post post : posts) {
        PostDTO dto = new PostDTO();
        dto.setTitle(post.getTitle());
        dto.setAuthorName(post.getAuthor().getName());  // N queries!
        dto.setCommentCount(post.getComments().size()); // N queries!
        result.add(dto);
    }
    return result;
}
```

**What happens in production:**

```
┌──────────────────────────────────────────────────────────┐
│              N+1 PROBLEM VISUALIZATION                    │
├──────────────────────────────────────────────────────────┤
│                                                           │
│  Step 1: Fetch all posts                                 │
│  Query 1: SELECT * FROM posts                            │
│  Result: 10,000 posts ✓                                  │
│                                                           │
│  Step 2: For each post, fetch author (LAZY loading)      │
│  Query 2: SELECT * FROM users WHERE id = 1               │
│  Query 3: SELECT * FROM users WHERE id = 2               │
│  Query 4: SELECT * FROM users WHERE id = 3               │
│  ...                                                      │
│  Query 10,001: SELECT * FROM users WHERE id = 10000      │
│                                                           │
│  Step 3: For each post, fetch comments                   │
│  Query 10,002: SELECT * FROM comments WHERE post_id = 1  │
│  Query 10,003: SELECT * FROM comments WHERE post_id = 2  │
│  ...                                                      │
│  Query 20,001: SELECT * FROM comments WHERE post_id=10000│
│                                                           │
│  TOTAL QUERIES: 1 + 10,000 + 10,000 = 20,001 queries!   │
│  TIME: 20 seconds (each query takes 1ms)                 │
│  APP TIMES OUT! ⏱️                                        │
│                                                           │
└──────────────────────────────────────────────────────────┘
```

**The Problem Formula:**
```
N+1 Problem:
- N = Number of parent records (posts)
- 1 = Initial query to fetch parents
- +N = One query for EACH child (author, comments)

Example with 10,000 posts:
- 1 query for posts
- 10,000 queries for authors
- 10,000 queries for comments
= 20,001 total queries! 💥
```

### How It Happens (Lazy Loading Trap)

```java
@Entity
public class Post {
    @Id
    private Long id;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)  // ⚠️ LAZY = Problem source!
    private User author;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments;
}

// When you access lazy relationships:
Post post = postRepository.findById(1L).get();  // 1 query
System.out.println(post.getTitle());            // No query (already loaded)
System.out.println(post.getAuthor().getName()); // +1 query! (Lazy load)
System.out.println(post.getComments().size());  // +1 query! (Lazy load)
```

### The Solution: JOIN FETCH

**Solution 1: JOIN FETCH in JPQL**

```java
// Bad: N+1 queries
List<Post> posts = postRepository.findAll();

// Good: 1 query with JOIN
@Query("SELECT p FROM Post p JOIN FETCH p.author")
List<Post> findAllWithAuthor();

// Better: 1 query with multiple JOINs
@Query("SELECT DISTINCT p FROM Post p " +
       "JOIN FETCH p.author " +
       "LEFT JOIN FETCH p.comments")
List<Post> findAllWithAuthorAndComments();
```

**What this does:**

```
Instead of:
Query 1: SELECT * FROM posts
Query 2: SELECT * FROM users WHERE id = 1
Query 3: SELECT * FROM users WHERE id = 2
... (10,000 more queries)

JOIN FETCH does:
Query 1: SELECT p.*, u.*, c.*
         FROM posts p
         JOIN users u ON p.author_id = u.id
         LEFT JOIN comments c ON c.post_id = p.id

Just 1 query! ✓ (10,000x faster!)
```

**Solution 2: @EntityGraph**

```java
@Entity
@NamedEntityGraph(
    name = "Post.withAuthorAndComments",
    attributePaths = {"author", "comments"}
)
public class Post { ... }

// In repository:
@EntityGraph(value = "Post.withAuthorAndComments")
List<Post> findAll();

// Or inline:
@EntityGraph(attributePaths = {"author", "comments"})
List<Post> findAll();
```

**Solution 3: Batch Fetching**

```java
@Entity
public class Post {
    @ManyToOne(fetch = FetchType.LAZY)
    @BatchSize(size = 10)  // Fetch 10 authors at once
    private User author;
}

// Instead of N queries, uses N/10 queries
// 10,000 posts → 1,000 queries (still bad, but better!)
```

### Real-World Performance Comparison

```
Scenario: Fetch 1,000 blog posts with authors

┌───────────────────────────────────────────────────────┐
│  Approach           │  Queries  │  Time     │ Result  │
├───────────────────────────────────────────────────────┤
│  N+1 (Bad)          │  1,001    │  10s      │ ❌ Slow │
│  Batch (size=10)    │  101      │  1s       │ ⚠️ OK   │
│  JOIN FETCH (Good)  │  1        │  0.1s     │ ✅ Fast │
└───────────────────────────────────────────────────────┘
```

### When N+1 Happens

```java
// ❌ Triggers N+1
List<Department> depts = deptRepository.findAll();
for (Department dept : depts) {
    dept.getEmployees().size();  // Lazy load for EACH dept
}

// ❌ Triggers N+1
List<Order> orders = orderRepository.findAll();
for (Order order : orders) {
    order.getCustomer().getName();  // Lazy load for EACH order
}

// ❌ Triggers N+1 in template
<!-- In Thymeleaf template -->
<div th:each="user : ${users}">
    <p th:text="${user.profile.bio}"></p>  <!-- Lazy load! -->
</div>

// ✅ No N+1 - Single query
@Query("SELECT u FROM User u JOIN FETCH u.profile")
List<User> findAllWithProfile();
```

### How to Detect N+1

**Enable SQL Logging:**

```properties
# application.properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
```

**Watch logs:**

```
Bad (N+1):
Hibernate: select * from posts
Hibernate: select * from users where id=1
Hibernate: select * from users where id=2
Hibernate: select * from users where id=3
... (thousands more!)

Good (JOIN FETCH):
Hibernate: select p.*, u.*
           from posts p
           join users u on p.author_id = u.id
(Just one query!)
```

### Real-World Analogy

**N+1 Problem = Grocery Shopping**

```
N+1 Approach (Bad):
┌──────────────────────────────────────────┐
│ You need milk, eggs, bread               │
│                                           │
│ Trip 1: Drive to store → Buy milk        │
│ Trip 2: Drive to store → Buy eggs        │
│ Trip 3: Drive to store → Buy bread       │
│                                           │
│ Total: 3 trips (wasteful!)               │
└──────────────────────────────────────────┘

JOIN FETCH Approach (Good):
┌──────────────────────────────────────────┐
│ You need milk, eggs, bread               │
│                                           │
│ Trip 1: Drive to store → Buy all 3 items │
│                                           │
│ Total: 1 trip (efficient!) ✓             │
└──────────────────────────────────────────┘

Database queries are like trips to the store!
```

### Key Takeaway

```
⚠️  LAZY loading = Convenient but causes N+1
✅  Always use JOIN FETCH or @EntityGraph for collections
🔍  Enable SQL logging in development to catch N+1 early
📊  Test with production-like data volumes (not just 5 records!)
```

---

N+1 problem occurs when fetching a collection requires N additional queries.

**Problem:**
```java
List<Department> departments = departmentRepository.findAll();  // 1 query

for (Department dept : departments) {
    System.out.println(dept.getEmployees().size());  // N queries!
}
// Total: 1 + N queries
```

**Solution 1: JOIN FETCH**
```java
@Query("SELECT d FROM Department d JOIN FETCH d.employees")
List<Department> findAllWithEmployees();
```

**Solution 2: @EntityGraph**
```java
@EntityGraph(attributePaths = {"employees"})
List<Department> findAll();
```

---

## Pagination and Sorting

```java
@Service
public class UserService {

    public Page<User> getUsers(int page, int size, String sortBy) {
        Sort sort = Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }
}

@RestController
@RequestMapping("/api/users")
public class UserController {

    // GET /api/users?page=0&size=10&sortBy=name
    @GetMapping
    public ResponseEntity<Page<User>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Page<User> users = userService.getUsers(page, size, sortBy);
        return ResponseEntity.ok(users);
    }
}
```

---

# Spring Security

## 🎯 Why & When: The Story Behind Spring Security

### The Problem Story

Imagine you're building an online banking application. On Day 1, you have no security:

```java
@GetMapping("/account/{id}")
public Account getAccount(@PathVariable Long id) {
    return accountService.getAccount(id);  // Anyone can access any account!
}
```

**What goes wrong?**
- Anyone can type `/account/123` and see someone else's account
- Hackers can transfer money from any account
- No way to know WHO is making the request
- No audit trail of who did what

### The Solution: Spring Security

Spring Security solves 3 critical problems:

**1. Authentication (WHO are you?)**
- "Prove you are John Smith before accessing account 123"
- Login with username/password or JWT token
- Session management

**2. Authorization (WHAT can you do?)**
- "John can view his own account, but only ADMIN can view all accounts"
- Role-based access (USER, ADMIN, MANAGER)
- Method-level security

**3. Protection (CSRF, CORS, XSS)**
- Prevent malicious websites from attacking your API
- Control who can call your API from where

### When to Use Spring Security?

✅ **ALWAYS use when:**
- Handling user login/authentication
- Protecting sensitive data (accounts, personal info, payments)
- Need to know WHO is making requests
- Different users have different permissions
- Building any production application with users

❌ **Skip only when:**
- Public API with no sensitive data (e.g., weather API, public news feed)
- Internal tools with no authentication needed
- Learning/demo projects

### Real-World Analogy

**Spring Security = Security Guard at Building Entrance**

```
Without Security:
┌─────────────┐
│   Anyone    │──→ Walks into any office, accesses any computer
└─────────────┘

With Security:
┌─────────────┐
│   Visitor   │──→ Shows ID (Authentication)
└─────────────┘    │
                   ↓
            ┌──────────────┐
            │Security Guard│──→ Checks access card level (Authorization)
            └──────────────┘    │
                                ↓
                         Allows entry to specific floors only
```

---

## Spring Security Architecture

Spring Security uses a chain of filters to secure your application.

**Filter Chain Flow:**
```
Client Request
    ↓
┌──────────────────────────────────┐
│ JWT Authentication Filter        │ ← Custom
│ - Extract JWT                    │
│ - Validate token                 │
│ - Set Authentication             │
└──────────────────────────────────┘
    ↓
┌──────────────────────────────────┐
│ Authorization Filter             │ ← Built-in
│ - Check roles/authorities        │
│ - Allow or deny                  │
└──────────────────────────────────┘
    ↓
Controller
```

---

## Core Concepts

**1. Authentication** - Who are you? (Verifying identity)
**2. Authorization** - What can you do? (Access control)
**3. Principal** - Currently authenticated user
**4. Granted Authority** - Permission/role (ROLE_USER, READ_PRIVILEGE)
**5. SecurityContext** - Holds authentication information

```java
// Get current user
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
String username = authentication.getName();
Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
```

---

## JWT Authentication - Complete Implementation

### 🎯 Why JWT? The Session Problem Story

**The Old Way: Sessions (Stateful)**

Imagine a shopping mall with a cloakroom:
1. You enter → Get a numbered ticket (Session ID)
2. Your coat is stored → Server stores your data in memory
3. Return → Show ticket → Get your coat back

```java
// Traditional session approach
@PostMapping("/login")
public String login(String username, String password, HttpSession session) {
    if (valid) {
        session.setAttribute("user", username);  // Stored on SERVER
        return "JSESSIONID=ABC123";  // Cookie sent to client
    }
}
```

**Problems with Sessions:**
```
Problem 1: Memory Usage
┌──────────────────┐
│  Server 1        │  10,000 active users = 10,000 sessions in RAM
│  - Session 1     │  Each session stores user data
│  - Session 2     │  Server runs out of memory!
│  - ...           │
│  - Session 10000 │
└──────────────────┘

Problem 2: Scalability (Multiple Servers)
┌──────────┐                    ┌──────────┐
│ Server 1 │ ←─ Login here     │ Server 2 │ ←─ Next request goes here
│ Has your │    Session created │ No idea  │    Session not found!
│ session  │                    │ who you  │    LOGIN AGAIN!
└──────────┘                    │ are!     │
                                └──────────┘

Problem 3: Microservices
User Service     →    Order Service    →    Payment Service
   ↓                      ↓                      ↓
Each service needs to share session data (nightmare!)
```

**The Solution: JWT (Stateless)**

JWT = Like an ID card you carry with you

```
Traditional Session = Cloakroom Ticket
- Ticket has just a number
- Cloakroom must remember what's yours
- Can't use ticket at different mall

JWT = Passport with Your Photo & Signature
- Contains all your info (username, roles, expiry)
- Anyone can verify it's real (using signature)
- Works anywhere (any server, any service)
```

**How JWT Works:**

```
┌────────────────────────────────────────────────────────────┐
│                     JWT TOKEN STRUCTURE                     │
├────────────────────────────────────────────────────────────┤
│ Header.Payload.Signature                                   │
│                                                             │
│ eyJ0eXAiOiJKV1QiLCJhbGc.eyJ1c2VybmFtZSI6Ikpv.SflKxwRJSMeK │
│         ↑                      ↑              ↑             │
│      Header              Payload (Data)   Signature        │
└────────────────────────────────────────────────────────────┘

Header:           { "alg": "HS256", "typ": "JWT" }
Payload (Data):   { "username": "john", "roles": ["USER"], "exp": 1234567890 }
Signature:        HMACSHA256(header + payload + SECRET_KEY)
```

**JWT Flow:**

```
Step 1: Login
Client                          Server
  │                               │
  │──── POST /login ─────────────▶│
  │     {username, password}      │
  │                               │  Validate credentials
  │                               │  Generate JWT with:
  │                               │  - username
  │                               │  - roles
  │                               │  - expiry time
  │                               │
  │◀──── JWT Token ───────────────│
  │     "eyJ0eXAiOiJKV1..."       │
  │                               │
  │  Store in localStorage        │

Step 2: Authenticated Request
Client                          Server
  │                               │
  │──── GET /api/account ────────▶│
  │  Header:                      │
  │  Authorization:               │  1. Extract JWT
  │  Bearer eyJ0eXAiOiJKV1...     │  2. Verify signature
  │                               │  3. Check expiry
  │                               │  4. Extract username & roles
  │                               │  5. Allow/Deny request
  │◀──── Account Data ────────────│
  │                               │
```

### When to Use JWT vs Sessions?

| Scenario | Use JWT ✅ | Use Sessions ❌ |
|----------|-----------|---------------|
| **REST API** | ✅ Stateless, scalable | ❌ Requires session storage |
| **Mobile Apps** | ✅ Easy to store token | ❌ Cookies don't work well |
| **Microservices** | ✅ Token works across services | ❌ Session sharing nightmare |
| **Multiple Servers** | ✅ No session replication needed | ❌ Must replicate sessions |
| **Traditional Web App** (Forms, JSP) | ❌ Cookies easier | ✅ Built-in support |

### JWT Advantages

✅ **Stateless** - Server doesn't store anything
✅ **Scalable** - Works with any number of servers
✅ **Cross-domain** - Send to different APIs
✅ **Mobile-friendly** - No cookie issues
✅ **Microservices** - One token for all services

### JWT Disadvantages

❌ **Can't revoke** - Valid until expiry (solution: short-lived + refresh tokens)
❌ **Larger size** - Contains data (vs session ID just "ABC123")
❌ **Exposed data** - Payload is readable (don't put passwords!)

### Real-World Example: E-commerce

```
Without JWT (Session):
1. Login on Server A → Session created on Server A
2. Browse products on Server B → "Who are you?" → Login again!

With JWT:
1. Login on Server A → JWT token received
2. Browse products on Server B → Send same JWT → Works!
3. Make payment on Server C → Send same JWT → Works!
4. Check orders on Server D → Send same JWT → Works!

One login, works everywhere!
```

---

### Step 1: JWT Service

```java
@Service
public class JwtService {

    private static final String SECRET_KEY = "mySecretKey12345mySecretKey12345";
    private static final long ACCESS_TOKEN_EXPIRATION = 900000;  // 15 minutes
    private static final long REFRESH_TOKEN_EXPIRATION = 604800000;  // 7 days

    // Generate access token
    public String generateAccessToken(String username, List<String> roles) {
        return Jwts.builder()
            .setSubject(username)
            .claim("roles", roles)
            .claim("type", "access")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }

    // Generate refresh token
    public String generateRefreshToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .claim("type", "refresh")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }

    // Extract username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Validate token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();
    }
}
```

### Step 2: JWT Authentication Filter

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // 1. Extract JWT from Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);

        // 2. Validate token and set authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
```

### Step 3: Security Configuration

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/user/**").hasRole("USER")
                .anyRequest().authenticated()
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

### Step 4: UserDetailsService

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
            .builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities(getAuthorities(user.getRoles()))
            .build();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    }
}
```

### Step 5: Authentication Controller

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    // Register
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    // Login - returns both access and refresh tokens
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        // Authenticate
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        // Generate tokens
        String accessToken = jwtService.generateAccessToken(userDetails.getUsername(), roles);
        String refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());

        return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken));
    }

    // Refresh - get new access token using refresh token
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        if (jwtService.isTokenExpired(refreshToken)) {
            throw new InvalidTokenException("Refresh token expired");
        }

        String username = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        String newAccessToken = jwtService.generateAccessToken(username, roles);
        String newRefreshToken = jwtService.generateRefreshToken(username);

        return ResponseEntity.ok(new TokenResponse(newAccessToken, newRefreshToken));
    }
}
```

---

## RBAC (Role-Based Access Control)

### 🎯 Why RBAC? The Permission Chaos Story

**The Problem: No Access Control**

Imagine a hospital management system:

```java
// Day 1: No role-based control
@GetMapping("/patients/{id}")
public Patient getPatient(@PathVariable Long id) {
    return patientService.getById(id);  // ANYONE can see ANY patient!
}

@DeleteMapping("/patients/{id}")
public void deletePatient(@PathVariable Long id) {
    patientService.delete(id);  // RECEPTIONIST can delete patients!
}
```

**What goes wrong?**
```
Day 1: Receptionist logs in
       → Can view patient records ✓ (Should be allowed)
       → Can DELETE patient records ✗ (Should NOT be allowed!)
       → Can view other doctors' schedules ✗ (Should NOT be allowed!)

Day 2: Nurse logs in
       → Can prescribe medicines ✗ (Only doctors should!)
       → Can access billing ✗ (Only admin should!)

Day 3: Hacker logs in as regular user
       → Can access everything!
       → Can delete all records!
       → Hospital data compromised!
```

### The Solution: RBAC (Role-Based Access Control)

Think of RBAC like **job titles in a company**:

```
┌────────────────────────────────────────────────────────────┐
│                      HOSPITAL RBAC                          │
├────────────────────────────────────────────────────────────┤
│                                                             │
│  ROLE: ADMIN                                                │
│  ├─ Can view all records                                   │
│  ├─ Can delete records                                     │
│  ├─ Can manage users                                       │
│  └─ Can access billing                                     │
│                                                             │
│  ROLE: DOCTOR                                               │
│  ├─ Can view patient records                               │
│  ├─ Can prescribe medicines                                │
│  ├─ Can update diagnoses                                   │
│  └─ Cannot delete records                                  │
│                                                             │
│  ROLE: NURSE                                                │
│  ├─ Can view patient records                               │
│  ├─ Can update vitals                                      │
│  └─ Cannot prescribe medicines                             │
│                                                             │
│  ROLE: RECEPTIONIST                                         │
│  ├─ Can view appointments                                  │
│  ├─ Can schedule appointments                              │
│  └─ Cannot view patient medical records                    │
│                                                             │
└────────────────────────────────────────────────────────────┘
```

### How RBAC Works

```
Step 1: User logs in
┌─────────────┐
│ Dr. Smith   │──→ Login with username/password
└─────────────┘    │
                   ↓
            ┌──────────────┐
            │ JWT Token    │
            │ Generated    │
            │ with ROLE:   │
            │ "DOCTOR"     │
            └──────────────┘

Step 2: User makes request
┌─────────────┐
│ Dr. Smith   │──→ DELETE /patients/123
└─────────────┘    │
                   ↓
            ┌──────────────────┐
            │ Spring Security  │
            │ Checks:          │
            │ 1. Is JWT valid? │ ✓
            │ 2. What role?    │ → DOCTOR
            │ 3. Can DOCTOR    │
            │    DELETE?       │ ✗ (Only ADMIN)
            └──────────────────┘
                   ↓
            ┌──────────────┐
            │ 403 Forbidden│
            │ Access Denied│
            └──────────────┘
```

### Real-World Example: E-commerce

```java
// Products Controller
@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Anyone can view products (even without login)
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    // Only logged-in users can add to cart
    @PostMapping("/cart")
    @PreAuthorize("isAuthenticated()")  // Must be logged in
    public void addToCart(@RequestBody CartItem item) {
        cartService.add(item);
    }

    // Only ADMIN can add new products
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")  // Must be ADMIN
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    // Only ADMIN can delete products
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }

    // User can view their own orders only
    @GetMapping("/orders")
    @PreAuthorize("isAuthenticated()")
    public List<Order> getMyOrders() {
        String username = SecurityContextHolder.getContext()
                            .getAuthentication().getName();
        return orderService.findByUsername(username);
    }

    // ADMIN can view all orders
    @GetMapping("/orders/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }
}
```

### When to Use RBAC?

✅ **Use RBAC when:**
- Different users need different permissions
- Multi-user applications (admin, regular users, moderators)
- Sensitive operations (delete, update, payment)
- Healthcare, banking, e-commerce, social media
- Any production application

❌ **Skip RBAC when:**
- Single-user applications
- All users have same permissions
- Public read-only APIs

### Role Hierarchy Example

```
┌─────────────────────────────────────────────────────┐
│              ROLE HIERARCHY                          │
├─────────────────────────────────────────────────────┤
│                                                      │
│                   SUPER_ADMIN                        │
│                        │                             │
│          ┌─────────────┴─────────────┐              │
│          ↓                           ↓              │
│        ADMIN                      MANAGER            │
│          │                           │              │
│    ┌─────┴─────┐                    │              │
│    ↓           ↓                    ↓              │
│  EDITOR      MODERATOR            USER             │
│                                                      │
│  Access flows down:                                 │
│  - SUPER_ADMIN can do everything                    │
│  - ADMIN can do EDITOR + MODERATOR tasks            │
│  - EDITOR can do USER tasks                         │
└─────────────────────────────────────────────────────┘
```

### Real-World Analogy

**RBAC = Job Access Badges**

```
Without RBAC:
┌────────────┐
│ Receptionist│──→ Can enter CEO's office
└────────────┘    Can access finance vault
                  Can fire employees
                  CHAOS!

With RBAC:
┌────────────┐
│ CEO        │──→ Badge Level 1: Access to ALL floors
└────────────┘

┌────────────┐
│ Manager    │──→ Badge Level 2: Access to office floors
└────────────┘

┌────────────┐
│ Employee   │──→ Badge Level 3: Access to own floor only
└────────────┘

┌────────────┐
│ Receptionist│──→ Badge Level 4: Access to lobby only
└────────────┘
```

---

### Role vs Authority

| Feature | Role | Authority |
|---------|------|-----------|
| Purpose | High-level | Granular permissions |
| Naming | Must start with ROLE_ | Any name |
| Example | ROLE_ADMIN, ROLE_USER | READ_PRIVILEGE, WRITE_PRIVILEGE |
| Usage | hasRole("ADMIN") | hasAuthority("READ_PRIVILEGE") |

**Example:**
```java
@Entity
public class User {
    @Id
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;  // ROLE_ADMIN, ROLE_USER
}

@Entity
public class Role {
    @Id
    private Long id;
    private String name;  // ROLE_ADMIN, ROLE_USER

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Privilege> privileges;  // READ, WRITE, DELETE
}

@Entity
public class Privilege {
    @Id
    private Long id;
    private String name;  // READ_PRIVILEGE, WRITE_PRIVILEGE
}
```

### Method Security

```java
@Service
public class UserService {

    // Only ADMIN can delete
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // User can only update their own profile
    @PreAuthorize("#username == authentication.principal.username")
    public void updateProfile(String username, ProfileDTO profile) {
        // Update logic
    }

    // Check multiple conditions
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // Only return data belonging to current user
    @PostFilter("filterObject.userId == authentication.principal.id")
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
```

---

## CORS (Cross-Origin Resource Sharing)

### 🎯 Why CORS? The Browser Security Story

**The Problem: Same-Origin Policy**

Imagine you're building an online banking app:

```
Your Banking API:     https://api.mybank.com
Your Banking Frontend: https://mybank.com

Everything works fine ✓
```

But then this happens:

```
Evil Website:         https://evil-hacker.com

User visits evil-hacker.com
↓
Evil JavaScript runs:
<script>
  // Try to steal money from mybank.com
  fetch('https://api.mybank.com/transfer', {
    method: 'POST',
    body: { to: 'hacker-account', amount: 10000 }
  });
</script>

Browser blocks this! ✓ (Thanks to Same-Origin Policy)
```

**Same-Origin Policy = Browser's Security Guard**

```
Browser Rule: "JavaScript from site A cannot call API on site B"

┌──────────────────┐     ❌ Blocked     ┌──────────────────┐
│ evil-hacker.com  │─────────────────▶│ api.mybank.com   │
│ (JavaScript)     │                   │ (Your API)       │
└──────────────────┘                   └──────────────────┘

But this blocks legitimate cases too!

┌──────────────────┐     ❌ Also Blocked ┌──────────────────┐
│ localhost:3000   │─────────────────▶│ localhost:8080   │
│ (React Frontend) │                   │ (Spring API)     │
└──────────────────┘                   └──────────────────┘
```

### The Real Problem: Separate Frontend & Backend

**Modern App Architecture:**

```
┌─────────────────────────────────────────────────────┐
│         DEVELOPMENT ENVIRONMENT                      │
├─────────────────────────────────────────────────────┤
│                                                      │
│  Frontend (React)      Backend (Spring Boot)        │
│  localhost:3000   →    localhost:8080               │
│                        ↓                             │
│  Different origin! Browser blocks by default        │
│                                                      │
└─────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────┐
│         PRODUCTION ENVIRONMENT                       │
├─────────────────────────────────────────────────────┤
│                                                      │
│  Frontend              Backend                       │
│  myapp.com       →     api.myapp.com                │
│                        ↓                             │
│  Different subdomain! Browser blocks                │
│                                                      │
└─────────────────────────────────────────────────────┘
```

### The Solution: CORS Configuration

CORS = "Hey browser, it's okay! These specific websites can call my API"

```
How CORS Works:

Step 1: Browser sends OPTIONS request (Preflight)
┌──────────────────┐                   ┌──────────────────┐
│ localhost:3000   │───── OPTIONS ────▶│ localhost:8080   │
│ (React)          │  "Can I call you?"│ (Spring Boot)    │
└──────────────────┘                   └────────┬─────────┘
                                               │
                                               ↓
                                        Check CORS config
                                        Is localhost:3000
                                        allowed?

Step 2: Server responds with CORS headers
┌──────────────────┐                   ┌──────────────────┐
│ localhost:3000   │◀──── Headers ─────│ localhost:8080   │
│                  │                   │                  │
│  Receives:       │                   │  Sends:          │
│  Access-Control- │                   │  Access-Control- │
│  Allow-Origin:   │                   │  Allow-Origin:   │
│  localhost:3000  │                   │  localhost:3000  │
└──────────────────┘                   └──────────────────┘
                   ↓
            Browser allows request ✓

Step 3: Actual request goes through
┌──────────────────┐                   ┌──────────────────┐
│ localhost:3000   │──── GET /api ────▶│ localhost:8080   │
│                  │                   │                  │
│                  │◀──── Response ────│                  │
└──────────────────┘                   └──────────────────┘
```

### When to Use CORS?

✅ **Enable CORS when:**
- Frontend and backend run on different origins
- Development: React (3000) + Spring Boot (8080)
- Production: frontend.com + api.frontend.com
- Mobile apps calling your API
- Public APIs meant to be called from browsers

❌ **Don't need CORS when:**
- Frontend and backend on same origin (myapp.com/api)
- API called only from backend (server-to-server)
- Mobile apps (native, not browser-based)

### Real-World Example

**Scenario: Netflix-like app**

```
Development:
Frontend:  localhost:3000      (React app)
Backend:   localhost:8080      (Spring Boot API)
           ↓
Need CORS because different ports = different origins!

Production:
Frontend:  netflix.com         (Served from CDN)
Backend:   api.netflix.com     (Spring Boot API)
           ↓
Need CORS because different subdomains = different origins!
```

### CORS Configuration Example

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Apply to all /api/* endpoints
            .allowedOrigins(
                "http://localhost:3000",      // Dev frontend
                "https://myapp.com",          // Prod frontend
                "https://www.myapp.com"       // Prod with www
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
            .allowCredentials(true)  // Allow cookies
            .maxAge(3600);  // Cache preflight for 1 hour
    }
}
```

### Common CORS Errors & Solutions

```
❌ Error: "CORS policy: No 'Access-Control-Allow-Origin' header"
Solution: Add CORS configuration with your frontend origin

❌ Error: "CORS policy: Request header field authorization is not allowed"
Solution: Add .allowedHeaders("Authorization") or .allowedHeaders("*")

❌ Error: "CORS policy: Method POST is not allowed"
Solution: Add .allowedMethods("POST") or .allowedMethods("*")

❌ Error: "Credentials flag is true, but Access-Control-Allow-Origin is *"
Solution: Change .allowedOrigins("*") to specific origins when using credentials
```

### Real-World Analogy

**CORS = Building Visitor Policy**

```
Without CORS (Same-Origin Policy):
┌───────────────┐
│ Your Building │  Only employees from THIS building can access
│ (Your Website)│  No visitors allowed from other buildings
└───────────────┘

With CORS:
┌───────────────┐
│ Your Building │  Visitor list:
│               │  ✓ Building A employees can visit
│               │  ✓ Building B employees can visit
│               │  ✗ All others blocked
└───────────────┘

CORS = Whitelist of allowed buildings (origins)
```

---

### Method 1: @CrossOrigin annotation

```java
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    // Methods
}
```

### Method 2: Global Configuration

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:3000", "https://example.com")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
}
```

### Method 3: With Spring Security

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable()
            .authorizeHttpRequests()
                .anyRequest().authenticated();

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
```

---

## CSRF (Cross-Site Request Forgery) Protection

### 🎯 Why CSRF Protection? The Hidden Attack Story

**The Attack Scenario**

You're logged into your bank (mybank.com). You have an active session cookie. Then you visit an innocent-looking website:

```
You're logged in:
┌──────────────────┐
│  mybank.com      │  Session Cookie: JSESSIONID=abc123
│  Balance: $10000 │  (Stored in browser)
└──────────────────┘

You visit evil site:
┌──────────────────┐
│ cutecat-pics.com │  Looks innocent, just cat pictures!
└──────────────────┘

Hidden in HTML:
<img src="https://mybank.com/transfer?to=hacker&amount=5000" />

Or worse:
<form action="https://mybank.com/transfer" method="POST">
  <input name="to" value="hacker" />
  <input name="amount" value="5000" />
</form>
<script>document.forms[0].submit();</script>
```

**What happens?**

```
┌────────────────────────────────────────────────────────────┐
│                  CSRF ATTACK FLOW                           │
├────────────────────────────────────────────────────────────┤
│                                                             │
│  Step 1: You log into mybank.com                           │
│  ┌────────┐                      ┌─────────────┐          │
│  │ You    │─────── Login ───────▶│ mybank.com  │          │
│  └────────┘                      └─────────────┘          │
│                                          │                 │
│                                  Cookie: JSESSIONID=abc123 │
│                                  stored in browser         │
│                                                             │
│  Step 2: You visit evil site (while still logged in!)     │
│  ┌────────┐                      ┌─────────────────┐      │
│  │ You    │──── Visit ──────────▶│ evil-hacker.com │      │
│  └────────┘                      └─────────────────┘      │
│                                          │                 │
│                                  Loads hidden form:        │
│                                  <form action=             │
│                                   "mybank.com/transfer">   │
│                                                             │
│  Step 3: Evil site submits form to mybank.com             │
│  ┌─────────────────┐             ┌─────────────┐          │
│  │ evil-hacker.com │             │ mybank.com  │          │
│  │                 │             │             │          │
│  │ Auto-submits:   │────────────▶│ Receives:   │          │
│  │ POST /transfer  │             │ POST request│          │
│  │ to=hacker       │             │ with your   │          │
│  │ amount=5000     │             │ cookie! ✓   │          │
│  └─────────────────┘             └─────────────┘          │
│                                          │                 │
│                                  Thinks it's you!          │
│                                  Transfers $5000           │
│                                  to hacker                 │
│                                                             │
│  Your account: $10000 → $5000 (You lost $5000!)           │
│                                                             │
└────────────────────────────────────────────────────────────┘
```

### Why Does This Work?

**Key Problem: Browser automatically sends cookies with EVERY request**

```
When you visit evil-hacker.com:
┌────────────────────────────────────────────────────────┐
│  Evil site makes request to mybank.com                 │
│                                                         │
│  Browser automatically attaches:                       │
│  - Your mybank.com cookies (JSESSIONID=abc123)        │
│  - Your credentials                                    │
│                                                         │
│  mybank.com server sees:                               │
│  "Request with valid session cookie → Must be user!"  │
│  → Executes transfer (YOU LOSE MONEY!)                │
└────────────────────────────────────────────────────────┘

CORS doesn't help here because:
- Browser blocks reading the RESPONSE
- But the REQUEST still goes through!
- Damage is already done!
```

### The Solution: CSRF Token

**CSRF Token = Secret handshake that evil site doesn't know**

```
┌────────────────────────────────────────────────────────────┐
│              CSRF PROTECTION FLOW                           │
├────────────────────────────────────────────────────────────┤
│                                                             │
│  Step 1: Server generates unique CSRF token                │
│  ┌─────────┐                        ┌──────────┐          │
│  │ Browser │◀──── GET /page ────────│  Server  │          │
│  └─────────┘                        └──────────┘          │
│      │                                    │                │
│      │  Receives:                         │                │
│      │  - Cookie: JSESSIONID=abc123       │                │
│      │  - Hidden field: CSRF=xyz789       │                │
│      │                                                      │
│  Step 2: Legitimate form submission includes token        │
│  ┌─────────┐                        ┌──────────┐          │
│  │ Browser │──── POST /transfer ───▶│  Server  │          │
│  └─────────┘                        └──────────┘          │
│      │                                    │                │
│      │  Sends:                            │                │
│      │  - Cookie: JSESSIONID=abc123       │                │
│      │  - Form field: CSRF=xyz789 ✓       │                │
│      │                                    │                │
│      │                            Server checks:           │
│      │                            Cookie matches token?    │
│      │                            YES ✓ → Allow            │
│      │                                                      │
│  Step 3: Evil site tries attack                           │
│  ┌──────────────┐                  ┌──────────┐          │
│  │ evil-hacker  │─── POST ────────▶│  Server  │          │
│  │   .com       │                  └──────────┘          │
│  └──────────────┘                        │                │
│      │                                   │                │
│      │  Sends:                           │                │
│      │  - Cookie: JSESSIONID=abc123 ✓    │                │
│      │  - CSRF token: MISSING! ✗         │                │
│      │                                   │                │
│      │                           Server rejects:          │
│      │                           403 Forbidden             │
│      │                           ATTACK BLOCKED! ✓         │
│      │                                                      │
└────────────────────────────────────────────────────────────┘
```

### When to Enable/Disable CSRF?

| Architecture | CSRF | Why? |
|--------------|------|------|
| **Traditional Web App** (JSP, Thymeleaf, cookies) | ✅ ENABLE | Uses session cookies, vulnerable to CSRF |
| **REST API with JWT** (Bearer token in header) | ❌ DISABLE | No cookies, token in header can't be auto-sent |
| **REST API with Session Cookies** | ✅ ENABLE | Cookies auto-sent, vulnerable to CSRF |
| **Mobile App (native)** | ❌ DISABLE | No browser, no CSRF risk |

### Real-World Example: Traditional Web App

```java
// Traditional web app with forms
@Controller
public class BankingController {

    @GetMapping("/transfer")
    public String showTransferForm(Model model) {
        // Spring automatically adds CSRF token to model
        return "transfer";  // transfer.html
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam String to,
                          @RequestParam double amount) {
        // Spring automatically validates CSRF token
        bankService.transfer(to, amount);
        return "redirect:/success";
    }
}

// transfer.html (Thymeleaf)
<form method="POST" action="/transfer">
    <input type="text" name="to" />
    <input type="number" name="amount" />

    <!-- CSRF token automatically included by Thymeleaf -->
    <input type="hidden" name="_csrf" value="${_csrf.token}" />

    <button type="submit">Transfer</button>
</form>
```

### Real-World Example: REST API with JWT

```java
// REST API with JWT (CSRF disabled)
@RestController
@RequestMapping("/api")
public class BankingController {

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestHeader("Authorization") String jwt,  // Bearer token
            @RequestBody TransferRequest request) {

        // No CSRF needed because:
        // 1. JWT in Authorization header
        // 2. Evil site can't access JWT from localStorage
        // 3. Browser won't auto-send Authorization header

        bankService.transfer(request.getTo(), request.getAmount());
        return ResponseEntity.ok("Success");
    }
}

// Security config
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.csrf().disable();  // Safe for JWT-based APIs
        return http.build();
    }
}
```

### Why JWT is Safe from CSRF

```
Evil Site Attack with JWT:

┌──────────────────┐                   ┌────────────────┐
│ evil-hacker.com  │                   │ mybank.com API │
└──────────────────┘                   └────────────────┘
        │                                      │
        │  fetch('mybank.com/api/transfer')   │
        │  {                                   │
        │    method: 'POST',                   │
        │    headers: {                        │
        │      Authorization: '???'  ← Evil site doesn't have JWT!
        │    }                                 │
        │  }                                   │
        │                                      │
        ▼                                      ▼
   ATTACK FAILS!                         Request rejected
   JWT is in localStorage                (No Authorization header)
   Evil site can't access it!
```

### Real-World Analogy

**CSRF Token = Matching Ticket Stubs**

```
Movie Theater (Without CSRF):
1. You buy ticket → Get wristband (cookie)
2. Hacker sees your wristband
3. Hacker enters theater using same wristband color
   (Server can't tell difference!)

Movie Theater (With CSRF):
1. You buy ticket → Get wristband (cookie) + numbered stub (CSRF token)
2. To enter: Must show BOTH wristband AND matching stub
3. Hacker has wristband but doesn't know your stub number
4. Entry denied! ✓

CSRF Token = The secret stub number only you and theater know
```

---

**Enable CSRF (for traditional web apps with cookies):**
```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .authorizeHttpRequests()
            .anyRequest().authenticated();

    return http.build();
}
```

**Disable CSRF (for REST APIs with JWT):**
```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()  // Disable for JWT-based APIs
        .authorizeHttpRequests()
            .anyRequest().authenticated();

    return http.build();
}
```

**When to use:**
- ✅ Enable: Traditional web apps with session cookies
- ❌ Disable: REST API with JWT/OAuth2 (stateless)

---

## OAuth2 Login (Google, GitHub)

**Add dependency:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>
```

**Configuration:**
```properties
# Google OAuth2
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
spring.security.oauth2.client.registration.google.scope=profile,email

# GitHub OAuth2
spring.security.oauth2.client.registration.github.client-id=YOUR_GITHUB_CLIENT_ID
spring.security.oauth2.client.registration.github.client-secret=YOUR_GITHUB_CLIENT_SECRET
```

**Security Configuration:**
```java
@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
                .requestMatchers("/", "/login").permitAll()
                .anyRequest().authenticated()
            .and()
            .oauth2Login()
                .defaultSuccessUrl("/dashboard", true);

        return http.build();
    }
}
```

---

# Microservices Architecture

## Service Discovery with Eureka

### Eureka Server

**Dependency:**
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

**Configuration:**
```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

```properties
server.port=8761
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

### Eureka Client

**Dependency:**
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

```properties
spring.application.name=user-service
server.port=8081
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```

---

## API Gateway with Spring Cloud Gateway

**Dependency:**
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

**Configuration:**
```yaml
spring:
  cloud:
    gateway:
      routes:
        # User Service
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/api/users/**

        # Order Service
        - id: order-service
          uri: lb://order-service
          predicates:
            - Path=/api/orders/**

server:
  port: 8080
```

**Custom Filter:**
```java
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Skip public endpoints
        if (isPublicEndpoint(request.getPath().value())) {
            return chain.filter(exchange);
        }

        // Get token
        String token = request.getHeaders().getFirst("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        token = token.substring(7);

        // Validate token
        if (!jwtService.isTokenValid(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
```

---

## Circuit Breaker with Resilience4j

**Dependency:**
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
</dependency>
```

**Configuration:**
```properties
resilience4j.circuitbreaker.instances.userService.slidingWindowSize=10
resilience4j.circuitbreaker.instances.userService.minimumNumberOfCalls=5
resilience4j.circuitbreaker.instances.userService.failureRateThreshold=50
resilience4j.circuitbreaker.instances.userService.waitDurationInOpenState=5s
```

**Implementation:**
```java
@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "userService", fallbackMethod = "getUserFallback")
    @Retry(name = "userService")
    public User getUserById(Long userId) {
        String url = "http://user-service/api/users/" + userId;
        return restTemplate.getForObject(url, User.class);
    }

    // Fallback method
    public User getUserFallback(Long userId, Exception ex) {
        User defaultUser = new User();
        defaultUser.setId(userId);
        defaultUser.setName("Default User");
        return defaultUser;
    }
}
```

---

## Distributed Tracing with Sleuth & Zipkin

**Dependencies:**
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-sleuth</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-sleuth-zipkin</artifactId>
</dependency>
```

**Configuration:**
```properties
spring.application.name=order-service
spring.sleuth.sampler.probability=1.0
spring.zipkin.base-url=http://localhost:9411
```

**Logs automatically include trace ID:**
```
2024-01-10 10:15:30 INFO [order-service,abc123,xyz789] OrderController - Fetching order
                                    [service-name,trace-id,span-id]
```

---

## Config Server

**Server Setup:**
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

```java
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication { }
```

```properties
server.port=8888
spring.cloud.config.server.git.uri=https://github.com/username/config-repo
```

**Client Setup:**
```properties
# bootstrap.properties
spring.application.name=user-service
spring.cloud.config.uri=http://localhost:8888
spring.profiles.active=dev
```

---

## Message Queue with RabbitMQ

**Dependency:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

**Configuration:**
```java
@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "order.queue";
    public static final String EXCHANGE_NAME = "order.exchange";
    public static final String ROUTING_KEY = "order.routing.key";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
```

**Producer:**
```java
@Service
public class OrderProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrder(Order order) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME,
            RabbitMQConfig.ROUTING_KEY,
            order
        );
    }
}
```

**Consumer:**
```java
@Service
public class OrderConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveOrder(Order order) {
        // Process order
    }
}
```

---

# Testing

## Unit Testing

```java
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetUserById() {
        User user = new User(1L, "John", "john@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }
}
```

## Integration Testing

```java
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() throws Exception {
        String userJson = "{\"name\":\"John\",\"email\":\"john@example.com\"}";

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("John"));
    }
}
```

## Repository Testing

```java
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByEmail() {
        User user = new User("John", "john@example.com");
        entityManager.persist(user);

        Optional<User> found = userRepository.findByEmail("john@example.com");

        assertTrue(found.isPresent());
        assertEquals("John", found.get().getName());
    }
}
```

## Security Testing

```java
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAccessUserEndpoint() throws Exception {
        mockMvc.perform(get("/api/users/profile"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAccessAdminEndpoint() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
            .andExpect(status().isOk());
    }
}
```

---

# Performance & Best Practices

## Performance Optimization

### 1. Database Optimization

```java
// Use specific columns instead of SELECT *
@Query("SELECT new com.example.dto.UserDTO(u.id, u.name) FROM User u")
List<UserDTO> findAllUserDTOs();

// Use pagination
Page<User> findAll(Pageable pageable);

// Use @EntityGraph to avoid N+1
@EntityGraph(attributePaths = {"roles", "profile"})
List<User> findAll();
```

### 2. Caching

```java
@EnableCaching
@SpringBootApplication
public class Application { }

@Service
public class UserService {

    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
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

### 3. Async Processing

```java
@EnableAsync
@SpringBootApplication
public class Application { }

@Service
public class EmailService {

    @Async
    public CompletableFuture<Void> sendEmail(String to, String message) {
        // Send email asynchronously
        return CompletableFuture.completedFuture(null);
    }
}
```

### 4. Connection Pooling

```properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=20000
```

---

## Best Practices

### 1. Use Constructor Injection

```java
// ✅ Good
@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}

// ❌ Bad
@Service
public class UserService {
    @Autowired
    private UserRepository repository;
}
```

### 2. Use DTOs

```java
// ✅ Good - Use DTOs
@GetMapping("/users")
public List<UserDTO> getUsers() {
    return userService.getAllUsers();
}

// ❌ Bad - Expose entities
@GetMapping("/users")
public List<User> getUsers() {
    return userRepository.findAll();
}
```

### 3. Handle Exceptions Properly

```java
// ✅ Good
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Error: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),
                                    HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

### 4. Use Transactions

```java
// ✅ Good
@Transactional
public void updateUser(User user) {
    userRepository.save(user);
    logRepository.save(new Log("User updated"));
}
```

### 5. Validate Input

```java
@PostMapping("/users")
public ResponseEntity<User> createUser(@Valid @RequestBody UserRequest request) {
    // Validation happens automatically
}
```

---

## Common Mistakes to Avoid

### ❌ 1. Don't use Field Injection
### ❌ 2. Don't return Entities directly
### ❌ 3. Don't ignore Exception handling
### ❌ 4. Don't skip Transaction boundaries
### ❌ 5. Don't fetch all data without pagination

---

**This comprehensive Spring Boot guide covers everything from fundamentals to advanced topics, organized logically for easy learning! 🚀**

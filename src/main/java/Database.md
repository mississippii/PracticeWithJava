# Database - Complete Reference Guide

---

## Table of Contents

1. [Introduction to Databases](#1-introduction-to-databases)
2. [Types of Databases & When to Use](#2-types-of-databases--when-to-use)
3. [RDBMS Fundamentals](#3-rdbms-fundamentals)
4. [Constraints & Data Types](#4-constraints--data-types)
5. [SQL Basics & Query Execution](#5-sql-basics--query-execution)
6. [SQL Functions](#6-sql-functions)
7. [Database Design](#7-database-design)
8. [Indexing & Performance](#8-indexing--performance)
9. [Transactions & Concurrency](#9-transactions--concurrency)
10. [Views, Procedures, Functions & Triggers](#10-views-procedures-functions--triggers)
11. [Cursors & Temporary Tables](#11-cursors--temporary-tables)
12. [Partitioning](#12-partitioning)
13. [Scaling Databases](#13-scaling-databases)
14. [Database Security](#14-database-security)
15. [Backup & Recovery](#15-backup--recovery)
16. [OLTP vs OLAP](#16-oltp-vs-olap)
17. [Interview Questions](#17-interview-questions)

---

## 1. Introduction to Databases

### What is a Database?

A database is an organized collection of structured data stored electronically. It allows efficient storage, retrieval, modification, and deletion of data.

### Why Do We Need Databases?

**Without Database (File System):**
```
Problems:
├── Data Redundancy (same data in multiple files)
├── Data Inconsistency (conflicting data)
├── No Concurrent Access (one user at a time)
├── No Security (anyone can access files)
├── No ACID properties (partial updates possible)
├── Complex Queries (manual file parsing)
└── No Relationships (hard to link related data)
```

**With Database:**
```
Solutions:
├── Centralized Data (single source of truth)
├── Data Integrity (constraints, validations)
├── Concurrent Access (multiple users safely)
├── Security (authentication, authorization)
├── ACID Compliance (reliable transactions)
├── Powerful Queries (SQL)
└── Relationships (foreign keys, joins)
```

### Real-World Example

**Scenario:** E-commerce application storing user orders

**File System Approach (Bad):**
```
users.txt:
1,John,john@email.com
2,Jane,jane@email.com

orders.txt:
101,1,iPhone,1000
102,1,Case,50
103,2,MacBook,2000

Problems:
- What if John's email changes? Update in how many files?
- What if we delete user 1? Orders become orphaned
- How to find "all orders by John"? Parse both files manually
- Two admins editing same file? Data corruption
```

**Database Approach (Good):**
```sql
-- Structured tables with relationships
CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE
);

CREATE TABLE orders (
    id INT PRIMARY KEY,
    user_id INT REFERENCES users(id),  -- Relationship!
    product VARCHAR(100),
    amount DECIMAL
);

-- Easy queries
SELECT * FROM orders WHERE user_id = 1;

-- Cascade updates/deletes
-- Data integrity maintained automatically
```

---

## 2. Types of Databases & When to Use

### Database Categories

```
                        DATABASES
                            │
            ┌───────────────┴───────────────┐
            │                               │
         RDBMS                           NoSQL
      (Relational)                    (Non-Relational)
            │                               │
    ┌───────┴───────┐           ┌───────────┼───────────┐
    │               │           │           │           │
  MySQL        PostgreSQL    Document   Key-Value    Graph
  Oracle       SQL Server    (MongoDB)  (Redis)    (Neo4j)
  MariaDB                    (CouchDB)  (Memcached)
                                        Column-Family
                                        (Cassandra)
                                        (HBase)
```

---

### RDBMS (Relational Database)

**What:** Stores data in tables with rows and columns, linked by relationships.

**Characteristics:**
- Structured schema (predefined)
- ACID compliant
- SQL for queries
- Strong consistency
- Vertical scaling (scale up)

**When to Use RDBMS:**

| Use Case | Why RDBMS? |
|----------|------------|
| Banking/Financial | ACID compliance, no data loss |
| E-commerce | Complex queries, transactions |
| ERP Systems | Structured data, relationships |
| Inventory Management | Data integrity, constraints |
| Healthcare Records | Compliance, consistency |
| Booking Systems | Transaction safety |

**Popular RDBMS:**

| Database | Best For |
|----------|----------|
| **PostgreSQL** | Complex queries, JSONB support, open-source |
| **MySQL** | Web applications, read-heavy workloads |
| **Oracle** | Enterprise, large scale |
| **SQL Server** | Microsoft ecosystem, BI |
| **SQLite** | Embedded, mobile apps, small projects |

---

### NoSQL Databases

#### Document Database (MongoDB, CouchDB)

**What:** Stores data as JSON-like documents.

```json
{
  "_id": "user123",
  "name": "John",
  "email": "john@email.com",
  "orders": [
    {"product": "iPhone", "amount": 1000},
    {"product": "Case", "amount": 50}
  ]
}
```

**When to Use:**
- Flexible/evolving schema
- Content management systems
- Catalogs, user profiles
- Real-time analytics
- Mobile app backends

**Example Use Cases:**
- Product catalog (varying attributes)
- Blog posts with comments
- User preferences/settings

---

#### Key-Value Store (Redis, Memcached)

**What:** Simple key-value pairs, extremely fast.

```
key: "session:user123"
value: "{userId: 123, token: 'abc', expiry: 3600}"
```

**When to Use:**
- Caching
- Session management
- Real-time leaderboards
- Rate limiting
- Message queues

**Example Use Cases:**
- Cache frequently accessed data
- Store user sessions
- Shopping cart (temporary data)

---

#### Column-Family (Cassandra, HBase)

**What:** Stores data in columns instead of rows, optimized for writes.

```
Row Key: user123
├── Column Family: profile
│   ├── name: "John"
│   └── email: "john@email.com"
└── Column Family: activity
    ├── last_login: "2024-01-15"
    └── login_count: 150
```

**When to Use:**
- Time-series data
- IoT sensor data
- Write-heavy workloads
- Distributed across regions
- Analytics/logging

**Example Use Cases:**
- Netflix viewing history
- IoT device logs
- Stock price history

---

#### Graph Database (Neo4j, Amazon Neptune)

**What:** Stores data as nodes and relationships (edges).

```
(John)-[:FRIENDS_WITH]->(Jane)
(John)-[:PURCHASED]->(iPhone)
(Jane)-[:REVIEWED]->(iPhone)
```

**When to Use:**
- Social networks
- Recommendation engines
- Fraud detection
- Knowledge graphs
- Network analysis

**Example Use Cases:**
- Facebook friend suggestions
- LinkedIn connections
- Fraud pattern detection

---

### Decision Matrix: Which Database to Choose?

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    DATABASE SELECTION GUIDE                             │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  START HERE                                                             │
│      │                                                                  │
│      ▼                                                                  │
│  Do you need ACID compliance?                                           │
│      │                                                                  │
│      ├── YES ──► Is schema well-defined?                                │
│      │               │                                                  │
│      │               ├── YES ──► RDBMS (PostgreSQL, MySQL)              │
│      │               │                                                  │
│      │               └── NO ──► Document DB with transactions           │
│      │                          (MongoDB 4.0+)                          │
│      │                                                                  │
│      └── NO ──► What's your priority?                                   │
│                    │                                                    │
│                    ├── Speed/Caching ──► Key-Value (Redis)              │
│                    │                                                    │
│                    ├── Flexibility ──► Document DB (MongoDB)            │
│                    │                                                    │
│                    ├── Write-heavy/Time-series ──► Column (Cassandra)   │
│                    │                                                    │
│                    └── Relationships/Networks ──► Graph (Neo4j)         │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### Quick Comparison Table

| Feature | RDBMS | Document | Key-Value | Column | Graph |
|---------|-------|----------|-----------|--------|-------|
| **Schema** | Fixed | Flexible | None | Flexible | Flexible |
| **ACID** | Yes | Partial | No | No | Yes |
| **Scaling** | Vertical | Horizontal | Horizontal | Horizontal | Vertical |
| **Query** | SQL | JSON-based | Key lookup | CQL | Cypher |
| **Best For** | Transactions | Flexibility | Speed | Writes | Relations |
| **Example** | PostgreSQL | MongoDB | Redis | Cassandra | Neo4j |

### Real-World Tech Stacks

| Company | Primary DB | Purpose | Secondary DB | Purpose |
|---------|-----------|---------|--------------|---------|
| **Uber** | PostgreSQL | Trips, Users | Redis | Caching, Geo |
| **Netflix** | Cassandra | Viewing history | MySQL | Billing |
| **Twitter** | MySQL | Tweets | Redis | Timeline cache |
| **LinkedIn** | Oracle | User data | Kafka | Events |
| **Instagram** | PostgreSQL | Core data | Cassandra | Feed |

---

## 3. RDBMS Fundamentals

### Core Concepts

```
DATABASE
    │
    └── TABLES (Relations)
            │
            ├── COLUMNS (Attributes) ──► Define data types
            │
            └── ROWS (Records/Tuples) ──► Actual data
```

**Example:**
```sql
-- Table: students
┌────────────┬─────────────┬─────────────────────┬─────┐
│ student_id │ name        │ email               │ age │
│ (PK)       │ (VARCHAR)   │ (VARCHAR, UNIQUE)   │(INT)│
├────────────┼─────────────┼─────────────────────┼─────┤
│ 1          │ Alice       │ alice@example.com   │ 20  │  ← Row
│ 2          │ Bob         │ bob@example.com     │ 22  │  ← Row
│ 3          │ Charlie     │ charlie@example.com │ 21  │  ← Row
└────────────┴─────────────┴─────────────────────┴─────┘
      ▲            ▲               ▲                ▲
   Column       Column          Column           Column
```

---

### Keys

#### Primary Key (PK)

Uniquely identifies each row in a table.

**Properties:**
- Must be **unique**
- Cannot be **NULL**
- Only **one per table**
- Creates **clustered index** automatically

```sql
CREATE TABLE students (
    student_id INT PRIMARY KEY,  -- Single column PK
    name VARCHAR(100),
    email VARCHAR(100)
);

-- Composite Primary Key
CREATE TABLE enrollments (
    student_id INT,
    course_id INT,
    PRIMARY KEY (student_id, course_id)  -- Two columns together
);
```

---

#### Foreign Key (FK)

References primary key of another table, creating relationships.

```sql
CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    customer_id INT,
    amount DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);
```

**Referential Integrity Actions:**
```sql
-- ON DELETE options
FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
    ON DELETE CASCADE      -- Delete orders when customer deleted
    ON DELETE SET NULL     -- Set customer_id to NULL
    ON DELETE RESTRICT     -- Prevent deletion if orders exist
    ON DELETE NO ACTION    -- Same as RESTRICT (default)

-- ON UPDATE options
    ON UPDATE CASCADE      -- Update FK when PK changes
```

---

#### Unique Key

Ensures all values in column are different.

| Primary Key | Unique Key |
|-------------|------------|
| Only one per table | Multiple allowed |
| Cannot be NULL | Can have one NULL |
| Creates clustered index | Creates non-clustered index |

```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY,
    email VARCHAR(100) UNIQUE,      -- Unique constraint
    phone VARCHAR(15) UNIQUE        -- Another unique constraint
);
```

---

### Relationships

#### One-to-One (1:1)

One record in Table A relates to exactly one record in Table B.

```
┌─────────┐         ┌──────────┐
│ Person  │────1:1──│ Passport │
└─────────┘         └──────────┘

CREATE TABLE persons (
    person_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE passports (
    passport_id INT PRIMARY KEY,
    person_id INT UNIQUE,  -- UNIQUE ensures 1:1
    passport_number VARCHAR(20),
    FOREIGN KEY (person_id) REFERENCES persons(person_id)
);
```

---

#### One-to-Many (1:N)

One record in Table A relates to many records in Table B.

```
┌────────────┐         ┌───────────┐
│ Department │────1:N──│ Employees │
└────────────┘         └───────────┘

CREATE TABLE departments (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(100)
);

CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100),
    dept_id INT,  -- Many employees can have same dept_id
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id)
);
```

---

#### Many-to-Many (M:N)

Many records in Table A relate to many records in Table B.

**Requires a Junction (Bridge) Table:**

```
┌──────────┐         ┌────────────┐         ┌─────────┐
│ Students │────M:N──│ Enrollment │────M:N──│ Courses │
└──────────┘         │ (Junction) │         └─────────┘
                     └────────────┘

CREATE TABLE students (
    student_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE courses (
    course_id INT PRIMARY KEY,
    course_name VARCHAR(100)
);

-- Junction table
CREATE TABLE enrollments (
    enrollment_id INT PRIMARY KEY,
    student_id INT,
    course_id INT,
    grade CHAR(1),
    enrolled_date DATE,
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id),
    UNIQUE (student_id, course_id)  -- Prevent duplicate enrollments
);
```

---

### ACID Properties

ACID ensures reliable database transactions.

```
┌─────────────────────────────────────────────────────────────────┐
│                        ACID PROPERTIES                          │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  A - ATOMICITY                                                  │
│  ─────────────                                                  │
│  "All or Nothing"                                               │
│                                                                 │
│  Transaction: Transfer $100 from Account A to Account B         │
│                                                                 │
│  Step 1: Debit A  (-$100)  ✓                                    │
│  Step 2: Credit B (+$100)  ✗ (fails)                            │
│                                                                 │
│  Result: ROLLBACK - Both steps undone                           │
│          A keeps $100, B gets nothing                           │
│                                                                 │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  C - CONSISTENCY                                                │
│  ───────────────                                                │
│  "Valid State to Valid State"                                   │
│                                                                 │
│  Constraint: balance >= 0                                       │
│                                                                 │
│  Before: A = $100, B = $50  (valid state)                       │
│  Transaction: Transfer $150 from A to B                         │
│  Result: REJECTED (would make A = -$50)                         │
│  After: A = $100, B = $50   (still valid state)                 │
│                                                                 │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  I - ISOLATION                                                  │
│  ─────────────                                                  │
│  "Transactions Don't Interfere"                                 │
│                                                                 │
│  Transaction 1: Read balance (sees $100)                        │
│  Transaction 2: Update balance to $150                          │
│  Transaction 1: Read balance again                              │
│                                                                 │
│  Depending on isolation level:                                  │
│  - READ UNCOMMITTED: sees $150 (dirty read)                     │
│  - REPEATABLE READ: sees $100 (consistent)                      │
│                                                                 │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  D - DURABILITY                                                 │
│  ─────────────                                                  │
│  "Permanent Once Committed"                                     │
│                                                                 │
│  1. Transaction commits successfully                            │
│  2. Database confirms: "COMMIT OK"                              │
│  3. Power failure / crash                                       │
│  4. System restarts                                             │
│  5. Data is still there (written to disk)                       │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 4. Constraints & Data Types

### Constraints

Constraints enforce rules on data in tables to maintain integrity.

```
┌─────────────────────────────────────────────────────────────────┐
│                      SQL CONSTRAINTS                            │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  NOT NULL      - Column cannot have NULL value                  │
│  UNIQUE        - All values in column must be different         │
│  PRIMARY KEY   - NOT NULL + UNIQUE (identifies row)             │
│  FOREIGN KEY   - Links to primary key in another table          │
│  CHECK         - Values must satisfy a condition                │
│  DEFAULT       - Sets default value if none provided            │
│  INDEX         - Speeds up data retrieval (not a constraint)    │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

#### NOT NULL Constraint

```sql
CREATE TABLE employees (
    id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100)  -- Can be NULL
);

-- Adding NOT NULL to existing column
ALTER TABLE employees MODIFY email VARCHAR(100) NOT NULL;
```

#### UNIQUE Constraint

```sql
CREATE TABLE users (
    id INT PRIMARY KEY,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15) UNIQUE
);

-- Named constraint
CREATE TABLE users (
    id INT PRIMARY KEY,
    email VARCHAR(100),
    CONSTRAINT uk_email UNIQUE (email)
);

-- Composite unique (combination must be unique)
CREATE TABLE enrollments (
    student_id INT,
    course_id INT,
    UNIQUE (student_id, course_id)
);
```

#### CHECK Constraint

```sql
CREATE TABLE employees (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    age INT CHECK (age >= 18 AND age <= 65),
    salary DECIMAL(10,2) CHECK (salary > 0),
    status VARCHAR(20) CHECK (status IN ('active', 'inactive', 'pending'))
);

-- Named CHECK constraint
CREATE TABLE products (
    id INT PRIMARY KEY,
    price DECIMAL(10,2),
    discount DECIMAL(5,2),
    CONSTRAINT chk_discount CHECK (discount >= 0 AND discount <= price)
);
```

#### DEFAULT Constraint

```sql
CREATE TABLE orders (
    id INT PRIMARY KEY,
    order_date DATE DEFAULT CURRENT_DATE,
    status VARCHAR(20) DEFAULT 'pending',
    quantity INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert without specifying default columns
INSERT INTO orders (id) VALUES (1);
-- Result: order_date = today, status = 'pending', quantity = 1
```

#### Constraint Summary Example

```sql
CREATE TABLE employees (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) UNIQUE,
    hire_date DATE DEFAULT CURRENT_DATE,
    salary DECIMAL(10,2) CHECK (salary >= 0),
    department_id INT,
    status VARCHAR(20) DEFAULT 'active' CHECK (status IN ('active', 'inactive')),

    FOREIGN KEY (department_id) REFERENCES departments(dept_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);
```

---

### Data Types

#### Numeric Types

```
┌────────────────────────────────────────────────────────────────┐
│                     NUMERIC DATA TYPES                         │
├────────────────────────────────────────────────────────────────┤
│                                                                │
│  INTEGER TYPES:                                                │
│  ─────────────                                                 │
│  TINYINT     │ 1 byte  │ -128 to 127 (0 to 255 unsigned)       │
│  SMALLINT    │ 2 bytes │ -32,768 to 32,767                     │
│  MEDIUMINT   │ 3 bytes │ -8M to 8M                             │
│  INT/INTEGER │ 4 bytes │ -2B to 2B                             │
│  BIGINT      │ 8 bytes │ -9 quintillion to 9 quintillion       │
│                                                                │
│  DECIMAL TYPES:                                                │
│  ─────────────                                                 │
│  DECIMAL(p,s) │ Exact precision │ For money/finance            │
│  NUMERIC(p,s) │ Same as DECIMAL │                              │
│  FLOAT        │ 4 bytes         │ Approximate, ~7 digits       │
│  DOUBLE       │ 8 bytes         │ Approximate, ~15 digits      │
│                                                                │
│  p = precision (total digits), s = scale (decimal places)      │
│  DECIMAL(10,2) = 12345678.90 (8 digits + 2 decimals)           │
│                                                                │
└────────────────────────────────────────────────────────────────┘
```

```sql
CREATE TABLE products (
    id INT,
    quantity SMALLINT,
    price DECIMAL(10, 2),      -- Exact: 99999999.99
    weight FLOAT,              -- Approximate
    rating DECIMAL(3, 2)       -- 0.00 to 9.99
);
```

#### String Types

```
┌────────────────────────────────────────────────────────────────┐
│                     STRING DATA TYPES                          │
├────────────────────────────────────────────────────────────────┤
│                                                                │
│  CHAR(n)      │ Fixed length, padded with spaces │ Max 255     │
│  VARCHAR(n)   │ Variable length                  │ Max 65,535  │
│  TEXT         │ Variable length, large text      │ Max 65,535  │
│  MEDIUMTEXT   │ Larger text                      │ Max 16MB    │
│  LONGTEXT     │ Huge text                        │ Max 4GB     │
│                                                                │
│  CHAR vs VARCHAR:                                              │
│  ───────────────                                               │
│  CHAR(10) 'abc'     → 'abc       ' (7 spaces padded)           │
│  VARCHAR(10) 'abc'  → 'abc' (no padding, 3 chars stored)       │
│                                                                │
│  Use CHAR for: Fixed-length codes (country_code, status)       │
│  Use VARCHAR for: Variable-length (names, emails, addresses)   │
│                                                                │
└────────────────────────────────────────────────────────────────┘
```

```sql
CREATE TABLE users (
    id INT,
    country_code CHAR(2),        -- 'US', 'IN', 'UK'
    name VARCHAR(100),           -- Variable length
    bio TEXT,                    -- Long text
    profile_json LONGTEXT        -- Very large text/JSON
);
```

#### Date & Time Types

```
┌────────────────────────────────────────────────────────────────┐
│                   DATE & TIME DATA TYPES                       │
├────────────────────────────────────────────────────────────────┤
│                                                                │
│  DATE       │ 'YYYY-MM-DD'              │ '2024-01-15'         │
│  TIME       │ 'HH:MM:SS'                │ '14:30:00'           │
│  DATETIME   │ 'YYYY-MM-DD HH:MM:SS'     │ '2024-01-15 14:30:00'│
│  TIMESTAMP  │ Same as DATETIME          │ Auto-converts to UTC │
│  YEAR       │ 'YYYY'                    │ '2024'               │
│                                                                │
│  DATETIME vs TIMESTAMP:                                        │
│  ──────────────────────                                        │
│  DATETIME  - Stores exact value, no timezone conversion        │
│  TIMESTAMP - Converts to UTC on storage, back on retrieval     │
│            - Range: 1970-2038 (32-bit limit)                   │
│            - Good for: created_at, updated_at                  │
│                                                                │
└────────────────────────────────────────────────────────────────┘
```

```sql
CREATE TABLE events (
    id INT,
    event_date DATE,
    start_time TIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    scheduled_at DATETIME
);
```

#### Other Important Types

```sql
-- BOOLEAN
CREATE TABLE users (
    id INT,
    is_active BOOLEAN DEFAULT TRUE,  -- Stored as TINYINT(1)
    is_verified BOOL DEFAULT FALSE
);

-- ENUM (predefined values)
CREATE TABLE orders (
    id INT,
    status ENUM('pending', 'processing', 'shipped', 'delivered') DEFAULT 'pending'
);

-- SET (multiple values from predefined list)
CREATE TABLE users (
    id INT,
    permissions SET('read', 'write', 'delete', 'admin')
);
INSERT INTO users VALUES (1, 'read,write');

-- JSON (MySQL 5.7+, PostgreSQL)
CREATE TABLE products (
    id INT,
    attributes JSON
);
INSERT INTO products VALUES (1, '{"color": "red", "size": "XL"}');

-- BLOB (Binary Large Object)
CREATE TABLE files (
    id INT,
    file_data BLOB,           -- Up to 65KB
    image MEDIUMBLOB,         -- Up to 16MB
    video LONGBLOB            -- Up to 4GB
);

-- UUID (PostgreSQL native, MySQL as CHAR/BINARY)
-- PostgreSQL
CREATE TABLE users (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY
);
-- MySQL
CREATE TABLE users (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID())
);
```

#### Auto Increment / Sequences

```sql
-- MySQL: AUTO_INCREMENT
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

-- PostgreSQL: SERIAL / IDENTITY
CREATE TABLE users (
    id SERIAL PRIMARY KEY,           -- Old way
    name VARCHAR(100)
);

CREATE TABLE users (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,  -- New way (SQL standard)
    name VARCHAR(100)
);

-- Oracle: SEQUENCE
CREATE SEQUENCE user_seq START WITH 1 INCREMENT BY 1;
INSERT INTO users (id, name) VALUES (user_seq.NEXTVAL, 'John');

-- Getting last inserted ID
-- MySQL
SELECT LAST_INSERT_ID();

-- PostgreSQL
INSERT INTO users (name) VALUES ('John') RETURNING id;

-- SQL Server
SELECT SCOPE_IDENTITY();
```

---

## 5. SQL Basics & Query Execution

### SQL Query Execution Order

**Important:** SQL executes in a different order than written!

```sql
-- WRITTEN ORDER:
SELECT column           -- 5th
FROM table              -- 1st
WHERE condition         -- 2nd
GROUP BY column         -- 3rd
HAVING condition        -- 4th
ORDER BY column         -- 6th
LIMIT n                 -- 7th

-- EXECUTION ORDER:
1. FROM table          → Get the data source
2. WHERE condition     → Filter individual rows
3. GROUP BY column     → Group rows together
4. HAVING condition    → Filter groups
5. SELECT column       → Choose columns to display
6. ORDER BY column     → Sort results
7. LIMIT n             → Limit output rows
```

**Why does this matter?**
```sql
-- This WORKS (column alias in ORDER BY)
SELECT name, salary * 12 AS annual_salary
FROM employees
ORDER BY annual_salary;  -- OK: ORDER BY runs after SELECT

-- This FAILS (column alias in WHERE)
SELECT name, salary * 12 AS annual_salary
FROM employees
WHERE annual_salary > 50000;  -- ERROR: WHERE runs before SELECT

-- Fix:
SELECT name, salary * 12 AS annual_salary
FROM employees
WHERE salary * 12 > 50000;  -- Use actual expression
```

---

### Basic SQL Operations

#### SELECT - Read Data
```sql
-- Basic select
SELECT * FROM employees;

-- Select specific columns
SELECT name, salary FROM employees;

-- With alias
SELECT name AS employee_name, salary * 12 AS annual_salary
FROM employees;

-- With conditions
SELECT * FROM employees WHERE salary > 50000;

-- With sorting
SELECT * FROM employees ORDER BY salary DESC;

-- With limit
SELECT * FROM employees LIMIT 10;
```

#### INSERT - Create Data
```sql
-- Single row
INSERT INTO employees (name, salary, dept_id)
VALUES ('John', 50000, 1);

-- Multiple rows
INSERT INTO employees (name, salary, dept_id)
VALUES
    ('Alice', 60000, 1),
    ('Bob', 55000, 2),
    ('Charlie', 70000, 1);

-- Insert from select
INSERT INTO employee_archive
SELECT * FROM employees WHERE status = 'inactive';
```

#### UPDATE - Modify Data
```sql
-- Update single column
UPDATE employees SET salary = 55000 WHERE id = 1;

-- Update multiple columns
UPDATE employees
SET salary = 60000, dept_id = 2
WHERE id = 1;

-- Update with calculation
UPDATE employees SET salary = salary * 1.1;  -- 10% raise for all
```

#### DELETE - Remove Data
```sql
-- Delete specific rows
DELETE FROM employees WHERE id = 1;

-- Delete with condition
DELETE FROM employees WHERE status = 'inactive';

-- Delete all (use TRUNCATE for better performance)
DELETE FROM employees;
```

---

### JOINs

```
Tables:
employees                      departments
┌────┬───────┬─────────┐      ┌─────────┬───────────┐
│ id │ name  │ dept_id │      │ dept_id │ dept_name │
├────┼───────┼─────────┤      ├─────────┼───────────┤
│ 1  │ Alice │ 1       │      │ 1       │ IT        │
│ 2  │ Bob   │ 2       │      │ 2       │ HR        │
│ 3  │ Carol │ NULL    │      │ 3       │ Finance   │
└────┴───────┴─────────┘      └─────────┴───────────┘
```

#### INNER JOIN
Returns only matching rows from both tables.

```sql
SELECT e.name, d.dept_name
FROM employees e
INNER JOIN departments d ON e.dept_id = d.dept_id;

-- Result:
-- Alice | IT
-- Bob   | HR
-- (Carol excluded - no matching dept)
-- (Finance excluded - no matching employee)
```

#### LEFT JOIN
Returns all rows from left table + matching from right.

```sql
SELECT e.name, d.dept_name
FROM employees e
LEFT JOIN departments d ON e.dept_id = d.dept_id;

-- Result:
-- Alice | IT
-- Bob   | HR
-- Carol | NULL  (included, no match)
```

#### RIGHT JOIN
Returns all rows from right table + matching from left.

```sql
SELECT e.name, d.dept_name
FROM employees e
RIGHT JOIN departments d ON e.dept_id = d.dept_id;

-- Result:
-- Alice   | IT
-- Bob     | HR
-- NULL    | Finance  (included, no match)
```

#### FULL OUTER JOIN
Returns all rows from both tables.

```sql
SELECT e.name, d.dept_name
FROM employees e
FULL OUTER JOIN departments d ON e.dept_id = d.dept_id;

-- Result:
-- Alice | IT
-- Bob   | HR
-- Carol | NULL
-- NULL  | Finance
```

#### CROSS JOIN
Returns Cartesian product (every combination).

```sql
SELECT e.name, d.dept_name
FROM employees e
CROSS JOIN departments d;

-- Result: 3 employees × 3 departments = 9 rows
```

#### SELF JOIN
Join table with itself.

```sql
-- Find employees and their managers
SELECT e.name AS employee, m.name AS manager
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.id;
```

---

### Aggregations & Grouping

#### Aggregate Functions
```sql
SELECT
    COUNT(*) AS total_employees,
    COUNT(DISTINCT dept_id) AS departments,
    SUM(salary) AS total_salary,
    AVG(salary) AS average_salary,
    MAX(salary) AS highest_salary,
    MIN(salary) AS lowest_salary
FROM employees;
```

#### GROUP BY
```sql
-- Count employees per department
SELECT dept_id, COUNT(*) AS employee_count
FROM employees
GROUP BY dept_id;

-- Average salary per department
SELECT dept_id, AVG(salary) AS avg_salary
FROM employees
GROUP BY dept_id;
```

#### HAVING (Filter Groups)
```sql
-- Departments with more than 5 employees
SELECT dept_id, COUNT(*) AS employee_count
FROM employees
GROUP BY dept_id
HAVING COUNT(*) > 5;

-- WHERE vs HAVING
SELECT dept_id, AVG(salary) AS avg_salary
FROM employees
WHERE status = 'active'      -- Filter ROWS before grouping
GROUP BY dept_id
HAVING AVG(salary) > 50000;  -- Filter GROUPS after grouping
```

---

### Subqueries

#### Scalar Subquery (Returns single value)
```sql
-- Employees earning above average
SELECT name, salary
FROM employees
WHERE salary > (SELECT AVG(salary) FROM employees);
```

#### Row Subquery (Returns single row)
```sql
-- Employee with highest salary
SELECT * FROM employees
WHERE (dept_id, salary) = (
    SELECT dept_id, MAX(salary)
    FROM employees
    GROUP BY dept_id
    LIMIT 1
);
```

#### Table Subquery (Returns multiple rows)
```sql
-- Employees in IT or HR
SELECT * FROM employees
WHERE dept_id IN (
    SELECT dept_id FROM departments
    WHERE dept_name IN ('IT', 'HR')
);
```

#### Correlated Subquery
```sql
-- Employees earning above their department average
SELECT e.name, e.salary, e.dept_id
FROM employees e
WHERE e.salary > (
    SELECT AVG(salary)
    FROM employees
    WHERE dept_id = e.dept_id  -- References outer query
);
```

---

### UNION Operations

```sql
-- UNION (removes duplicates)
SELECT name FROM employees
UNION
SELECT name FROM contractors;

-- UNION ALL (keeps duplicates, faster)
SELECT name FROM employees
UNION ALL
SELECT name FROM contractors;
```

| UNION | UNION ALL |
|-------|-----------|
| Removes duplicates | Keeps all rows |
| Slower (sorts data) | Faster |
| Uses DISTINCT internally | No deduplication |

---

## 6. SQL Functions

### String Functions

```sql
-- LENGTH / CHAR_LENGTH
SELECT LENGTH('Hello');                    -- 5
SELECT CHAR_LENGTH('Hello');               -- 5

-- CONCAT / CONCAT_WS
SELECT CONCAT('Hello', ' ', 'World');      -- 'Hello World'
SELECT CONCAT_WS('-', '2024', '01', '15'); -- '2024-01-15'

-- UPPER / LOWER
SELECT UPPER('hello');                     -- 'HELLO'
SELECT LOWER('HELLO');                     -- 'hello'

-- TRIM / LTRIM / RTRIM
SELECT TRIM('  hello  ');                  -- 'hello'
SELECT LTRIM('  hello');                   -- 'hello'
SELECT RTRIM('hello  ');                   -- 'hello'

-- SUBSTRING / SUBSTR
SELECT SUBSTRING('Hello World', 1, 5);     -- 'Hello'
SELECT SUBSTRING('Hello World', 7);        -- 'World'

-- LEFT / RIGHT
SELECT LEFT('Hello', 3);                   -- 'Hel'
SELECT RIGHT('Hello', 3);                  -- 'llo'

-- REPLACE
SELECT REPLACE('Hello World', 'World', 'SQL');  -- 'Hello SQL'

-- REVERSE
SELECT REVERSE('Hello');                   -- 'olleH'

-- INSTR / LOCATE (find position)
SELECT INSTR('Hello World', 'o');          -- 5 (first occurrence)
SELECT LOCATE('o', 'Hello World', 6);      -- 8 (search from position 6)

-- LPAD / RPAD (padding)
SELECT LPAD('123', 5, '0');                -- '00123'
SELECT RPAD('123', 5, '0');                -- '12300'

-- COALESCE (first non-null)
SELECT COALESCE(NULL, NULL, 'default');    -- 'default'
SELECT COALESCE(name, 'Unknown') FROM users;
```

---

### Numeric Functions

```sql
-- ROUND / CEIL / FLOOR
SELECT ROUND(3.567, 2);         -- 3.57
SELECT ROUND(3.567);            -- 4
SELECT CEIL(3.1);               -- 4
SELECT FLOOR(3.9);              -- 3

-- ABS / SIGN
SELECT ABS(-5);                 -- 5
SELECT SIGN(-5);                -- -1 (negative)
SELECT SIGN(5);                 -- 1 (positive)
SELECT SIGN(0);                 -- 0

-- MOD / % (modulo)
SELECT MOD(10, 3);              -- 1
SELECT 10 % 3;                  -- 1

-- POWER / SQRT
SELECT POWER(2, 3);             -- 8 (2^3)
SELECT SQRT(16);                -- 4

-- TRUNCATE
SELECT TRUNCATE(3.567, 2);      -- 3.56 (no rounding)

-- RAND (random number 0-1)
SELECT RAND();                  -- 0.123456...
SELECT FLOOR(RAND() * 100);     -- Random 0-99
```

---

### Date & Time Functions

```sql
-- Current date/time
SELECT CURRENT_DATE;            -- '2024-01-15'
SELECT CURRENT_TIME;            -- '14:30:00'
SELECT CURRENT_TIMESTAMP;       -- '2024-01-15 14:30:00'
SELECT NOW();                   -- '2024-01-15 14:30:00'

-- Extract parts
SELECT YEAR('2024-01-15');      -- 2024
SELECT MONTH('2024-01-15');     -- 1
SELECT DAY('2024-01-15');       -- 15
SELECT HOUR('14:30:00');        -- 14
SELECT MINUTE('14:30:00');      -- 30
SELECT SECOND('14:30:00');      -- 0

-- EXTRACT (SQL Standard)
SELECT EXTRACT(YEAR FROM '2024-01-15');    -- 2024
SELECT EXTRACT(MONTH FROM '2024-01-15');   -- 1

-- DATE_ADD / DATE_SUB
SELECT DATE_ADD('2024-01-15', INTERVAL 1 DAY);    -- '2024-01-16'
SELECT DATE_ADD('2024-01-15', INTERVAL 1 MONTH);  -- '2024-02-15'
SELECT DATE_SUB('2024-01-15', INTERVAL 1 YEAR);   -- '2023-01-15'

-- DATEDIFF (difference in days)
SELECT DATEDIFF('2024-01-20', '2024-01-15');      -- 5

-- DATE_FORMAT (MySQL)
SELECT DATE_FORMAT('2024-01-15', '%d/%m/%Y');     -- '15/01/2024'
SELECT DATE_FORMAT('2024-01-15', '%W, %M %d');    -- 'Monday, January 15'

-- TO_CHAR (PostgreSQL, Oracle)
SELECT TO_CHAR(NOW(), 'DD/MM/YYYY');              -- '15/01/2024'

-- DAYNAME / MONTHNAME
SELECT DAYNAME('2024-01-15');   -- 'Monday'
SELECT MONTHNAME('2024-01-15'); -- 'January'

-- Last day of month
SELECT LAST_DAY('2024-02-15');  -- '2024-02-29'
```

---

### Conditional Functions

```sql
-- CASE WHEN (most flexible)
SELECT
    name,
    salary,
    CASE
        WHEN salary >= 100000 THEN 'High'
        WHEN salary >= 50000 THEN 'Medium'
        ELSE 'Low'
    END AS salary_grade
FROM employees;

-- Simple CASE
SELECT
    status,
    CASE status
        WHEN 'A' THEN 'Active'
        WHEN 'I' THEN 'Inactive'
        WHEN 'P' THEN 'Pending'
        ELSE 'Unknown'
    END AS status_name
FROM users;

-- IF (MySQL)
SELECT IF(score >= 60, 'Pass', 'Fail') AS result FROM students;

-- IIF (SQL Server)
SELECT IIF(score >= 60, 'Pass', 'Fail') AS result FROM students;

-- IFNULL / ISNULL / NVL (handle NULL)
SELECT IFNULL(phone, 'N/A') FROM users;           -- MySQL
SELECT ISNULL(phone, 'N/A') FROM users;           -- SQL Server
SELECT NVL(phone, 'N/A') FROM users;              -- Oracle
SELECT COALESCE(phone, 'N/A') FROM users;         -- Standard SQL

-- NULLIF (returns NULL if equal)
SELECT NULLIF(value, 0);                          -- Returns NULL if value is 0
SELECT total / NULLIF(count, 0);                  -- Avoid division by zero

-- GREATEST / LEAST
SELECT GREATEST(10, 20, 15);    -- 20
SELECT LEAST(10, 20, 15);       -- 10
```

---

### Aggregate Functions (Review)

```sql
-- Basic aggregates
SELECT COUNT(*) FROM orders;                    -- Count all rows
SELECT COUNT(DISTINCT category) FROM products;  -- Count unique values
SELECT SUM(amount) FROM orders;                 -- Sum
SELECT AVG(salary) FROM employees;              -- Average
SELECT MAX(price) FROM products;                -- Maximum
SELECT MIN(price) FROM products;                -- Minimum

-- GROUP_CONCAT / STRING_AGG (combine values)
-- MySQL
SELECT department, GROUP_CONCAT(name SEPARATOR ', ')
FROM employees GROUP BY department;
-- Result: 'IT', 'John, Jane, Bob'

-- PostgreSQL
SELECT department, STRING_AGG(name, ', ')
FROM employees GROUP BY department;

-- SQL Server
SELECT department, STRING_AGG(name, ', ')
FROM employees GROUP BY department;
```

---

## 7. Database Design

### Normalization

**Purpose:** Organize data to reduce redundancy and improve integrity.

```
┌─────────────────────────────────────────────────────────────────┐
│                     NORMALIZATION LEVELS                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Unnormalized ──► 1NF ──► 2NF ──► 3NF ──► BCNF                  │
│                                                                 │
│  Less Normal ◄──────────────────────────────────► More Normal   │
│  More Redundancy                              Less Redundancy   │
│  Faster Reads                                 Faster Writes     │
│  Slower Writes                                More Joins        │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

#### 1NF (First Normal Form)

**Rule:** Each column contains atomic (indivisible) values.

**Before 1NF (Violates):**
```
┌────────────┬───────┬───────────────────┐
│ student_id │ name  │ courses           │
├────────────┼───────┼───────────────────┤
│ 1          │ Alice │ Math, Physics     │  ← Multiple values!
│ 2          │ Bob   │ Chemistry         │
└────────────┴───────┴───────────────────┘
```

**After 1NF:**
```
┌────────────┬───────┬───────────┐
│ student_id │ name  │ course    │
├────────────┼───────┼───────────┤
│ 1          │ Alice │ Math      │  ← One value per cell
│ 1          │ Alice │ Physics   │
│ 2          │ Bob   │ Chemistry │
└────────────┴───────┴───────────┘
```

---

#### 2NF (Second Normal Form)

**Rule:** Must be in 1NF + No partial dependency (all non-key columns depend on entire primary key).

**Before 2NF (Violates):**
```
Primary Key: (student_id, course_id)

┌────────────┬───────────┬──────────────┬─────────────┐
│ student_id │ course_id │ student_name │ course_name │
├────────────┼───────────┼──────────────┼─────────────┤
│ 1          │ 101       │ Alice        │ Math        │
│ 1          │ 102       │ Alice        │ Physics     │
└────────────┴───────────┴──────────────┴─────────────┘

Problem: student_name depends only on student_id (partial dependency)
         course_name depends only on course_id (partial dependency)
```

**After 2NF:**
```
Students Table:
┌────────────┬──────────────┐
│ student_id │ student_name │
├────────────┼──────────────┤
│ 1          │ Alice        │
└────────────┴──────────────┘

Courses Table:
┌───────────┬─────────────┐
│ course_id │ course_name │
├───────────┼─────────────┤
│ 101       │ Math        │
│ 102       │ Physics     │
└───────────┴─────────────┘

Enrollments Table:
┌────────────┬───────────┐
│ student_id │ course_id │
├────────────┼───────────┤
│ 1          │ 101       │
│ 1          │ 102       │
└────────────┴───────────┘
```

---

#### 3NF (Third Normal Form)

**Rule:** Must be in 2NF + No transitive dependency (non-key columns don't depend on other non-key columns).

**Before 3NF (Violates):**
```
┌────────────┬──────────────┬─────────┬───────────┐
│ student_id │ student_name │ dept_id │ dept_name │
├────────────┼──────────────┼─────────┼───────────┤
│ 1          │ Alice        │ 10      │ CS        │
│ 2          │ Bob          │ 10      │ CS        │
└────────────┴──────────────┴─────────┴───────────┘

Problem: dept_name depends on dept_id (not on student_id directly)
         student_id → dept_id → dept_name (transitive)
```

**After 3NF:**
```
Students Table:
┌────────────┬──────────────┬─────────┐
│ student_id │ student_name │ dept_id │
├────────────┼──────────────┼─────────┤
│ 1          │ Alice        │ 10      │
│ 2          │ Bob          │ 10      │
└────────────┴──────────────┴─────────┘

Departments Table:
┌─────────┬───────────┐
│ dept_id │ dept_name │
├─────────┼───────────┤
│ 10      │ CS        │
└─────────┴───────────┘
```

---

#### BCNF (Boyce-Codd Normal Form)

**Rule:** Must be in 3NF + Every determinant is a candidate key.

**Stricter than 3NF.** Used when a table has multiple candidate keys.

---

### Denormalization

**Purpose:** Intentionally add redundancy to improve read performance.

```
┌─────────────────────────────────────────────────────────────────┐
│                    NORMALIZATION TRADE-OFFS                     │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  NORMALIZED                        DENORMALIZED                 │
│  ──────────                        ────────────                 │
│  ✓ Less redundancy                 ✓ Faster reads               │
│  ✓ Faster writes                   ✓ Fewer joins                │
│  ✓ Data consistency                ✓ Simpler queries            │
│  ✗ More joins (slower reads)       ✗ Data redundancy            │
│  ✗ Complex queries                 ✗ Slower writes              │
│                                    ✗ Inconsistency risk         │
│                                                                 │
│  Best for: OLTP, write-heavy       Best for: OLAP, read-heavy   │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Example:**
```sql
-- Normalized: Need JOIN every time
SELECT o.order_id, o.amount, c.name, c.email
FROM orders o
JOIN customers c ON o.customer_id = c.id;

-- Denormalized: No JOIN needed
SELECT order_id, amount, customer_name, customer_email
FROM orders;  -- customer data stored in orders table
```

---

### Entity-Relationship (ER) Diagrams

```
┌─────────────────────────────────────────────────────────────────┐
│                      ER DIAGRAM SYMBOLS                         │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────┐                                                │
│  │   ENTITY    │  Rectangle = Table                             │
│  └─────────────┘                                                │
│                                                                 │
│  (  attribute  )  Oval = Column                                 │
│                                                                 │
│  ((derived_att))  Double Oval = Derived/Computed                │
│                                                                 │
│  ◇───────────◇    Diamond = Relationship                        │
│                                                                 │
│  ─────────────    Line = Connection                             │
│                                                                 │
│  ──────┤├─────    Cardinality: One                              │
│  ──────<>─────    Cardinality: Many                             │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘

Example ER Diagram:

┌───────────────┐          ┌─────────────┐          ┌──────────────┐
│   CUSTOMER    │          │    ORDER    │          │   PRODUCT    │
├───────────────┤          ├─────────────┤          ├──────────────┤
│ *customer_id  │──┐   ┌───│ *order_id   │───┐  ┌───│ *product_id  │
│  name         │  │   │   │  order_date │   │  │   │  name        │
│  email        │  │   │   │  total      │   │  │   │  price       │
└───────────────┘  │   │   └─────────────┘   │  │   └──────────────┘
                   │   │                     │  │
                   │ 1:N                    M:N │
                   │   │                     │  │
                   └───┘                     └──┘

                   One customer has many orders
                   One order has many products (via order_items)
```

---

## 8. Indexing & Performance

### Why Indexes? The Phone Book Analogy

```
┌─────────────────────────────────────────────────────────────────┐
│                    WITHOUT INDEX                                │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Query: Find student with ID 987654 (from 1 million students)   │
│                                                                 │
│  Process:                                                       │
│  ─────────                                                      │
│  Check row 1:      ID = 1       ✗                               │
│  Check row 2:      ID = 2       ✗                               │
│  Check row 3:      ID = 3       ✗                               │
│  ...                                                            │
│  Check row 987654: ID = 987654  ✓ FOUND!                        │
│                                                                 │
│  Result: Checked 987,654 rows (FULL TABLE SCAN)                 │
│  Time: ~10 seconds                                              │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                     WITH INDEX (B-Tree)                         │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Index Structure:                                               │
│                     500000                                      │
│                    /      \                                     │
│               250000      750000                                │
│                          /      \                               │
│                     625000      875000                          │
│                                /      \                         │
│                           812500      937500                    │
│                                      /      \                   │
│                                 906250      968750              │
│                                            /      \             │
│                                       953125      984375        │
│                                                  /              │
│                                             987654 ✓            │
│                                                                 │
│  Result: Checked only ~20 nodes (BINARY SEARCH)                 │
│  Time: ~0.001 seconds (10,000x faster!)                         │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

### Index Types

#### Clustered Index

- Determines **physical order** of data on disk
- Only **one per table**
- Primary key creates clustered index by default
- **Best for:** Range queries, sorting

```sql
-- Primary key = Clustered index
CREATE TABLE users (
    id INT PRIMARY KEY,  -- Clustered index created automatically
    name VARCHAR(100)
);

-- Data stored in order of 'id' on disk
```

#### Non-Clustered Index

- **Separate structure** pointing to data
- **Multiple allowed** per table
- Contains: indexed columns + pointer to row
- **Best for:** Specific lookups

```sql
CREATE INDEX idx_email ON users(email);

-- Index structure:
-- email → row pointer
-- "alice@email.com" → Row 1
-- "bob@email.com" → Row 2
```

#### Composite Index

Index on **multiple columns**.

```sql
CREATE INDEX idx_name_age ON users(name, age);

-- Uses index:
SELECT * FROM users WHERE name = 'John' AND age = 25;  ✓
SELECT * FROM users WHERE name = 'John';               ✓ (leftmost prefix)
SELECT * FROM users WHERE age = 25;                    ✗ (not leftmost!)
```

**Leftmost Prefix Rule:**
```
Index: (A, B, C)

✓ WHERE A = ?
✓ WHERE A = ? AND B = ?
✓ WHERE A = ? AND B = ? AND C = ?
✗ WHERE B = ?
✗ WHERE C = ?
✗ WHERE B = ? AND C = ?
```

#### Covering Index

Index contains **all columns** needed by query (no table access).

```sql
CREATE INDEX idx_covering ON orders(customer_id, order_date, amount);

-- This query uses ONLY the index (no table lookup)
SELECT customer_id, order_date, amount
FROM orders
WHERE customer_id = 123;
```

---

### When to Use Indexes

```
┌─────────────────────────────────────────────────────────────────┐
│                     INDEX DECISION GUIDE                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ✅ USE INDEX WHEN:                                             │
│  ─────────────────                                              │
│  • Column used in WHERE frequently                              │
│  • Column used in JOIN conditions                               │
│  • Column used in ORDER BY                                      │
│  • Column has high cardinality (many unique values)             │
│  • Table is large (100K+ rows)                                  │
│  • Read-heavy workload                                          │
│                                                                 │
│  ❌ AVOID INDEX WHEN:                                           │
│  ─────────────────────                                          │
│  • Table is small (< 1000 rows)                                 │
│  • Column has low cardinality (gender: M/F)                     │
│  • Column is rarely used in queries                             │
│  • Table has heavy INSERT/UPDATE/DELETE                         │
│  • Column values change frequently                              │
│  • Large portion of table is selected (> 20%)                   │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

### Index Costs

```
┌─────────────────────────────────────────────────────────────────┐
│                      INDEX TRADE-OFFS                           │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  BENEFITS:                        COSTS:                        │
│  ─────────                        ──────                        │
│  ✅ SELECT: 100-10,000x faster   ❌ Storage: +10-20% disk       │
│  ✅ WHERE: Much faster            ❌ INSERT: Slower (update idx)│
│  ✅ JOIN: Much faster             ❌ UPDATE: Slower if idx col  │
│  ✅ ORDER BY: Much faster         ❌ DELETE: Slower (update idx)│
│                                   ❌ Memory: Index in RAM       │
│                                                                 │
│  Example (1M rows):                                             │
│  ─────────────────                                              │
│  Table size: 500 MB                                             │
│  3 indexes: +100 MB storage                                     │
│  INSERT time: 0.01s → 0.03s (3x slower)                         │
│  SELECT time: 5s → 0.001s (5000x faster!)                       │
│                                                                 │
│  CONCLUSION: Trade slower writes for much faster reads          │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

### Query Optimization

#### Using EXPLAIN

```sql
EXPLAIN SELECT * FROM users WHERE email = 'john@example.com';

-- Look for:
-- type: ALL (bad - full scan) vs ref/const (good - using index)
-- rows: Number of rows examined
-- Extra: "Using index" (good) vs "Using filesort" (bad)
```

#### Optimization Tips

```sql
-- 1. Select only needed columns
-- Bad
SELECT * FROM users;
-- Good
SELECT id, name, email FROM users;

-- 2. Use LIMIT
SELECT * FROM orders ORDER BY created_at DESC LIMIT 10;

-- 3. Avoid functions in WHERE (breaks index)
-- Bad
SELECT * FROM orders WHERE YEAR(created_at) = 2024;
-- Good
SELECT * FROM orders
WHERE created_at >= '2024-01-01' AND created_at < '2025-01-01';

-- 4. Use EXISTS instead of IN for large datasets
-- Slower
SELECT * FROM users WHERE id IN (SELECT user_id FROM orders);
-- Faster
SELECT * FROM users u WHERE EXISTS (SELECT 1 FROM orders o WHERE o.user_id = u.id);

-- 5. Use JOIN instead of subquery
-- Slower
SELECT * FROM orders WHERE customer_id IN (SELECT id FROM customers WHERE country = 'USA');
-- Faster
SELECT o.* FROM orders o JOIN customers c ON o.customer_id = c.id WHERE c.country = 'USA';

-- 6. Avoid LIKE with leading wildcard
-- Bad (no index)
SELECT * FROM users WHERE name LIKE '%john%';
-- Better (uses index)
SELECT * FROM users WHERE name LIKE 'john%';
```

---

## 9. Transactions & Concurrency

### Transaction Basics

```sql
-- Start transaction
START TRANSACTION;  -- or BEGIN;

-- Perform operations
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;

-- End transaction
COMMIT;     -- Save changes permanently
-- or
ROLLBACK;   -- Undo all changes
```

### Transaction States

```
                  ┌──────────────────┐
                  │      Active      │
                  └────────┬─────────┘
                           │
              ┌────────────┴────────────┐
              │                         │
              ▼                         ▼
    ┌─────────────────┐      ┌─────────────────┐
    │    Partially    │      │     Failed      │
    │    Committed    │      └────────┬────────┘
    └────────┬────────┘               │
             │                        ▼
             ▼               ┌─────────────────┐
    ┌─────────────────┐      │     Aborted     │
    │    Committed    │      │   (Rolled Back) │
    └─────────────────┘      └─────────────────┘
```

---

### Isolation Levels

```
┌─────────────────────────────────────────────────────────────────┐
│                      ISOLATION LEVELS                           │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Level              │ Dirty Read │ Non-Repeat │ Phantom │ Speed │
│  ───────────────────┼────────────┼────────────┼─────────┼───────│
│  READ UNCOMMITTED   │    Yes     │    Yes     │   Yes   │ Fast  │
│  READ COMMITTED     │    No      │    Yes     │   Yes   │   ↑   │
│  REPEATABLE READ    │    No      │    No      │   Yes   │   │   │
│  SERIALIZABLE       │    No      │    No      │   No    │ Slow  │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

#### Concurrency Problems Explained

**1. Dirty Read** - Reading uncommitted data
```sql
-- Transaction 1                    -- Transaction 2
BEGIN;
UPDATE products SET price = 100;
                                    BEGIN;
                                    SELECT price FROM products;  -- Reads 100
ROLLBACK;                           -- But it was rolled back!
                                    -- Transaction 2 has wrong data
```

**2. Non-Repeatable Read** - Same query, different results
```sql
-- Transaction 1                    -- Transaction 2
BEGIN;
SELECT balance FROM accounts;
-- Returns 1000
                                    BEGIN;
                                    UPDATE accounts SET balance = 1500;
                                    COMMIT;
SELECT balance FROM accounts;
-- Returns 1500 (different!)
```

**3. Phantom Read** - New rows appear
```sql
-- Transaction 1                    -- Transaction 2
BEGIN;
SELECT COUNT(*) FROM orders;
-- Returns 10
                                    BEGIN;
                                    INSERT INTO orders VALUES (...);
                                    COMMIT;
SELECT COUNT(*) FROM orders;
-- Returns 11 (phantom row!)
```

---

### Locking Mechanisms

#### Lock Types

```sql
-- Shared Lock (Read Lock) - Multiple readers allowed
SELECT * FROM products WHERE id = 1 FOR SHARE;

-- Exclusive Lock (Write Lock) - Only one writer
SELECT * FROM products WHERE id = 1 FOR UPDATE;
```

#### Deadlock

Two transactions waiting for each other forever.

```sql
-- Transaction 1                    -- Transaction 2
BEGIN;
UPDATE accounts SET balance = 900
WHERE id = 1;  -- Locks row 1
                                    BEGIN;
                                    UPDATE accounts SET balance = 1100
                                    WHERE id = 2;  -- Locks row 2

UPDATE accounts SET balance = 1100
WHERE id = 2;  -- Waits for row 2
                                    UPDATE accounts SET balance = 900
                                    WHERE id = 1;  -- Waits for row 1

-- DEADLOCK! Both waiting forever
```

**Prevention:**
- Acquire locks in consistent order
- Use lock timeouts
- Keep transactions short

---

### Advanced SQL Techniques

#### Window Functions

Perform calculations across rows **without grouping**.

```sql
-- ROW_NUMBER: Unique sequential number
SELECT
    name, salary,
    ROW_NUMBER() OVER (ORDER BY salary DESC) as row_num
FROM employees;
-- Alice  80000  1
-- Bob    70000  2
-- Carol  70000  3  (different from Bob)

-- RANK: Same rank for ties, skips numbers
SELECT
    name, salary,
    RANK() OVER (ORDER BY salary DESC) as rank
FROM employees;
-- Alice  80000  1
-- Bob    70000  2
-- Carol  70000  2  (same as Bob)
-- Dave   60000  4  (skips 3!)

-- DENSE_RANK: Same rank for ties, no skips
SELECT
    name, salary,
    DENSE_RANK() OVER (ORDER BY salary DESC) as dense_rank
FROM employees;
-- Alice  80000  1
-- Bob    70000  2
-- Carol  70000  2
-- Dave   60000  3  (no skip)
```

**PARTITION BY:**

```sql
-- Rank within each department
SELECT
    department, name, salary,
    RANK() OVER (PARTITION BY department ORDER BY salary DESC) as dept_rank
FROM employees;

-- IT    | Alice  | 80000 | 1
-- IT    | Bob    | 70000 | 2
-- HR    | Carol  | 75000 | 1
-- HR    | Dave   | 65000 | 2
```

---

#### Common Table Expressions (CTE)

```sql
-- Simple CTE
WITH high_earners AS (
    SELECT * FROM employees WHERE salary > 100000
)
SELECT * FROM high_earners WHERE department = 'IT';

-- Multiple CTEs
WITH
dept_avg AS (
    SELECT department, AVG(salary) as avg_salary
    FROM employees
    GROUP BY department
),
high_paying_depts AS (
    SELECT department FROM dept_avg WHERE avg_salary > 70000
)
SELECT e.* FROM employees e
JOIN high_paying_depts h ON e.department = h.department;
```

**Recursive CTE:**

```sql
-- Employee hierarchy (manager-employee tree)
WITH RECURSIVE emp_hierarchy AS (
    -- Base case: top-level managers
    SELECT id, name, manager_id, 1 as level
    FROM employees
    WHERE manager_id IS NULL

    UNION ALL

    -- Recursive case: employees under managers
    SELECT e.id, e.name, e.manager_id, h.level + 1
    FROM employees e
    JOIN emp_hierarchy h ON e.manager_id = h.id
)
SELECT * FROM emp_hierarchy ORDER BY level, name;
```

---

## 10. Views, Procedures, Functions & Triggers

### Views

A view is a **virtual table** based on the result of a SELECT query. It doesn't store data itself.

```
┌─────────────────────────────────────────────────────────────────┐
│                         VIEWS                                   │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Benefits:                                                      │
│  ✅ Simplify complex queries                                    │
│  ✅ Security (hide sensitive columns)                           │
│  ✅ Data abstraction (change underlying tables)                 │
│  ✅ Consistent data access                                      │
│                                                                 │
│  Limitations:                                                   │
│  ❌ Performance (complex views can be slow)                     │
│  ❌ Update restrictions (some views are read-only)              │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

#### Basic View Operations

```sql
-- Create view
CREATE VIEW active_customers AS
SELECT id, name, email, phone
FROM customers
WHERE status = 'active';

-- Use view like a table
SELECT * FROM active_customers WHERE name LIKE 'John%';

-- Update existing view
CREATE OR REPLACE VIEW active_customers AS
SELECT id, name, email, phone, created_at
FROM customers
WHERE status = 'active';

-- Alter view (SQL Server)
ALTER VIEW active_customers AS
SELECT id, name, email FROM customers WHERE status = 'active';

-- Drop view
DROP VIEW active_customers;
DROP VIEW IF EXISTS active_customers;

-- View definition
SHOW CREATE VIEW active_customers;  -- MySQL
```

#### View with JOIN

```sql
CREATE VIEW order_details AS
SELECT
    o.id AS order_id,
    o.order_date,
    c.name AS customer_name,
    c.email,
    p.name AS product_name,
    oi.quantity,
    oi.price,
    (oi.quantity * oi.price) AS total
FROM orders o
JOIN customers c ON o.customer_id = c.id
JOIN order_items oi ON o.id = oi.order_id
JOIN products p ON oi.product_id = p.id;

-- Now use simple query
SELECT * FROM order_details WHERE customer_name = 'John';
```

#### Updatable Views

```sql
-- Simple views are updatable
CREATE VIEW california_customers AS
SELECT id, name, email, state
FROM customers
WHERE state = 'CA';

-- INSERT through view
INSERT INTO california_customers (name, email, state)
VALUES ('Jane', 'jane@email.com', 'CA');

-- UPDATE through view
UPDATE california_customers SET email = 'new@email.com' WHERE id = 1;

-- DELETE through view
DELETE FROM california_customers WHERE id = 1;

-- WITH CHECK OPTION (prevent inserting rows that don't match view)
CREATE VIEW california_customers AS
SELECT id, name, email, state
FROM customers
WHERE state = 'CA'
WITH CHECK OPTION;

-- This will FAIL (state is not 'CA')
INSERT INTO california_customers (name, email, state)
VALUES ('Jane', 'jane@email.com', 'NY');  -- Error!
```

#### Materialized Views

Materialized views **store the result** physically (not in all databases).

```sql
-- PostgreSQL
CREATE MATERIALIZED VIEW sales_summary AS
SELECT
    product_id,
    SUM(quantity) as total_qty,
    SUM(amount) as total_amount
FROM sales
GROUP BY product_id;

-- Refresh materialized view
REFRESH MATERIALIZED VIEW sales_summary;

-- Oracle
CREATE MATERIALIZED VIEW sales_summary
REFRESH FAST ON COMMIT
AS SELECT product_id, SUM(amount) FROM sales GROUP BY product_id;
```

| Regular View | Materialized View |
|--------------|-------------------|
| Virtual (no storage) | Physical storage |
| Always up-to-date | Needs refresh |
| Slower for complex queries | Faster reads |
| No maintenance | Needs maintenance |

---

### Stored Procedures

A stored procedure is a **precompiled set of SQL statements** stored in the database.

```
┌─────────────────────────────────────────────────────────────────┐
│                    STORED PROCEDURES                            │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Benefits:                                                      │
│  ✅ Precompiled (faster execution)                              │
│  ✅ Reduced network traffic                                     │
│  ✅ Reusability                                                 │
│  ✅ Security (grant execute, not table access)                  │
│  ✅ Maintainability (change in one place)                       │
│                                                                 │
│  Limitations:                                                   │
│  ❌ Database-specific syntax                                    │
│  ❌ Harder to debug                                             │
│  ❌ Version control challenges                                  │
│  ❌ Can become complex                                          │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

#### Basic Stored Procedure

```sql
-- MySQL
DELIMITER //

CREATE PROCEDURE GetAllCustomers()
BEGIN
    SELECT * FROM customers;
END //

DELIMITER ;

-- Execute
CALL GetAllCustomers();
```

#### Procedure with IN Parameters

```sql
DELIMITER //

CREATE PROCEDURE GetCustomerOrders(IN customerId INT)
BEGIN
    SELECT o.id, o.order_date, o.total
    FROM orders o
    WHERE o.customer_id = customerId
    ORDER BY o.order_date DESC;
END //

DELIMITER ;

-- Execute
CALL GetCustomerOrders(123);
```

#### Procedure with OUT Parameters

```sql
DELIMITER //

CREATE PROCEDURE GetOrderCount(
    IN customerId INT,
    OUT orderCount INT
)
BEGIN
    SELECT COUNT(*) INTO orderCount
    FROM orders
    WHERE customer_id = customerId;
END //

DELIMITER ;

-- Execute
CALL GetOrderCount(123, @count);
SELECT @count;  -- Output: 5
```

#### Procedure with INOUT Parameters

```sql
DELIMITER //

CREATE PROCEDURE DoubleValue(INOUT value INT)
BEGIN
    SET value = value * 2;
END //

DELIMITER ;

-- Execute
SET @num = 5;
CALL DoubleValue(@num);
SELECT @num;  -- Output: 10
```

#### Procedure with Logic

```sql
DELIMITER //

CREATE PROCEDURE ProcessOrder(
    IN orderId INT,
    OUT status VARCHAR(50)
)
BEGIN
    DECLARE orderTotal DECIMAL(10,2);
    DECLARE customerCredit DECIMAL(10,2);

    -- Get order total
    SELECT total INTO orderTotal FROM orders WHERE id = orderId;

    -- Get customer credit
    SELECT credit_limit INTO customerCredit
    FROM customers c
    JOIN orders o ON c.id = o.customer_id
    WHERE o.id = orderId;

    -- Check credit
    IF orderTotal <= customerCredit THEN
        UPDATE orders SET status = 'approved' WHERE id = orderId;
        SET status = 'Order approved';
    ELSE
        UPDATE orders SET status = 'pending' WHERE id = orderId;
        SET status = 'Insufficient credit';
    END IF;
END //

DELIMITER ;
```

#### Procedure with Error Handling

```sql
DELIMITER //

CREATE PROCEDURE TransferMoney(
    IN fromAccount INT,
    IN toAccount INT,
    IN amount DECIMAL(10,2),
    OUT result VARCHAR(100)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET result = 'Error: Transaction failed';
    END;

    START TRANSACTION;

    UPDATE accounts SET balance = balance - amount WHERE id = fromAccount;
    UPDATE accounts SET balance = balance + amount WHERE id = toAccount;

    COMMIT;
    SET result = 'Success: Transfer completed';
END //

DELIMITER ;
```

---

### User-Defined Functions (UDF)

Functions **return a value** and can be used in SQL expressions.

| Stored Procedure | Function |
|------------------|----------|
| May or may not return value | Must return a value |
| Called with CALL | Called in SELECT/WHERE |
| Can modify data | Should not modify data |
| Cannot use in SELECT | Can use in SELECT |

#### Scalar Function (returns single value)

```sql
-- MySQL
DELIMITER //

CREATE FUNCTION CalculateAge(birthDate DATE)
RETURNS INT
DETERMINISTIC
BEGIN
    RETURN TIMESTAMPDIFF(YEAR, birthDate, CURDATE());
END //

DELIMITER ;

-- Use in query
SELECT name, CalculateAge(birth_date) AS age FROM employees;
SELECT * FROM employees WHERE CalculateAge(birth_date) >= 21;
```

#### Function with Logic

```sql
DELIMITER //

CREATE FUNCTION GetDiscountedPrice(
    price DECIMAL(10,2),
    discountPercent INT
)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE discountedPrice DECIMAL(10,2);
    SET discountedPrice = price - (price * discountPercent / 100);
    RETURN discountedPrice;
END //

DELIMITER ;

-- Use
SELECT name, price, GetDiscountedPrice(price, 10) AS sale_price FROM products;
```

#### Table-Valued Function (PostgreSQL, SQL Server)

```sql
-- PostgreSQL
CREATE FUNCTION GetEmployeesByDept(deptId INT)
RETURNS TABLE (id INT, name VARCHAR, salary DECIMAL)
AS $$
    SELECT id, name, salary
    FROM employees
    WHERE department_id = deptId;
$$ LANGUAGE SQL;

-- Use
SELECT * FROM GetEmployeesByDept(10);
```

---

### Triggers

A trigger is a **stored procedure that automatically executes** when a specific event occurs.

```
┌─────────────────────────────────────────────────────────────────┐
│                      TRIGGER TYPES                              │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Timing:                                                        │
│  ─────────                                                      │
│  BEFORE - Execute before the operation                          │
│  AFTER  - Execute after the operation                           │
│  INSTEAD OF - Replace the operation (views only)                │
│                                                                 │
│  Events:                                                        │
│  ────────                                                       │
│  INSERT - When new row is inserted                              │
│  UPDATE - When row is modified                                  │
│  DELETE - When row is removed                                   │
│                                                                 │
│  Special Variables:                                             │
│  ──────────────────                                             │
│  NEW - New row data (INSERT, UPDATE)                            │
│  OLD - Old row data (UPDATE, DELETE)                            │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

#### BEFORE INSERT Trigger

```sql
-- Auto-set created_at
DELIMITER //

CREATE TRIGGER before_employee_insert
BEFORE INSERT ON employees
FOR EACH ROW
BEGIN
    SET NEW.created_at = NOW();
    SET NEW.updated_at = NOW();
END //

DELIMITER ;
```

#### BEFORE UPDATE Trigger

```sql
-- Auto-update timestamp
DELIMITER //

CREATE TRIGGER before_employee_update
BEFORE UPDATE ON employees
FOR EACH ROW
BEGIN
    SET NEW.updated_at = NOW();
END //

DELIMITER ;
```

#### AFTER INSERT Trigger (Audit Log)

```sql
DELIMITER //

CREATE TRIGGER after_employee_insert
AFTER INSERT ON employees
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (
        table_name, action, record_id, new_data, created_at
    ) VALUES (
        'employees', 'INSERT', NEW.id,
        JSON_OBJECT('name', NEW.name, 'email', NEW.email),
        NOW()
    );
END //

DELIMITER ;
```

#### AFTER DELETE Trigger

```sql
DELIMITER //

CREATE TRIGGER after_employee_delete
AFTER DELETE ON employees
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (
        table_name, action, record_id, old_data, created_at
    ) VALUES (
        'employees', 'DELETE', OLD.id,
        JSON_OBJECT('name', OLD.name, 'email', OLD.email),
        NOW()
    );
END //

DELIMITER ;
```

#### Trigger for Data Validation

```sql
DELIMITER //

CREATE TRIGGER validate_employee_salary
BEFORE INSERT ON employees
FOR EACH ROW
BEGIN
    IF NEW.salary < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Salary cannot be negative';
    END IF;

    IF NEW.salary > 1000000 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Salary exceeds maximum limit';
    END IF;
END //

DELIMITER ;
```

#### Trigger for Maintaining Summary Tables

```sql
DELIMITER //

CREATE TRIGGER after_order_insert
AFTER INSERT ON orders
FOR EACH ROW
BEGIN
    -- Update customer's total orders and spent amount
    UPDATE customers
    SET total_orders = total_orders + 1,
        total_spent = total_spent + NEW.amount
    WHERE id = NEW.customer_id;
END //

CREATE TRIGGER after_order_delete
AFTER DELETE ON orders
FOR EACH ROW
BEGIN
    UPDATE customers
    SET total_orders = total_orders - 1,
        total_spent = total_spent - OLD.amount
    WHERE id = OLD.customer_id;
END //

DELIMITER ;
```

#### Managing Triggers

```sql
-- Show triggers
SHOW TRIGGERS;
SHOW TRIGGERS LIKE 'employees';

-- Drop trigger
DROP TRIGGER before_employee_insert;
DROP TRIGGER IF EXISTS before_employee_insert;

-- Disable/Enable (SQL Server)
DISABLE TRIGGER trigger_name ON table_name;
ENABLE TRIGGER trigger_name ON table_name;
```

---

## 11. Cursors & Temporary Tables

### Cursors

A cursor allows **row-by-row processing** of query results.

```
┌─────────────────────────────────────────────────────────────────┐
│                         CURSORS                                 │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  When to Use:                                                   │
│  ✅ Row-by-row processing required                              │
│  ✅ Complex calculations per row                                │
│  ✅ Calling procedures for each row                             │
│                                                                 │
│  When to Avoid:                                                 │
│  ❌ Set-based operations possible (use JOIN, UPDATE)            │
│  ❌ Large datasets (very slow)                                  │
│  ❌ Simple aggregations                                         │
│                                                                 │
│  Cursor Lifecycle:                                              │
│  DECLARE → OPEN → FETCH → CLOSE → DEALLOCATE                    │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

#### Basic Cursor Example

```sql
DELIMITER //

CREATE PROCEDURE ProcessAllOrders()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE orderId INT;
    DECLARE orderAmount DECIMAL(10,2);

    -- Declare cursor
    DECLARE order_cursor CURSOR FOR
        SELECT id, amount FROM orders WHERE status = 'pending';

    -- Declare handler for end of data
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- Open cursor
    OPEN order_cursor;

    -- Loop through rows
    read_loop: LOOP
        FETCH order_cursor INTO orderId, orderAmount;

        IF done THEN
            LEAVE read_loop;
        END IF;

        -- Process each row
        IF orderAmount > 1000 THEN
            UPDATE orders SET priority = 'high' WHERE id = orderId;
        ELSE
            UPDATE orders SET priority = 'normal' WHERE id = orderId;
        END IF;
    END LOOP;

    -- Close cursor
    CLOSE order_cursor;
END //

DELIMITER ;
```

#### Cursor with Multiple Columns

```sql
DELIMITER //

CREATE PROCEDURE SendOrderNotifications()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_order_id INT;
    DECLARE v_customer_email VARCHAR(100);
    DECLARE v_order_total DECIMAL(10,2);

    DECLARE order_cursor CURSOR FOR
        SELECT o.id, c.email, o.total
        FROM orders o
        JOIN customers c ON o.customer_id = c.id
        WHERE o.status = 'shipped';

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN order_cursor;

    fetch_loop: LOOP
        FETCH order_cursor INTO v_order_id, v_customer_email, v_order_total;

        IF done THEN
            LEAVE fetch_loop;
        END IF;

        -- Insert notification record
        INSERT INTO notifications (email, message, created_at)
        VALUES (
            v_customer_email,
            CONCAT('Order #', v_order_id, ' (Total: $', v_order_total, ') has been shipped!'),
            NOW()
        );
    END LOOP;

    CLOSE order_cursor;
END //

DELIMITER ;
```

---

### Temporary Tables

Temporary tables exist only for the **duration of a session** or transaction.

```
┌─────────────────────────────────────────────────────────────────┐
│                    TEMPORARY TABLES                             │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Types:                                                         │
│  ─────────                                                      │
│  Local Temp Table   - Visible only to current session           │
│  Global Temp Table  - Visible to all sessions (SQL Server)      │
│                                                                 │
│  Benefits:                                                      │
│  ✅ Store intermediate results                                  │
│  ✅ Simplify complex queries                                    │
│  ✅ Auto-deleted when session ends                              │
│  ✅ Can have indexes                                            │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

#### Creating Temporary Tables

```sql
-- MySQL: CREATE TEMPORARY TABLE
CREATE TEMPORARY TABLE temp_high_value_orders (
    order_id INT,
    customer_name VARCHAR(100),
    total DECIMAL(10,2)
);

INSERT INTO temp_high_value_orders
SELECT o.id, c.name, o.total
FROM orders o
JOIN customers c ON o.customer_id = c.id
WHERE o.total > 1000;

-- Use the temp table
SELECT * FROM temp_high_value_orders;

-- Auto-dropped when session ends, or manually:
DROP TEMPORARY TABLE temp_high_value_orders;
```

#### Create Temp Table from Query

```sql
-- MySQL
CREATE TEMPORARY TABLE temp_summary AS
SELECT department, AVG(salary) as avg_salary, COUNT(*) as emp_count
FROM employees
GROUP BY department;

-- SQL Server (SELECT INTO)
SELECT department, AVG(salary) as avg_salary, COUNT(*) as emp_count
INTO #temp_summary
FROM employees
GROUP BY department;

-- PostgreSQL
CREATE TEMP TABLE temp_summary AS
SELECT department, AVG(salary) as avg_salary
FROM employees
GROUP BY department;
```

#### SQL Server Temp Table Types

```sql
-- Local temp table (visible only to current session)
CREATE TABLE #local_temp (
    id INT,
    name VARCHAR(100)
);

-- Global temp table (visible to all sessions)
CREATE TABLE ##global_temp (
    id INT,
    name VARCHAR(100)
);

-- Table variable (lives in memory)
DECLARE @temp_table TABLE (
    id INT,
    name VARCHAR(100)
);

INSERT INTO @temp_table VALUES (1, 'John');
SELECT * FROM @temp_table;
```

#### Using Temp Tables for Complex Queries

```sql
-- Step 1: Get top customers
CREATE TEMPORARY TABLE temp_top_customers AS
SELECT customer_id, SUM(total) as total_spent
FROM orders
WHERE order_date >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR)
GROUP BY customer_id
HAVING SUM(total) > 10000;

-- Step 2: Get their recent orders
CREATE TEMPORARY TABLE temp_recent_orders AS
SELECT o.*
FROM orders o
JOIN temp_top_customers tc ON o.customer_id = tc.customer_id
WHERE o.order_date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY);

-- Step 3: Final analysis
SELECT
    c.name,
    tc.total_spent,
    COUNT(ro.id) as recent_orders
FROM temp_top_customers tc
JOIN customers c ON tc.customer_id = c.id
LEFT JOIN temp_recent_orders ro ON tc.customer_id = ro.customer_id
GROUP BY c.name, tc.total_spent;

-- Cleanup
DROP TEMPORARY TABLE temp_top_customers;
DROP TEMPORARY TABLE temp_recent_orders;
```

---

## 12. Partitioning

Partitioning divides a large table into smaller, manageable pieces.

```
┌─────────────────────────────────────────────────────────────────┐
│                      PARTITIONING                               │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Horizontal Partitioning (Row-based):                           │
│  ────────────────────────────────────                           │
│  Split rows across partitions                                   │
│  Example: Orders by year                                        │
│                                                                 │
│  Vertical Partitioning (Column-based):                          │
│  ─────────────────────────────────────                          │
│  Split columns across tables                                    │
│  Example: Separate BLOB data                                    │
│                                                                 │
│  Benefits:                                                      │
│  ✅ Faster queries (partition pruning)                          │
│  ✅ Easier maintenance (drop old partitions)                    │
│  ✅ Parallel processing                                         │
│  ✅ Improved availability                                       │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### Range Partitioning

```sql
-- Partition by date range
CREATE TABLE orders (
    id INT,
    customer_id INT,
    order_date DATE,
    amount DECIMAL(10,2)
)
PARTITION BY RANGE (YEAR(order_date)) (
    PARTITION p2021 VALUES LESS THAN (2022),
    PARTITION p2022 VALUES LESS THAN (2023),
    PARTITION p2023 VALUES LESS THAN (2024),
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);

-- Query only scans relevant partition
SELECT * FROM orders WHERE order_date = '2024-01-15';
```

### List Partitioning

```sql
-- Partition by discrete values
CREATE TABLE customers (
    id INT,
    name VARCHAR(100),
    region VARCHAR(50)
)
PARTITION BY LIST (region) (
    PARTITION p_east VALUES IN ('NY', 'NJ', 'PA', 'CT'),
    PARTITION p_west VALUES IN ('CA', 'WA', 'OR', 'NV'),
    PARTITION p_central VALUES IN ('TX', 'IL', 'OH', 'MI'),
    PARTITION p_other VALUES IN (DEFAULT)
);
```

### Hash Partitioning

```sql
-- Evenly distribute data
CREATE TABLE users (
    id INT,
    name VARCHAR(100),
    email VARCHAR(100)
)
PARTITION BY HASH(id)
PARTITIONS 4;

-- Data distributed across 4 partitions based on id % 4
```

### Key Partitioning (MySQL)

```sql
-- Similar to hash but uses MySQL's internal hash
CREATE TABLE sessions (
    id INT,
    user_id INT,
    data TEXT,
    PRIMARY KEY (id, user_id)
)
PARTITION BY KEY(user_id)
PARTITIONS 8;
```

### Managing Partitions

```sql
-- Add partition
ALTER TABLE orders ADD PARTITION (
    PARTITION p2025 VALUES LESS THAN (2026)
);

-- Drop partition (deletes data!)
ALTER TABLE orders DROP PARTITION p2021;

-- Truncate partition (keep structure)
ALTER TABLE orders TRUNCATE PARTITION p2021;

-- Reorganize partitions
ALTER TABLE orders REORGANIZE PARTITION p_future INTO (
    PARTITION p2025 VALUES LESS THAN (2026),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);

-- Show partitions
SELECT PARTITION_NAME, TABLE_ROWS
FROM INFORMATION_SCHEMA.PARTITIONS
WHERE TABLE_NAME = 'orders';
```

### Vertical Partitioning Example

```sql
-- Original table with large BLOB
CREATE TABLE products (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    price DECIMAL(10,2),
    image LONGBLOB,        -- Large data
    manual PDF LONGBLOB    -- Large data
);

-- Split into two tables (vertical partitioning)
CREATE TABLE products (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    price DECIMAL(10,2)
);

CREATE TABLE product_files (
    product_id INT PRIMARY KEY,
    image LONGBLOB,
    manual_pdf LONGBLOB,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Join when needed
SELECT p.*, pf.image
FROM products p
LEFT JOIN product_files pf ON p.id = pf.product_id
WHERE p.id = 123;
```

---

## 13. Scaling Databases

### Vertical vs Horizontal Scaling

```
┌─────────────────────────────────────────────────────────────────┐
│                     SCALING STRATEGIES                          │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  VERTICAL SCALING (Scale Up)      HORIZONTAL SCALING (Scale Out)│
│  ──────────────────────────       ──────────────────────────────│
│                                                                 │
│  ┌─────────────────┐              ┌─────┐ ┌─────┐ ┌─────┐       │
│  │                 │              │ DB  │ │ DB  │ │ DB  │       │
│  │    BIGGER       │              │  1  │ │  2  │ │  3  │       │
│  │    SERVER       │              └─────┘ └─────┘ └─────┘       │
│  │                 │                                            │
│  └─────────────────┘              More servers                  │
│  More CPU/RAM/Disk                                              │
│                                                                 │
│  ✅ Simple                        ✅ Unlimited scaling          │
│  ✅ No code changes               ✅ Fault tolerant             │
│  ❌ Hardware limits               ❌ Complex                    │
│  ❌ Single point of failure       ❌ Data distribution issues   │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

### Replication

Copy data from master to replicas.

```
┌────────────────────────────────────────────────────────────────┐
│                    MASTER-SLAVE REPLICATION                    │
├────────────────────────────────────────────────────────────────┤
│                                                                │
│                    ┌──────────┐                                │
│      Writes ──────►│  MASTER  │                                │
│                    └────┬─────┘                                │
│                         │ Replication                          │
│              ┌──────────┼──────────┐                           │
│              │          │          │                           │
│              ▼          ▼          ▼                           │
│         ┌────────┐ ┌────────┐ ┌────────┐                       │
│  Reads ─│ SLAVE  │ │ SLAVE  │ │ SLAVE  │                       │
│         │   1    │ │   2    │ │   3    │                       │
│         └────────┘ └────────┘ └────────┘                       │
│                                                                │
│  Benefits:                                                     │
│  • Read scalability (distribute reads)                         │
│  • High availability (failover to slave)                       │
│  • Backup without affecting master                             │
│                                                                │
└────────────────────────────────────────────────────────────────┘
```

**Replication Types:**
- **Synchronous:** Wait for replica confirmation (consistent, slower)
- **Asynchronous:** Don't wait (faster, eventual consistency)
- **Semi-synchronous:** Wait for at least one replica

---

### Sharding (Horizontal Partitioning)

Distribute data across multiple databases.

```
┌────────────────────────────────────────────────────────────────┐
│                         SHARDING                               │
├────────────────────────────────────────────────────────────────┤
│                                                                │
│  Without Sharding:                                             │
│  ┌─────────────────────────────────────┐                       │
│  │         Single Database              │                      │
│  │         10 Million Users             │                      │
│  └─────────────────────────────────────┘                       │
│                                                                │
│  With Sharding (by user_id % 4):                               │
│  ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌────────────┐   │
│  │  Shard 0   │ │  Shard 1   │ │  Shard 2   │ │  Shard 3   │   │
│  │ ID % 4 = 0 │ │ ID % 4 = 1 │ │ ID % 4 = 2 │ │ ID % 4 = 3 │   │
│  │ 2.5M users │ │ 2.5M users │ │ 2.5M users │ │ 2.5M users │   │
│  └────────────┘ └────────────┘ └────────────┘ └────────────┘   │
│                                                                │
│  Sharding Strategies:                                          │
│  • Range-based: user_id 1-1M, 1M-2M, etc.                      │
│  • Hash-based: user_id % num_shards                            │
│  • Geographic: by region/country                               │
│  • Directory-based: lookup table                               │
│                                                                │
└────────────────────────────────────────────────────────────────┘
```

---

### Connection Pooling

Reuse database connections.

```
┌────────────────────────────────────────────────────────────────┐
│                    CONNECTION POOLING                          │
├────────────────────────────────────────────────────────────────┤
│                                                                │
│  Without Pool:                                                 │
│  ┌─────────┐     Create (50ms)      ┌──────────┐               │
│  │ Request │ ──────────────────────► │ Database │              │
│  └─────────┘     Close              └──────────┘               │
│                  (Repeat for every request!)                   │
│                                                                │
│  With Pool:                                                    │
│  ┌─────────┐                        ┌──────────┐               │
│  │ Request │ ──┐   ┌────────────┐   │          │               │
│  └─────────┘   │   │            │   │          │               │
│  ┌─────────┐   ├──►│ Connection │◄──│ Database │               │
│  │ Request │ ──┤   │    Pool    │   │          │               │
│  └─────────┘   │   │  (10 conn) │   │          │               │
│  ┌─────────┐   │   │            │   │          │               │
│  │ Request │ ──┘   └────────────┘   └──────────┘               │
│  └─────────┘                                                   │
│                                                                │
│  Get from pool: 1ms (vs 50ms create!)                          │
│                                                                │
└────────────────────────────────────────────────────────────────┘
```

**Popular Pools:** HikariCP, C3P0, Apache DBCP

---

### CAP Theorem

Distributed systems can only guarantee 2 of 3:

```
┌────────────────────────────────────────────────────────────────┐
│                       CAP THEOREM                              │
├────────────────────────────────────────────────────────────────┤
│                                                                │
│                      Consistency                               │
│                          /\                                    │
│                         /  \                                   │
│                        /    \                                  │
│                       / CA   \                                 │
│                      /        \                                │
│                     /   CP     \                               │
│                    /____________\                              │
│         Availability ─────────── Partition                     │
│                          AP       Tolerance                    │
│                                                                │
│  C = All nodes see same data at same time                      │
│  A = Every request gets a response                             │
│  P = System works despite network failures                     │
│                                                                │
│  Choose 2:                                                     │
│  • CA: Traditional RDBMS (single server)                       │
│  • CP: MongoDB, HBase, Redis (sacrifice availability)          │
│  • AP: Cassandra, DynamoDB (sacrifice consistency)             │
│                                                                │
└────────────────────────────────────────────────────────────────┘
```

---

## 14. Database Security

### Authentication & Authorization

```
┌─────────────────────────────────────────────────────────────────┐
│                    DATABASE SECURITY                            │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Authentication: Who are you?                                   │
│  ─────────────────────────────                                  │
│  • Username/Password                                            │
│  • LDAP/Active Directory                                        │
│  • SSL Certificates                                             │
│  • Two-factor authentication                                    │
│                                                                 │
│  Authorization: What can you do?                                │
│  ───────────────────────────────                                │
│  • GRANT/REVOKE permissions                                     │
│  • Role-based access control                                    │
│  • Row-level security                                           │
│  • Column-level security                                        │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### User Management

```sql
-- Create user
CREATE USER 'app_user'@'localhost' IDENTIFIED BY 'secure_password';
CREATE USER 'app_user'@'%' IDENTIFIED BY 'secure_password';  -- Any host

-- Change password
ALTER USER 'app_user'@'localhost' IDENTIFIED BY 'new_password';

-- Drop user
DROP USER 'app_user'@'localhost';

-- Show users
SELECT User, Host FROM mysql.user;
```

### Privileges & Permissions

```sql
-- Grant privileges
GRANT SELECT ON database_name.* TO 'app_user'@'localhost';
GRANT SELECT, INSERT, UPDATE ON database_name.table_name TO 'app_user'@'localhost';
GRANT ALL PRIVILEGES ON database_name.* TO 'admin_user'@'localhost';

-- Grant with grant option (user can grant to others)
GRANT SELECT ON database_name.* TO 'app_user'@'localhost' WITH GRANT OPTION;

-- Revoke privileges
REVOKE INSERT ON database_name.* FROM 'app_user'@'localhost';
REVOKE ALL PRIVILEGES ON database_name.* FROM 'app_user'@'localhost';

-- Show privileges
SHOW GRANTS FOR 'app_user'@'localhost';

-- Apply changes
FLUSH PRIVILEGES;
```

### Role-Based Access Control

```sql
-- Create roles
CREATE ROLE 'read_only';
CREATE ROLE 'read_write';
CREATE ROLE 'admin';

-- Grant privileges to roles
GRANT SELECT ON database_name.* TO 'read_only';
GRANT SELECT, INSERT, UPDATE, DELETE ON database_name.* TO 'read_write';
GRANT ALL PRIVILEGES ON database_name.* TO 'admin';

-- Assign roles to users
GRANT 'read_only' TO 'reporting_user'@'localhost';
GRANT 'read_write' TO 'app_user'@'localhost';
GRANT 'admin' TO 'dba_user'@'localhost';

-- Activate role
SET DEFAULT ROLE 'read_only' TO 'reporting_user'@'localhost';
```

### SQL Injection Prevention

```
┌─────────────────────────────────────────────────────────────────┐
│                    SQL INJECTION                                │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  What is it?                                                    │
│  ───────────                                                    │
│  Attacker injects malicious SQL through user input              │
│                                                                 │
│  Example Attack:                                                │
│  ─────────────────                                              │
│  Input: ' OR '1'='1                                             │
│  Query: SELECT * FROM users WHERE name = '' OR '1'='1'          │
│  Result: Returns ALL users!                                     │
│                                                                 │
│  Prevention:                                                    │
│  ───────────                                                    │
│  ✅ Use Prepared Statements / Parameterized Queries             │
│  ✅ Use ORM frameworks                                          │
│  ✅ Input validation and sanitization                           │
│  ✅ Least privilege principle                                   │
│  ✅ Web Application Firewall (WAF)                              │
│  ❌ Never concatenate user input into SQL                       │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

#### Vulnerable Code (DON'T DO THIS)

```java
// VULNERABLE - SQL Injection possible!
String query = "SELECT * FROM users WHERE username = '" + username + "'";
Statement stmt = connection.createStatement();
ResultSet rs = stmt.executeQuery(query);
```

#### Safe Code (Prepared Statements)

```java
// SAFE - Parameterized query
String query = "SELECT * FROM users WHERE username = ?";
PreparedStatement pstmt = connection.prepareStatement(query);
pstmt.setString(1, username);
ResultSet rs = pstmt.executeQuery();
```

```python
# Python - Safe
cursor.execute("SELECT * FROM users WHERE username = %s", (username,))

# Python with SQLAlchemy ORM - Safe
user = session.query(User).filter(User.username == username).first()
```

### Data Encryption

```sql
-- Encryption at rest (Transparent Data Encryption)
-- MySQL
ALTER TABLE sensitive_data ENCRYPTION = 'Y';

-- Column-level encryption
-- Store encrypted data
INSERT INTO users (name, ssn_encrypted)
VALUES ('John', AES_ENCRYPT('123-45-6789', 'encryption_key'));

-- Decrypt when reading
SELECT name, AES_DECRYPT(ssn_encrypted, 'encryption_key') AS ssn
FROM users;

-- Hashing passwords (one-way)
INSERT INTO users (username, password_hash)
VALUES ('john', SHA2('password123', 256));

-- Verify password
SELECT * FROM users
WHERE username = 'john' AND password_hash = SHA2('password123', 256);
```

---

## 15. Backup & Recovery

### Backup Types

```
┌─────────────────────────────────────────────────────────────────┐
│                      BACKUP TYPES                               │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Full Backup:                                                   │
│  ────────────                                                   │
│  • Complete copy of entire database                             │
│  • Largest size, longest time                                   │
│  • Simplest to restore                                          │
│                                                                 │
│  Incremental Backup:                                            │
│  ───────────────────                                            │
│  • Only changes since LAST backup (full or incremental)         │
│  • Smallest size, fastest backup                                │
│  • Requires all incrementals + full to restore                  │
│                                                                 │
│  Differential Backup:                                           │
│  ────────────────────                                           │
│  • Only changes since LAST FULL backup                          │
│  • Medium size                                                  │
│  • Requires last full + last differential to restore            │
│                                                                 │
│  Example Timeline:                                              │
│  ─────────────────                                              │
│  Sun: Full (100GB)                                              │
│  Mon: Incremental (5GB) or Differential (5GB)                   │
│  Tue: Incremental (3GB) or Differential (8GB)                   │
│  Wed: Incremental (4GB) or Differential (12GB)                  │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### MySQL Backup

```bash
# Full backup with mysqldump
mysqldump -u root -p --all-databases > full_backup.sql
mysqldump -u root -p database_name > database_backup.sql
mysqldump -u root -p database_name table_name > table_backup.sql

# With options
mysqldump -u root -p \
    --single-transaction \      # Consistent snapshot
    --routines \                # Include stored procedures
    --triggers \                # Include triggers
    --events \                  # Include events
    database_name > backup.sql

# Compressed backup
mysqldump -u root -p database_name | gzip > backup.sql.gz

# Restore from backup
mysql -u root -p database_name < backup.sql
gunzip < backup.sql.gz | mysql -u root -p database_name
```

### PostgreSQL Backup

```bash
# Full backup
pg_dump database_name > backup.sql
pg_dump -U username -h hostname database_name > backup.sql

# All databases
pg_dumpall > full_backup.sql

# Custom format (compressed, flexible restore)
pg_dump -Fc database_name > backup.dump

# Restore
psql database_name < backup.sql
pg_restore -d database_name backup.dump
```

### Point-in-Time Recovery (PITR)

```sql
-- MySQL: Enable binary logging
-- my.cnf
[mysqld]
log_bin = /var/log/mysql/mysql-bin.log
server_id = 1

-- Restore to specific point in time
-- 1. Restore last full backup
-- 2. Apply binary logs up to desired time
mysqlbinlog --stop-datetime="2024-01-15 14:30:00" mysql-bin.000001 | mysql -u root -p

-- PostgreSQL: Enable WAL archiving
-- postgresql.conf
archive_mode = on
archive_command = 'cp %p /archive/%f'

-- Restore to point in time
-- recovery.conf
restore_command = 'cp /archive/%f %p'
recovery_target_time = '2024-01-15 14:30:00'
```

### Backup Best Practices

```
✅ DO:
────────
• Test restores regularly
• Keep backups off-site (3-2-1 rule)
• Encrypt backup files
• Document recovery procedures
• Monitor backup jobs
• Retain multiple backup versions

❌ DON'T:
──────────
• Store backups on same server
• Skip backup verification
• Forget to backup transaction logs
• Ignore backup failures
• Delete old backups without policy
```

---

## 16. OLTP vs OLAP

```
┌─────────────────────────────────────────────────────────────────┐
│                    OLTP vs OLAP                                 │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  OLTP (Online Transaction Processing)                           │
│  ─────────────────────────────────────                          │
│  • Day-to-day operations                                        │
│  • Many short transactions                                      │
│  • INSERT, UPDATE, DELETE heavy                                 │
│  • Current data                                                 │
│  • Normalized schema                                            │
│  • Fast response time                                           │
│  • Examples: Banking, E-commerce, CRM                           │
│                                                                 │
│  OLAP (Online Analytical Processing)                            │
│  ───────────────────────────────────                            │
│  • Business intelligence, reporting                             │
│  • Complex queries, aggregations                                │
│  • SELECT heavy (read-mostly)                                   │
│  • Historical data                                              │
│  • Denormalized schema (star/snowflake)                         │
│  • Query time less critical                                     │
│  • Examples: Data warehouse, BI dashboards                      │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### Comparison Table

| Aspect | OLTP | OLAP |
|--------|------|------|
| **Purpose** | Daily transactions | Analysis & reporting |
| **Operations** | INSERT, UPDATE, DELETE | SELECT, aggregations |
| **Data** | Current, detailed | Historical, summarized |
| **Schema** | Normalized (3NF) | Denormalized (Star/Snowflake) |
| **Queries** | Simple, predefined | Complex, ad-hoc |
| **Response** | Milliseconds | Seconds to minutes |
| **Users** | Many (customers, clerks) | Few (analysts, managers) |
| **Size** | GB to TB | TB to PB |
| **Backup** | Frequent | Less frequent |
| **Examples** | MySQL, PostgreSQL | Snowflake, Redshift, BigQuery |

### Data Warehouse Schema Designs

#### Star Schema

```
                    ┌─────────────────┐
                    │  FACT_SALES     │
                    ├─────────────────┤
     ┌──────────────│ product_id (FK) │──────────────┐
     │              │ customer_id (FK)│              │
     │              │ date_id (FK)    │              │
     │              │ store_id (FK)   │              │
     │              │ quantity        │              │
     │              │ amount          │              │
     │              └─────────────────┘              │
     │                      │                        │
     ▼                      ▼                        ▼
┌──────────┐        ┌──────────────┐        ┌──────────────┐
│DIM_PRODUCT│       │ DIM_CUSTOMER │        │  DIM_DATE    │
├──────────┤        ├──────────────┤        ├──────────────┤
│ id (PK)  │        │ id (PK)      │        │ id (PK)      │
│ name     │        │ name         │        │ date         │
│ category │        │ city         │        │ month        │
│ brand    │        │ country      │        │ quarter      │
└──────────┘        └──────────────┘        │ year         │
                                            └──────────────┘
```

```sql
-- Star schema query example
SELECT
    d.year,
    d.month,
    p.category,
    c.country,
    SUM(f.amount) as total_sales,
    COUNT(*) as num_transactions
FROM fact_sales f
JOIN dim_date d ON f.date_id = d.id
JOIN dim_product p ON f.product_id = p.id
JOIN dim_customer c ON f.customer_id = c.id
WHERE d.year = 2024
GROUP BY d.year, d.month, p.category, c.country
ORDER BY total_sales DESC;
```

#### ETL Process (Extract, Transform, Load)

```
┌─────────────────────────────────────────────────────────────────┐
│                      ETL PROCESS                                │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  EXTRACT                 TRANSFORM               LOAD           │
│  ───────                 ─────────               ────           │
│                                                                 │
│  ┌─────────┐            ┌─────────┐            ┌─────────┐      │
│  │  OLTP   │───────────►│ Staging │───────────►│  Data   │      │
│  │ Systems │            │  Area   │            │Warehouse│      │
│  └─────────┘            └─────────┘            └─────────┘      │
│  ┌─────────┐                 │                                  │
│  │   API   │─────────────────┤                                  │
│  └─────────┘                 │                                  │
│  ┌─────────┐                 ▼                                  │
│  │  Files  │          • Clean data                              │
│  └─────────┘          • Validate                                │
│                       • Deduplicate                             │
│                       • Aggregate                               │
│                       • Join sources                            │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### Modern Data Stack

```
Traditional:                    Modern:
────────────                    ───────

OLTP → ETL → Data Warehouse    OLTP → ELT → Data Lake/Lakehouse
                ↓                              ↓
           OLAP Cube                    Query Engine
                ↓                              ↓
           BI Tools                    BI Tools / ML

Popular Modern Tools:
• Data Lake: S3, Azure Data Lake, GCS
• Data Warehouse: Snowflake, BigQuery, Redshift
• ETL/ELT: Airflow, dbt, Fivetran
• BI: Tableau, Power BI, Looker, Metabase
```

---

## 17. Interview Questions

### Basic Level

**Q1: What is the difference between DELETE, TRUNCATE, and DROP?**

| Command | Description | Rollback | Speed | Triggers |
|---------|-------------|----------|-------|----------|
| DELETE | Remove specific rows | Yes | Slow | Yes |
| TRUNCATE | Remove all rows, keep structure | No | Fast | No |
| DROP | Remove entire table | No | Fast | No |

---

**Q2: What is the difference between WHERE and HAVING?**

| WHERE | HAVING |
|-------|--------|
| Filters rows BEFORE grouping | Filters groups AFTER grouping |
| Cannot use aggregate functions | Can use aggregate functions |
| Used with SELECT, UPDATE, DELETE | Used only with SELECT + GROUP BY |

---

**Q3: What are the different types of JOINs?**

- **INNER JOIN:** Only matching rows
- **LEFT JOIN:** All left + matching right
- **RIGHT JOIN:** All right + matching left
- **FULL JOIN:** All rows from both
- **CROSS JOIN:** Cartesian product
- **SELF JOIN:** Table joined with itself

---

**Q4: What is the difference between UNION and UNION ALL?**

| UNION | UNION ALL |
|-------|-----------|
| Removes duplicates | Keeps duplicates |
| Slower | Faster |

---

**Q5: What is a Primary Key vs Unique Key?**

| Primary Key | Unique Key |
|-------------|------------|
| One per table | Multiple allowed |
| Cannot be NULL | Can have NULL |
| Creates clustered index | Creates non-clustered index |

---

### Intermediate Level

**Q6: What is the N+1 Query Problem?**

Executing 1 query to fetch N records, then N additional queries for related data.

```sql
-- Bad (N+1): 101 queries
SELECT * FROM users;  -- 1 query, returns 100 users
-- Then for each user:
SELECT * FROM orders WHERE user_id = ?;  -- 100 queries!

-- Good (2 queries)
SELECT * FROM users;
SELECT * FROM orders WHERE user_id IN (1, 2, ..., 100);

-- Best (1 query with JOIN)
SELECT u.*, o.* FROM users u LEFT JOIN orders o ON u.id = o.user_id;
```

---

**Q7: How to find the second highest salary?**

```sql
-- Method 1: LIMIT OFFSET
SELECT DISTINCT salary FROM employees ORDER BY salary DESC LIMIT 1 OFFSET 1;

-- Method 2: Subquery
SELECT MAX(salary) FROM employees WHERE salary < (SELECT MAX(salary) FROM employees);

-- Method 3: DENSE_RANK
SELECT salary FROM (
    SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) as rank
    FROM employees
) ranked WHERE rank = 2;
```

---

**Q8: How to find duplicate records?**

```sql
SELECT email, COUNT(*) as count
FROM users
GROUP BY email
HAVING COUNT(*) > 1;
```

---

**Q9: Explain RANK() vs DENSE_RANK() vs ROW_NUMBER()**

```sql
-- Data: 90, 90, 80, 70
ROW_NUMBER():  1, 2, 3, 4  -- Always unique
RANK():        1, 1, 3, 4  -- Same rank for ties, skips
DENSE_RANK():  1, 1, 2, 3  -- Same rank for ties, no skip
```

---

**Q10: What is a Covering Index?**

An index that contains all columns needed by a query, avoiding table access.

```sql
CREATE INDEX idx_covering ON orders(customer_id, order_date, amount);

-- This query uses ONLY the index
SELECT customer_id, order_date, amount FROM orders WHERE customer_id = 123;
```

---

### Advanced Level

**Q11: Explain database isolation levels and their problems.**

| Level | Dirty Read | Non-Repeatable | Phantom |
|-------|------------|----------------|---------|
| READ UNCOMMITTED | Yes | Yes | Yes |
| READ COMMITTED | No | Yes | Yes |
| REPEATABLE READ | No | No | Yes |
| SERIALIZABLE | No | No | No |

---

**Q12: What is database sharding and when to use it?**

Sharding is horizontal partitioning across multiple databases.

**When to use:**
- Data too large for single server
- High write throughput needed
- Geographic distribution required

**Strategies:**
- Range-based: user_id 1-1M
- Hash-based: user_id % shards
- Geographic: by region

---

**Q13: Explain CAP theorem with examples.**

- **CA (PostgreSQL single):** Consistent + Available, no partition tolerance
- **CP (MongoDB):** Consistent + Partition tolerant, may be unavailable
- **AP (Cassandra):** Available + Partition tolerant, eventually consistent

---

**Q14: How to optimize a slow query?**

1. Use EXPLAIN to analyze
2. Add appropriate indexes
3. Select only needed columns
4. Avoid functions in WHERE
5. Use JOIN instead of subquery
6. Use EXISTS instead of IN
7. Add LIMIT for pagination
8. Consider denormalization

---

**Q15: What are database design anti-patterns?**

1. **EAV (Entity-Attribute-Value):** Generic attributes table
2. **Polymorphic associations:** commentable_type column
3. **ENUM abuse:** Hard to modify statuses
4. **UUID as string:** Use BINARY(16) instead
5. **Over-normalization:** Too many joins
6. **Under-indexing:** Slow queries
7. **Over-indexing:** Slow writes

---

### SQL Coding Questions

**Q16: Write a query to get employees earning more than their department average.**

```sql
SELECT e.name, e.salary, e.department
FROM employees e
WHERE e.salary > (
    SELECT AVG(salary)
    FROM employees
    WHERE department = e.department
);

-- Or with CTE
WITH dept_avg AS (
    SELECT department, AVG(salary) as avg_salary
    FROM employees
    GROUP BY department
)
SELECT e.name, e.salary, e.department
FROM employees e
JOIN dept_avg d ON e.department = d.department
WHERE e.salary > d.avg_salary;
```

---

**Q17: Write a query to find customers who have never ordered.**

```sql
-- Using LEFT JOIN
SELECT c.* FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
WHERE o.id IS NULL;

-- Using NOT EXISTS
SELECT * FROM customers c
WHERE NOT EXISTS (SELECT 1 FROM orders WHERE customer_id = c.id);

-- Using NOT IN
SELECT * FROM customers
WHERE id NOT IN (SELECT customer_id FROM orders);
```

---

**Q18: Write a query to get running total of sales.**

```sql
SELECT
    order_date,
    amount,
    SUM(amount) OVER (ORDER BY order_date) as running_total
FROM orders;
```

---

**Q19: Write a query to pivot data (rows to columns).**

```sql
SELECT
    product,
    SUM(CASE WHEN month = 'Jan' THEN sales ELSE 0 END) as Jan,
    SUM(CASE WHEN month = 'Feb' THEN sales ELSE 0 END) as Feb,
    SUM(CASE WHEN month = 'Mar' THEN sales ELSE 0 END) as Mar
FROM sales
GROUP BY product;
```

---

**Q20: Delete duplicate rows keeping one (lowest ID).**

```sql
-- Using self-join
DELETE e1 FROM employees e1
JOIN employees e2 ON e1.email = e2.email
WHERE e1.id > e2.id;

-- Using CTE (PostgreSQL)
WITH duplicates AS (
    SELECT id, ROW_NUMBER() OVER (PARTITION BY email ORDER BY id) as rn
    FROM employees
)
DELETE FROM employees WHERE id IN (SELECT id FROM duplicates WHERE rn > 1);
```

---

## Quick Reference Card

```
┌─────────────────────────────────────────────────────────────────┐
│                   DATABASE CHEAT SHEET                          │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  QUERY ORDER:  FROM → WHERE → GROUP BY → HAVING → SELECT        │
│                → ORDER BY → LIMIT                               │
│                                                                 │
│  JOINS:        INNER (matching) | LEFT (all left) |             │
│                RIGHT (all right) | FULL (all)                   │
│                                                                 │
│  KEYS:         PK (unique, not null, one) |                     │
│                FK (references PK) | UK (unique, allows null)    │
│                                                                 │
│  ACID:         Atomicity | Consistency | Isolation | Durability │
│                                                                 │
│  NORMAL FORMS: 1NF (atomic) → 2NF (no partial dep) →            │
│                3NF (no transitive dep) → BCNF                   │
│                                                                 │
│  INDEXES:      Clustered (1/table, physical order) |            │
│                Non-clustered (pointer) | Composite (multi-col)  │
│                                                                 │
│  ISOLATION:    READ UNCOMMITTED → READ COMMITTED →              │
│                REPEATABLE READ → SERIALIZABLE                   │
│                                                                 │
│  SCALING:      Replication (read) | Sharding (write) |          │
│                Pooling (connections)                            │
│                                                                 │
│  CAP:          Consistency + Availability + Partition Tolerance │
│                (Pick 2)                                         │
│                                                                 │
│  DB CHOICE:    RDBMS (ACID, structured) |                       │
│                Document (flexible) | Key-Value (cache) |        │
│                Column (writes) | Graph (relationships)          │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

**Q21: How do you optimize slow database queries?**

**Answer:**

**Step 1: Identify the Problem**
```sql
-- Use EXPLAIN/EXPLAIN ANALYZE to see query execution plan
EXPLAIN ANALYZE SELECT * FROM orders WHERE customer_id = 123;
```

**Step 2: Common Optimization Techniques**

| Technique | When to Use | Example |
|-----------|-------------|---------|
| **Indexing** | Columns in WHERE, JOIN, ORDER BY | `CREATE INDEX idx_customer ON orders(customer_id)` |
| **Avoid SELECT *** | Fetch only needed columns | `SELECT id, name FROM users` |
| **Pagination** | Large result sets | `LIMIT 20 OFFSET 40` |
| **Query Rewriting** | Subqueries that can be JOINs | Replace `IN (SELECT ...)` with `JOIN` |
| **Denormalization** | Frequent expensive JOINs | Store computed/aggregated data |
| **Partitioning** | Very large tables | Range/hash partition by date |
| **Connection Pooling** | High concurrent access | HikariCP in Spring Boot |
| **Caching** | Repeated identical queries | Redis for frequently accessed data |

**Step 3: Indexing Best Practices**

```sql
-- Single column index
CREATE INDEX idx_email ON users(email);

-- Composite index (order matters!)
CREATE INDEX idx_status_date ON orders(status, created_date);

-- Covering index (includes all columns needed by query)
CREATE INDEX idx_covering ON orders(customer_id, status, total_amount);
```

**Step 4: Spring Boot / JPA Specific**

```java
// N+1 Problem — BAD
@OneToMany(fetch = FetchType.LAZY)
private List<Order> orders;
// Accessing orders in a loop causes N+1 queries

// Solution: Use JOIN FETCH
@Query("SELECT c FROM Customer c JOIN FETCH c.orders WHERE c.id = :id")
Customer findCustomerWithOrders(@Param("id") Long id);

// Solution: Entity Graph
@EntityGraph(attributePaths = {"orders"})
Optional<Customer> findById(Long id);
```

**Step 5: Monitor and Measure**
- Enable slow query log in MySQL/PostgreSQL
- Use Spring Boot Actuator for connection pool metrics
- Profile with tools like p6spy or datasource-proxy

---

**Q22: How do you design a database schema from Business Analyst (BA) requirements?**

**Answer:**

**Process Overview:**

```
BA Requirements → Entities → Relationships → Schema → Normalization → Indexes
```

**Step 1: Identify Entities from Requirements**

Example BA Requirement: *"Users can place orders. Each order contains multiple products. Users can have multiple addresses."*

Entities identified: `User`, `Order`, `Product`, `Address`

**Step 2: Define Relationships**

```
User ──(1:N)──> Order
User ──(1:N)──> Address
Order ──(M:N)──> Product  (needs junction table: order_items)
```

**Step 3: Design Tables**

```sql
CREATE TABLE users (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(255) UNIQUE NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE addresses (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id     BIGINT NOT NULL,
    street      VARCHAR(255),
    city        VARCHAR(100),
    zip_code    VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE orders (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id     BIGINT NOT NULL,
    status      VARCHAR(50) DEFAULT 'PENDING',
    total       DECIMAL(10,2),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE products (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(200) NOT NULL,
    price       DECIMAL(10,2) NOT NULL,
    stock       INT DEFAULT 0
);

-- Junction table for M:N relationship
CREATE TABLE order_items (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id    BIGINT NOT NULL,
    product_id  BIGINT NOT NULL,
    quantity    INT NOT NULL,
    unit_price  DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

**Step 4: Apply Normalization**
- **1NF** — Each column holds atomic values, no repeating groups
- **2NF** — All non-key columns depend on the entire primary key
- **3NF** — No transitive dependencies (non-key column depending on another non-key column)

**Step 5: Map to JPA Entities**

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses;
}
```

---

**Q23: What are the different types of Keys in a database? Explain all keys.**

**Answer:**

```
┌─────────────────────────────────────────────────────────────┐
│                    DATABASE KEYS HIERARCHY                    │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│   Super Key (all possible unique identifiers)                │
│   ├── Candidate Key (minimal super keys)                     │
│   │   ├── Primary Key (chosen candidate key)                 │
│   │   └── Alternate Key (remaining candidate keys)           │
│   │                                                          │
│   Foreign Key (references another table's PK)                │
│   Unique Key (unique but allows NULL)                        │
│   Composite Key (multiple columns as key)                    │
│   Surrogate Key (system-generated, no business meaning)      │
│   Natural Key (real-world data used as key)                  │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

**Example Table: `employees`**

```
┌─────┬──────────────┬─────────────────────┬────────────┬─────────┐
│ id  │ employee_code│ email               │ name       │ dept_id │
├─────┼──────────────┼─────────────────────┼────────────┼─────────┤
│ 1   │ EMP001       │ alice@company.com   │ Alice      │ 10      │
│ 2   │ EMP002       │ bob@company.com     │ Bob        │ 20      │
│ 3   │ EMP003       │ charlie@company.com │ Charlie    │ 10      │
└─────┴──────────────┴─────────────────────┴────────────┴─────────┘
```

| Key Type | Definition | Example from Table |
|----------|------------|-------------------|
| **Super Key** | Any combination of columns that can uniquely identify a row | `{id}`, `{email}`, `{employee_code}`, `{id, name}`, `{id, email}` |
| **Candidate Key** | Minimal super key — no column can be removed and still be unique | `{id}`, `{email}`, `{employee_code}` |
| **Primary Key** | ONE candidate key chosen as the main identifier — NOT NULL, unique | `id` (chosen as PK) |
| **Alternate Key** | Candidate keys that were NOT chosen as primary key | `email`, `employee_code` |
| **Foreign Key** | Column that references the primary key of another table | `dept_id → departments(id)` |
| **Unique Key** | Ensures uniqueness, allows one NULL, multiple per table | `email UNIQUE`, `employee_code UNIQUE` |
| **Composite Key** | Primary key made of 2+ columns together | `PRIMARY KEY (student_id, course_id)` |
| **Surrogate Key** | System-generated key with no business meaning (auto-increment, UUID) | `id INT AUTO_INCREMENT` |
| **Natural Key** | Real-world data used as key | `email`, `SSN`, `ISBN` |

```sql
-- All keys in action
CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,          -- Primary Key (Surrogate)
    employee_code VARCHAR(10) UNIQUE NOT NULL,  -- Alternate Key / Unique Key
    email VARCHAR(100) UNIQUE NOT NULL,         -- Alternate Key / Unique Key
    name VARCHAR(100),
    dept_id INT,                                -- Foreign Key
    FOREIGN KEY (dept_id) REFERENCES departments(id)
);

-- Composite Key example
CREATE TABLE order_items (
    order_id INT,
    product_id INT,
    quantity INT,
    PRIMARY KEY (order_id, product_id),         -- Composite Key
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

**Surrogate Key vs Natural Key:**

| Aspect | Surrogate Key | Natural Key |
|--------|--------------|-------------|
| What | Auto-generated (id, UUID) | Business data (email, SSN) |
| Changes? | Never changes | Can change (email update) |
| Size | Small (INT = 4 bytes) | Can be large (VARCHAR) |
| Meaning | No business meaning | Has business meaning |
| JOIN performance | Faster (small INT) | Slower (large strings) |
| Best for | Most tables | Lookup/reference tables |

---

**Q24: Explain ER Diagrams — What are Cardinality and Ordinality?**

**Answer:**

An **ER (Entity-Relationship) Diagram** visually represents tables, their columns, and how they relate to each other.

**Cardinality** — The **maximum** number of times an entity can participate in a relationship (1 or Many).

**Ordinality (Participation)** — The **minimum** number of times an entity MUST participate (0 = optional, 1 = mandatory).

```
┌─────────────────────────────────────────────────────────────┐
│              CARDINALITY + ORDINALITY NOTATION                │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  Crow's Foot Notation (most common):                         │
│                                                              │
│  ──────┤├──────    One (and only one)      │  mandatory one   │
│  ──────○├──────    Zero or one             │  optional one    │
│  ──────┤<──────    One or many             │  mandatory many  │
│  ──────○<──────    Zero or many            │  optional many   │
│                                                              │
│  Symbol Meaning:                                             │
│  ┤  or  │  = "exactly one" (mandatory)                       │
│  ○      = "zero" (optional)                                  │
│  <  or  > = "many"                                           │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

**The 4 Types of Cardinality:**

**1. One-to-One (1:1)**
```
┌──────────┐          ┌──────────────┐
│  USER    │          │   PASSPORT   │
│──────────│          │──────────────│
│ *user_id ├────┤├────┤ *passport_id │
│  name    │          │  user_id (FK)│
│  email   │          │  number      │
└──────────┘          └──────────────┘

One user has exactly one passport.
One passport belongs to exactly one user.
```

**2. One-to-Many (1:N)**
```
┌──────────────┐              ┌──────────────┐
│  DEPARTMENT  │              │   EMPLOYEE   │
│──────────────│              │──────────────│
│ *dept_id     ├────┤├────○<──┤ *emp_id      │
│  dept_name   │              │  name        │
└──────────────┘              │  dept_id (FK)│
                              └──────────────┘

One department has zero or many employees.
One employee belongs to exactly one department.
```

**3. Many-to-Many (M:N)**
```
┌──────────┐          ┌──────────────┐          ┌──────────┐
│ STUDENT  │          │  ENROLLMENT  │          │  COURSE  │
│──────────│          │──────────────│          │──────────│
│ *stu_id  ├──┤├──○<──┤ *stu_id (FK) │──>○──┤├──┤ *crs_id  │
│  name    │          │ *crs_id (FK) │          │  title   │
└──────────┘          │  grade       │          └──────────┘
                      └──────────────┘
                       (Junction Table)

One student enrolls in zero or many courses.
One course has zero or many students.
Resolved through junction/bridge table.
```

**4. Zero/Optional Relationships**
```
┌──────────┐              ┌──────────────┐
│ CUSTOMER │              │    ORDER     │
│──────────│              │──────────────│
│ *cust_id ├────┤├────○<──┤ *order_id    │
│  name    │              │  cust_id (FK)│
└──────────┘              └──────────────┘

One customer has ZERO or many orders (○ = optional, customer may not have ordered yet).
One order belongs to exactly ONE customer (┤├ = mandatory).
```

**Cardinality vs Ordinality Summary:**

| Concept | Question It Answers | Values | Symbol |
|---------|-------------------|--------|--------|
| **Cardinality** | "How many MAX?" | One (1) or Many (N) | `┤├` = one, `<` = many |
| **Ordinality** | "Is it required?" | Mandatory (1) or Optional (0) | `┤` = mandatory, `○` = optional |

**Reading an ER Relationship:**
```
CUSTOMER ──┤├────○<── ORDER

Read from left:  "Each CUSTOMER has ZERO or MANY orders"
Read from right: "Each ORDER belongs to exactly ONE customer"

Cardinality: 1:N (one-to-many)
Ordinality:  Customer side = mandatory (must exist)
             Order side = optional (may have zero orders)
```

---

**Q25: What are all the types of SQL functions? Explain with categories.**

**Answer:**

```
┌─────────────────────────────────────────────────────────────┐
│                   SQL FUNCTION CATEGORIES                     │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  1. Aggregate Functions   — Operate on groups of rows        │
│  2. Scalar Functions      — Operate on single values         │
│     ├── String Functions                                     │
│     ├── Numeric Functions                                    │
│     ├── Date/Time Functions                                  │
│     └── Conditional Functions                                │
│  3. Window Functions      — Aggregate + keep individual rows │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

**1. Aggregate Functions** — Take multiple rows, return ONE value

| Function | Purpose | Example |
|----------|---------|---------|
| `COUNT()` | Count rows | `COUNT(*)`, `COUNT(DISTINCT col)` |
| `SUM()` | Total of values | `SUM(salary)` |
| `AVG()` | Average | `AVG(price)` |
| `MAX()` | Highest value | `MAX(salary)` |
| `MIN()` | Lowest value | `MIN(price)` |
| `GROUP_CONCAT()` | Combine values | `GROUP_CONCAT(name SEPARATOR ', ')` |

```sql
SELECT dept_id,
       COUNT(*) AS total,
       SUM(salary) AS total_salary,
       AVG(salary) AS avg_salary,
       MAX(salary) AS max_salary,
       MIN(salary) AS min_salary
FROM employees
GROUP BY dept_id;
```

**2. Scalar Functions** — Take ONE value, return ONE value

| Category | Functions | Example |
|----------|-----------|---------|
| **String** | `UPPER()`, `LOWER()`, `LENGTH()`, `CONCAT()`, `SUBSTRING()`, `TRIM()`, `REPLACE()`, `REVERSE()`, `LPAD()`, `RPAD()` | `UPPER('hello')` → `'HELLO'` |
| **Numeric** | `ROUND()`, `CEIL()`, `FLOOR()`, `ABS()`, `MOD()`, `POWER()`, `SQRT()`, `TRUNCATE()`, `RAND()` | `ROUND(3.567, 2)` → `3.57` |
| **Date** | `NOW()`, `CURDATE()`, `YEAR()`, `MONTH()`, `DAY()`, `DATEDIFF()`, `DATE_ADD()`, `DATE_FORMAT()` | `YEAR('2024-01-15')` → `2024` |
| **Conditional** | `IF()`, `IFNULL()`, `COALESCE()`, `NULLIF()`, `CASE WHEN` | `COALESCE(NULL, 'default')` → `'default'` |

**3. Window / Analytic Functions** — Like aggregate but keep ALL rows

Window functions perform calculations across a **set of rows related to the current row** without collapsing them into one.

```sql
SELECT
    name,
    department,
    salary,
    -- Ranking
    ROW_NUMBER() OVER (ORDER BY salary DESC) AS row_num,
    RANK()       OVER (ORDER BY salary DESC) AS rank,
    DENSE_RANK() OVER (ORDER BY salary DESC) AS dense_rank,

    -- Aggregates as window
    SUM(salary)  OVER (PARTITION BY department) AS dept_total,
    AVG(salary)  OVER (PARTITION BY department) AS dept_avg,
    COUNT(*)     OVER (PARTITION BY department) AS dept_count,

    -- Navigation
    LAG(salary, 1)  OVER (ORDER BY salary) AS prev_salary,
    LEAD(salary, 1) OVER (ORDER BY salary) AS next_salary,
    FIRST_VALUE(name) OVER (PARTITION BY department ORDER BY salary DESC) AS top_earner,

    -- Running total
    SUM(salary) OVER (ORDER BY hire_date ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS running_total
FROM employees;
```

**Window Functions Summary:**

| Category | Functions | Purpose |
|----------|-----------|---------|
| **Ranking** | `ROW_NUMBER()`, `RANK()`, `DENSE_RANK()`, `NTILE()` | Assign rank/position |
| **Aggregate** | `SUM()`, `AVG()`, `COUNT()`, `MAX()`, `MIN()` over window | Group calc without collapsing |
| **Navigation** | `LAG()`, `LEAD()`, `FIRST_VALUE()`, `LAST_VALUE()`, `NTH_VALUE()` | Access other rows |

**Aggregate vs Window Function:**

| Aspect | Aggregate Function | Window Function |
|--------|-------------------|-----------------|
| Rows returned | One row per group | All rows preserved |
| Requires | `GROUP BY` | `OVER()` clause |
| Example | `SELECT dept, AVG(sal) ... GROUP BY dept` | `AVG(sal) OVER (PARTITION BY dept)` |
| Result | Collapses rows | Adds column to each row |

---

**Q26: Explain all types of Indexes in a database.**

**Answer:**

| Index Type | Description | Use Case |
|-----------|-------------|----------|
| **Clustered** | Defines physical order of data on disk. One per table. PK creates this automatically. | Range queries, ORDER BY |
| **Non-Clustered** | Separate structure with pointers to rows. Multiple allowed. | Specific lookups (email, phone) |
| **Composite** | Index on 2+ columns. Follows leftmost prefix rule. | Multi-column WHERE/JOIN |
| **Covering** | Index contains ALL columns a query needs — no table access. | Frequently run queries |
| **Unique** | Ensures all indexed values are distinct. | Email, SSN, phone columns |
| **Full-Text** | Optimized for text search in large text columns. | Search in articles, descriptions |
| **Partial / Filtered** | Index on a subset of rows (with WHERE condition). | Active users only, recent orders |
| **Hash** | Uses hash function. Exact match only, no range queries. | Equality lookups (=) |
| **Bitmap** | Bit arrays for each distinct value. Low-cardinality columns. | Gender, status, boolean flags |

```sql
-- 1. Clustered (created automatically with PRIMARY KEY)
CREATE TABLE users (
    id INT PRIMARY KEY  -- clustered index
);

-- 2. Non-Clustered
CREATE INDEX idx_email ON users(email);

-- 3. Composite
CREATE INDEX idx_dept_salary ON employees(department, salary);
-- Uses index: WHERE department = 'IT' AND salary > 50000  ✓
-- Uses index: WHERE department = 'IT'                      ✓
-- Skips index: WHERE salary > 50000                        ✗ (not leftmost)

-- 4. Covering
CREATE INDEX idx_covering ON orders(customer_id, order_date, amount);
-- This query uses ONLY the index:
SELECT customer_id, order_date, amount FROM orders WHERE customer_id = 1;

-- 5. Unique
CREATE UNIQUE INDEX idx_unique_email ON users(email);

-- 6. Full-Text
CREATE FULLTEXT INDEX idx_ft_content ON articles(title, body);
-- Usage:
SELECT * FROM articles WHERE MATCH(title, body) AGAINST('database indexing');

-- 7. Partial / Filtered (PostgreSQL)
CREATE INDEX idx_active_users ON users(email) WHERE is_active = true;
-- SQL Server:
CREATE INDEX idx_active ON users(email) WHERE is_active = 1;

-- 8. Hash (PostgreSQL)
CREATE INDEX idx_hash_email ON users USING HASH (email);
-- Fast for: WHERE email = 'john@email.com'
-- Cannot do: WHERE email LIKE 'john%'  (no range support)
```

**B-Tree vs Hash Index:**

| Aspect | B-Tree (Default) | Hash |
|--------|-----------------|------|
| Equality (`=`) | Yes | Yes |
| Range (`>`, `<`, `BETWEEN`) | Yes | No |
| Sorting (`ORDER BY`) | Yes | No |
| Pattern (`LIKE 'abc%'`) | Yes (prefix) | No |
| Default in | MySQL, PostgreSQL | Memory tables |

---

**Q27: What are Subqueries? Explain all types with examples.**

**Answer:**

A **subquery** is a query nested inside another query. The inner query executes first, and its result is used by the outer query.

**Types of Subqueries:**

```
Subqueries
├── By Return Value
│   ├── Scalar Subquery    → Returns single value
│   ├── Row Subquery       → Returns single row
│   └── Table Subquery     → Returns multiple rows/columns
│
├── By Dependency
│   ├── Non-Correlated     → Independent of outer query
│   └── Correlated         → References outer query (runs per row)
│
└── By Location
    ├── In WHERE clause
    ├── In FROM clause (Derived Table / Inline View)
    └── In SELECT clause (Scalar only)
```

```sql
-- 1. Scalar Subquery (single value) — in WHERE
SELECT name, salary FROM employees
WHERE salary > (SELECT AVG(salary) FROM employees);

-- 2. Scalar Subquery — in SELECT
SELECT name, salary,
       salary - (SELECT AVG(salary) FROM employees) AS diff_from_avg
FROM employees;

-- 3. Table Subquery — with IN
SELECT * FROM employees
WHERE dept_id IN (SELECT id FROM departments WHERE location = 'Mumbai');

-- 4. Correlated Subquery — references outer query
SELECT e.name, e.salary, e.dept_id
FROM employees e
WHERE e.salary > (
    SELECT AVG(salary) FROM employees WHERE dept_id = e.dept_id
);
-- Runs the inner query ONCE per row of outer query

-- 5. Subquery in FROM (Derived Table)
SELECT dept_name, avg_salary
FROM (
    SELECT d.dept_name, AVG(e.salary) AS avg_salary
    FROM employees e JOIN departments d ON e.dept_id = d.id
    GROUP BY d.dept_name
) AS dept_stats
WHERE avg_salary > 60000;

-- 6. EXISTS (checks if subquery returns any rows)
SELECT c.name FROM customers c
WHERE EXISTS (
    SELECT 1 FROM orders o WHERE o.customer_id = c.id
);
-- Returns customers who have at least one order

-- 7. NOT EXISTS
SELECT c.name FROM customers c
WHERE NOT EXISTS (
    SELECT 1 FROM orders o WHERE o.customer_id = c.id
);
-- Returns customers who have NEVER ordered
```

**EXISTS vs IN:**

| Aspect | IN | EXISTS |
|--------|-----|--------|
| How it works | Compares value against a list | Checks if subquery returns any row |
| NULL handling | Fails with NULLs in subquery | Handles NULLs correctly |
| Performance (large outer, small inner) | Better | Slower |
| Performance (small outer, large inner) | Slower | Better |
| Readability | Simpler | More verbose |

```sql
-- IN — better when subquery result is small
SELECT * FROM employees WHERE dept_id IN (SELECT id FROM departments WHERE location = 'Mumbai');

-- EXISTS — better when outer table is small, inner table is large
SELECT * FROM departments d WHERE EXISTS (SELECT 1 FROM employees e WHERE e.dept_id = d.id);
```

**Subquery vs JOIN:**

```sql
-- Subquery
SELECT name FROM employees
WHERE dept_id IN (SELECT id FROM departments WHERE location = 'Mumbai');

-- Equivalent JOIN (usually better performance)
SELECT e.name FROM employees e
JOIN departments d ON e.dept_id = d.id
WHERE d.location = 'Mumbai';
```

| Aspect | Subquery | JOIN |
|--------|----------|------|
| Readability | More readable for simple cases | Better for complex relations |
| Performance | Can be slower (executed per row for correlated) | Usually faster (optimizer can merge) |
| When to use | Aggregate comparisons, EXISTS checks | Fetching columns from multiple tables |

---

**End of Database Reference Guide**

# Database - Complete Reference Guide

## SQL Query Execution Order

**Important:** SQL executes queries in the order: **FROM → WHERE → GROUP BY → HAVING → SELECT → ORDER BY → LIMIT**, not in the written order.

```sql
-- Written order:
SELECT column
FROM table
WHERE condition
GROUP BY column
HAVING condition
ORDER BY column
LIMIT n

-- Actual execution order:
1. FROM table          -- Get the data
2. WHERE condition     -- Filter rows
3. GROUP BY column     -- Group data
4. HAVING condition    -- Filter groups
5. SELECT column       -- Select columns
6. ORDER BY column     -- Sort results
7. LIMIT n            -- Limit results
```

---

## Key Database Concepts

### Primary Key (PK)
A primary key is a column (or combination of columns) that uniquely identifies each row in a table.

**Properties:**
- Must be **unique**
- Cannot be **NULL**
- Only **one per table**
- Automatically creates a **clustered index** (in most databases)

**Example:**
```sql
CREATE TABLE Student (
    student_id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);
```

---

### Foreign Key (FK)
A foreign key is a column that references the primary key of another table, creating a relationship between tables.

**Referential Integrity:** Ensures that relationships between tables remain consistent.

**Example:**
```sql
CREATE TABLE Enrollment (
    enrollment_id INT PRIMARY KEY,
    student_id INT,
    course_id INT,
    FOREIGN KEY (student_id) REFERENCES Student(student_id),
    FOREIGN KEY (course_id) REFERENCES Course(course_id)
);
```

**Benefits:**
- Maintains data integrity
- Prevents orphaned records
- Enforces relationships

---

### Unique Key (UK)
A unique key ensures that all values in a column (or combination of columns) are different.

**Difference from Primary Key:**
| Primary Key                  | Unique Key                    |
|------------------------------|-------------------------------|
| Only one per table           | Multiple allowed              |
| Cannot be NULL               | Can have one NULL             |
| Creates clustered index      | Creates non-clustered index   |
| Identifies row uniquely      | Ensures column uniqueness     |

**Example:**
```sql
CREATE TABLE User (
    user_id INT PRIMARY KEY,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15) UNIQUE
);
```

---

## ACID Properties

ACID ensures reliable database transactions.

### A - Atomicity
**"All or Nothing"**
- Transaction completes fully or not at all
- No partial updates

**Example:** Bank transfer - both debit and credit must happen, or neither

### C - Consistency
**"Valid State to Valid State"**
- Database moves from one valid state to another
- All constraints are satisfied

**Example:** Balance cannot be negative if constraint exists

### D - Durability
**"Permanent Once Committed"**
- Once transaction is committed, changes are permanent
- Survives system crashes

**Example:** After successful commit, data persists even if server crashes

### I - Isolation
**"Transactions Don't Interfere"**
- Concurrent transactions don't affect each other
- Each transaction sees consistent data

**Example:** Two people booking same seat don't interfere

---

## Normalization

**What is Normalization?**
The process of organizing data to reduce redundancy and improve data integrity.

**Benefits:**
- Eliminates redundant data
- Reduces storage space
- Prevents update anomalies
- Ensures data consistency
- Makes maintenance easier

### 1NF (First Normal Form)
**Rules:**
- Each column contains atomic (indivisible) values
- Each column contains values of single type
- Each column has unique name
- Order doesn't matter

**Before 1NF:**
```
| student_id | name  | courses           |
|------------|-------|-------------------|
| 1          | Alice | Math, Physics     |
```

**After 1NF:**
```
| student_id | name  | course  |
|------------|-------|---------|
| 1          | Alice | Math    |
| 1          | Alice | Physics |
```

---

### 2NF (Second Normal Form)
**Rules:**
- Must be in 1NF
- No partial dependency (non-key attributes fully depend on primary key)

**Before 2NF:**
```
| student_id | course_id | student_name | course_name |
|------------|-----------|--------------|-------------|
| 1          | 101       | Alice        | Math        |
```
`student_name` depends only on `student_id`, not on full key (`student_id`, `course_id`)

**After 2NF:**
```
Student table:
| student_id | student_name |
|------------|--------------|
| 1          | Alice        |

Enrollment table:
| student_id | course_id | course_name |
|------------|-----------|-------------|
| 1          | 101       | Math        |
```

---

### 3NF (Third Normal Form)
**Rules:**
- Must be in 2NF
- No transitive dependency (non-key attributes don't depend on other non-key attributes)

**Before 3NF:**
```
| student_id | student_name | dept_id | dept_name |
|------------|--------------|---------|-----------|
| 1          | Alice        | 10      | CS        |
```
`dept_name` depends on `dept_id`, not directly on `student_id`

**After 3NF:**
```
Student table:
| student_id | student_name | dept_id |
|------------|--------------|---------|
| 1          | Alice        | 10      |

Department table:
| dept_id | dept_name |
|---------|-----------|
| 10      | CS        |
```

---

### BCNF (Boyce-Codd Normal Form)
**Rules:**
- Must be in 3NF
- Every determinant is a candidate key

**Stricter version of 3NF**

---

## Denormalization

**What is it?** Intentionally adding redundancy to improve read performance.

**When to use:**
- Read-heavy applications
- Complex joins hurting performance
- Reporting/analytics databases
- Caching frequently accessed data

**Trade-offs:**
- ✅ Faster reads
- ❌ Slower writes
- ❌ More storage
- ❌ Data inconsistency risk

---

## Relationships & ER Diagrams

### Entity-Relationship (ER) Diagram
Visual representation of database structure showing:

**Components:**
- **Entities (tables)** - Rectangles
- **Attributes (columns)** - Ovals
- **Relationships** - Diamonds/lines
- **Cardinality** - 1:1, 1:N, M:N

### Relationship Types:

#### 1:1 (One-to-One)
One record in Table A relates to one record in Table B.

**Example:** Person ↔ Passport

#### 1:N (One-to-Many)
One record in Table A relates to many records in Table B.

**Example:** Department → Employees

#### M:N (Many-to-Many)
Many records in Table A relate to many records in Table B.

**Example:** Students ↔ Courses

**Implementation:** Requires a junction table

---

### Junction/Bridge Table

A junction table (also called bridge, linking, or associative table) implements many-to-many relationships.

**Example:**
```sql
-- Students and Courses (M:N relationship)

CREATE TABLE Student (
    student_id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE Course (
    course_id INT PRIMARY KEY,
    course_name VARCHAR(100)
);

-- Junction table
CREATE TABLE Enrollment (
    enrollment_id INT PRIMARY KEY,
    student_id INT,
    course_id INT,
    grade CHAR(1),
    FOREIGN KEY (student_id) REFERENCES Student(student_id),
    FOREIGN KEY (course_id) REFERENCES Course(course_id)
);
```

---

## Indexes

### 🎯 Why Indexes? The Phone Book Story

**The Problem: Searching Without Index**

Imagine you're building a student management system with 1 million students:

```sql
-- Find student with ID 987654
SELECT * FROM students WHERE student_id = 987654;

Without Index:
┌──────────────────────────────────────────────────────────┐
│              DATABASE SEARCH (NO INDEX)                   │
├──────────────────────────────────────────────────────────┤
│                                                           │
│  Check row 1:    ID = 1       ✗ (not 987654)            │
│  Check row 2:    ID = 2       ✗                          │
│  Check row 3:    ID = 3       ✗                          │
│  ...                                                      │
│  Check row 987654: ID = 987654 ✓ FOUND!                  │
│                                                           │
│  Checked 987,654 rows = FULL TABLE SCAN                  │
│  Time: 10 seconds (slow!) ⏱️                              │
│                                                           │
└──────────────────────────────────────────────────────────┘

With Index:
┌──────────────────────────────────────────────────────────┐
│              DATABASE SEARCH (WITH INDEX)                 │
├──────────────────────────────────────────────────────────┤
│                                                           │
│  Index (B-Tree):                                          │
│          500000                                           │
│         /      \                                          │
│    250000      750000                                     │
│              /       \                                    │
│          625000    875000                                 │
│                   /      \                                │
│               812500    937500                            │
│                        /      \                           │
│                    906250    968750                       │
│                             /      \                      │
│                         953125   984375                   │
│                                 /      \                  │
│                             968750   990625               │
│                                     /                     │
│                                 987654 ✓ FOUND!           │
│                                                           │
│  Checked only 12 rows (Binary search)                    │
│  Time: 0.001 seconds (10,000x faster!) ⚡                │
│                                                           │
└──────────────────────────────────────────────────────────┘
```

### Real-World Analogy

**Index = Phone Book vs Random Contact List**

```
WITHOUT INDEX (Phone contacts in random order):
┌────────────────────────────────────┐
│ Random Contact List (1000 people)  │
├────────────────────────────────────┤
│ 1. Zara Wilson                     │
│ 2. Bob Smith                       │
│ 3. Alice Johnson                   │
│ ...                                │
│ 867. John Doe     ← Looking for!   │
│ ...                                │
│ 1000. Mike Brown                   │
└────────────────────────────────────┘

To find "John Doe":
- Check contact 1: Not John ✗
- Check contact 2: Not John ✗
- Check contact 3: Not John ✗
...
- Check contact 867: FOUND! ✓

Time: Check 867 contacts (slow!)

WITH INDEX (Phone book - alphabetically sorted):
┌────────────────────────────────────┐
│ Phone Book (1000 people, sorted)   │
├────────────────────────────────────┤
│ ...                                │
│ 465. Jane Wilson                   │
│ 466. Jim Anderson                  │
│ 467. John Doe     ← Jump directly! │
│ 468. John Smith                    │
│ ...                                │
└────────────────────────────────────┘

To find "John Doe":
- Open middle (500) → "Jane Wilson" → Go right
- Check (750) → "Sarah Taylor" → Go left
- Check (625) → "Mary Lopez" → Go left
- Check (550) → "Kevin Harris" → Go left
- Check (475) → "John Anderson" → Go right
- Check (467) → "John Doe" FOUND! ✓

Time: Check only 6 contacts (167x faster!)
```

### Performance Impact Example

**E-commerce scenario:** Search products by name

```sql
-- Table: products (10 million records)

-- WITHOUT INDEX
SELECT * FROM products WHERE name = 'iPhone 15';
-- Scans all 10 million rows
-- Time: 30 seconds ⏱️

-- WITH INDEX
CREATE INDEX idx_product_name ON products(name);

SELECT * FROM products WHERE name = 'iPhone 15';
-- Uses index, checks ~20 rows
-- Time: 0.003 seconds ⚡
-- 10,000x faster!
```

### When Indexes Help (Real Scenarios)

✅ **Search by user ID** (Login)
```sql
-- 1 million users, find by ID
SELECT * FROM users WHERE user_id = 12345;
-- Without index: 5 seconds
-- With index: 0.001 seconds (5000x faster!)
```

✅ **Search by email** (Login by email)
```sql
-- Find user by email
SELECT * FROM users WHERE email = 'john@example.com';
-- Without index: 8 seconds (checks all 1M rows)
-- With index: 0.002 seconds (4000x faster!)
```

✅ **Product search** (E-commerce)
```sql
-- 10 million products, search by category
SELECT * FROM products WHERE category = 'Electronics' ORDER BY price;
-- Without index: 45 seconds
-- With index on (category, price): 0.1 seconds (450x faster!)
```

### When Indexes DON'T Help

❌ **Small tables** (< 1000 rows)
```sql
-- Table: countries (195 rows)
SELECT * FROM countries WHERE name = 'India';
-- Without index: 0.001 seconds
-- With index: 0.001 seconds
-- No benefit! Index overhead not worth it.
```

❌ **Columns with few unique values** (Low cardinality)
```sql
-- Table: users (1 million rows)
-- Column: gender (only 2 values: 'M' or 'F')
SELECT * FROM users WHERE gender = 'M';
-- Returns 500,000 rows (50% of table)
-- Index doesn't help! Still scans half the table.
```

❌ **Heavy write operations**
```sql
-- Table with lots of INSERTs
INSERT INTO logs (message) VALUES ('Log entry');
-- Every INSERT must update the index
-- Without index: 0.01 seconds
-- With 5 indexes: 0.05 seconds (5x slower!)
```

### The Cost of Indexes

**Indexes are NOT free:**

```
┌────────────────────────────────────────────────────────┐
│             INDEX TRADEOFFS                             │
├────────────────────────────────────────────────────────┤
│                                                         │
│  Benefits:                                              │
│  ✅ SELECT queries: 100-10,000x faster                 │
│  ✅ WHERE, JOIN, ORDER BY: Much faster                 │
│                                                         │
│  Costs:                                                 │
│  ❌ Storage: Extra disk space (10-20% of table size)   │
│  ❌ INSERT: Slower (must update index)                 │
│  ❌ UPDATE: Slower if indexed columns change           │
│  ❌ DELETE: Slower (must update index)                 │
│  ❌ Memory: Indexes loaded in RAM                      │
│                                                         │
└────────────────────────────────────────────────────────┘

Example:
Table: users (1 million rows)
- Table size: 500 MB
- 3 indexes: +100 MB storage
- INSERT time: 0.01s → 0.03s (3x slower)
- SELECT time: 5s → 0.001s (5000x faster!)

Trade-off: Slower writes for MUCH faster reads
(Usually worth it for read-heavy applications!)
```

### Real-World Decision: When to Index?

```
E-commerce Product Table:
┌──────────────────────────────────────────────────────┐
│ Column        │ Index? │ Why?                        │
├──────────────────────────────────────────────────────┤
│ product_id    │ ✅ YES │ Primary key, frequent lookup │
│ name          │ ✅ YES │ Search by name (common)      │
│ category      │ ✅ YES │ Filter by category (common)  │
│ price         │ ✅ YES │ Sort by price (common)       │
│ description   │ ❌ NO  │ Full text, rarely filtered   │
│ color         │ ⚠️ MAYBE│ Low cardinality (10 colors)  │
│ created_at    │ ❌ NO  │ Rarely queried               │
│ updated_at    │ ❌ NO  │ Rarely queried               │
└──────────────────────────────────────────────────────┘

Result: 4 indexes (id, name, category, price)
Perfect for typical queries like:
- Search: WHERE name LIKE '%iPhone%'
- Filter: WHERE category = 'Electronics'
- Sort: ORDER BY price
```

### Key Takeaway

```
⚡ Index = Trade read speed for write speed
📚 Use on columns in WHERE, JOIN, ORDER BY
🎯 Essential for large tables (100k+ rows)
❌ Don't over-index (slows writes!)
🔍 Test with realistic data volumes!
```

---

### What is an Index?
An index is a data structure that improves query speed by providing quick lookups.

**Analogy:** Like a book's index - instead of reading every page, jump directly to relevant pages.

**Benefits:**
- ✅ Faster SELECT queries
- ✅ Faster WHERE clause filtering
- ✅ Faster JOIN operations
- ✅ Faster ORDER BY

**Drawbacks:**
- ❌ Extra storage space
- ❌ Slower INSERT/UPDATE/DELETE
- ❌ Maintenance overhead

---

### Clustered vs Non-Clustered Index

| Clustered Index                  | Non-Clustered Index              |
|----------------------------------|----------------------------------|
| Determines physical order of data| Separate structure pointing to data |
| Only one per table               | Multiple allowed                 |
| Faster for range queries         | Faster for specific lookups      |
| Table IS the index               | Index points to table            |
| Primary key by default           | Created manually                 |

**Clustered Index:**
```sql
CREATE CLUSTERED INDEX idx_id ON Student(student_id);
```

**Non-Clustered Index:**
```sql
CREATE NONCLUSTERED INDEX idx_name ON Student(name);
```

---

### Composite Index
A composite index (compound index) includes multiple columns.

**Example:**
```sql
CREATE INDEX idx_name_age ON Student(name, age);
```

**Best for queries:**
```sql
SELECT * FROM Student WHERE name = 'Alice' AND age = 20;
```

---

### When NOT to Use Indexes

Avoid indexes when:
- ❌ Table is small (full scan is faster)
- ❌ Column has low cardinality (few unique values like gender)
- ❌ Column is rarely used in WHERE/JOIN/ORDER BY
- ❌ Table has heavy INSERT/UPDATE/DELETE operations
- ❌ Column values are frequently updated
- ❌ Large portion of table is selected

---

### Covering Index
A covering index contains all columns needed by a query, avoiding table access entirely.

**Example:**
```sql
CREATE INDEX idx_covering ON Student(name, age, email);

-- This query is fully covered by index
SELECT name, age, email FROM Student WHERE name = 'Alice';
```

---

## SQL Interview Questions

### 1. WHERE vs HAVING

| WHERE                              | HAVING                              |
|------------------------------------|-------------------------------------|
| Filter rows **before** grouping    | Filter groups **after** GROUP BY    |
| Can't use aggregate functions      | Can use aggregate functions         |
| Used with SELECT, UPDATE, DELETE   | Used only with SELECT               |

**Example:**
```sql
-- WHERE
SELECT * FROM Students WHERE age > 18;

-- HAVING
SELECT dept, COUNT(*) as count
FROM Students
GROUP BY dept
HAVING COUNT(*) > 5;
```

---

### 2. JOIN Types

#### INNER JOIN
Returns only matching rows from both tables.

```sql
SELECT s.name, c.course_name
FROM Student s
INNER JOIN Enrollment e ON s.student_id = e.student_id
INNER JOIN Course c ON e.course_id = c.course_id;
```

#### LEFT JOIN
Returns all rows from left table + matching rows from right (NULL if no match).

```sql
SELECT s.name, e.course_id
FROM Student s
LEFT JOIN Enrollment e ON s.student_id = e.student_id;
```

#### RIGHT JOIN
Returns all rows from right table + matching rows from left (NULL if no match).

```sql
SELECT s.name, c.course_name
FROM Student s
RIGHT JOIN Course c ON s.student_id = c.student_id;
```

#### FULL OUTER JOIN
Returns all rows from both tables.

```sql
SELECT s.name, c.course_name
FROM Student s
FULL OUTER JOIN Course c ON s.student_id = c.student_id;
```

---

### 3. UNION vs UNION ALL

| UNION                    | UNION ALL              |
|--------------------------|------------------------|
| Removes duplicate rows   | Keeps all rows         |
| Comparatively slower     | Comparatively faster   |
| Performs DISTINCT        | No DISTINCT operation  |

**Example:**
```sql
-- UNION (removes duplicates)
SELECT name FROM Student
UNION
SELECT name FROM Teacher;

-- UNION ALL (keeps duplicates)
SELECT name FROM Student
UNION ALL
SELECT name FROM Teacher;
```

---

### 4. DELETE vs TRUNCATE vs DROP

| Command  | Description                    | Rollback | Speed  |
|----------|--------------------------------|----------|--------|
| DELETE   | Remove specific rows           | ✅ Yes   | Slow   |
| TRUNCATE | Remove all rows, keep structure| ❌ No    | Fast   |
| DROP     | Remove entire table            | ❌ No    | Fast   |

**Example:**
```sql
-- DELETE (specific rows, can rollback)
DELETE FROM Student WHERE age < 18;

-- TRUNCATE (all rows, cannot rollback)
TRUNCATE TABLE Student;

-- DROP (entire table, cannot rollback)
DROP TABLE Student;
```

---

### 5. GROUP BY vs ORDER BY

| GROUP BY                          | ORDER BY                     |
|-----------------------------------|------------------------------|
| Groups rows with same values      | Sorts result set             |
| Used with aggregate functions     | Used for sorting             |
| Reduces number of rows            | Doesn't reduce rows          |

**Example:**
```sql
-- GROUP BY
SELECT dept, COUNT(*) as count
FROM Student
GROUP BY dept;

-- ORDER BY
SELECT name, age
FROM Student
ORDER BY age DESC;
```

---

### 6. DISTINCT

Removes duplicate rows from result set.

**Example:**
```sql
SELECT DISTINCT city FROM Student;
```

---

### 7. Aggregate Functions

Perform calculations on a set of values and return a single value.

**Common Aggregate Functions:**
- **COUNT()** - Count rows
- **SUM()** - Sum values
- **AVG()** - Average value
- **MAX()** - Maximum value
- **MIN()** - Minimum value

**Example:**
```sql
SELECT
    COUNT(*) as total_students,
    AVG(age) as average_age,
    MAX(age) as oldest,
    MIN(age) as youngest
FROM Student;
```

---

### 8. Subquery

A query nested inside another query.

**Types:**
- **Scalar subquery** - Returns single value
- **Row subquery** - Returns single row
- **Table subquery** - Returns multiple rows

**Example:**
```sql
-- Scalar subquery
SELECT name
FROM Student
WHERE age > (SELECT AVG(age) FROM Student);

-- Correlated subquery
SELECT name
FROM Student s
WHERE age > (SELECT AVG(age) FROM Student WHERE dept = s.dept);
```

---

### 9. IN vs EXISTS

| IN                               | EXISTS                           |
|----------------------------------|----------------------------------|
| Compares value with list         | Checks if subquery returns rows  |
| Slower for large datasets        | Faster for large datasets        |
| Returns actual values            | Returns true/false               |

**Example:**
```sql
-- IN
SELECT name FROM Student WHERE dept_id IN (1, 2, 3);

-- EXISTS
SELECT name FROM Student s
WHERE EXISTS (SELECT 1 FROM Enrollment e WHERE e.student_id = s.student_id);
```

---

### 10. CHAR vs VARCHAR

| CHAR                          | VARCHAR                       |
|-------------------------------|-------------------------------|
| Fixed length                  | Variable length               |
| Padded with spaces            | No padding                    |
| Faster for fixed-size data    | More space-efficient          |
| Max 255 characters            | Max 65,535 characters         |

**Example:**
```sql
CREATE TABLE Example (
    code CHAR(5),        -- Always 5 characters
    name VARCHAR(100)    -- Up to 100 characters
);
```

---

## Advanced Topics

### Connection Pooling
Reuses database connections instead of creating new ones for each request.

**Benefits:**
- Faster connections
- Reduced overhead
- Better resource management

**Popular libraries:** HikariCP, C3P0, Apache DBCP

---

### Clustering
Multiple database servers working together as a single system.

**Benefits:**
- High availability
- Load balancing
- Fault tolerance

---

### Replication
Copying data from one database (master) to another (slave).

**Types:**
- **Master-Slave** - One master, multiple slaves
- **Master-Master** - Multiple masters

---

### Partitioning
Dividing large table into smaller, manageable pieces.

**Types:**
- **Horizontal** - Split by rows (sharding)
- **Vertical** - Split by columns

**Benefits:**
- Improved query performance
- Better manageability
- Parallel processing

---

## Summary

**Core Concepts:** PK, FK, UK, ACID, Normalization

**Relationships:** 1:1, 1:N, M:N, Junction tables

**Indexes:** Clustered, Non-clustered, Composite, Covering

**Queries:** WHERE, HAVING, JOINs, UNION, Subqueries

**Advanced:** Connection pooling, Clustering, Replication, Partitioning

---

This is your complete database reference guide for coding interviews! 🚀

---

## Transactions (Detailed)

### What is a Transaction?

**Definition:** A sequence of database operations that are treated as a single unit of work. Either all operations succeed, or none do.

**Example:**
```sql
-- Bank transfer transaction
START TRANSACTION;

UPDATE accounts SET balance = balance - 100 WHERE account_id = 1;
UPDATE accounts SET balance = balance + 100 WHERE account_id = 2;

COMMIT;  -- If both succeed
-- or
ROLLBACK;  -- If any fails
```

---

### Transaction States

```
Active → Partially Committed → Committed
  ↓
Failed → Aborted
```

1. **Active** - Transaction is executing
2. **Partially Committed** - Final operation executed
3. **Committed** - Transaction completed successfully
4. **Failed** - Error occurred
5. **Aborted** - Transaction rolled back

---

### Transaction Control Commands

**START TRANSACTION / BEGIN**
```sql
START TRANSACTION;
-- or
BEGIN;
```

**COMMIT** - Save all changes
```sql
COMMIT;
```

**ROLLBACK** - Undo all changes
```sql
ROLLBACK;
```

**SAVEPOINT** - Create checkpoint
```sql
SAVEPOINT sp1;
-- operations
ROLLBACK TO sp1;  -- Rollback to savepoint
```

---

### Isolation Levels

**Definition:** Degree to which transactions are isolated from each other.

| Isolation Level   | Dirty Read | Non-Repeatable Read | Phantom Read |
|-------------------|------------|---------------------|--------------|
| Read Uncommitted  | ✅ Yes     | ✅ Yes              | ✅ Yes       |
| Read Committed    | ❌ No      | ✅ Yes              | ✅ Yes       |
| Repeatable Read   | ❌ No      | ❌ No               | ✅ Yes       |
| Serializable      | ❌ No      | ❌ No               | ❌ No        |

**Dirty Read:** Reading uncommitted data
**Non-Repeatable Read:** Same query returns different results
**Phantom Read:** New rows appear in subsequent reads

---

### Concurrency Problems

**1. Dirty Read**
```sql
-- Transaction 1
UPDATE products SET price = 100 WHERE id = 1;
-- Not committed yet

-- Transaction 2
SELECT price FROM products WHERE id = 1;  -- Reads 100 (dirty data)

-- Transaction 1
ROLLBACK;  -- Price reverts to original
-- Transaction 2 read incorrect data!
```

**2. Lost Update**
```sql
-- Transaction 1
SELECT balance FROM accounts WHERE id = 1;  -- balance = 1000

-- Transaction 2
SELECT balance FROM accounts WHERE id = 1;  -- balance = 1000

-- Transaction 1
UPDATE accounts SET balance = 900 WHERE id = 1;  -- -100
COMMIT;

-- Transaction 2
UPDATE accounts SET balance = 1200 WHERE id = 1;  -- +200
COMMIT;

-- Final balance should be 1100, but it's 1200 (lost update)
```

---

## Locking Mechanisms

### Types of Locks

**1. Shared Lock (S-Lock / Read Lock)**
- Multiple transactions can hold shared locks
- Prevents write, allows read

```sql
SELECT * FROM products WHERE id = 1 FOR SHARE;
```

**2. Exclusive Lock (X-Lock / Write Lock)**
- Only one transaction can hold exclusive lock
- Prevents both read and write

```sql
SELECT * FROM products WHERE id = 1 FOR UPDATE;
```

---

### Locking Granularity

**1. Row-level Locking** - Lock specific rows
**2. Page-level Locking** - Lock pages (groups of rows)
**3. Table-level Locking** - Lock entire table

---

### Deadlock

**Definition:** Two or more transactions waiting for each other to release locks.

**Example:**
```sql
-- Transaction 1
BEGIN;
UPDATE accounts SET balance = 900 WHERE id = 1;  -- Locks row 1

-- Transaction 2
BEGIN;
UPDATE accounts SET balance = 1100 WHERE id = 2;  -- Locks row 2

-- Transaction 1
UPDATE accounts SET balance = 1100 WHERE id = 2;  -- Waits for row 2

-- Transaction 2
UPDATE accounts SET balance = 900 WHERE id = 1;  -- Waits for row 1

-- DEADLOCK! Both waiting for each other
```

**Deadlock Prevention:**
- Acquire locks in same order
- Use timeout
- Deadlock detection and rollback

---

## Query Optimization

### EXPLAIN Command

```sql
EXPLAIN SELECT * FROM users WHERE age > 25;

-- Shows:
-- - Execution plan
-- - Index usage
-- - Estimated rows
-- - Join type
```

---

### Optimization Techniques

**1. Use Indexes**
```sql
-- Without index - Slow
SELECT * FROM users WHERE email = 'user@example.com';

-- With index - Fast
CREATE INDEX idx_email ON users(email);
SELECT * FROM users WHERE email = 'user@example.com';
```

**2. Avoid SELECT \***
```sql
-- Bad
SELECT * FROM users;

-- Good - Select only needed columns
SELECT id, name, email FROM users;
```

**3. Use LIMIT**
```sql
-- Get only needed rows
SELECT * FROM users LIMIT 10;
```

**4. Use EXISTS instead of IN**
```sql
-- Slower
SELECT * FROM users WHERE id IN (SELECT user_id FROM orders);

-- Faster
SELECT * FROM users u WHERE EXISTS (SELECT 1 FROM orders o WHERE o.user_id = u.id);
```

**5. Avoid Functions in WHERE**
```sql
-- Bad - Index not used
SELECT * FROM users WHERE YEAR(created_at) = 2024;

-- Good - Index can be used
SELECT * FROM users WHERE created_at >= '2024-01-01' AND created_at < '2025-01-01';
```

**6. Use JOIN instead of Subquery**
```sql
-- Slower
SELECT * FROM users WHERE id IN (SELECT user_id FROM orders);

-- Faster
SELECT DISTINCT u.* FROM users u JOIN orders o ON u.id = o.user_id;
```

---

## Views

### What is a View?

**Definition:** Virtual table based on result of a SELECT query. Doesn't store data, only stores query.

**Creating View:**
```sql
CREATE VIEW active_users AS
SELECT id, name, email
FROM users
WHERE status = 'active';

-- Using view
SELECT * FROM active_users;
```

**Benefits:**
- Simplify complex queries
- Security (hide columns)
- Data abstraction
- Reusability

**Updating View:**
```sql
CREATE OR REPLACE VIEW active_users AS
SELECT id, name, email, phone
FROM users
WHERE status = 'active';
```

**Dropping View:**
```sql
DROP VIEW active_users;
```

---

## Stored Procedures

### What is a Stored Procedure?

**Definition:** Precompiled SQL code stored in database that can be executed repeatedly.

**Creating Stored Procedure:**
```sql
DELIMITER //

CREATE PROCEDURE GetUserOrders(IN userId INT)
BEGIN
    SELECT o.order_id, o.order_date, o.total
    FROM orders o
    WHERE o.user_id = userId
    ORDER BY o.order_date DESC;
END //

DELIMITER ;

-- Execute procedure
CALL GetUserOrders(123);
```

**With Parameters:**
```sql
DELIMITER //

CREATE PROCEDURE CreateUser(
    IN userName VARCHAR(100),
    IN userEmail VARCHAR(100),
    OUT userId INT
)
BEGIN
    INSERT INTO users (name, email) VALUES (userName, userEmail);
    SET userId = LAST_INSERT_ID();
END //

DELIMITER ;

-- Execute
CALL CreateUser('John Doe', 'john@example.com', @newUserId);
SELECT @newUserId;
```

**Benefits:**
- Precompiled (faster)
- Reduce network traffic
- Reusability
- Security
- Maintainability

---

## Triggers

### What is a Trigger?

**Definition:** Stored procedure that automatically executes when specific database event occurs.

**Types:**
- **BEFORE INSERT** - Before inserting row
- **AFTER INSERT** - After inserting row
- **BEFORE UPDATE** - Before updating row
- **AFTER UPDATE** - After updating row
- **BEFORE DELETE** - Before deleting row
- **AFTER DELETE** - After deleting row

**Example:**
```sql
-- Auto-update timestamp
CREATE TRIGGER update_timestamp
BEFORE UPDATE ON users
FOR EACH ROW
SET NEW.updated_at = NOW();

-- Audit log
CREATE TRIGGER user_delete_audit
AFTER DELETE ON users
FOR EACH ROW
INSERT INTO audit_log (action, user_id, timestamp)
VALUES ('DELETE', OLD.id, NOW());

-- Validate data
CREATE TRIGGER validate_age
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
    IF NEW.age < 0 OR NEW.age > 150 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Invalid age';
    END IF;
END;
```

---

## Advanced SQL Examples

### Window Functions

**ROW_NUMBER()**
```sql
SELECT 
    name,
    salary,
    ROW_NUMBER() OVER (ORDER BY salary DESC) as rank
FROM employees;
```

**RANK() and DENSE_RANK()**
```sql
SELECT 
    name,
    salary,
    RANK() OVER (ORDER BY salary DESC) as rank,
    DENSE_RANK() OVER (ORDER BY salary DESC) as dense_rank
FROM employees;
```

**Partition By**
```sql
SELECT 
    department,
    name,
    salary,
    RANK() OVER (PARTITION BY department ORDER BY salary DESC) as dept_rank
FROM employees;
```

---

### Common Table Expressions (CTE)

**Simple CTE:**
```sql
WITH high_earners AS (
    SELECT * FROM employees WHERE salary > 100000
)
SELECT * FROM high_earners WHERE department = 'IT';
```

**Recursive CTE:**
```sql
-- Generate numbers 1 to 10
WITH RECURSIVE numbers AS (
    SELECT 1 as n
    UNION ALL
    SELECT n + 1 FROM numbers WHERE n < 10
)
SELECT * FROM numbers;

-- Employee hierarchy
WITH RECURSIVE employee_hierarchy AS (
    SELECT id, name, manager_id, 1 as level
    FROM employees
    WHERE manager_id IS NULL
    
    UNION ALL
    
    SELECT e.id, e.name, e.manager_id, eh.level + 1
    FROM employees e
    JOIN employee_hierarchy eh ON e.manager_id = eh.id
)
SELECT * FROM employee_hierarchy;
```

---

### Pivot Tables

**Using CASE:**
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

## More Interview Questions

### Q11: What is the difference between RANK() and DENSE_RANK()?

**Answer:**
- **RANK()** - Leaves gaps in ranking (1, 2, 2, 4)
- **DENSE_RANK()** - No gaps in ranking (1, 2, 2, 3)

```sql
SELECT 
    name,
    score,
    RANK() OVER (ORDER BY score DESC) as rank,
    DENSE_RANK() OVER (ORDER BY score DESC) as dense_rank
FROM students;

-- Output:
-- Alice   95   1   1
-- Bob     90   2   2
-- Charlie 90   2   2
-- David   85   4   3  (RANK skips 3, DENSE_RANK doesn't)
```

---

### Q12: What is the difference between TRUNCATE and DELETE?

| DELETE                        | TRUNCATE                      |
|-------------------------------|-------------------------------|
| DML command                   | DDL command                   |
| Can have WHERE clause         | No WHERE clause               |
| Row by row deletion           | All rows at once              |
| Slower                        | Faster                        |
| Can be rolled back            | Cannot be rolled back         |
| Triggers are fired            | Triggers not fired            |
| Maintains identity            | Resets identity               |

---

### Q13: What is the difference between WHERE and HAVING?

```sql
-- WHERE - Filters rows before grouping
SELECT department, COUNT(*)
FROM employees
WHERE salary > 50000
GROUP BY department;

-- HAVING - Filters groups after grouping
SELECT department, COUNT(*)
FROM employees
GROUP BY department
HAVING COUNT(*) > 5;

-- Both together
SELECT department, AVG(salary) as avg_salary
FROM employees
WHERE status = 'active'
GROUP BY department
HAVING AVG(salary) > 60000;
```

---

### Q14: What is a Self Join?

**Answer:** Join a table with itself.

```sql
-- Find employees and their managers
SELECT 
    e.name as employee,
    m.name as manager
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.id;

-- Find employees in same department
SELECT 
    e1.name as employee1,
    e2.name as employee2,
    e1.department
FROM employees e1
JOIN employees e2 ON e1.department = e2.department
WHERE e1.id < e2.id;  -- Avoid duplicates
```

---

### Q15: What is the N+1 Query Problem?

**Answer:** Executing 1 query to fetch N records, then N additional queries to fetch related data.

**Bad (N+1):**
```sql
-- 1 query to get users
SELECT * FROM users;  -- Returns 100 users

-- Then 100 queries for orders
SELECT * FROM orders WHERE user_id = 1;
SELECT * FROM orders WHERE user_id = 2;
...
SELECT * FROM orders WHERE user_id = 100;
```

**Good (2 queries):**
```sql
-- 1 query to get users
SELECT * FROM users;

-- 1 query to get all orders
SELECT * FROM orders WHERE user_id IN (1, 2, ..., 100);
```

**Best (1 query with JOIN):**
```sql
SELECT u.*, o.*
FROM users u
LEFT JOIN orders o ON u.id = o.user_id;
```

---

### Q16: How to find duplicate records?

```sql
-- Find duplicate emails
SELECT email, COUNT(*)
FROM users
GROUP BY email
HAVING COUNT(*) > 1;

-- Get all duplicate rows
SELECT *
FROM users
WHERE email IN (
    SELECT email
    FROM users
    GROUP BY email
    HAVING COUNT(*) > 1
);
```

---

### Q17: How to find second highest salary?

```sql
-- Method 1: Using LIMIT OFFSET
SELECT DISTINCT salary
FROM employees
ORDER BY salary DESC
LIMIT 1 OFFSET 1;

-- Method 2: Using subquery
SELECT MAX(salary)
FROM employees
WHERE salary < (SELECT MAX(salary) FROM employees);

-- Method 3: Using DENSE_RANK
SELECT salary
FROM (
    SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) as rank
    FROM employees
) ranked
WHERE rank = 2;
```

---

### Q18: How to delete duplicate rows keeping one?

```sql
-- Keep row with lowest ID
DELETE e1
FROM employees e1
JOIN employees e2
WHERE e1.email = e2.email
AND e1.id > e2.id;

-- Or using window function
WITH cte AS (
    SELECT *,
           ROW_NUMBER() OVER (PARTITION BY email ORDER BY id) as rn
    FROM employees
)
DELETE FROM cte WHERE rn > 1;
```

---

## Query Performance Tips

### Do's ✅

1. **Use indexes wisely**
2. **Select only needed columns**
3. **Use LIMIT for pagination**
4. **Use appropriate data types**
5. **Normalize database**
6. **Use connection pooling**
7. **Cache frequent queries**
8. **Use EXPLAIN to analyze queries**
9. **Batch inserts instead of individual**
10. **Use prepared statements**

### Don'ts ❌

1. **Don't use SELECT \***
2. **Don't use functions in WHERE clause**
3. **Don't use LIKE with leading wildcard (%search)**
4. **Don't use OR in WHERE (use IN instead)**
5. **Don't create too many indexes**
6. **Don't forget to close connections**
7. **Don't store large BLOBs in database**
8. **Don't use subqueries when JOIN is possible**
9. **Don't forget to handle NULL values**
10. **Don't use cursors when set-based operations possible**

---

---

## Advanced Interview Questions

### Q19: What is database sharding and when to use it?

**Answer:**
Sharding is horizontal partitioning where data is distributed across multiple database servers.

**Types:**
1. **Range-based sharding** - Data divided by ranges (e.g., user IDs 1-1000, 1001-2000)
2. **Hash-based sharding** - Hash function determines shard (e.g., user_id % 4)
3. **Geographic sharding** - Data divided by location
4. **Directory-based sharding** - Lookup table maintains shard mapping

**Example:**
```
Without Sharding:
┌─────────────────────┐
│   Single Database   │
│  - All 10M users    │
└─────────────────────┘

With Sharding:
┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐
│ Shard 1  │ │ Shard 2  │ │ Shard 3  │ │ Shard 4  │
│ Users    │ │ Users    │ │ Users    │ │ Users    │
│ 1-2.5M   │ │ 2.5-5M   │ │ 5-7.5M   │ │ 7.5-10M  │
└──────────┘ └──────────┘ └──────────┘ └──────────┘
```

**Pros:**
- Improved performance
- Horizontal scalability
- Fault isolation

**Cons:**
- Complex implementation
- Cross-shard joins difficult
- Data rebalancing complexity

**When to use:**
- Massive datasets (> 100GB)
- High write throughput needed
- Geographic distribution required

---

### Q20: What is database replication and its types?

**Answer:**
Replication is copying data from master to one or more replica databases.

**Types:**

**1. Master-Slave (Primary-Replica):**
```
        ┌────────┐
        │ Master │ (Writes)
        └────┬───┘
         ┌───┴────┬─────────┐
         │        │         │
     ┌───▼───┐ ┌─▼────┐ ┌──▼────┐
     │Slave 1│ │Slave 2│ │Slave 3│ (Reads)
     └───────┘ └───────┘ └───────┘
```

**2. Master-Master (Multi-Primary):**
```
     ┌────────┐ ◄───► ┌────────┐
     │Master 1│       │Master 2│
     └────────┘       └────────┘
     (Both read/write)
```

**Replication Types:**
- **Synchronous** - Wait for replica confirmation (slower, consistent)
- **Asynchronous** - Don't wait (faster, eventual consistency)
- **Semi-synchronous** - Wait for at least one replica

**Benefits:**
- Read scalability
- High availability
- Disaster recovery
- Geographic distribution

**Example configuration (MySQL):**
```sql
-- On Master
CREATE USER 'replica'@'%' IDENTIFIED BY 'password';
GRANT REPLICATION SLAVE ON *.* TO 'replica'@'%';

-- On Slave
CHANGE MASTER TO
    MASTER_HOST='master_ip',
    MASTER_USER='replica',
    MASTER_PASSWORD='password',
    MASTER_LOG_FILE='mysql-bin.000001',
    MASTER_LOG_POS=107;

START SLAVE;
```

---

### Q21: What is CAP theorem?

**Answer:**
CAP theorem states that a distributed database can only guarantee 2 out of 3:

1. **Consistency** - All nodes see same data
2. **Availability** - Every request gets response
3. **Partition Tolerance** - System works despite network failures

**Trade-offs:**

**CA (Consistency + Availability):**
- Traditional relational databases (single server)
- Example: PostgreSQL, MySQL (single instance)

**CP (Consistency + Partition Tolerance):**
- Sacrifice availability for consistency
- Example: MongoDB, HBase, Redis

**AP (Availability + Partition Tolerance):**
- Sacrifice consistency for availability
- Example: Cassandra, DynamoDB, CouchDB

**Example scenario:**
```
User updates balance: $100 → $150

CP System (MongoDB):
- Update waits for all nodes to agree
- If network partition, some nodes reject updates
- ✅ Consistent
- ❌ Not always available

AP System (Cassandra):
- Update succeeds even with network partition
- Nodes may have different values temporarily
- ✅ Always available
- ❌ Eventually consistent
```

---

### Q22: What is connection pooling and why is it important?

**Answer:**
Connection pooling maintains a pool of database connections that can be reused.

**Without pooling:**
```java
// Create connection (expensive - 50-100ms)
Connection conn = DriverManager.getConnection(url, user, password);

// Use connection
// ...

// Close connection
conn.close();

// Next request: Create connection again (expensive!)
```

**With pooling:**
```java
// Get connection from pool (fast - 1ms)
Connection conn = dataSource.getConnection();

// Use connection
// ...

// Return to pool (not closed)
conn.close();

// Next request: Reuse from pool (fast!)
```

**Benefits:**
- Faster response times
- Reduced resource usage
- Better scalability
- Connection limit control

**Popular libraries:**
- HikariCP (fastest)
- Apache Commons DBCP
- C3P0

**Configuration example:**
```properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.idle-timeout=300000
spring.datasource.hikari.max-lifetime=1200000
```

---

### Q23: Explain ACID properties with examples.

**Answer:**

**A - Atomicity:**
All operations succeed or all fail (no partial updates).

```sql
START TRANSACTION;

UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;

COMMIT;  -- Both succeed
-- OR
ROLLBACK;  -- Both fail
```

**C - Consistency:**
Database remains in valid state before and after transaction.

```sql
-- Constraint: balance >= 0
START TRANSACTION;

UPDATE accounts SET balance = balance - 200 WHERE id = 1;
-- If balance becomes negative, transaction fails
-- Database stays consistent

ROLLBACK;
```

**I - Isolation:**
Concurrent transactions don't interfere with each other.

```sql
-- Transaction 1
START TRANSACTION;
SELECT balance FROM accounts WHERE id = 1;  -- Reads 1000
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
COMMIT;

-- Transaction 2 (concurrent)
START TRANSACTION;
SELECT balance FROM accounts WHERE id = 1;  -- May read 1000 or 900 depending on isolation level
COMMIT;
```

**D - Durability:**
Committed changes persist even after system failure.

```sql
START TRANSACTION;
INSERT INTO orders VALUES (1, 'Product', 100);
COMMIT;

-- System crash here

-- After restart, order is still there
SELECT * FROM orders WHERE id = 1;  -- Found
```

---

### Q24: What are isolation levels and their problems?

**Answer:**

**Isolation Levels:**
1. **READ UNCOMMITTED** - Can read uncommitted data
2. **READ COMMITTED** - Can read only committed data
3. **REPEATABLE READ** - Same query returns same results
4. **SERIALIZABLE** - Full isolation (slowest)

**Problems:**

**1. Dirty Read:**
Reading uncommitted data that gets rolled back.

```sql
-- Transaction 1
UPDATE accounts SET balance = 1000 WHERE id = 1;
-- Not committed yet

-- Transaction 2 (READ UNCOMMITTED)
SELECT balance FROM accounts WHERE id = 1;  -- Reads 1000

-- Transaction 1
ROLLBACK;  -- Oops! Transaction 2 read wrong data
```

**2. Non-Repeatable Read:**
Same query returns different results.

```sql
-- Transaction 1
SELECT balance FROM accounts WHERE id = 1;  -- Reads 1000

-- Transaction 2
UPDATE accounts SET balance = 1500 WHERE id = 1;
COMMIT;

-- Transaction 1
SELECT balance FROM accounts WHERE id = 1;  -- Reads 1500 (different!)
```

**3. Phantom Read:**
New rows appear in subsequent queries.

```sql
-- Transaction 1
SELECT COUNT(*) FROM orders WHERE status = 'pending';  -- Returns 10

-- Transaction 2
INSERT INTO orders VALUES (11, 'pending');
COMMIT;

-- Transaction 1
SELECT COUNT(*) FROM orders WHERE status = 'pending';  -- Returns 11 (phantom row!)
```

**Isolation Level Matrix:**

| Level | Dirty Read | Non-Repeatable Read | Phantom Read |
|-------|------------|---------------------|--------------|
| READ UNCOMMITTED | ✅ | ✅ | ✅ |
| READ COMMITTED | ❌ | ✅ | ✅ |
| REPEATABLE READ | ❌ | ❌ | ✅ |
| SERIALIZABLE | ❌ | ❌ | ❌ |

**Setting isolation level:**
```sql
-- MySQL
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;

-- PostgreSQL
BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
```

---

### Q25: How to optimize a slow query?

**Answer:**

**Step-by-step approach:**

**1. Use EXPLAIN to analyze:**
```sql
EXPLAIN SELECT *
FROM orders o
JOIN customers c ON o.customer_id = c.id
WHERE o.created_at > '2024-01-01'
AND c.country = 'USA';
```

**2. Check for:**
- Full table scan (type = ALL)
- Missing indexes
- Large row counts
- filesort or temporary table

**3. Common optimizations:**

**Add indexes:**
```sql
CREATE INDEX idx_orders_created ON orders(created_at);
CREATE INDEX idx_customers_country ON customers(country);
```

**Select only needed columns:**
```sql
-- Bad
SELECT * FROM orders;

-- Good
SELECT id, order_date, amount FROM orders;
```

**Use LIMIT:**
```sql
SELECT * FROM orders
ORDER BY created_at DESC
LIMIT 10;
```

**Avoid functions in WHERE:**
```sql
-- Bad (index not used)
SELECT * FROM orders WHERE YEAR(created_at) = 2024;

-- Good (index used)
SELECT * FROM orders
WHERE created_at >= '2024-01-01'
AND created_at < '2025-01-01';
```

**Use JOIN instead of subquery:**
```sql
-- Bad (slow subquery)
SELECT * FROM orders
WHERE customer_id IN (SELECT id FROM customers WHERE country = 'USA');

-- Good (faster join)
SELECT o.* FROM orders o
JOIN customers c ON o.customer_id = c.id
WHERE c.country = 'USA';
```

**Use EXISTS instead of IN:**
```sql
-- Slower for large datasets
SELECT * FROM customers
WHERE id IN (SELECT customer_id FROM orders);

-- Faster
SELECT * FROM customers c
WHERE EXISTS (SELECT 1 FROM orders o WHERE o.customer_id = c.id);
```

**Denormalize if needed:**
```sql
-- Instead of joining every time
SELECT o.*, c.name FROM orders o
JOIN customers c ON o.customer_id = c.id;

-- Add customer_name to orders table
ALTER TABLE orders ADD COLUMN customer_name VARCHAR(100);
UPDATE orders o SET customer_name = (SELECT name FROM customers WHERE id = o.customer_id);
```

---

### Q26: What is database indexing and types?

**Answer:**
Index is a data structure that improves query performance.

**Index Types:**

**1. Clustered Index:**
- Determines physical order of data
- One per table
- Primary key creates clustered index

```sql
CREATE TABLE users (
    id INT PRIMARY KEY,  -- Clustered index
    name VARCHAR(100)
);
```

**2. Non-Clustered Index:**
- Separate structure with pointers to data
- Multiple per table

```sql
CREATE INDEX idx_name ON users(name);
```

**3. Unique Index:**
- Ensures uniqueness

```sql
CREATE UNIQUE INDEX idx_email ON users(email);
```

**4. Composite Index:**
- Index on multiple columns

```sql
CREATE INDEX idx_name_age ON users(name, age);

-- Used by queries:
SELECT * FROM users WHERE name = 'John' AND age = 25;  -- ✅ Uses index
SELECT * FROM users WHERE name = 'John';  -- ✅ Uses index (leftmost prefix)
SELECT * FROM users WHERE age = 25;  -- ❌ Doesn't use index (not leftmost)
```

**5. Covering Index:**
- Index contains all columns needed by query

```sql
CREATE INDEX idx_covering ON orders(customer_id, order_date, amount);

-- Query doesn't need to access table
SELECT customer_id, order_date, amount
FROM orders
WHERE customer_id = 123;  -- All columns in index!
```

**6. Full-Text Index:**
- For text search

```sql
CREATE FULLTEXT INDEX idx_description ON products(description);

SELECT * FROM products
WHERE MATCH(description) AGAINST('wireless mouse');
```

**When to use indexes:**
- ✅ Frequently searched columns (WHERE, JOIN)
- ✅ Columns used in ORDER BY, GROUP BY
- ✅ Foreign key columns

**When NOT to use:**
- ❌ Small tables
- ❌ Frequently updated columns
- ❌ Columns with low cardinality (e.g., boolean)
- ❌ Too many indexes (slows down writes)

---

### Q27: What is normalization and denormalization trade-offs?

**Answer:**

**Normalization:**
- Organizing data to reduce redundancy
- Faster writes, slower reads
- Less storage, more joins

**Denormalization:**
- Adding redundancy for performance
- Slower writes, faster reads
- More storage, fewer joins

**Example:**

**Normalized (3NF):**
```sql
-- Orders table
CREATE TABLE orders (
    id INT PRIMARY KEY,
    customer_id INT,
    order_date DATE,
    amount DECIMAL
);

-- Customers table
CREATE TABLE customers (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    country VARCHAR(50)
);

-- Query (needs JOIN)
SELECT o.id, o.amount, c.name, c.email
FROM orders o
JOIN customers c ON o.customer_id = c.id;
```

**Denormalized:**
```sql
-- Orders table (with customer data)
CREATE TABLE orders (
    id INT PRIMARY KEY,
    customer_id INT,
    customer_name VARCHAR(100),  -- Denormalized
    customer_email VARCHAR(100), -- Denormalized
    order_date DATE,
    amount DECIMAL
);

-- Query (no JOIN needed)
SELECT id, amount, customer_name, customer_email
FROM orders;
```

**When to denormalize:**
- ✅ Read-heavy applications
- ✅ Performance is critical
- ✅ Data doesn't change often
- ✅ Complex queries with many joins

**When to stay normalized:**
- ✅ Write-heavy applications
- ✅ Data changes frequently
- ✅ Data consistency is critical
- ✅ Storage is limited

---

### Q28: How to handle database migrations?

**Answer:**
Database migrations are versioned changes to database schema.

**Best practices:**

**1. Version control migrations:**
```
migrations/
  ├── V1__create_users_table.sql
  ├── V2__add_email_to_users.sql
  ├── V3__create_orders_table.sql
  └── V4__add_index_to_orders.sql
```

**2. Always use UP and DOWN:**
```sql
-- V2__add_email_to_users.sql (UP)
ALTER TABLE users ADD COLUMN email VARCHAR(100);

-- V2__add_email_to_users_rollback.sql (DOWN)
ALTER TABLE users DROP COLUMN email;
```

**3. Never modify existing migrations:**
```
❌ Bad: Edit V2__add_email_to_users.sql

✅ Good: Create V5__modify_email_column.sql
```

**4. Test migrations:**
```bash
# Test on dev/staging first
flyway migrate -url=jdbc:postgresql://staging/mydb

# Then production
flyway migrate -url=jdbc:postgresql://prod/mydb
```

**5. Handle data migrations:**
```sql
-- V6__migrate_user_data.sql
UPDATE users
SET status = 'active'
WHERE last_login > DATE_SUB(NOW(), INTERVAL 30 DAY);

UPDATE users
SET status = 'inactive'
WHERE last_login <= DATE_SUB(NOW(), INTERVAL 30 DAY);
```

**Tools:**
- Flyway
- Liquibase
- Django migrations
- Rails migrations

---

### Q29: What are database design anti-patterns?

**Answer:**

**1. EAV (Entity-Attribute-Value) Anti-pattern:**
```sql
-- Anti-pattern: Generic attributes table
CREATE TABLE attributes (
    entity_id INT,
    attribute_name VARCHAR(50),
    attribute_value TEXT
);

INSERT INTO attributes VALUES (1, 'name', 'John');
INSERT INTO attributes VALUES (1, 'email', 'john@example.com');
INSERT INTO attributes VALUES (1, 'age', '30');

-- Problem: No type safety, complex queries, no constraints
```

**Better approach:**
```sql
CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    age INT CHECK (age >= 0 AND age <= 150)
);
```

**2. Polymorphic Associations:**
```sql
-- Anti-pattern: Comments can belong to multiple entities
CREATE TABLE comments (
    id INT PRIMARY KEY,
    commentable_id INT,
    commentable_type VARCHAR(50),  -- 'Post' or 'Photo'
    content TEXT
);

-- Problem: Can't use foreign keys, integrity issues
```

**Better approach:**
```sql
CREATE TABLE post_comments (
    id INT PRIMARY KEY,
    post_id INT REFERENCES posts(id),
    content TEXT
);

CREATE TABLE photo_comments (
    id INT PRIMARY KEY,
    photo_id INT REFERENCES photos(id),
    content TEXT
);
```

**3. ENUM abuse:**
```sql
-- Anti-pattern
CREATE TABLE orders (
    id INT PRIMARY KEY,
    status ENUM('pending', 'processing', 'shipped', 'delivered')
);

-- Problem: Hard to add new status, requires schema change
```

**Better approach:**
```sql
CREATE TABLE order_statuses (
    id INT PRIMARY KEY,
    name VARCHAR(50) UNIQUE
);

CREATE TABLE orders (
    id INT PRIMARY KEY,
    status_id INT REFERENCES order_statuses(id)
);
```

**4. UUID as string:**
```sql
-- Anti-pattern
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY  -- '550e8400-e29b-41d4-a716-446655440000'
);

-- Problem: Large storage (36 bytes), slower comparison
```

**Better approach:**
```sql
-- MySQL
CREATE TABLE users (
    id BINARY(16) PRIMARY KEY  -- Only 16 bytes
);

-- PostgreSQL
CREATE TABLE users (
    id UUID PRIMARY KEY
);
```

---

### Q30: How to design a scalable database schema?

**Answer:**

**Principles:**

**1. Use appropriate data types:**
```sql
-- Bad
CREATE TABLE users (
    id VARCHAR(255),  -- Overkill
    age VARCHAR(10),  -- Should be INT
    balance VARCHAR(20),  -- Should be DECIMAL
    active VARCHAR(5)  -- Should be BOOLEAN
);

-- Good
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    age TINYINT UNSIGNED,  -- 0-255
    balance DECIMAL(10, 2),
    active BOOLEAN DEFAULT TRUE
);
```

**2. Normalize until it hurts, denormalize until it works:**
```sql
-- Start normalized
CREATE TABLE orders (
    id INT PRIMARY KEY,
    customer_id INT REFERENCES customers(id),
    product_id INT REFERENCES products(id)
);

-- Denormalize frequently accessed data
ALTER TABLE orders
ADD COLUMN customer_name VARCHAR(100),
ADD COLUMN product_name VARCHAR(100),
ADD COLUMN product_price DECIMAL(10, 2);
```

**3. Use partitioning for large tables:**
```sql
-- Range partitioning by date
CREATE TABLE orders (
    id INT,
    order_date DATE,
    amount DECIMAL
)
PARTITION BY RANGE (YEAR(order_date)) (
    PARTITION p2022 VALUES LESS THAN (2023),
    PARTITION p2023 VALUES LESS THAN (2024),
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);

-- Hash partitioning
CREATE TABLE users (
    id INT,
    name VARCHAR(100)
)
PARTITION BY HASH(id)
PARTITIONS 4;
```

**4. Use soft deletes:**
```sql
CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    deleted_at TIMESTAMP NULL,
    INDEX idx_deleted (deleted_at)
);

-- Soft delete
UPDATE users SET deleted_at = NOW() WHERE id = 1;

-- Query active users
SELECT * FROM users WHERE deleted_at IS NULL;
```

**5. Add audit columns:**
```sql
CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by INT REFERENCES users(id),
    updated_by INT REFERENCES users(id)
);
```

**6. Use UUIDs for distributed systems:**
```sql
CREATE TABLE users (
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    name VARCHAR(100)
);

-- No collision across multiple databases
```

---

This comprehensive database guide now covers 30+ interview questions from basic to advanced for mid-level Software Engineer preparation! 🎯

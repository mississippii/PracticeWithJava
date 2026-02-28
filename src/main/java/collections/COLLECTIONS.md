# Java Collections Framework - Complete Guide

> **Collections Framework** = A unified architecture for storing and manipulating groups of objects.

---

## Collection Hierarchy

```
                         Iterable
                            │
                        Collection
                ┌───────────┼───────────┐
               List        Set        Queue
                │           │           │
         ┌──────┼──────┐    │     ┌─────┼─────┐
     ArrayList  │  Vector   │   PriorityQueue │
            LinkedList      │              Deque
                     ┌──────┼──────┐         │
                  HashSet   │   TreeSet   ArrayDeque
                            │              LinkedList
                     LinkedHashSet


                          Map (separate hierarchy)
                ┌───────────┼───────────┐
            HashMap    TreeMap    LinkedHashMap
                │
           Hashtable
                │
         ConcurrentHashMap
```

---

## Quick Reference Table

| Collection | Ordering | Duplicates | Null | Thread-Safe | Best For |
|------------|----------|------------|------|-------------|----------|
| **ArrayList** | Insertion order | ✅ Yes | ✅ Yes | ❌ No | Random access, iteration |
| **LinkedList** | Insertion order | ✅ Yes | ✅ Yes | ❌ No | Frequent add/remove at ends |
| **Vector** | Insertion order | ✅ Yes | ✅ Yes | ✅ Yes | Legacy, avoid in new code |
| **HashSet** | No order | ❌ No | ✅ One null | ❌ No | Fast lookup, uniqueness |
| **LinkedHashSet** | Insertion order | ❌ No | ✅ One null | ❌ No | Ordered unique elements |
| **TreeSet** | Sorted | ❌ No | ❌ No | ❌ No | Sorted unique elements |
| **HashMap** | No order | Keys: No, Values: Yes | ✅ One null key | ❌ No | Fast key-value lookup |
| **LinkedHashMap** | Insertion/Access order | Keys: No, Values: Yes | ✅ One null key | ❌ No | Ordered key-value pairs |
| **TreeMap** | Sorted by key | Keys: No, Values: Yes | ❌ No null key | ❌ No | Sorted key-value pairs |
| **Hashtable** | No order | Keys: No, Values: Yes | ❌ No | ✅ Yes | Legacy, use ConcurrentHashMap |
| **PriorityQueue** | Priority (natural/comparator) | ✅ Yes | ❌ No | ❌ No | Priority-based processing |
| **ArrayDeque** | Insertion order | ✅ Yes | ❌ No | ❌ No | Stack/Queue operations |

---

## Time Complexity Comparison

### List Implementations

| Operation | ArrayList | LinkedList | Vector |
|-----------|-----------|------------|--------|
| `get(index)` | **O(1)** | O(n) | O(1) |
| `add(element)` | O(1)* | **O(1)** | O(1)* |
| `add(index, element)` | O(n) | O(n)† | O(n) |
| `remove(index)` | O(n) | O(n)† | O(n) |
| `remove(object)` | O(n) | O(n) | O(n) |
| `contains(object)` | O(n) | O(n) | O(n) |
| `iterator.remove()` | O(n) | **O(1)** | O(n) |

*\* Amortized O(1), O(n) when resizing*
*† O(1) if at head/tail, O(n) to find position*

---

### Set Implementations

| Operation | HashSet | LinkedHashSet | TreeSet |
|-----------|---------|---------------|---------|
| `add(element)` | **O(1)** | **O(1)** | O(log n) |
| `remove(element)` | **O(1)** | **O(1)** | O(log n) |
| `contains(element)` | **O(1)** | **O(1)** | O(log n) |
| `iterator.next()` | O(h/n)* | **O(1)** | O(log n) |
| `first() / last()` | N/A | N/A | **O(1)** |

*\* h = table capacity*

---

### Map Implementations

| Operation | HashMap | LinkedHashMap | TreeMap | Hashtable |
|-----------|---------|---------------|---------|-----------|
| `put(key, value)` | **O(1)** | **O(1)** | O(log n) | O(1) |
| `get(key)` | **O(1)** | **O(1)** | O(log n) | O(1) |
| `remove(key)` | **O(1)** | **O(1)** | O(log n) | O(1) |
| `containsKey(key)` | **O(1)** | **O(1)** | O(log n) | O(1) |
| `containsValue(value)` | O(n) | O(n) | O(n) | O(n) |

---

### Queue/Deque Implementations

| Operation | PriorityQueue | ArrayDeque | LinkedList |
|-----------|---------------|------------|------------|
| `offer(element)` | O(log n) | **O(1)** | **O(1)** |
| `poll()` | O(log n) | **O(1)** | **O(1)** |
| `peek()` | **O(1)** | **O(1)** | **O(1)** |
| `addFirst()` | N/A | **O(1)** | **O(1)** |
| `addLast()` | N/A | **O(1)** | **O(1)** |
| `removeFirst()` | N/A | **O(1)** | **O(1)** |
| `removeLast()` | N/A | **O(1)** | **O(1)** |

---

# List Interface

## When to Use Which List?

```
Need a List?
├─ Frequent random access (get by index)?
│   └─ YES → ArrayList
│
├─ Frequent add/remove at beginning?
│   └─ YES → LinkedList
│
├─ Need thread safety?
│   ├─ YES → Collections.synchronizedList() or CopyOnWriteArrayList
│   └─ NO → ArrayList (most common choice)
│
└─ Unsure?
    └─ ArrayList (default choice)
```

---

## ArrayList

**What:** Resizable array implementation.

**Use When:**
- ✅ Frequent random access by index
- ✅ Mostly reading, less writing
- ✅ Iteration over elements
- ✅ Adding at the end

**Avoid When:**
- ❌ Frequent insertions/deletions in middle
- ❌ Frequent add/remove at beginning

```java
// Creation
List<String> list = new ArrayList<>();
List<String> listWithCapacity = new ArrayList<>(100);  // Initial capacity

// Common Operations
list.add("Apple");                    // Add at end - O(1)
list.add(0, "Banana");               // Add at index - O(n)
list.get(0);                          // Get by index - O(1)
list.set(0, "Cherry");               // Update by index - O(1)
list.remove(0);                       // Remove by index - O(n)
list.remove("Apple");                 // Remove by value - O(n)
list.contains("Apple");               // Check existence - O(n)
list.indexOf("Apple");                // Find index - O(n)
list.size();                          // Get size - O(1)
list.isEmpty();                       // Check empty - O(1)
list.clear();                         // Remove all - O(n)

// Iteration
for (String item : list) { }          // For-each
list.forEach(System.out::println);    // Lambda
list.stream().filter(...).collect();  // Stream API
```

**Internal Working:**
- Default initial capacity: 10
- Grows by 50% when full: `newCapacity = oldCapacity + (oldCapacity >> 1)`
- Uses `System.arraycopy()` for shifting elements

---

## LinkedList

**What:** Doubly-linked list implementation. Also implements `Deque`.

**Use When:**
- ✅ Frequent add/remove at beginning or end
- ✅ Implementing Stack or Queue
- ✅ Don't need random access
- ✅ Frequent iteration with removal

**Avoid When:**
- ❌ Frequent random access by index
- ❌ Memory is constrained (extra overhead for node pointers)

```java
// Creation
LinkedList<String> list = new LinkedList<>();

// List Operations (same as ArrayList)
list.add("Apple");
list.get(0);                          // O(n) - slower than ArrayList!

// Deque Operations (efficient)
list.addFirst("First");               // O(1)
list.addLast("Last");                 // O(1)
list.removeFirst();                   // O(1)
list.removeLast();                    // O(1)
list.getFirst();                      // O(1)
list.getLast();                       // O(1)

// Stack Operations
list.push("Element");                 // Same as addFirst - O(1)
list.pop();                           // Same as removeFirst - O(1)
list.peek();                          // Same as getFirst - O(1)

// Queue Operations
list.offer("Element");                // Same as addLast - O(1)
list.poll();                          // Same as removeFirst - O(1)
```

**Memory:**
- Each node stores: `data + prev pointer + next pointer`
- More memory than ArrayList for same data

---

## ArrayList vs LinkedList

| Scenario | Winner | Why |
|----------|--------|-----|
| Random access `get(i)` | **ArrayList** | O(1) vs O(n) |
| Add at end | **Tie** | Both O(1) |
| Add at beginning | **LinkedList** | O(1) vs O(n) |
| Add in middle | **ArrayList** | Cache locality advantage |
| Remove from middle | **Tie** | Both O(n) |
| Memory usage | **ArrayList** | No pointer overhead |
| Iterator remove | **LinkedList** | O(1) vs O(n) |

**Rule of Thumb:** Use `ArrayList` unless you have a specific reason for `LinkedList`.

---

# Set Interface

## When to Use Which Set?

```
Need unique elements?
├─ Need sorted order?
│   └─ YES → TreeSet
│
├─ Need insertion order?
│   └─ YES → LinkedHashSet
│
├─ Just need uniqueness + fast lookup?
│   └─ YES → HashSet (default choice)
│
└─ Need thread safety?
    └─ YES → ConcurrentSkipListSet or Collections.synchronizedSet()
```

---

## HashSet

**What:** Hash table implementation, no ordering guarantee.

**Use When:**
- ✅ Need unique elements
- ✅ Fast add/remove/contains operations
- ✅ Order doesn't matter

**Avoid When:**
- ❌ Need sorted elements
- ❌ Need to maintain insertion order

```java
// Creation
Set<String> set = new HashSet<>();
Set<String> setWithCapacity = new HashSet<>(100);         // Initial capacity
Set<String> setWithLoadFactor = new HashSet<>(100, 0.75f); // Capacity + load factor

// Operations
set.add("Apple");                     // Add - O(1)
set.remove("Apple");                  // Remove - O(1)
set.contains("Apple");                // Check - O(1)
set.size();                           // Size - O(1)
set.isEmpty();                        // Is empty - O(1)
set.clear();                          // Clear all - O(n)

// Bulk Operations
set.addAll(anotherSet);               // Union
set.retainAll(anotherSet);            // Intersection
set.removeAll(anotherSet);            // Difference

// Convert to Array
String[] array = set.toArray(new String[0]);
```

**Internal Working:**
- Backed by `HashMap` (elements stored as keys, dummy value)
- Default capacity: 16
- Default load factor: 0.75
- Rehashes when `size > capacity * loadFactor`

---

## LinkedHashSet

**What:** Hash table + linked list, maintains insertion order.

**Use When:**
- ✅ Need unique elements
- ✅ Need to maintain insertion order
- ✅ Need predictable iteration order

**Avoid When:**
- ❌ Memory is very constrained
- ❌ Don't care about order (use HashSet)

```java
Set<String> set = new LinkedHashSet<>();

set.add("Banana");
set.add("Apple");
set.add("Cherry");

// Iteration preserves insertion order
for (String item : set) {
    System.out.println(item);  // Banana, Apple, Cherry
}
```

**Overhead:** Slightly more memory and time than HashSet due to linked list maintenance.

---

## TreeSet

**What:** Red-Black tree implementation, elements sorted.

**Use When:**
- ✅ Need sorted unique elements
- ✅ Need range operations (subSet, headSet, tailSet)
- ✅ Need first/last element quickly

**Avoid When:**
- ❌ Elements don't implement `Comparable` (or no Comparator provided)
- ❌ Need O(1) operations (TreeSet is O(log n))
- ❌ Need to store null

```java
// Creation
Set<Integer> set = new TreeSet<>();                      // Natural ordering
Set<String> customSet = new TreeSet<>(Comparator.reverseOrder()); // Custom comparator

// Operations (all O(log n))
set.add(5);
set.remove(5);
set.contains(5);

// NavigableSet Operations
TreeSet<Integer> treeSet = new TreeSet<>(Arrays.asList(1, 3, 5, 7, 9));

treeSet.first();                      // 1 - smallest
treeSet.last();                       // 9 - largest
treeSet.lower(5);                     // 3 - largest element < 5
treeSet.higher(5);                    // 7 - smallest element > 5
treeSet.floor(6);                     // 5 - largest element <= 6
treeSet.ceiling(6);                   // 7 - smallest element >= 6

// Range Views
treeSet.subSet(3, 7);                 // [3, 5] - elements from 3 (inclusive) to 7 (exclusive)
treeSet.headSet(5);                   // [1, 3] - elements < 5
treeSet.tailSet(5);                   // [5, 7, 9] - elements >= 5

// Descending
treeSet.descendingSet();              // [9, 7, 5, 3, 1]
```

---

## HashSet vs LinkedHashSet vs TreeSet

| Feature | HashSet | LinkedHashSet | TreeSet |
|---------|---------|---------------|---------|
| Order | None | Insertion | Sorted |
| Add/Remove/Contains | O(1) | O(1) | O(log n) |
| Null | One null | One null | No null |
| Memory | Lowest | Medium | Medium |
| Use Case | Fast lookup | Ordered unique | Sorted unique |

---

# Map Interface

## When to Use Which Map?

```
Need key-value pairs?
├─ Need sorted keys?
│   └─ YES → TreeMap
│
├─ Need insertion order?
│   └─ YES → LinkedHashMap
│
├─ Need access order (LRU cache)?
│   └─ YES → LinkedHashMap with accessOrder=true
│
├─ Need thread safety?
│   ├─ High concurrency → ConcurrentHashMap
│   └─ Low concurrency → Collections.synchronizedMap()
│
└─ Just need fast lookup?
    └─ YES → HashMap (default choice)
```

---

## HashMap

**What:** Hash table implementation, no ordering.

**Use When:**
- ✅ Fast key-value lookup
- ✅ Order doesn't matter
- ✅ Most common use case

**Avoid When:**
- ❌ Need sorted keys
- ❌ Need insertion order

```java
// Creation
Map<String, Integer> map = new HashMap<>();
Map<String, Integer> mapWithCapacity = new HashMap<>(100);

// Basic Operations
map.put("Apple", 10);                 // Add/Update - O(1)
map.get("Apple");                     // Get - O(1), returns null if not found
map.getOrDefault("Banana", 0);        // Get with default - O(1)
map.remove("Apple");                  // Remove - O(1)
map.containsKey("Apple");             // Check key - O(1)
map.containsValue(10);                // Check value - O(n)
map.size();                           // Size - O(1)
map.isEmpty();                        // Is empty - O(1)
map.clear();                          // Clear - O(n)

// Java 8+ Operations
map.putIfAbsent("Apple", 10);         // Add only if key absent
map.computeIfAbsent("Apple", k -> 10); // Compute if absent
map.computeIfPresent("Apple", (k, v) -> v + 1); // Compute if present
map.merge("Apple", 1, Integer::sum);  // Merge values

// Views
map.keySet();                         // Set of keys
map.values();                         // Collection of values
map.entrySet();                       // Set of key-value pairs

// Iteration
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

map.forEach((key, value) -> System.out.println(key + ": " + value));
```

**Internal Working (Java 8+):**
- Array of buckets (nodes)
- Each bucket: linked list (< 8 entries) or red-black tree (>= 8 entries)
- Default capacity: 16
- Default load factor: 0.75
- Tree threshold: 8 (converts to tree), 6 (converts back to list)

---

## LinkedHashMap

**What:** Hash table + linked list, maintains insertion order (or access order).

**Use When:**
- ✅ Need key-value pairs with predictable iteration order
- ✅ Building LRU cache (with access order)
- ✅ Need insertion order

```java
// Insertion Order (default)
Map<String, Integer> map = new LinkedHashMap<>();

map.put("Banana", 2);
map.put("Apple", 1);
map.put("Cherry", 3);

// Iteration maintains insertion order
map.forEach((k, v) -> System.out.println(k));  // Banana, Apple, Cherry

// Access Order (for LRU cache)
Map<String, Integer> lruCache = new LinkedHashMap<>(16, 0.75f, true);
// Third parameter: true = access order, false = insertion order

lruCache.put("A", 1);
lruCache.put("B", 2);
lruCache.put("C", 3);
lruCache.get("A");  // Access A, moves it to end

lruCache.forEach((k, v) -> System.out.print(k + " ")); // B C A (A moved to end)
```

**LRU Cache Implementation:**
```java
class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int maxSize;

    public LRUCache(int maxSize) {
        super(maxSize, 0.75f, true);  // true = access order
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;  // Remove oldest when exceeds maxSize
    }
}

// Usage
LRUCache<String, Integer> cache = new LRUCache<>(3);
cache.put("A", 1);
cache.put("B", 2);
cache.put("C", 3);
cache.put("D", 4);  // "A" is removed (oldest)
```

---

## TreeMap

**What:** Red-Black tree implementation, keys sorted.

**Use When:**
- ✅ Need sorted keys
- ✅ Need range operations
- ✅ Need first/last key operations

**Avoid When:**
- ❌ Keys don't implement `Comparable`
- ❌ Need O(1) operations
- ❌ Need null keys

```java
// Creation
Map<String, Integer> map = new TreeMap<>();                    // Natural order
Map<String, Integer> reverseMap = new TreeMap<>(Comparator.reverseOrder());

// Basic Operations (all O(log n))
map.put("Banana", 2);
map.get("Banana");
map.remove("Banana");

// NavigableMap Operations
TreeMap<Integer, String> treeMap = new TreeMap<>();
treeMap.put(1, "One");
treeMap.put(3, "Three");
treeMap.put(5, "Five");
treeMap.put(7, "Seven");

treeMap.firstKey();                   // 1
treeMap.lastKey();                    // 7
treeMap.lowerKey(5);                  // 3 - largest key < 5
treeMap.higherKey(5);                 // 7 - smallest key > 5
treeMap.floorKey(4);                  // 3 - largest key <= 4
treeMap.ceilingKey(4);                // 5 - smallest key >= 4

// Range Views
treeMap.subMap(2, 6);                 // {3=Three, 5=Five}
treeMap.headMap(5);                   // {1=One, 3=Three}
treeMap.tailMap(5);                   // {5=Five, 7=Seven}

// First/Last Entry
treeMap.firstEntry();                 // 1=One
treeMap.lastEntry();                  // 7=Seven
treeMap.pollFirstEntry();             // Removes and returns first
treeMap.pollLastEntry();              // Removes and returns last
```

---

## HashMap vs LinkedHashMap vs TreeMap

| Feature | HashMap | LinkedHashMap | TreeMap |
|---------|---------|---------------|---------|
| Order | None | Insertion/Access | Sorted |
| Put/Get/Remove | O(1) | O(1) | O(log n) |
| Null key | One null | One null | No null |
| Memory | Lowest | Medium | Medium |
| Use Case | Fast lookup | LRU cache, ordered | Sorted keys |

---

# Queue & Deque

## When to Use Which?

```
Need Queue/Deque?
├─ Need priority ordering?
│   └─ YES → PriorityQueue
│
├─ Need both ends access (stack + queue)?
│   └─ YES → ArrayDeque (default choice for Deque)
│
├─ Need thread safety?
│   ├─ Blocking required → LinkedBlockingQueue, ArrayBlockingQueue
│   └─ Non-blocking → ConcurrentLinkedQueue
│
└─ Just need FIFO queue?
    └─ ArrayDeque or LinkedList
```

---

## PriorityQueue

**What:** Heap-based priority queue, elements ordered by priority.

**Use When:**
- ✅ Need to process elements by priority
- ✅ Find min/max efficiently
- ✅ Implementing Dijkstra's, task scheduling

```java
// Min-Heap (default - smallest first)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max-Heap (largest first)
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

// Custom priority
PriorityQueue<Task> taskQueue = new PriorityQueue<>(
    Comparator.comparingInt(Task::getPriority)
);

// Operations
minHeap.offer(5);                     // Add - O(log n)
minHeap.offer(3);
minHeap.offer(7);

minHeap.peek();                       // View top - O(1), returns 3
minHeap.poll();                       // Remove top - O(log n), returns 3
minHeap.size();                       // Size - O(1)
minHeap.contains(5);                  // Contains - O(n)

// Note: Iteration order is NOT sorted!
// PriorityQueue only guarantees head is min/max
```

**Common Pattern - Top K Elements:**
```java
// Find K largest elements
public int[] topK(int[] nums, int k) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    for (int num : nums) {
        minHeap.offer(num);
        if (minHeap.size() > k) {
            minHeap.poll();  // Remove smallest
        }
    }

    return minHeap.stream().mapToInt(i -> i).toArray();
}
```

---

## ArrayDeque

**What:** Resizable array implementation of Deque.

**Use When:**
- ✅ Need Stack (faster than Stack class)
- ✅ Need Queue (faster than LinkedList)
- ✅ Need both ends access

**Avoid When:**
- ❌ Need null elements
- ❌ Need thread safety

```java
Deque<String> deque = new ArrayDeque<>();

// Stack Operations (LIFO)
deque.push("First");                  // Add to front - O(1)
deque.push("Second");
deque.pop();                          // Remove from front - O(1), returns "Second"
deque.peek();                         // View front - O(1)

// Queue Operations (FIFO)
deque.offer("First");                 // Add to back - O(1)
deque.offer("Second");
deque.poll();                         // Remove from front - O(1), returns "First"

// Deque Operations (both ends)
deque.addFirst("Front");              // O(1)
deque.addLast("Back");                // O(1)
deque.removeFirst();                  // O(1)
deque.removeLast();                   // O(1)
deque.getFirst();                     // O(1)
deque.getLast();                      // O(1)
```

**ArrayDeque vs LinkedList for Queue/Stack:**
- ArrayDeque is faster (no node allocation)
- ArrayDeque uses less memory
- LinkedList allows null, ArrayDeque doesn't

---

# Thread-Safe Collections

## When to Use Which?

| Need | Collection |
|------|------------|
| Thread-safe List | `CopyOnWriteArrayList` (read-heavy) or `Collections.synchronizedList()` |
| Thread-safe Set | `ConcurrentSkipListSet` or `CopyOnWriteArraySet` |
| Thread-safe Map | `ConcurrentHashMap` (best) or `Collections.synchronizedMap()` |
| Thread-safe Queue | `ConcurrentLinkedQueue` (non-blocking) or `LinkedBlockingQueue` (blocking) |

---

## ConcurrentHashMap

**What:** Thread-safe HashMap with high concurrency.

```java
ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();

// Basic operations (thread-safe)
map.put("key", 1);
map.get("key");
map.remove("key");

// Atomic operations
map.putIfAbsent("key", 1);
map.computeIfAbsent("key", k -> expensiveComputation());
map.merge("key", 1, Integer::sum);  // Thread-safe increment

// DON'T do this (not atomic)
// map.put("key", map.get("key") + 1);  // Race condition!

// DO this instead
map.merge("key", 1, Integer::sum);
// or
map.compute("key", (k, v) -> v == null ? 1 : v + 1);
```

---

## Collections Utility Methods

```java
// Synchronized wrappers
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
Set<String> syncSet = Collections.synchronizedSet(new HashSet<>());
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());

// Unmodifiable (immutable views)
List<String> immutableList = Collections.unmodifiableList(list);
Set<String> immutableSet = Collections.unmodifiableSet(set);
Map<String, Integer> immutableMap = Collections.unmodifiableMap(map);

// Java 9+ Immutable factories
List<String> list = List.of("A", "B", "C");
Set<String> set = Set.of("A", "B", "C");
Map<String, Integer> map = Map.of("A", 1, "B", 2);

// Sorting
Collections.sort(list);                          // Natural order
Collections.sort(list, Comparator.reverseOrder()); // Custom
list.sort(Comparator.comparing(String::length));  // By length

// Other utilities
Collections.reverse(list);
Collections.shuffle(list);
Collections.binarySearch(sortedList, key);
Collections.min(collection);
Collections.max(collection);
Collections.frequency(collection, element);
Collections.disjoint(c1, c2);                    // True if no common elements
```

---

# Decision Flowchart

## Choosing the Right Collection

```
What do you need?
│
├─ Unique elements only?
│   ├─ Sorted? → TreeSet
│   ├─ Insertion order? → LinkedHashSet
│   └─ Just unique? → HashSet
│
├─ Key-Value pairs?
│   ├─ Sorted keys? → TreeMap
│   ├─ Insertion order? → LinkedHashMap
│   ├─ Thread-safe? → ConcurrentHashMap
│   └─ Just fast lookup? → HashMap
│
├─ Ordered sequence (duplicates OK)?
│   ├─ Frequent random access? → ArrayList
│   ├─ Frequent add/remove at ends? → LinkedList or ArrayDeque
│   └─ Thread-safe? → CopyOnWriteArrayList
│
├─ FIFO Queue?
│   ├─ Priority based? → PriorityQueue
│   ├─ Thread-safe blocking? → LinkedBlockingQueue
│   └─ Regular queue? → ArrayDeque
│
└─ Stack (LIFO)?
    └─ ArrayDeque (don't use Stack class)
```

---

## Quick Decision Table

| Requirement | Best Choice |
|-------------|-------------|
| Fast random access list | `ArrayList` |
| Fast add/remove at ends | `ArrayDeque` |
| Unique elements, unordered | `HashSet` |
| Unique elements, sorted | `TreeSet` |
| Unique elements, insertion order | `LinkedHashSet` |
| Key-value, unordered | `HashMap` |
| Key-value, sorted | `TreeMap` |
| Key-value, insertion order | `LinkedHashMap` |
| LRU Cache | `LinkedHashMap` (accessOrder=true) |
| Priority Queue | `PriorityQueue` |
| Stack | `ArrayDeque` |
| Queue | `ArrayDeque` or `LinkedList` |
| Thread-safe Map | `ConcurrentHashMap` |
| Thread-safe List (read-heavy) | `CopyOnWriteArrayList` |

---

## Performance Tips

1. **Set initial capacity** for ArrayList/HashMap if size is known
   ```java
   new ArrayList<>(1000);
   new HashMap<>(1000);
   ```

2. **Use primitives collections** for large datasets (Eclipse Collections, Trove)

3. **Avoid boxing/unboxing** in loops
   ```java
   // Bad
   List<Integer> list = new ArrayList<>();
   for (int i = 0; i < 1000000; i++) {
       list.add(i);  // Boxing overhead
   }

   // Better: Use int[] or IntList from primitive collections
   ```

4. **Use ArrayDeque instead of Stack/LinkedList** for Stack/Queue

5. **Use ConcurrentHashMap instead of Hashtable** for thread safety

6. **Use EnumSet/EnumMap** for enum keys
   ```java
   Set<DayOfWeek> weekend = EnumSet.of(SATURDAY, SUNDAY);
   Map<DayOfWeek, String> schedule = new EnumMap<>(DayOfWeek.class);
   ```

---

## Common Mistakes

| Mistake | Solution |
|---------|----------|
| Using `Vector` or `Hashtable` | Use `ArrayList` or `HashMap` |
| Using `Stack` class | Use `ArrayDeque` |
| Not setting initial capacity | Set capacity if size is known |
| Using `LinkedList` for random access | Use `ArrayList` |
| Using `HashMap` when order matters | Use `LinkedHashMap` or `TreeMap` |
| Modifying collection while iterating | Use `Iterator.remove()` or `removeIf()` |
| Using wrong equals/hashCode | Override both for custom objects |

---

## Remember

- **ArrayList** = Default list choice
- **HashSet** = Default set choice
- **HashMap** = Default map choice
- **ArrayDeque** = Default stack/queue choice
- **ConcurrentHashMap** = Default thread-safe map choice

When in doubt, start with these and optimize later if needed!

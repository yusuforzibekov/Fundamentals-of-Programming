# Hashtable Open 8-16

The purpose of this exercise is to describe, create, and use hash tables that are built according to special rules.  

Duration: **1 hour**  


## Description
In this task, you need to describe a hash table based on open addressing. Linear probing is used to resolve collisions.  
Please proceed to the `HashtableOpen8to16` interface and implement the `getInstance()` method. The method should return an instance of the class (`HashtableOpen8to16Impl`), which implements this interface.  
Please proceed to the `HashtableOpen8to16Impl` class, which implements the `HashtableOpen8to16` interface, and provide implementations of the following methods:  
* `void insert(int key, Object value)`  
   Inserts a key/value pair in the hash table if the key is missing or replaces the object if the key exists  

* `Object search(int key)`  
   Returns the object associated with the specified key. If there is no such key, it returns null.  

* `void remove(int key)`  
   Removes a key/value pair from the hash table if the specified key is in the hash table  

* `int size()`  
   Returns the number of pairs in the hash table  

* `int[] keys()`  
   Returns as an array all the keys distributed in the hash table  

### Details: 
* The initial capacity of the hash table is 8 buckets.  
* The maximum capacity is 16; inserting more elements should raise an `IllegalStateException`.
* The capacity should double each time a new element arrives and all the buckets are already occupied. For example, if 8 buckets are already filled and a new key is about to be added, the capacity should increase.  
* The capacity should be cut in half each time an element is removed if the size is not equal to 0 and the ratio of size to capacity reaches 1:4 after removing the element. For example, if there are 8 buckets and 3 elements, after removing one of them, the capacity should decrease to 4. Another example: If there are 4 buckets and 2 elements, removing one of them causes the capacity to reduce to 2.
* Note that in the case of 2 buckets and 1 element, removing that element leads to a size of 0. In this case, there is no reduction in capacity, so the minimum capacity of the implementation is effectively 2.


## Restrictions
You may not:  
* Use any type from the `java.util` package or its subpackages, except for the `java.util.Iterator`
* Use lambdas or streams when implementing this task


# Doubly Linked List

The purpose of this exercise is to create and use a custom doubly linked list. 

Duration: **1 hour**


## Description 

In this task, you will describe a bidirectional list based on a linked representation. The internal structure of the list is a node chain (nested class `Node`) where each node encapsulates a list element and has one reference to the previous node and one reference to the following node. The first node has no reference to the previous node, and the last node has no reference to the next one.


Now, please proceed to the  `DoublyLinkedListImpl` class, which implements the  `DoublyLinkedList` interface, and provide implementations of the following methods:  

* `boolean addFirst(Object element)`  
   Inserts an element at the beginning of the list and returns true if an element is successfully added and, if not, false  

* `boolean addLast(Object element)`  
   Appends an element to the end of this list and returns true if an element is successfully added and, if not, false  

* `boolean delete(int index)`  
   Deletes an element by index and throws `IndexOutOfBoundsException` if the index is out of range  

* `Optional<Object> remove(Object element)`  
   Returns and deletes the first occurrence of the specified element in the list  

* `boolean set(int index, Object element)`  
   Finds the element at the specified index and replaces it or throws the `IndexOutOfBoundsException` if the index is out of range  

* `int size()`  
   Returns the number of elements in the list  

* `Object[] toArray() `  
   Returns an array containing all the elements in order from first to last 

* `String toString()`  
   Returns a string representation of the list 

### Details

*	The `DoublyLinkedListImpl` class has two fields, head and tail, and should not contain a size field to store the number of elements.
*	The list is created by the default constructor, which initializes the head and tail fields to null.
*	The list cannot contain null elements. The add, remove, and set methods must throw a `NullPointerException` if they get null.
*	The remove method must use the equals method to find the occurrence of the object in the list.
*	The string representation of a list consists of all its elements separated by a space. For example, if a list contains three elements, o1, o2, and o3, the `toString` method returns "s1 s2 s3", where `s1=o1.toString()`, `s2=o2.toStribng()`, and `s3=o3.toString()`.

## Restrictions

You may not: 
*	Use any type from the  `java.util` package or its subpackages except for  `java.util.Optional`
*	Add additional fields to the  `DoublyLinkedListImpl` and  `DoublyLinkedListImpl.Node` classes
*	Add any additional methods to the  `DoublyLinkedListImpl.Node` class except constructors


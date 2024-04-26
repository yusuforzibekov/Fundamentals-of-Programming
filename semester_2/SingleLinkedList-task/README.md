# Singly Linked List  

The purpose of this exercise is to describe, create, and use lists that are built according to special rules.


Duration: **1 hours**


## Description

In this task, you will describe a unidirectional list based on a linked representation. The internal structure of the list is a node chain (nested class `Node`) where each node encapsulates a list element and has a reference to another similar node. The node chain starts with a header element that stores the number of elements in the list. The last node in the list is not linked to any other node and has a null reference.
Now, please proceed to the `SingleLinkedListImpl` class, which implements the `List` interface, and provide implementations of the following methods:

* `void clear()`   
   Removes all elements from the list

*	`int size()`   
   Returns the number of elements in the list

*	`boolean add(Object element)`  
   Inserts the specified element at the beginning of the list

*	`Optional<Object> remove(Object element)` 
   Removes the first occurrence of the specified element from the list

*	`Object get(int index)` 
   Finds and returns an element from the list by index

*	`Iterator<Object> iterator()` 
   Returns an iterator for the list  

* `String toString()` 
   Makes a string representation of the list

### Details:
*	The list is created by the default constructor, which creates a header and sets the list's element count to zero.
* List modification methods (add and remove) must change the number of list elements in the header.
*	The list cannot contain null elements. The `add` and `remove` methods must throw a `NullPointerException` if they get null.
*	The get method must throw `IndexOutOfBoundsException` if the index is out of range.
*	You can add any private methods to the `SinglyLinkedListImpl` class.


## Restrictions
You may not: 
* Use any type from the `java.util` package (or its subpackages) except for: 
  * `java.util.Iterator`
  * `java.util.NoSuchElementException`
  * `java.util.Objects`
  * `java.util.Optional`
* Add additional fields to the `SingleArrayListImpl` and `SinglyLinkedListImpl.Node` classes.
* Add any additional methods to the  `SinglyLinkedListImpl.Node` class except constructors

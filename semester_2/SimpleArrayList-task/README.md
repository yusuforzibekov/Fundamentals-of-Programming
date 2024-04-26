# Simple Array List  

The purpose of this exercise is to describe, create, and use lists that are built according to special rules.


Duration: **45 minutes**



## Description

In this task, you will describe a list that is based on a dynamic array. The internal structure of the list is a one-dimensional array of the `Object` type with an initial capacity/size of 10 and a size field that stores the number of elements in the list. You must provide for the expansion of the array (list) capacity when the number of elements reaches 75% (load factor 0.75) of its size. Use the following expression to calculate the increase in capacity: Doub le the current capacity of the array and then multiply it by the load factor.

Now, please proceed to the `SimpleArrayListImpl` class, which implements the `SimpleArrayList` interface, and provide implementations of the following methods:

*	`int size()`  
   Returns the number of elements  

*	`String toString()`  
   Makes a string representation of the list  

*	`boolean add(Object element)`  
   Appends the specified element to the end of the list

*	`Object get(int index)`  
   Returns an element at a specified position in the list

*	`Optional<Object> remove(Object element)`  
   Searches for and removes the specified element

*	`int capacity()`  
   Returns the current length of the list

*	`boolean decreaseCapacity()`  
   Decreases the capacity of the list if it is 40% full or less


### Details:
*	The list is created by the default constructor, which creates an internal array with an initial length of 10 elements.
*	The list cannot contain `null` elements. The add and remove methods must throw a `NullPointerException` if they get null.
*	The get method must throw `IndexOutOfBoundsException` if the index is outside the [0 … size - 1] range.
*	The size field specifies the number of elements in the list and where to place the next element.
*	The method for adding a new element must first check whether the capacity of the list should be increased before adding it.
*	The `decreaseCapacity()` method calculates the new capacity by doubling the current number of elements.
*	You can add any private methods to the `SimpleArrayListImp` class.


## Restrictions
You may not: 
* Use any type from the `java.util` package (or its subpackages) except for:  
      * `java.util.Iterator`  
      * `java.util.Optional`  
*	Add any additional fields into the `SingleArrayListImpl` class.
*	Add any additional public methods to the `SingleArrayListImpl` class.

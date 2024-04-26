# Binary Tree
 
The purpose of this exercise is to describe, create, and use binary trees that are built according to certain rules.

Duration: **2 hours 30 minutes**


## Description

In this task, you need to describe a binary tree that stores integers. The tree's internal structure is a set of nodes (the nested class `Node`), where each node encapsulates an integer and has two references to the left and right (child) nodes  . The left node is strictly less than the parent, and the right node is strictly greater. Access to the tree always starts at the root node.  

Please proceed to the `BinaryTree` class and provide implementations of the following methods:

* `boolean add(Integer element)`  
   Adds an object to the tree  
  If such an object exists, it is not added. Otherwise, a new element is created and becomes a leaf on the tree.
* `void addAll(Integer... elements)`  
Adds all non-existing integers to the tree   from the specified set  

*	`String toString()`  
   Returns a string representation of the tree whose elements are in natural order  

*	`Optional<Integer> remove(Integer element)`  
   Returns and deletes the specified element from the tree  
   If there is no such element, it returns null.
    
*	`int size()`  
   Returns the number of elements in the tree


### Details

* The `BinaryTree` class has two fields: `root` (tree top) and `size` (the number of elements).
* The class has two constructors for building the initial representation of the tree: The default constructor creates an empty tree, and the constructor with a parameter creates a tree based on an array of integers.
* `Add` and `remove` methods always begin searching for the node's position at the root. Further, moving along the tree is carried out according to the following rule: If the specified object has a value less than that of the current node, then you move to the node to the left of the current one; if the value is greater, then you move to the one to the right of the current node.    
* The first element added becomes the tree's root.
* When you implement a remove method, you must consider the following:
  * If the node to be deleted does not have any child nodes, the corresponding reference must be replaced with null in its parent.
  * If the node to be deleted has only one child node, you need to create a new relationship between its parent and child node.
  * If the node to be deleted has two child nodes, then you need to:
     * Find the leftmost node in its right subtree (a node that does not have a left child)
     * Create a new relationship between a parent and a right child of this node     
     * Replace the node to be deleted with the one that was found
* To form a string representation of a tree, it is necessary to use a direct (sequential) tree bypass.   For example, if the tree was formed from the sequence of integers 6, 2, 9, 1, 5, the string will be represented as follows: [1, 2, 5, 6, 9].
* Recursive algorithms are recommended when adding, deleting, and iterating over tree nodes.
* You can add any private methods to the `BinaryTree` and `BinaryTree.Node` classes.

## Restrictions

You may not: 
*	Use any type from the `java.util` package or its subpackages, except for the `java.util.Optional`
* Add extra fields to the `BinaryTree` and `BinaryTree.Node` classes
* Add extra methods to the `BinaryTree.Node` class
* Use lambdas or streams when implementing this task

## Example

1. Code
```java
BinaryTree tree = new BinaryTree();  
tree.addAll(3, 1, 2, 5, 6, 4, 0);  
System.out.println(tree);  
System.out.println("~~~~~~~~~~~~~~");   
for (int j : new int[] {3, 5, 4, 1, 0} ) {  
	System.out.printf("remove %d: %b%n", j, tree.remove(j));  
	System.out.println(tree);  
	System.out.println("~~~~~~~~~~~~~~");  
}
```
2. The code above must generate the following output:
```
[0, 1, 2, 3, 4, 5, 6]
~~~~~~~~~~~~~~
remove 3: true
[0, 1, 2, 4, 5, 6]
~~~~~~~~~~~~~~
remove 5: true
[0, 1, 2, 4, 6]
~~~~~~~~~~~~~~
remove 4: true
[0, 1, 2, 6]
~~~~~~~~~~~~~~
remove 1: true
[0, 2, 6]
~~~~~~~~~~~~~~
remove 0: true
[2, 6]
~~~~~~~~~~~~~~
```

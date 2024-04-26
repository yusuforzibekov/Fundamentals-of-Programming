# New Post Office Storage
 
The purpose of this exercise is to work with List implementations, add/delete elements, and iterate over a collection to perform operations on each element.


Duration: **1 hour**


## Description

In this task, you will simulate the work of the New Post Storage service, which accepts parcels (boxes) and prepares them for distribution to New Post offices.
The  `Box` class describes a parcel with the following characteristics: `sender`, `recipient`, `weight`, `volume`, `cost`, `city`, and `office`. All characteristics are passed to the constructor to create an object. You must not change this class. 

The  `NewPostOfficeStorage` interface has several methods that perform `add`, `delete`, `filter`, and `update` operations in a storage:  

- `boolean acceptBox(Box box)` \
  Appends the specified parcel to the end of this storage
- `boolean acceptAllBoxes(Collection<Box> boxes)` \
  Appends to this storage all parcels that are contained in 
  the specified collection. It must add either all the parcels 
  or nothing, if an exception occurs
- `boolean carryOutBoxes(Collection<Box> boxes)` \
  Removes from this storage all of its parcels that are contained 
  in the specified collection
- `List<Box> carryOutBoxes(Predicate<Box> predicate)` \
  Removes from this storage all of its parcels that are satisfied the 
  specified predicate
- `List<Box> getAllWeightLessThan(double weight)` \
  Selects all parcels whose weight is less than the parameter value.
- `List<Box> getAllCostGreaterThan(BigDecimal cost)` \
  Selects all parcels whose cost is greater than the parameter value.
- `List<Box> getAllVolumeGreaterOrEqual(double volume)` \
  Selects all parcels whose volume is greater than or equal 
  to the parameter value  
- `List<Box> searchBoxes(Predicate<Box> predicate)`  
  Searches parcels using a predicate. This method does 
  not change the original storage
- `void updateOfficeNumber(Predicate<Box> predicate, int newOfficeNumber)`   
  Updates the `office` field in each box of this storage

Please, proceed to the `NewPostOfficeStorageImpl`
that the implementor of `NewPostOfficeStorage` interface.

> Its highly recommended avoid indexed access to elements of a collection.

The  `Main` class is not under tests. You can use it for your own purposes.

## Restrictions:

- Because the `Box` class does not have any identity field the 
  implementation **must not check uniqueness** of `Box` instances.
- Because the `NewPostOfficeStorage` describes business logic 
  not a collection, an implementation **must not extend** any 
  existing implementation of `List` interface or implement it 
  internally **but must aggregate** it instead. 
- You should choose the best existing implementation of the 
  `java.util.List` interface from the Java collection framework.
- The implementation **must not permit null values** and must throw
  `NullPointerException` in such cases.
- It is forbidden to use: 
  - lambda when you implement functional interfaces.
  - `stream()`, `contains()`, `containsAll()` `indexOf()` methods 
    of `List` interface.
  - `for ( i )` loop (use a `foreach` loop or `Iterator` with a
    `while()` loop or the `foreach()` method instead).

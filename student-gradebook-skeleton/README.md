# Student’s Gradebook

The purpose of this exercise is to work with Comparator implementations on the TreeMap maps.  

Duration: **1 hour**


## Description

In this task, you will simulate the work of the student's gradebook. The internal structure of the gradebook is a collection of the `TreeMap` type where a key is an object of the `Student` type, the value is a `HashMap` whose key is the name of a discipline (an object of the `String` type), and the value is the grade (an object of the `BigDecimal` type).  
The `Student` class describes a student with the following characteristics: `first/last` name and `group`. All characteristics are passed to the constructor to create an object. 
**You must not change this class.**   
Now, please proceed to the `StudentsGradebookImpl` class, which implements the `StudentsGradebook` interface, and provide implementations of the following methods:
* `public GradeBookImpl()`  
   Creates the gradebook object in which a `Comparator` is described to compare students
* `boolean addEntryOfStudent(Student student, String discipline, BigDecimal grade)`  
   Adds a student entry to the gradebook; returns true if the addition was successful, and false if not.
* `int size()`  
   Returns the number of students in the gradebook
* `Comparator<Student> getComparator()`  
   Returns the `Comparator`, which is used to compare students
* `List<String> getStudentsByDiscipline(String discipline)`  
   Gets a list of strings with students' names and their grades for the specified disciplines in the format `"first_last name : grade"`
* `Map<Student, Map<String, BigDecimal>> removeStudentsByGrade(BigDecimal grade)`  
   Finds, removes, and returns from the gradebook students who have a grade below the specified one in any discipline  
   If no such students are found, an empty map will be returned
* `Map<BigDecimal, List<Student>> getAndSortAllStudents()`  
   Gets the gradebook as an ordered map where the key is the average grade and the value is a list of the `Student` type

### Details

* Since the `Student` objects are the keys to the gradebook, when you create the gradebook, you will need to declare the `Comparator` to compare objects of the `Student` type and pass it to the `TreeMap<Student, Map<String, BigDecimal>>` constructor.  
* The grading scale for any discipline is in the range `[0 .. 5]`.  The minimum passing grade is `3` points.  
* The `removeStudentsByGrade` method returns a map ordered by the `Student` type. To create the resulting map, you must use the `Comparator` applied in the gradebook constructor.  
* The `getAndSortAllStudents` method generates a gradebook based on the student's average grade. The average grade is indicated in increments of one tenth—for example, 3.1, 3.2, 3.3, etc.  
* It is guaranteed that the parameters passed to all methods are correct.  
* You can add any private methods to the `StudentsGradebookImpl` class.  
  

## Restrictions: 

You may not use lambdas or streams to complete this task.  

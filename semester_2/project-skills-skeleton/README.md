# Project skills

The purpose of this exercise is to give you some practice using collections of the Set type.  

Duration: **1 hour**

## Description

In this task, you will determine the percentage of compliance of a certain team with project requirements. In other words, you will determine if the team is suitable for the project.   

You have the following description:
* The `Position` enum defines a set of positions.
*	The `Skill` enum defines a set of skills.
*	The `Level` enum defines a set of skill levels.
*	The `Member` class defines a team member. Each member has a name, a level, and a set of skills (a collection of the `Set<Skill>` type implemented as `EnumSet<Skill>`). TThe team is described as a set of members in the form of `HashSet<Member>`.
*	The `Role` class defines the role of the participant in the project. Each role has a level, a position, and required skills (a collection of the `Set<Skill>` type implemented as an `EnumSet<Skill>`).
*	The `Project` class defines the project and contains a description of it and a list of roles.
*	The `Project.Entry` class combines a skill and its level and is used in the algorithm for calculating the team's compliance with the project.

Now, please proceed to the `Member` class and implement its contents:
* `public Member(String name, Level level, Skill... skills)`   
   Creates a member with the specified name, level, and skills
* `Getters`  
   Return values of the appropriate fields

Now, please proceed to the `Role` class and implement its contents:    
* `public Role(Position position, Level level, Skill... skills)`   
   Creates a role with the specified position, level, and skills
* `Getters`  
   Return values of the appropriate fields  

Now, please proceed to the `Project` class and implement its contents:
* `public Project(Role... roles)`    
   Creates a project with the specified roles
* `public List<Role> getRoles()`  
   Returns a list of roles
* `public int getConformity(Set<Member> team)`  
   Returns the percentage of team compliance with the project requirements
* `private static class Entry {}`  
   Describes an entry containing a level/skill pair

### Details:
* The `Entry` must implement the equals and `hashCode` methods. Two entries are considered equal if and only if they have the same skill and level.
* •	To calculate the percentage of team compliance with project requirements, you must use the following algorithm:
   -  Generate a list of pairs  `<Level, Skill>` for the project's roles (e.g., named projectEntries).
   -	Save the size of this list to a variable (e.g., originalSize).
   -	Generate a list of pairs `<Level, Skill>` for the team members (e.g., named teamEntries.
   -	Remove common elements from both lists.  
   -	Calculate the compliance percentage as follows:  
        * Subtract the current size of the list of entries for the project roles from the originalSize variable
        * Multiply the  difference by 100
        * Divide by the original size of the list of entries for the project roles



## Restrictions
You may not:  
•	Add extra fields to classes  
•	Add extra methods to classes  
•	Use lambdas or streams when implementing this task  


## Example:
1. Suppose the project has the following roles:  
```
•	DEVELOPER which has the A1 level and the JAVA, DATABASE skills 
•	KEY_DEVELOPER which has the A3 level and the JAVA, DATABASE, SPRING skills 
•	TESTER which has the A2 level and the JAVA, SPRING, TESTING_TOOLS skills
```
Then the list of pairs will be:  
```
projectEntries → [<A1, JAVA>, <A1, DATABASE>, <A3, JAVA>, <A3, DATABASE>, <A3, SPRING>, <A2, JAVA>, <A2, SPRING>, <A2, TESTING_TOOLS>]
```
And the list size is 8.

2. Suppose the team has the following members:  
```
•	Member1 which has the A1 level and the JAVA, SPRING skills 
•	Member2 which has the A2 and the JAVA, DATABASE, SPRING skills
```
Then the list of pairs will be:  
```
teamEntries → [<A1, JAVA>, <A1, SPRING>, <A2, JAVA>, <A2, DATABASE>, <A2, SPRING>]
```

3. Removing the common elements in both lists yields the following result:  
```
projectEntries → [<A1, DATABASE>, <A3, JAVA>, <A3, DATABASE>, <A3, SPRING>, <A2, TESTING_TOOLS>]
teamEntries → [<A1, SPRING>, <A2, DATABASE>]
```
The new size of the projectEntries list is 5.  

4. The compliance percentage is 37.5%.  

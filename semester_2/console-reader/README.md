# Console Reader

The purpose of this task is to give you some practice reading data with InputStream/Reader.

It should take you about _30 minutes_ to complete this task.

### Description

In this task, you will implement methods for processing data from the console.

Please implement methods in the `ConsoleReader` class.

* `public static void readNames()` - reads names line by line until it encounters an exit character _0_. The program prints "Number of names: _N_" as the output. Each line contains one name that consists of one or more words. The name can contain letters and the following characters: ",", ".", "'", "-". If the name includes an invalid character, the program stops reading and throws an exception.
* `public static void readNumbers()` - reads numbers separated by " " until a cursor moves to a new line. The line can contain only digits and spaces. If the line includes an invalid character, the program stops reading and throws an exception.



### Examples

```java
        Ashley
        Marco
        Tony
        0

        readNames();
// Number of names: 3
```

---

```java
        132 865 456722 6 261

        readNumbers;
// Numbers: 132 865 456722 6 261
// Sum: 457986
```

# Simple text input/output

The purpose of this exercise is to train you how to read, parse, and write data to a text file.

Estimated workload of this exercise is _45 min_.


### Description

Let an input file contain correct coordinates of segments (one per line) in the format:

`(x1;y1)(x2;y2)`

where `x1`, `y1`, `x2`, `y2` are floating point numbers. 

Any subsequence of spaces may occur at any place in a line. 

Calculate the frequency of lengths of segments rounded to integer. 

Output results sorted by the length to a file in the format:

`length;number`

where `length` - is the segment length, `number` is the number of segments of this length. 

You have to implement a static method

`public static void lengthFrequency(File in, File out) throws FileNotFoundException`

where `in` and `out` are instances of an input and output text files. 

### Example

The content of the input file [`src/in.txt`](src/in.txt).
```
(1.0;2.0) (1.0;  3.0)
(0;0)(1;0)
(3.0;0.0) (5.0;   0.0)
             (   3.0;1.0) (8.0   ; 1.0   )
 (0.1e-3;  0.0f)         ( 0.1e-3   ; 2.0e0f)
(0;0) (0;0.1)
(0;0) (0;0.5)
```

The content of the output file [`src/out.txt`](src/out.txt).
```
0;1
1;3
2;2
5;1

```

See an example of the method `lengthFrequency` call in the method `main` of the class
[`Solution`](src/main/java/com/epam/bsp/Solution.java).


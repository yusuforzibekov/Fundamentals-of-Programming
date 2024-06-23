# Rolling Zipper

The purpose of this task is to give you some practice using ZIP-related streams in Java. 

It should take you about _2 hours_ to complete this task. 

## Description

The concept of the rolling zipper is quite simple: first, you zip content into the output. When a certain condition is met, the output destination is changed, and then the content is zipped again. So, in this task, you'll implement this mechanism with two conditions: 
1. The specified size of the ZIP archive is exceeded. 
2. The specified number of files in the ZIP archive is reached. 

These conditions are known as `RollingPolicy` or just policies. 

The size policy is inaccurate by nature, so no action needs to be taken to make an archive an exact size. All you have to do is track the size of the archive. If it reaches or exceeds the specified size,  you "roll" to another archive immediately, i.e., if the size is already exceeded, an additional file cannot be written to it. Overall, you might have archives of a size that is: 
* Greater than or equal to the threshold 
* Less than the threshold, but AT MOST one 

The amount policy is more precise so that archives can contain either the specified number of files or fewer. But the archive should NEVER contain more files than specified. 

These policies must be represented in the code as `RollingPolicy` instances and be returned by the corresponding methods in `RollingPolicyFactory`. `RollingPolicy` has just one method, which accepts `ZipEntry` (which specifies a file in the ZIP archive) and returns a `boolean`. If `true` is returned, the output archive must be changed. 

Also, you need to implement a mechanism to "roll" archives. To do this, you're provided with the class `RollingZipper`. It has just one `zip` method, which you must implement. This method does not return anything; however, it has quite a few arguments: 
* `Iterable<File>` `inputFiles` is the sequence of files that must be archived. Since the file's order matters for archiving, it is guaranteed that files will not come in random order, so you don't need to worry about that. 
* `RollingPolicy` `policy` is a policy instance that specifies when the archive must be rolled. 
* `Iterator<OutputStream>` `outputs` is this method's output parameter, which describes the sequence of outputs where archives must be written. When you need to roll to a new archive, it must be chosen from this iterator. 

Please note, that this method might throw `IOException`.

Now, take a look at the overall workflow of this method: 

Each input file is zipped into the archive provided by the `outputs` iterator. Using the rolling policy, check whether 
the output should be changed or not. If it returns `true`, the current output file is closed, and you get a new one from an iterator. 

Otherwise, you continue using the current output. 

## Requirements

Here are a few assumptions: 
* `RollingZipper#zip` arguments are never null, and neither are their contents. 
* `Iterator<OutputStream>` is infinite. 
* `Iterable<File>` is finite, and all its files have unique names. 

The requirements:
* All methods in `RollingPolicyFactory` are implemented. 
* `RollingZipper#zip` is implemented. 
* The `zip` method writes zip archives into `OutputStream` objects provided by the `outputs` iterator. 
* All input files must be zipped in the order determined by `inputFiles`. 
* The `zip` method must check whether the output archive must be replaced by a new one using the `policy` argument. 
* The amount policy must check whether the number of files in the current archive is equal to its threshold. 
* The size policy must check whether the sum of the compressed size of the files in the current archive is greater than or equal to the specified value. 
* `zip` should throw an `IOException` if needed. 
* The names of input files must be preserved in the resulting archives. 
* The contents of archived files must not be corrupted. 
* The total number of files in the resulting archives must be equal to the number of input files. 

## Examples

Files:
* file1 - has size 50
* file2 - has size 100
* file3 - has size 100
* file4 - has size 50

```java
Iterable<Files> inputFiles = List.of(file1, file2, file3, file4);
RollingPolicy sizePolicy = RollingPolicyFactory.sizePolicy(100);
RollingPolicy amountPolicy = RollingPolicyFactory.amountPolicy(3);
RollingZipper zipper = new RollingZipper();
Iterator<OutputStream> sizeOutputs = // some infinite iterator which returns files with name prefix: "size-"
Iterator<OutputStream> amountOutputs = // some infinite iterator which returns files with name prefix: "amount-"

zipper.zip(inputFiles, sizePolicy, sizeOutputs);
zipper.zip(inputFiles, amountPolicy, amountOutputs);
```

The resulting files:
* size-1
  * file1
  * file2
* size-2
  * file3
* size-3
  * file4

* amount-1
  * file1
  * file2
* amount-2
  * file3
  * file4

If we change the order of files to `file1, file4, file2, file3`, then the result for size policy in that case will be:
* size-1
  * file1
  * file4
* size-2
  * file2
* size-3
  * file3

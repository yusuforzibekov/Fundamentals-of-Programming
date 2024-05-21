# Symbol Distributor

The purpose of this task is to practice using `FileInputStream` and `FileOutputStream`.

It should take you about _1 hour_ to complete this task.

## Description

In this task, your goal is to implement `SymbolsDistributor` interface and made `SymbolsDistributorFactory` returns it.
`SymbolsDistributor` reads a passed `inputFile` and distribute it symbols through multiple files which are described
in `outputMapping`. `outputMapping` is a map of `Predicate<Integer>` against output `File`. Please use keys of this map
to determine should a symbol goes to a specified output file or not. Be aware, that output file must be created before
writing to it. Input file must be also checked, because it might not be readable or doesn't even exist.
Moreover, `distribute` method must not throw checked exceptions.

## Requirements

The input parameters are never `null` or empty.

* `SymbolsDistributionFactory.getInstance` must return an instance of `SymbolsDistributor`.
* The `distribute` method should throw `IllegalArgumentException` when:
  * `inputFile` does not exist
  * `inputFile` cannot be read
  * `inputFile` is not a regular file (e.g., it is a directory)
* The `distribute` method should not throw checked exceptions.
* `distribute` must read an `inputFile` and distribute its symbols throughout multiple output files using the corresponding predicates.
* Unsupported symbols should be ignored.
* Letter case should be preserved.
* The order of symbols should be preserved.
* No additional symbols should be present in output files (no new line is required at the end of a file).


## Examples

```java
distributor.distribute(inputFile, Map.of(
    Character::isLetter, lettersFile,
    Character::isDigit, digitsFile
))
```

Input file:
```
Hello 42 world!
```

Letters file:
```
Helloworld
```

Digits file:
```
42
```

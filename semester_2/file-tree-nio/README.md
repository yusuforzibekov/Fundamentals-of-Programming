# File Tree (NIO approach)

The purpose of this task is to train you to manage files and directories using Java NIO.

It should take you about _1.5 hours_ to complete this task.


## Description

You must implement a method to build a directory representation. You can also create other methods for convenience that will be called from the `tree` method.
Please implement methods in the `FileTree` class.

* `public Optional<String> tree(final String path)` - takes a path to a file/directory as an input parameter and builds a `String` representation of its hierarchy.

    1. If a given path _does not exist_, return an empty `Optional`.
    2. If a given path _refers to a file_, return a `String` with its name and size like this:

        `some-file.txt 128 bytes`

    3. If a given path _refers to a directory_, return a `String` with its name, total size, and full hierarchy:

`

    some-dir 100 bytes
    ├─ some-inner-dir 50 bytes
    │  ├─ some-file.txt 20 bytes    
    │  └─ some-other-file.txt 30 bytes
    └─ some-one-more-file.txt 50 bytes
`

* Use pseudo-graphic characters to format the output.
* Compute the size of a directory as the sum of all its contents.
* Sort the contents as follows:
    * Directories should go first.
    * Directories and files are sorted in lexicographic order (case-insensitive).

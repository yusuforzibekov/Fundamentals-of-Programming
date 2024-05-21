# Cipher Creator

The purpose of this exercise is to give you some practice managing I/O streams.

It should take you about _30 minutes_ to complete this task.

### Description

You will implement an input stream to process data from the file. Please also implement a util class to check a file and
return a ciphered text.

Please implement methods in the [CipherCreator](src/main/java/com/epam/autotasks/CipherCreator.java) class.

- `public static StringBuilder cipherText(File input)` - takes a file as an input and returns a ciphered text.

Please override methods in the [TransformerInputStream](src/main/java/com/epam/autotasks/TransformerInputStream.java) class.

- `public int read()` - reads the next data byte and returns a ciphered byte.
- `public void close()` - closes the stream and prints a message about it to the console.

Cipher key points:

1. The text can only contain letters, and each word is written on a new line.
2. Each letter of the ciphered text is shifted by two characters from the original one, case sensitive. A -> C, f ->h.
3. The alphabet is looped. It means Z -> B, y -> a.
4. All other characters are ignored.

### Examples

```java
        Test
        cipher
        For
        Fundamentals
        Of
        Programming
        XYZ
        zyx
        CamelCase
        DiScO

        cipherText(file);

//      Vguv
//      ekrjgt
//      Hqt
//      Hwpfcogpvcnu
//      Qh
//      Rtqitcookpi
//      ZAB
//      baz
//      EcognEcug
//      FkUeQ
```

---

```java
        "Hi"
        10101
        !--!

        cipherText(file);
// Jk
//
//
```

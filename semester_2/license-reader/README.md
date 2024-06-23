# License Reader

The purpose of this task is to give you some practice reading, processing, and writing to a file using Java Reader/Writer classes.

It should take you about _1.5 hours_ to complete this task. 

## Description

In this task, you will implement `LicenseReader#collectLicenses(File, File)` method. This method reads all license files from the `root`, formats them the same way, and writes them into `outputFile`. `root` can be either a directory or a regular file. Please note that you have to collect files from all the subdirectories in the root directory. All non-license files must be ignored. Keep in mind that this method should not throw checked exceptions.

Take a look at the format of the license header:
```
---
License: <license name>
Issued by: <issuer>
Issued on: <date>
Expires on: <date>
---
```

The order of these properties is not restricted, so you can expect `Expires on` to occur earlier than `License`. All of the properties are mandatory except for `Expires on`. The date format is ISO (`yyyy-mm-dd`). The license is wrapped between lines containing three hyphens (`---`). The file always starts with the license header; otherwise, it's just a regular file.

If the license header is invalid, `IllegalArgumentException` should be thrown. Likewise, if `root` or `outputFile` is `null`, `IllegalArgumentException` should be thrown. If `root` does not exist or is not readable, the same exception should be thrown. And if `root` is a directory but is not executable (meaning that it could not be entered), `IllegalArgumentException` should be thrown.

`IllegalArgumentException` should be thrown in the following cases:
* The license header is invalid
* `root` or `outputFile` is `null`
* `root` does not exist, or it cannot be read
* `root` is a directory but cannot be executed

The output format for each license looks like the following:
```
License for <file name> is <license name> issued by <issuer> [<issuedOn date> - <expiresOn date>]
```

If the license does not have an expiration date, `unlimited` must be used instead of <expiresOn date>.

If `outputFile` exists, it must be cleared since appending is not allowed.


## Requirements

* `collectLicenses` method must verify that `root`:
    * Is not `null`
    * Exists
    * Can be read
    * Can be executed if it is a directory
* The `collectLicenses` method must verify that `outputFile` is not `null`.
* If `outputFile` is not empty, it must be cleared before writing to it.
* All files and directories of the `root` must be processed.
* Non-license files should be ignored.
* License files must be processed, and their info must be stored in the `outputFile` (see the formats in the `Description` section).
* If the license format is invalid or any verification fails, `IllegalArgumentException` should be thrown.
* All license properties are mandatory except for `Expires on`, which is optional.
* The order of license properties is undefined.
* The order in the file containing your result does not matter.
* If `Expires on` is omitted, `unlimited` must be used.
* `collectLicenses` does not throw checked exceptions.

## Examples

libA/libraryA.lic
```
---
License: GNU
Issued by: GNU Commitet
Issued on: 2020-01-01
Expires on: 2025-01-01
---

Library description...
```

libraryB.lic
```
---
License: Custom
Issued by: Goverment
Issued on: 1984-04-13
---

Top secret info...
```

output
```
License for libA/libraryA.lic is GNU issued by GNU Commitet [2020-01-01 - 2025-01-01]
License for libraryB.lic is Custom issued by Goverment [1984-04-13 - unlimited]
```

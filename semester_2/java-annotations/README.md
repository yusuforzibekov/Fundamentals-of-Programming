# Behavior Annotating

The purpose of this task is to practice creating custom annotations and using them in your code.

Duration: _30 minutes_

## Description

In this task, you will define multiple annotations and use them to annotate the provided class - `ImportantClass`.
It does nothing, but it does not matter for this task.
It has a constructor, which accepts `Properties` as a parameter, one field, and one method.

There are total 5 annotations that you must define:
1. `@Documentation` - says that an annotation must be documented by some custom tool.
2. `@ExceptionMapping` - declares the relation between an exception class and HTTP status.
3. `@Mutable` - says that a field or a parameter might be changed during runtime.
4. `@Source` - defines from where the data comes into this parameter, e.g. database, properties file, etc.
5. `@Tracked` - says that a class's activity must be tracked by some custom tool.

## Requirements

* all annotations must be located in the same package as `ImportantClass`
* All annotations must have a `RUNTIME` retention policy

Now here are the type-specific requirements:

### @Documentation

* Must target only annotations
* Must have `@Documented` annotation on it
* Must be inheritable

### @ExceptionMapping

* Must target only methods
* Must be repeatable
* Must be annotated with `@Documentation`
* Must have `int status` parameter
* Must have `String message` parameter
* Must have `Class<? extends Throwable>[] types` parameter

### @Mutable

* Must target fields and parameters
* Must have optional `String reason` parameter
* Default value of the `reason` parameter is an empty string

### @Source

* Must target only constructors
* Must have `Origin origin` parameter
* `Origin` is an enum with the following values:
    * `DB`
    * `PROPERTIES`
    * `SERVER`

### @Tracked

* Must target types
* Must be annotated with `@Documentation`
* Must have `String value` parameter

### ImportantClass

* Must be annotated with `@Tracked` with `important-class-track-number` value
* `properties` field must be annotated with `@Mutable`
* The constructor must be annotated with `@Source`, and its origin must be set to `DB`
* The constructor's parameter must be annotated with `@Mutable` with the reason: `Needs to filter out invalid properties`
* `execute` method must be annotated with 3 `@ExceptionMapping` annotations:
    * The first one must have status - 400, message - "Bad Request",
      and types - `IllegalArgumentException` and `IllegalStateException`
    * The second one must have status - 404, message - "Not Found",
      and types - `FileNotFoundException` (from `java.io` package)
    * The third one must have status - 401, message - "Unauthorized",
      and types - `GeneralSecurityException` (from `java.security` package)
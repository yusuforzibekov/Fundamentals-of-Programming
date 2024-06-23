# Headline Formatting

The purpose of this task is to give you some practice using Java formatting utilities. 

It should take you about _1 hour_ to complete this task. 

## Description

In this task, you will provide various formatting patterns and write a simple template engine. A template engine is a utility that replaces placeholders in a template text with provided values. The binding between a placeholder and its value will be called `reference`. 

First, take a look at the record `ReferencedText`, which has a constructor with two arguments: 

1. `String text` is template text with placeholders. 
2. `Map<String, ReferenceValue> references` refers to a map that contains placeholder names against their values. It has one explicit method, `format(ReferenceFormat)`, which must return processed text, i.e., with replaced placeholders. Implementing it is part of your task. 

Next, go through the interface `ReferenceFormat`, which has some predefined inner classes: 

* enum `Type` specifies the type of placeholder. You will need it to determine how specific values must be processed. 
* record `ReferenceValue` is just a pair made up of `Type` and the values that define the placeholder value. 

Also, this interface has plenty of abstract methods and one default method. Consider the abstract ones first. All of them must return a specific pattern, which is described below. For now, it's enough to understand their purpose: 

* `companyPattern` - returns a pattern for placeholders with `Type` = `COMPANY`
* `personNamePattern` - returns a pattern for placeholders with `Type` = `PERSON_NAME`
* `integerPattern` - returns a pattern for placeholders with `Type` = `INTEGER`
* `doublePattern` - returns a pattern for placeholders with `Type` = `DOUBLE`
* `percentPattern` - returns a pattern for placeholders with `Type` = `PERCENT`
* `dateTimePattern` - returns a pattern for placeholders with `Type` = `DATE_TIME`

You will learn how to implement them later in the requirements section. 

Also, there is the `format(ReferenceValue)` method, which is needed to make the API more fluent and robust. It accepts the `ReferenceValue` object and uses the `Type` of `ReferenceValue` object to determine which pattern must be used to process the passed value. Please be aware that not all types can be formatted using the same API. 

The last class is `ReferenceFormatFactory`. It has two methods: 

* `simpleFormat`
* `formalFormat`

Both of these methods return `ReferenceFormat` instances; they will be described below. 

## Requirements

There are no invalid inputs during testing. 

* `ReferenceText#format` is implemented: 
    * All placeholders are replaced with their corresponding values. 
    * A placeholder looks like `${name}`. 
    * A map of references contains only placeholder names (without ${}). 
    * If a placeholder occurs multiple times in a template text, all of its occurrences must be replaced. 
    * A placeholder must be replaced with a properly formatted value. 
* `ReferenceFormat#format` is implemented: 
    * It defines the proper reference value pattern based on its type and uses it to format the value's parameters. 
* `simpleFormat` is implemented: 
    * The company name must be enclosed in double quotes. 
    * The person's name is written as usual (`first name <space> last name`), but the last name must be uppercase. 
    * Integers must have group separators. 
    * Percentages must always have signs, a precision of exactly one digit, and the percent symbol at the end. 
    * Doubles must have group separators and a precision of two digits. 
    * The date must be converted to `on <date> at <hour> o'clock`. 
        * The date is just the year, month, and day separated by a hyphen (-). The month and day must be preceded by 0. 
        * The hour cannot have any preceding symbols, so if you try to format 7, the result must be exactly 7, not 07. 
* formalFormat is implemented: 
    * The company name must be uppercase and followed by LLC. 
    * The person's name must be written last name and then first name, separated by a comma, in that exact order. 
    * Integers must have a space reserved for a plus sign and a width of at least three symbols. 
    * Doubles must be in scientific form, preceded by a + or - sign, and have a precision of two digits. 
    * Percentages must have a precision of two digits, be followed by the percent symbol, and be preceded by 0 if the percentage is less than 100. 
    * The date must be converted to `at <time> of <date>`. 
        * The date is a short day (with no preceding symbols), the abbreviation for the month (Jan, Feb, etc.), and the year, separated by a space. 
        * Time is the standard hour (two digits) and minutes (two digits) separated by a colon. 

## Examples

```java
String template = """
${name} works in ${company} since ${date}. His salary is ${salary} per year, he spends ${spending} of it on games
and buys at least ${games} games a month.
""";

    ReferencedText refText = new ReferencedText(template,Map.of(
    "name",new ReferenceValue(Type.PERSON_NAME,"John","Doe"),
    "company",new ReferenceValue(Type.COMPANY,"Macrohard"),
    "date",new ReferenceValue(Type.DATE_TIME,LocalDateTime.now()),
    "salary",new ReferenceValue(Type.DOUBLE,450000.50),
    "spending",new ReferenceValue(Type.PERCENT,40.37),
    "games",new ReferenceValue(Type.INTEGER,5)
    ));

    refText.format(ReferenceFormatFactory.simpleFormat());
/* result
John DOE works in "Macrohard" since on 2022-09-30 at 5 o'clock. His salary is 450,000.50 per year, he spends +40.4% of it on games
and buys at least 5 games a month.
 */

    refText.format(ReferenceFormatFactory.formatFormat());
/* result
Doe, John works in MACROHARD LLC since at 05:51 of 30 Sep 2022. His salary is +4.50E+05 per year, he spends 40.37% of it on games
and buys at least   5 games a month.
 */
```

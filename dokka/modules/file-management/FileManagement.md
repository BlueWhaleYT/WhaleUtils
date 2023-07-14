# Module file-management

This module contains code for managing files and directories on the device. It typically includes classes for reading and writing files, creating directories, and managing permissions.

## Get Started

```kt
// Kotlin
val file = FileUtils()
val saf = SAFUtils(context)
```

```java
// Java
FileUtils file = new FileUtils();
SAFUtils saf = new SAFUtils(context);

// Extensions
FileExtKt, SAFExtKt
```

## Examples

```kt
// Kotlin
val saf = SAFUtils(context)
val str1 = saf.readFile(uri)
val str2 = uri.getFileContent() // Extension
```
```java
// Java
SAFUtils saf = new SAFUtils(context);
String str1 = saf.readFile(uri);
String str2 = SAFExtKt.getFileContent(uri); // Extension
```

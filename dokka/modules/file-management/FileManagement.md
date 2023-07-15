# Module file-management

This module contains code for managing files and directories on the device. It typically includes classes for reading and writing files, creating directories, and managing permissions.

There are two types of APIs of this module:

- **Basic File management**: This API is to handle file operations using file paths. It provides a simple and straightforward way to manage files on a local file system. With this module, you can perform common file operations such as creating, deleting, copying, moving, and renaming files. However, this is no longer recommended for Android 11+ devices.

- **SAF File management**: SAF, aka Storage Access Framework, handles file operations using URIs and the Storage Access Framework. The Storage Access Framework is an Android API that allows applications to access files and directories on a device's external storage.

## Get Started

Here are some notes and guides for you to get started.

### Set permission in manifest

Before using the `file-managment` module's APIs, you need to set the following permissions in your project's `AndroidManifest.xml`.

```xml
<!-- not required for SAF -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
```

### Declare instances

Basically, you can just use `SAFUtils` if you're going to use full features with SAF, not the pass techniques by just using file paths.

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

The examples written below, are just for showing the usage of functions and extension functions.

<p class="note danger">
The APIs perhaps get updated but might not be changed instantly here.
</p>

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

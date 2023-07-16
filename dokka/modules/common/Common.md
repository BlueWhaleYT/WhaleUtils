# Module common

This module contains code that is shared among different parts of your application. It typically includes utility classes, constants, and helper functions that can be used across multiple modules.

## Shared Preferences

<p class="note danger">
    SharedPrefsUtils simplifies the way of using SharedPreferences which aims to be used in Java only. (Kotlin can also use it)
    For Kotlin coroutine, please refer to the <a href="https://developer.android.com/topic/libraries/architecture/datastore">DataStore</a>.
</p>

### Get Started

#### Declare a key name

```kt
// Kotlin
val key = "key_name"
```

```java
// Java
String key = "key_name";
```

#### Declare instances

Like the normal usage of `SharedPreferences`, you need to specify a key name for the preference.

```kt
// Kotlin
val pref = SharedPrefsUtils(context, key)
```

```java
// Java
SharedPrefsUtils pref = new SharedPrefsUtils(context, key)
```

## Examples

### Write data

```kt
// Kotlin
val value = "123"
pref.write(key, value)
```

```java
// Java
String value = "123";
pref.write(key, value);
```

### Get data

```kt
// Kotlin
val value = pref.get(key)
```

```java
// Java
String value = pref.get(key, defaultValue);
```
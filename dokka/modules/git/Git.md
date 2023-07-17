# Module git

This module contains code for Git operation. It typically includes classes for committing changes, cloning repository, and handling branches.

<p class="note">
    In this module, <a href="https://git-scm.com/book/sv/v2/Bilaga-B%3A-Embedding-Git-in-your-Applications-JGit">JGit</a> is imported and used as a part of dependency by default. That said, you are not required to import the dependency manually.
</p>

## Get Started

### Declare instances

```kt
// Kotlin
val git = GitUtils(localPath, remotePath)
```

```java
// Java
GitUtils git = new GitUtils(localPath, remotePath);
```

### Declare listeners

```kt
// Kotlin
val listener = object : GitListener {
    override fun onProgress(file: File, progress: Double) {

    }
    override fun onFailure(error: String) {

    }
    override fun onSuccess() {

    }
}
```

```java
// Java
var listener = new GitListener() {
    @Override
    public void onProgress(@NonNull File file, double progress) {
    
    }
    @Override
    public void onFailure(@NonNull String error) {
    
    }
    @Override
    public void onSuccess() {
    
    }
};
```

## Examples

### Clone remote repository to local

```kt
// Kotlin
git.cloneRepository(listener)
```

```java
// Java
git.cloneRepository(listener);
```
# Module git

This module contains code for Git operation. It typically includes classes for committing changes, cloning repository, and handling branches.

<p class="note">
    In this module, <a href="https://git-scm.com/book/sv/v2/Bilaga-B%3A-Embedding-Git-in-your-Applications-JGit">JGit</a> is imported and used as a part of dependency by default. That said, you are not required to import the dependency manually.
</p>

<div class="tabs">
<input type="radio" name="tab-group" id="kt" checked />
<label for="kt">Kotlin</label>
<div class="single-tab">

```kt
val listener = object : GitListener {
    override fun onProgress(file: File, progress: Double) {
        // actions to handle the update of the progress
    }
    override fun onFailure(error: String) {
        // actions to handle the failure of the operation
    }
    override fun onSuccess() {
        // actions to handle the succession of the operation 
    }
}
```

</div>
<input type="radio" name="tab-group" id="java" />
<label for="java">Java</label>
<div class="single-tab">

```java
var listener = new GitListener() {
    @Override
    public void onProgress(@NonNull File file, double progress) {
        // actions to handle the update of the progress
    }
    @Override
    public void onFailure(@NonNull String error) {
        // actions to handle the failure of the operation
    }
    @Override
    public void onSuccess() {
        // actions to handle the succession of the operation 
    }
};
```

</div>
</div>
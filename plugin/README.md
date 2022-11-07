```kotlin
resolutionStrategy {
    eachPlugin {
        if (requested.id.id.startsWith("com.kgit2")) {
            println("Using plugin ${requested.id.id} version ${requested.version}")
            println(project(":plugin"))
            useModule(project(":plugin"))
            // useModule("com.kgit2.bitmask:plugin:1.0.0-SNAPSHOT")
        }
    }
}
```

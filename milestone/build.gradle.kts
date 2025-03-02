// milestone is a container module, not an Android or library module.
plugins {
    `java-library` // ✅ Defines milestone as a non-Android container
}

// No need to add dependencies here! milestone_modules_01 is already included in settings.gradle.kts.

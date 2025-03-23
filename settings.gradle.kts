// Plugin management configuration for Gradle
pluginManagement {
    repositories {
        google {
            content {
                // Includes Android and Google-related dependencies
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral() // Includes dependencies from Maven Central
        gradlePluginPortal() // Includes Gradle plugins
    }
}

// Dependency resolution management
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}


// Root project configuration
rootProject.name = "EduMilestone"

// Including submodules in the project
include(":app") // Main application module
include(":centralcore") // Core module for shared functionality
include(":milestone:modules01") // Milestone Modules 01

// Setting custom directory for the milestone module
project(":milestone:modules01").projectDir = file("milestone/modules01")

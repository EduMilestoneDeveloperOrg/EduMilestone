package com.edumilestone.app.navigation

import android.util.Log
import com.edumilestone.app.features.FeaturesModuleMapping
import com.edumilestone.app.modules.ModuleManager
import com.edumilestone.app.modules.ModuleLoadStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import androidx.navigation.NavController

/**
 * AppNavigation class handles navigation actions based on feature requests.
 * It ensures that the correct module is loaded, and navigates to the appropriate UI screen.
 * This class is responsible for managing both navigation and module loading asynchronously.
 */
class AppNavigation {

    // Initializing currentModuleStatus to failed load status.
    private var currentModuleStatus: ModuleLoadStatus = ModuleLoadStatus.FAILED

    // Creating a CoroutineScope with SupervisorJob to handle concurrent tasks.
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    /**
     * Handles the feature request by checking if the requested feature is mapped to a module.
     * It intelligently manages module loading and navigation to the appropriate UI screen.
     */
    fun handleFeatureRequest(navController: NavController, feature: String) {
        // Launching a coroutine to handle the feature request in an asynchronous manner.
        scope.launch {
            // Getting the module name associated with the requested feature using the FeaturesModuleMapping.
            val moduleName: String? = FeaturesModuleMapping.getModuleForFeature(feature)

            // If no module is found for the feature, navigate to the Home Screen directly.
            if (moduleName == null) {
                navigateToHomeScreen(navController)
                return@launch
            }

            // Getting the active module from the ModuleManager.
            val activeModule = ModuleManager.getActiveModule().value

            // If there's an active module and its name matches the requested module.
            if (activeModule != null && activeModule.moduleName == moduleName) {
                // Get the current module load status from the active module.
                val currentStatus: ModuleLoadStatus = ModuleManager.getModuleLoadStatus().value

                // If the current module is successfully loaded, navigate to the feature UI.
                if (currentStatus == ModuleLoadStatus.SUCCESS) {
                    navigateToFeatureUI(navController, feature)
                    return@launch
                }
            }

            // If the active module doesn't match or the module isn't successfully loaded, reload the module.
            unloadModuleForFeature(feature)
            loadModuleAndNavigate(navController, feature)
        }
    }

    /**
     * Tries to load the module and navigate to the feature UI after the module is successfully loaded.
     * Retries up to 3 times if the loading fails, providing a retry mechanism for better reliability.
     */
    private suspend fun loadModuleAndNavigate(navController: NavController, feature: String) {
        var retryCount = 0
        var success = false

        // Retry loading the module up to 3 times.
        while (retryCount < 3 && !success) {
            // Requesting the ModuleManager to load the module based on the feature request.
            currentModuleStatus = ModuleManager.loadModuleForFeature(feature)
            delay(200) // Wait for a short period before checking the status again
            // Wait until the module is loaded
            while (currentModuleStatus == ModuleLoadStatus.LOADING) {
                delay(500) // Wait for a short period before checking the status again
                currentModuleStatus = ModuleManager.getModuleLoadStatus().value
            }

            // If the module loaded successfully, navigate to the feature UI.
            if (currentModuleStatus == ModuleLoadStatus.SUCCESS) {
                navigateToFeatureUI(navController, feature)
                success = true
            } else {
                // Retry if the module loading failed.
                retryCount++
                Log.e("AppNavigation", "Module load failed. Retrying... ($retryCount/3)")
            }
        }

        // If after 3 retries the module failed to load, navigate to the Home Screen.
        if (!success) {
            Log.e("AppNavigation", "Module load failed after 3 retries. Navigating to Home Screen.")
            navigateToHomeScreen(navController)
        }
    }

    /**
     * Navigates to the requested feature's UI after the module has been successfully loaded.
     * This is triggered once the module is ready and prepared to show the feature UI.
     */
    private fun navigateToFeatureUI(navController: NavController, feature: String) {
        // Navigating to the feature screen using the feature name passed as a route parameter.
        navController.navigate("feature_screen/$feature")
    }

    /**
     * Navigates to the default Home Screen in case of any issues or failures.
     * It also clears the back stack to ensure the user cannot navigate back to previous screens.
     * This function ensures a fresh start whenever navigation to the Home screen is triggered.
     */
    private fun navigateToHomeScreen(navController: NavController) {
        // Navigating to the Home Screen and popping all previous screens from the back stack to prevent back navigation.
        navController.navigate("home_screen") {
            popUpTo("home_screen") { inclusive = true } // Clears the back stack and ensures no back navigation is possible.
        }
    }

    /**
     * Unloads the module based on the feature request.
     */
    fun unloadModuleForFeature(feature: String) {
        scope.launch {
            currentModuleStatus = ModuleManager.unloadModuleForFeature(feature)
            delay(200) // Wait for a short period before checking the status again
            // Wait until the module is unloaded
            while (currentModuleStatus == ModuleLoadStatus.UNLOADING) {
                delay(500) // Wait for a short period before checking the status again
                currentModuleStatus = ModuleManager.getModuleLoadStatus().value
            }

            if (currentModuleStatus == ModuleLoadStatus.UNLOADED) {
                Log.i("AppNavigation", "Module unloaded successfully.")
            } else {
                Log.e("AppNavigation", "Failed to unload module.")
            }
        }
    }
    /**
     * Cleanup function to cancel the coroutine scope when AppNavigation is no longer needed.
     * This ensures there are no active background tasks, prevents memory leaks, and ensures proper cleanup.
     */
    fun cleanup() {
        job.cancel() // Cancels the CoroutineScope to clean up the job and its coroutines, ensuring no background tasks run after cleanup.
    }
}

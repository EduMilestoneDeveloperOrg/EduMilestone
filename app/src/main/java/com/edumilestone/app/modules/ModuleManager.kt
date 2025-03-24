package com.edumilestone.app.modules

import android.util.Log
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import com.edumilestone.modules01.tools.features.ProductToolsMainApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.edumilestone.app.features.FeaturesModuleMapping
import java.io.IOException
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicBoolean // Import AtomicBoolean for atomic updates

// Enum to represent the module load status
enum class ModuleLoadStatus {
    LOADING,   // Module is currently loading
    UNLOADING, // Module Unloading
    UNLOADED,  //Module Unloaded Successfully
    SUCCESS,   // Module loaded successfully
    FAILED,    // Module loading failed
    CRASHED,   // Module has crashed due to an error
    ERROR      // A generic error occurred while loading the module
}
// Data class to store module info (name and load status)
data class ModuleInfo(val moduleName: String, val loadStatus: ModuleLoadStatus)
// The ModuleManager is responsible for loading and unloading modules dynamically based on requests
object ModuleManager {
    private val mutex = Mutex() // Mutex to ensure thread safety during module load/unload
    private val _moduleLoadStatus = MutableStateFlow(ModuleLoadStatus.FAILED) // Tracks module load status
    // StateFlow to track the active module's name and status
    private val _activeModule: MutableStateFlow<ModuleInfo?> = MutableStateFlow(null) // Keeps track of the currently active module
    private val activeModule: StateFlow<ModuleInfo?> = _activeModule // private property since it is only used inside this class
    @JvmStatic
    private val isLoading = AtomicBoolean(false) // Track if a module is currently loading using AtomicBoolean for atomic updates
    @JvmStatic
    private val isUnLoading = AtomicBoolean(false) // Track if a module is currently unloading using AtomicBoolean for atomic updates

    /**
     * ✅ **Loads a Module Based on Module Name**
     * - Handles loading of modules and updates the status.
     * - Ensures only one module is loaded at a time.
     * - Uses **Mutex** for thread-safety to prevent race conditions.
     *
     * @param moduleName The name of the module to load (e.g., "Module01", "Module02").
     * @return The load status of the requested module.
     */
    private suspend fun loadModule(moduleName: String): ModuleLoadStatus {
        // Prevents multiple modules from loading simultaneously
        if (isLoading.get() || isUnLoading.get()) {
            Log.d("ModuleManager", "Module operation already in progress.")
            return ModuleLoadStatus.FAILED
        }

        mutex.withLock {
            if (!isLoading.compareAndSet(false, true)) {
                return ModuleLoadStatus.FAILED
            }
        }

        try {
            Log.d("ModuleManager", "Starting module loading...")

            // Unload the current module only if there is an active module
            _activeModule.value?.let {
                Log.d("ModuleManager", "Unloading current active module: ${it.moduleName}")
                unloadModule(it.moduleName)
                _activeModule.value = null // Ensure no active module after unloading
            }
            mutex.withLock {
                // Check and load the requested module based on its name
                when (moduleName) {
                    "Module01" -> {
                        Log.d("ModuleManager", "🚀 Loading Module 01")
                        // Update the active module state
                        _activeModule.value = ModuleInfo(moduleName, ModuleLoadStatus.LOADING) // Store both module name and status
                        // Call actual initialization for Module01
                        ProductToolsMainApp.initializeModule01()
                    }
                    // Placeholder modules (02-13)
                    "Module02" -> Log.d("ModuleManager", "📌 Loading Future Module 02 - Placeholder")
                    "Module03" -> Log.d("ModuleManager", "📌 Loading Future Module 03 - Placeholder")
                    "Module04" -> Log.d("ModuleManager", "📌 Loading Future Module 04 - Placeholder")
                    "Module05" -> Log.d("ModuleManager", "📌 Loading Future Module 05 - Placeholder")
                    "Module06" -> Log.d("ModuleManager", "📌 Loading Future Module 06 - Placeholder")
                    "Module07" -> Log.d("ModuleManager", "📌 Loading Future Module 07 - Placeholder")
                    "Module08" -> Log.d("ModuleManager", "📌 Loading Future Module 08 - Placeholder")
                    "Module09" -> Log.d("ModuleManager", "📌 Loading Future Module 09 - Placeholder")
                    "Module10" -> Log.d("ModuleManager", "📌 Loading Future Module 10 - Placeholder")
                    "Module11" -> Log.d("ModuleManager", "📌 Loading Future Module 11 - Placeholder")
                    "Module12" -> Log.d("ModuleManager", "📌 Loading Future Module 12 - Placeholder")
                    "Module13" -> Log.d("ModuleManager", "📌 Loading Future Module 13 - Placeholder")
                    else -> {
                        Log.e("ModuleManager", "❌ Invalid module name [$moduleName]. Cannot load.")
                        // Update the active module state
                        _activeModule.value = ModuleInfo(moduleName, ModuleLoadStatus.FAILED) // Store both module name and status
                        return ModuleLoadStatus.FAILED
                    }
                }

                // Update the active module state
                _activeModule.value = ModuleInfo(moduleName, ModuleLoadStatus.SUCCESS) // Store both module name and status
                // Update load status to SUCCESS
                _moduleLoadStatus.value = ModuleLoadStatus.SUCCESS
                Log.d("ModuleManager", "✅ Module [$moduleName] successfully loaded.")
                return ModuleLoadStatus.SUCCESS
            }
        } catch (e: Exception) {
            Log.e("ModuleManager", "❌ Error while loading module [$moduleName]: ${e.message}")
            // Handle specific exceptions
            handleException(e, moduleName)
            return _moduleLoadStatus.value // Return the updated status based on exception
        } finally {
            isLoading.set(false) // Reset loading flag
        }
    }

    /**
     * ✅ **Unloads a Module Efficiently**
     * - Uses **mutex** for thread-safe execution.
     * - Ensures clean memory management before switching modules.
     *
     * @param moduleName The name of the module to unload.
     */
    private suspend fun unloadModule(moduleName: String): ModuleLoadStatus {
        // Prevents multiple modules from Unloading simultaneously
        if (isLoading.get() || isUnLoading.get()) {
            Log.d("ModuleManager", "Module operation already in progress.")
            return ModuleLoadStatus.FAILED
        }

        mutex.withLock {
            if (!isUnLoading.compareAndSet(false, true)) {
                return ModuleLoadStatus.FAILED
            }
        }

        try {
            Log.d("ModuleManager", "❌ Unloading Module [$moduleName]")
            // Unload the current module and handle placeholders for 02-13

            mutex.withLock {
                when (moduleName) {
                    "Module01" -> {
                        Log.d("ModuleManager", "🚀 Unloading Module 01")
                        _activeModule.value = ModuleInfo(moduleName, ModuleLoadStatus.UNLOADING) // Store both module name and status
                        ProductToolsMainApp.cleanupModule01()
                    }
                    "Module02" -> Log.d("ModuleManager", "📌 Unloading Future Module 02 - Placeholder")
                    "Module03" -> Log.d("ModuleManager", "📌 Unloading Future Module 03 - Placeholder")
                    "Module04" -> Log.d("ModuleManager", "📌 Unloading Future Module 04 - Placeholder")
                    "Module05" -> Log.d("ModuleManager", "📌 Unloading Future Module 05 - Placeholder")
                    "Module06" -> Log.d("ModuleManager", "📌 Unloading Future Module 06 - Placeholder")
                    "Module07" -> Log.d("ModuleManager", "📌 Unloading Future Module 07 - Placeholder")
                    "Module08" -> Log.d("ModuleManager", "📌 Unloading Future Module 08 - Placeholder")
                    "Module09" -> Log.d("ModuleManager", "📌 Unloading Future Module 09 - Placeholder")
                    "Module10" -> Log.d("ModuleManager", "📌 Unloading Future Module 10 - Placeholder")
                    "Module11" -> Log.d("ModuleManager", "📌 Unloading Future Module 11 - Placeholder")
                    "Module12" -> Log.d("ModuleManager", "📌 Unloading Future Module 12 - Placeholder")
                    "Module13" -> Log.d("ModuleManager", "📌 Unloading Future Module 13 - Placeholder")
                    else -> {
                        Log.e("ModuleManager", "❌ Invalid module name [$moduleName]. Cannot unload.")
                        // Update the active module state
                        _activeModule.value = ModuleInfo(moduleName, ModuleLoadStatus.FAILED) // Store both module name and status
                        return ModuleLoadStatus.FAILED
                    }
                }
                // Update the active module state
                _activeModule.value = ModuleInfo(moduleName, ModuleLoadStatus.UNLOADED) // Store both module name and status
                // Reset the active module state
                _activeModule.value = null
                Log.d("ModuleManager", "✅ Module [$moduleName] successfully unloaded.")
                // Update load status to UNLOADED
                _moduleLoadStatus.value = ModuleLoadStatus.UNLOADED
                return ModuleLoadStatus.UNLOADED
            }
        } catch (e: Exception) {
            Log.e("ModuleManager", "❌ Error while unloading module [$moduleName]: ${e.message}")
            // Handle specific exceptions
            handleException(e, moduleName)
            return _moduleLoadStatus.value // Return the updated status based on exception
        } finally {
            isUnLoading.set(false) // Reset unloading flag
        }
    }
    /**
     * ✅ **Get Current Module Load Status**
     * - Returns the current module load status.
     */
    fun getModuleLoadStatus(): StateFlow<ModuleLoadStatus> = _moduleLoadStatus
    /**
     * Method to get the current active module info.
     *
     * @return The current module info (module name and load status).
     */
    fun getActiveModule(): StateFlow<ModuleInfo?> = activeModule
    /**
     * ✅ **Load Module Based on Feature Request**
     * - Uses **FeaturesModuleMapping** to dynamically map a feature to its corresponding module.
     * - Ensures the correct module is loaded based on the feature requested.
     *
     * @param feature The feature name (e.g., "OCR", "PDFViewer").
     * @return The load status of the module after checking and loading it.
     */
    suspend fun loadModuleForFeature(feature: String): ModuleLoadStatus {
        // Use FeaturesModuleMapping to get the corresponding module for the feature
        val moduleName = FeaturesModuleMapping.getModuleForFeature(feature)

        if (moduleName != null) {
            // If a module is found for the feature, attempt to load it
            return loadModule(moduleName)
        } else {
            // If no module is found for the feature, log the error and return failed status
            Log.e("ModuleManager", "❌ Feature [$feature] is not mapped to any module.")
            return ModuleLoadStatus.FAILED
        }
    }

    /**
     * ✅ **Unload Module Based on Feature Request**
     * - Uses **FeaturesModuleMapping** to dynamically map a feature to its corresponding module.
     * - Unloads the correct module based on the feature requested.
     *
     * @param feature The feature name (e.g., "OCR", "PDFViewer").
     * @return The load status of the module after unloading it.
     */
    suspend fun unloadModuleForFeature(feature: String): ModuleLoadStatus {
        // Use FeaturesModuleMapping to get the corresponding module for the feature
        val moduleName = FeaturesModuleMapping.getModuleForFeature(feature)

        if (moduleName != null) {
            // If a module is found for the feature, attempt to unload it
            return unloadModule(moduleName)
        } else {
            // If no module is found for the feature, log the error and return failed status
            Log.e("ModuleManager", "❌ Feature [$feature] is not mapped to any module.")
            return ModuleLoadStatus.FAILED
        }
    }

    /**
     * Handles specific exceptions during module load/unload.
     *
     * @param e The exception that occurred.
     * @param moduleName The name of the module being loaded/unloaded.
     */
    private fun handleException(e: Exception, moduleName: String) {
        when (e) {
            is IOException -> {
                _activeModule.value = ModuleInfo(moduleName, ModuleLoadStatus.FAILED)
                _moduleLoadStatus.value = ModuleLoadStatus.FAILED
                Log.e("ModuleManager", "❌ I/O Error occurred while loading/unloading the module.")
            }

            is TimeoutException -> {
                _activeModule.value = ModuleInfo(moduleName, ModuleLoadStatus.FAILED)
                _moduleLoadStatus.value = ModuleLoadStatus.FAILED
                Log.e("ModuleManager", "❌ Timeout occurred while loading/unloading the module.")
            }

            is IllegalStateException -> {
                _activeModule.value = ModuleInfo(moduleName, ModuleLoadStatus.ERROR)
                _moduleLoadStatus.value = ModuleLoadStatus.ERROR
                Log.e("ModuleManager", "❌ Illegal state while loading/unloading the module.")
            }

            is NullPointerException -> {
                _activeModule.value = ModuleInfo(moduleName, ModuleLoadStatus.CRASHED)
                _moduleLoadStatus.value = ModuleLoadStatus.CRASHED
                Log.e(
                    "ModuleManager",
                    "❌ Null pointer exception occurred while loading/unloading the module."
                )
            }

            else -> {
                _activeModule.value = ModuleInfo(moduleName, ModuleLoadStatus.CRASHED)
                _moduleLoadStatus.value = ModuleLoadStatus.CRASHED
                Log.e("ModuleManager", "❌ Unknown error: ${e.localizedMessage}")
            }
        }
    }
}

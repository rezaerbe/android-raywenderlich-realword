package com.erbe.petsavesecurity.core.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Debug
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.*

class WatchDog {

    fun deviceCompromised(): Boolean {
        var tags = Build.TAGS
        tags = if (tags != null && tags.trim().isNotEmpty()) {
            tags.toLowerCase(Locale.US)
        } else {
            ""
        }

        var brand = Build.BRAND
        brand = if (brand != null && brand.trim().isNotEmpty()) {
            brand.toLowerCase(Locale.US)
        } else {
            ""
        }

        var device = Build.DEVICE
        device = if (device != null && device.trim().isNotEmpty()) {
            device.toLowerCase(Locale.US)
        } else {
            ""
        }

        var manufactuer = Build.MANUFACTURER
        manufactuer = if (manufactuer != null && manufactuer.trim().isNotEmpty()) {
            manufactuer.toLowerCase(Locale.US)
        } else {
            ""
        }

        var model = Build.MODEL
        model = if (model != null && model.trim().isNotEmpty()) {
            model.toLowerCase(Locale.US)
        } else {
            ""
        }

        var product = Build.PRODUCT
        product = if (product != null && product.trim().isNotEmpty()) {
            product.toLowerCase(Locale.US)
        } else {
            ""
        }

        var hardware = Build.HARDWARE
        hardware = if (hardware != null && hardware.trim().isNotEmpty()) {
            hardware.toLowerCase(Locale.US)
        } else {
            ""
        }

        if (tags.contains("test-keys") ||
            model.contains("google_sdk") ||
            model.contains("android sdk built for x86") ||
            model.contains("emulator") ||
            model.contains("droid4x") ||
            model.contains("andy") ||
            model.contains("tiantianvm") ||

            product.contains("vbox86p") ||
            product.contains("emulator") ||
            product.contains("simulator") ||
            product.contains("sdk") ||
            product.contains("nox") ||
            product.contains("andy") ||
            product.contains("droid4x") ||
            product.contains("ttvm_hdragon") ||

            manufactuer.contains("genymotion") ||
            manufactuer.contains("nox") ||
            manufactuer.contains("andy") ||
            manufactuer.contains("tiantianvm") ||

            hardware.contains("ranchu") ||
            hardware.contains("vbox86") ||
            hardware.contains("nox") ||
            hardware.contains("goldfish") ||

            (brand.startsWith("generic") && device.startsWith("generic"))
        ) {

            return true
        }
        return false
    }

    fun environmentCompromised(): Boolean {
        val paths = arrayOf(
            // rooted
            "/system/xbin/su",
            "/system/bin/failsafe/su",
            "/system/bin/su",
            "/system/sd/xbin/su",
            "/sbin/su",
            "/su/bin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/data/local/su",
            "/system/app/Superuser.apk",

            // genymotion
            "/dev/socket/genyd",
            "/dev/socket/baseband_genyd",

            // nox
            "fstab.nox",
            "init.nox.rc",
            "ueventd.nox.rc",

            // andy
            "fstab.andy",
            "ueventd.andy.rc",

            // pipe
            "/dev/socket/qemud",
            "/dev/qemu_pipe",

            // x86
            "ueventd.android_x86.rc",
            "x86.prop",
            "ueventd.ttVM_x86.rc",
            "init.ttVM_x86.rc",
            "fstab.ttVM_x86",
            "fstab.vbox86",
            "init.vbox86.rc",
            "ueventd.vbox86.rc"
        )

        for (path in paths) {
            if (File(path).exists()) {
                return true
            }
        }

        return false
    }

    fun superUserInstalled(): Boolean {
        var process: Process? = null
        var rooted = false
        try {
            process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            if (bufferedReader.readLine() != null) {
                rooted = true
            }
        } catch (t: Throwable) {
        } finally {
            process?.destroy()
        }
        return rooted
    }

    fun isDebuggerConnected() = Debug.isDebuggerConnected()

    fun isEmulator() = Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MODEL.contains("Emulator")
            || Build.BOARD == "QC_Reference_Phone" //bluestacks
            || Build.MANUFACTURER.contains("Genymotion")
            || Build.HOST.startsWith("Build") //MSI App Player
            || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
            || "google_sdk" == Build.PRODUCT

    //Many emulators do not have Google Play

    fun googlePlayStoreEnabled(packageManager: PackageManager): Boolean {
        return try {
            val appInfo = packageManager.getApplicationInfo("com.android.vending", 0)
            appInfo.enabled
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            false
        } catch (e: NullPointerException) {
            e.printStackTrace()
            false
        }
    }

    fun googlePlayServicesEnabled(context: Context, onlyAllowLatestVersion: Boolean): Boolean {
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
        return if (onlyAllowLatestVersion) {
            status == ConnectionResult.SUCCESS
        } else {
            status == ConnectionResult.SUCCESS ||
                    status == ConnectionResult.SERVICE_UPDATING ||
                    status == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED
        }
    }
}
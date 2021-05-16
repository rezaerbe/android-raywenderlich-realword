package com.erbe.petsavemultimodule

import com.erbe.logging.Logger
import com.google.android.play.core.splitcompat.SplitCompatApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PetSaveApplication : SplitCompatApplication() {

    // initiate analytics, crashlytics, etc

    override fun onCreate() {
        super.onCreate()

        initLogger()
    }

    private fun initLogger() {
        Logger.init()
    }
}
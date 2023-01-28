package pl.pgorny.pixabayapp

import android.app.Application
import timber.log.Timber

class PixabayApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}
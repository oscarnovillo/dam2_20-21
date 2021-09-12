package com.example.listacompra.framework

import android.app.Application
import com.example.listacompra.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ListaComprasApp: Application()
{
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree(){

                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, "--$tag", message, t)
                }

                override fun createStackElementTag(element: StackTraceElement): String? {
                    return super.createStackElementTag(element)
                }
            })
        }
    }
}
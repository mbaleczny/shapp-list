package com.mbaleczny.shapp_list

import android.app.Application

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    companion object {
        private var INSTANCE: App? = null

        @JvmStatic
        fun get(): App = INSTANCE!!
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initDependencies()
    }

    private fun initDependencies() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
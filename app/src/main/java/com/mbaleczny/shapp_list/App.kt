package com.mbaleczny.shapp_list

import android.app.Application
import com.mbaleczny.shapp_list.data.DaggerDataComponent
import com.mbaleczny.shapp_list.data.DataComponent

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
class App : Application() {

    private lateinit var dataComponent: DataComponent

    override fun onCreate() {
        super.onCreate()
        initDependencies()
    }

    private fun initDependencies() {
        dataComponent = DaggerDataComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun getDataComponent(): DataComponent = dataComponent
}
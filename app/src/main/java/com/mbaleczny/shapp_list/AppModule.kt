package com.mbaleczny.shapp_list

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
@Module
class AppModule(private val appContext: Application) {

    @Provides
    @Singleton
    fun context(): Context = appContext
}
package com.mbaleczny.shapp_list

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
@Module
abstract class AppModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideContext(app: App): Context {
            return app
        }
    }
}
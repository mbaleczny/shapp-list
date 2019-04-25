package com.mbaleczny.shapp_list.util

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
@Module
class SchedulerModule {

    @Provides
    @Singleton
    fun schedulerProvider(): SchedulerProvider = AndroidSchedulerProvider()
}
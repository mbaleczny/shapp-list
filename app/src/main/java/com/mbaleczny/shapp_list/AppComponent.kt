package com.mbaleczny.shapp_list

import com.mbaleczny.shapp_list.data.DbModule
import com.mbaleczny.shapp_list.data.repo.ShoppingListRepository
import com.mbaleczny.shapp_list.util.SchedulerModule
import com.mbaleczny.shapp_list.util.SchedulerProvider
import dagger.Component
import javax.inject.Singleton

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
@Singleton
@Component(modules = [AppModule::class, SchedulerModule::class, DbModule::class])
interface AppComponent {

    fun provideShoppingListRepository(): ShoppingListRepository

    fun provideSchedulerProvider(): SchedulerProvider
}
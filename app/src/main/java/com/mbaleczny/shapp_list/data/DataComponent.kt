package com.mbaleczny.shapp_list.data

import com.mbaleczny.shapp_list.AppModule
import com.mbaleczny.shapp_list.data.repo.ShoppingListRepository
import dagger.Component
import javax.inject.Singleton

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
@Singleton
@Component(modules = [AppModule::class, DbModule::class])
interface DataComponent {

    fun provideShoppingListRepository(): ShoppingListRepository
}
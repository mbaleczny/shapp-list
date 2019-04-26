package com.mbaleczny.shapp_list.util

import com.mbaleczny.shapp_list.ui.list.ShoppingListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Mariusz Baleczny
 * @date 26/04/19
 */
@Module
abstract class AndroidBindingModule {

    @ContributesAndroidInjector
    abstract fun shoppingListFragment(): ShoppingListFragment
}
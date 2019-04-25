package com.mbaleczny.shapp_list.ui.list

import com.mbaleczny.shapp_list.AppComponent
import com.mbaleczny.shapp_list.ui.base.FragmentScope
import dagger.Component

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
@FragmentScope
@Component(
    modules = [ShoppingListModule::class],
    dependencies = [AppComponent::class]
)
interface ShoppingListComponent {

    fun inject(fragment: ShoppingListFragment)
}
package com.mbaleczny.shapp_list.ui.list

import dagger.Module
import dagger.Provides

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
@Module
class ShoppingListModule(
    private val view: ShoppingListContract.View,
    private val isArchived: Boolean
) {

    @Provides
    fun provideView(): ShoppingListContract.View = view

    @Provides
    fun provideIsArchived(): Boolean = isArchived

    @Provides
    fun providePresenter(presenter: ShoppingListPresenter): ShoppingListContract.Presenter = presenter
}
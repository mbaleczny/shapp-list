package com.mbaleczny.shapp_list.ui.list

import dagger.Binds
import dagger.Module

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
@Module
abstract class ShoppingListModule {

    @Binds
    abstract fun providePresenter(presenter: ShoppingListPresenter): ShoppingListContract.Presenter
}
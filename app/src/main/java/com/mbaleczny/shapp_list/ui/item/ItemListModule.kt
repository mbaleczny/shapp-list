package com.mbaleczny.shapp_list.ui.item

import dagger.Binds
import dagger.Module

/**
 * @author Mariusz Baleczny
 * @date 26/04/19
 */
@Module
abstract class ItemListModule {

    @Binds
    abstract fun provideItemListPresenter(presenter: ItemListPresenter): ItemListContract.Presenter
}
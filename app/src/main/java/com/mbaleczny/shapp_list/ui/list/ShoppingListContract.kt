package com.mbaleczny.shapp_list.ui.list

import com.mbaleczny.shapp_list.data.model.ShoppingList
import com.mbaleczny.shapp_list.ui.base.BasePresenter

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
interface ShoppingListContract {

    interface View {

        fun clearLists()

        fun stopLoadingIndicator()

        fun startLoadingIndicator()

        fun showLists(lists: List<ShoppingList>)

        fun showEmptyListView(show: Boolean)

        fun showErrorMessageView(message: String)

        fun isOffScreen(): Boolean
    }

    interface Presenter : BasePresenter<View> {

        fun loadShoppingLists()

        fun createShoppingList(title: String)
    }
}
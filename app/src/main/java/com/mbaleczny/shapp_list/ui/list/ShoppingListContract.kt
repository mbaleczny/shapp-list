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

        fun showLists(lists: List<ShoppingList>)

        fun showEmptyListView(show: Boolean)

        fun showErrorMessageView(message: String)

        fun isVisible(): Boolean
    }

    interface Presenter : BasePresenter<View> {

        fun loadShoppingLists(archived: Boolean)
    }
}
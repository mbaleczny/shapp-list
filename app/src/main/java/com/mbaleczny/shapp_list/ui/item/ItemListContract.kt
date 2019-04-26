package com.mbaleczny.shapp_list.ui.item

import com.mbaleczny.shapp_list.data.model.Item
import com.mbaleczny.shapp_list.ui.base.BasePresenter

/**
 * @author Mariusz Baleczny
 * @date 26/04/19
 */
interface ItemListContract {

    interface View {

        fun showAddButton(show: Boolean)

        fun clearLists()

        fun stopLoadingIndicator()

        fun startLoadingIndicator()

        fun showLists(lists: List<Item>)

        fun showEmptyListView(show: Boolean)

        fun showErrorMessageView(message: String)

        fun hideArchiveMenuItem()

        fun closeView()
    }

    interface Presenter : BasePresenter<View> {

        fun loadItemList()

        fun setShoppingListId(id: Long)

        fun createItem(name: String)

        fun isListArchived(): Boolean

        fun archiveList()

        fun deleteList()
    }
}
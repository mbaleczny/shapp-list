package com.mbaleczny.shapp_list.ui.list

import android.os.Bundle
import com.mbaleczny.shapp_list.App
import com.mbaleczny.shapp_list.data.model.ShoppingList
import com.mbaleczny.shapp_list.ui.base.BaseListFragment
import javax.inject.Inject

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
class ShoppingListFragment : BaseListFragment(), ShoppingListContract.View {

    @Inject
    lateinit var presenter: ShoppingListContract.Presenter

    companion object {
        fun newInstance(): ShoppingListFragment {
            return ShoppingListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    private fun injectDependencies() {
        DaggerShoppingListComponent.builder()
            .shoppingListModule(ShoppingListModule(this))
            .appComponent(App.get().appComponent)
            .build().inject(this)
    }

    override fun clearLists() {
        if (isVisible) {
            // TODO
        }
    }

    override fun stopLoadingIndicator() {
        // TODO
    }

    override fun showLists(lists: List<ShoppingList>) {
        // TODO
    }

    override fun showEmptyListView(show: Boolean) {
        // TODO
    }

    override fun showErrorMessageView(message: String) {
        // TODO
    }
}
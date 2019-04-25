package com.mbaleczny.shapp_list.ui.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbaleczny.shapp_list.App
import com.mbaleczny.shapp_list.data.model.ShoppingList
import com.mbaleczny.shapp_list.ui.base.BaseListFragment
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
class ShoppingListFragment : BaseListFragment(), ShoppingListContract.View {

    @Inject
    lateinit var presenter: ShoppingListContract.Presenter

    private lateinit var adapter: ShoppingListAdapter

    companion object {
        private const val IS_ARCHIVED = "is_archived"

        fun newInstance(archived: Boolean): ShoppingListFragment {
            return ShoppingListFragment().apply {
                arguments = Bundle().apply { putBoolean(IS_ARCHIVED, archived) }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        adapter = ShoppingListAdapter(arrayListOf())
        shopping_list_recycler.layoutManager = LinearLayoutManager(context)
        shopping_list_recycler.adapter = adapter
        shopping_list_recycler.itemAnimator = DefaultItemAnimator()

        refresh.setOnRefreshListener { presenter.loadShoppingLists(arguments?.getBoolean(IS_ARCHIVED)) }
        if (arguments?.getBoolean(IS_ARCHIVED) == true) {
            add_list_button.hide()
        }
    }

    override fun clearLists() {
        adapter.clearData()
    }

    override fun stopLoadingIndicator() {
        if (refresh.isRefreshing) {
            refresh.isRefreshing = false
        }
    }

    override fun showLists(lists: List<ShoppingList>) {
        adapter.replaceData(lists)
    }

    override fun showEmptyListView(show: Boolean) {
        empty_list_indicator.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showErrorMessageView(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun isOffScreen(): Boolean = !isVisible

    private fun injectDependencies() {
        DaggerShoppingListComponent.builder()
            .shoppingListModule(ShoppingListModule(this, arguments?.getBoolean(IS_ARCHIVED) == true))
            .appComponent(App.get().appComponent)
            .build().inject(this)
    }
}
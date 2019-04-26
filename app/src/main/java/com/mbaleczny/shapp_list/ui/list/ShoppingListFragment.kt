package com.mbaleczny.shapp_list.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbaleczny.shapp_list.R
import com.mbaleczny.shapp_list.data.model.ShoppingList
import com.mbaleczny.shapp_list.ui.add.AddItemDialogFragment
import com.mbaleczny.shapp_list.ui.add.OnAddItemListener
import com.mbaleczny.shapp_list.ui.base.RecyclerViewListener
import com.mbaleczny.shapp_list.ui.item.ItemListActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.base_list.*
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
class ShoppingListFragment : DaggerFragment(), ShoppingListContract.View, OnAddItemListener {

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
        presenter.setArchived(arguments?.getBoolean(IS_ARCHIVED) == true)
        presenter.attachView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.base_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        adapter = ShoppingListAdapter(arrayListOf())
        adapter.itemClickListener = object : RecyclerViewListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val item = adapter.getItem(position)
                startActivity(ItemListActivity.startIntent(context, item.id, item.title))
            }
        }

        list_recycler.layoutManager = LinearLayoutManager(context)
        list_recycler.adapter = adapter
        list_recycler.itemAnimator = DefaultItemAnimator()

        refresh.setOnRefreshListener { presenter.loadShoppingLists() }
        if (arguments?.getBoolean(IS_ARCHIVED) == true) {
            add_button.hide()
        } else {
            add_button.setOnClickListener { displayAddShoppingListDialog() }
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

    override fun startLoadingIndicator() {
        refresh.post { refresh.isRefreshing = true }
    }

    override fun showLists(lists: List<ShoppingList>) {
        adapter.replaceData(lists)
    }

    override fun showEmptyListView(show: Boolean) {
        empty_list_indicator.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showErrorMessageView(message: String) {
        toast(message).show()
    }

    override fun addItem(value: String) {
        presenter.createShoppingList(value)
    }

    private fun displayAddShoppingListDialog() {
        val title = context?.getString(R.string.create_shopping_list_title)
        val hint = context?.getString(R.string.title)

        fragmentManager?.let { fm ->
            AddItemDialogFragment.newInstance(title, hint).apply {
                onAddItemListener = this@ShoppingListFragment
                show(fm, AddItemDialogFragment.TAG)
            }
        }
    }
}
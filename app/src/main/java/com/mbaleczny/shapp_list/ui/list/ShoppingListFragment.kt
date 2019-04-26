package com.mbaleczny.shapp_list.ui.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbaleczny.shapp_list.R
import com.mbaleczny.shapp_list.data.model.ShoppingList
import com.mbaleczny.shapp_list.ui.add.AddItemDialogFragment
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
        const val ADD_LIST_REQUEST_CODE = 358
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        adapter = ShoppingListAdapter(arrayListOf())
        shopping_list_recycler.layoutManager = LinearLayoutManager(context)
        shopping_list_recycler.adapter = adapter
        shopping_list_recycler.itemAnimator = DefaultItemAnimator()

        refresh.setOnRefreshListener { presenter.loadShoppingLists() }
        if (arguments?.getBoolean(IS_ARCHIVED) == true) {
            add_list_button.hide()
        } else {
            add_list_button.setOnClickListener { displayAddShoppingListDialog() }
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

    private fun displayAddShoppingListDialog() {
        val title = context?.getString(R.string.create_shopping_list_title)
        val hint = context?.getString(R.string.title)

        val dialog = AddItemDialogFragment.newInstance(title, hint)
        dialog.setTargetFragment(this, ADD_LIST_REQUEST_CODE)

        fragmentManager?.apply {
            dialog.show(this, AddItemDialogFragment.TAG)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_LIST_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(AddItemDialogFragment.RESULT)?.apply {
                presenter.createShoppingList(this)
            }
        }
    }

    override fun startLoadingIndicator() {
        refresh.post { refresh.isRefreshing = true }
    }
}
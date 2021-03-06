package com.mbaleczny.shapp_list.ui.item

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbaleczny.shapp_list.R
import com.mbaleczny.shapp_list.data.model.Item
import com.mbaleczny.shapp_list.ui.add.AddItemDialogFragment
import com.mbaleczny.shapp_list.ui.add.OnAddItemListener
import com.mbaleczny.shapp_list.ui.base.RecyclerViewListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.base_list.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import javax.inject.Inject

/**
 * @author Mariusz Baleczny
 * @date 26/04/19
 */
class ItemListActivity : DaggerAppCompatActivity(), ItemListContract.View, OnAddItemListener {

    companion object {
        const val SHOPPING_LIST_ID = "shopping_list_id"
        const val SHOPPING_LIST_TITLE = "shopping_list_title"

        fun startIntent(context: Context?, id: Long?, title: String?): Intent =
            Intent(context, ItemListActivity::class.java)
                .apply {
                    putExtra(SHOPPING_LIST_ID, id)
                    putExtra(SHOPPING_LIST_TITLE, title)
                }
    }

    @Inject
    lateinit var presenter: ItemListContract.Presenter

    private lateinit var adapter: ItemListAdapter

    private var archiveMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_list)
        presenter.attachView(this)

        if (intent.hasExtra(SHOPPING_LIST_ID) && intent.hasExtra(SHOPPING_LIST_TITLE)) {
            val title = intent.getStringExtra(SHOPPING_LIST_TITLE)
            setTitle(title)

            val id = intent.getLongExtra(SHOPPING_LIST_ID, 0L)
            presenter.setShoppingListId(id)

            setupViews()
        } else {
            toast("Internal error occurred").show()
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_list_menu, menu)
        archiveMenuItem = menu?.findItem(R.id.archive)?.apply {
            isVisible = !presenter.run { isListArchived() }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.archive ->
                showConfirmationAlert(R.string.archive_list) { presenter.archiveList() }

            R.id.delete ->
                showConfirmationAlert(R.string.delete_list) { presenter.deleteList() }

            else -> toast("Unsupported menu item!")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showAddButton(show: Boolean) {
        when (show) {
            true -> add_button.show()
            else -> add_button.hide()
        }
    }

    override fun addItem(value: String) {
        presenter.createItem(value)
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
        if (!refresh.isRefreshing) {
            refresh.isRefreshing = true
        }
    }

    override fun showLists(lists: List<Item>, archivedList: Boolean) {
        adapter.archived = archivedList
        adapter.replaceData(lists)
    }

    override fun showEmptyListView(show: Boolean) {
        empty_list_indicator.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showErrorMessageView(message: String) {
        toast(message).show()
    }

    override fun hideArchiveMenuItem() {
        archiveMenuItem?.isVisible = false
    }

    override fun closeView() {
        finish()
    }

    private fun setupViews() {
        adapter = ItemListAdapter(arrayListOf(), presenter.isListArchived())
        adapter.deleteItemClickListener = object : RecyclerViewListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val title = presenter.getItemName(position)?.let { String.format(getString(R.string.delete_item), it) }
                    ?: getString(R.string.delete_item)
                showConfirmationAlert(title) { presenter.deleteItem(position) }
            }
        }

        list_recycler.layoutManager = LinearLayoutManager(this)
        list_recycler.adapter = adapter
        list_recycler.itemAnimator = DefaultItemAnimator()

        refresh.setOnRefreshListener { presenter.loadItemList() }

        add_button.setOnClickListener { displayAddItemDialog() }
    }

    private fun displayAddItemDialog() {
        val title = getString(R.string.create_item_title)
        val hint = getString(R.string.name)

        val dialog = AddItemDialogFragment.newInstance(title, hint)
        dialog.onAddItemListener = this@ItemListActivity

        dialog.show(supportFragmentManager, AddItemDialogFragment.TAG)
    }

    private fun showConfirmationAlert(@StringRes title: Int, action: () -> Unit) =
        showConfirmationAlert(getString(title), action)

    private fun showConfirmationAlert(title: String, action: () -> Unit) {
        alert(getString(R.string.are_you_sure), title) {
            yesButton { action.invoke() }
            noButton { it.dismiss() }
        }.show()
    }
}
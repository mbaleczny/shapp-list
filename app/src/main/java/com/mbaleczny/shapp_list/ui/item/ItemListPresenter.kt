package com.mbaleczny.shapp_list.ui.item

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.mbaleczny.shapp_list.data.model.Item
import com.mbaleczny.shapp_list.data.model.ShoppingListAndItems
import com.mbaleczny.shapp_list.data.repo.ShoppingListRepository
import com.mbaleczny.shapp_list.util.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author Mariusz Baleczny
 * @date 26/04/19
 */
class ItemListPresenter @Inject constructor(
    private val repository: ShoppingListRepository,
    private val schedulerProvider: SchedulerProvider
) :
    ItemListContract.Presenter, LifecycleObserver {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private lateinit var view: ItemListContract.View
    private var shoppingListId: Long? = null
    private var shoppingListAndItems: ShoppingListAndItems? = null

    override fun attachView(v: ItemListContract.View) {
        view = v
        if (view is LifecycleOwner) {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        }
    }

    override fun setShoppingListId(id: Long) {
        shoppingListId = id
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onAttach() {
        loadItemList()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onDetach() {
        disposable.clear()
    }

    override fun loadItemList() {
        view.clearLists()

        shoppingListId?.let { id ->
            disposable.add(
                repository.getShoppingListWithItems(id)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(this::handleReturnedData, this::handleError)
            )
        }
    }

    override fun createItem(name: String) {
        disposable.add(
            Completable.fromAction { repository.addItem(Item(null, shoppingListId, name)) }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe { loadItemList() }
        )
    }

    override fun archiveList() {
        shoppingListAndItems?.shoppingList?.copy(archived = true)
            ?.let {
                disposable.add(
                    Completable.fromAction { repository.updateShoppingList(it) }
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe {
                            view.hideArchiveMenuItem()
                            loadItemList()
                        }
                )
            }
    }

    override fun isListArchived(): Boolean = shoppingListAndItems?.shoppingList?.archived ?: false

    override fun deleteList() {
        shoppingListAndItems?.shoppingList
            ?.let {
                disposable.add(
                    Completable.fromAction { repository.deleteShoppingList(it) }
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe { view.closeView() }
                )
            }
    }

    private fun handleReturnedData(list: ShoppingListAndItems) {
        view.stopLoadingIndicator()

        shoppingListAndItems = list
        view.showAddButton(!list.shoppingList.archived)
        view.showEmptyListView(list.items.isEmpty())
        list.items
            .takeIf { it.isNotEmpty() }
            ?.let { view.showLists(it) }
    }

    private fun handleError(throwable: Throwable) {
        view.stopLoadingIndicator()
        view.showErrorMessageView(throwable.localizedMessage)
    }
}
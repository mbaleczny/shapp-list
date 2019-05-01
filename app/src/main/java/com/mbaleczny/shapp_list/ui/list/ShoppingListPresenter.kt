package com.mbaleczny.shapp_list.ui.list

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.mbaleczny.shapp_list.data.model.ShoppingList
import com.mbaleczny.shapp_list.data.repo.ShoppingListRepository
import com.mbaleczny.shapp_list.util.SchedulerProvider
import com.mbaleczny.shapp_list.util.applySchedulers
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
class ShoppingListPresenter @Inject constructor(
    private val repository: ShoppingListRepository,
    private val schedulerProvider: SchedulerProvider
) :
    ShoppingListContract.Presenter, LifecycleObserver {

    private val disposable: CompositeDisposable = CompositeDisposable()

    private lateinit var view: ShoppingListContract.View
    private var isArchived: Boolean = false

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onAttach() {
        loadShoppingLists()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onDetach() {
        disposable.clear()
    }

    override fun attachView(v: ShoppingListContract.View) {
        view = v
        if (view is LifecycleOwner) {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        }
    }

    override fun setArchived(archived: Boolean) {
        isArchived = archived
    }

    override fun loadShoppingLists() {
        view.clearLists()

        disposable.add(
            repository.getAllShoppingLists(isArchived)
                .applySchedulers(schedulerProvider)
                .subscribe(this::handleReturnedData, this::handleError, view::stopLoadingIndicator)
        )
    }

    override fun createShoppingList(title: String) {
        view.startLoadingIndicator()
        disposable.add(
            Completable.fromAction { repository.addShoppingList(ShoppingList(null, false, title)) }
                .applySchedulers(schedulerProvider)
                .subscribe(this::loadShoppingLists, this::handleError)
        )
    }

    private fun handleReturnedData(list: List<ShoppingList>) {
        view.stopLoadingIndicator()

        view.showEmptyListView(list.isEmpty())
        list.takeIf { it.isNotEmpty() }?.let {
            view.showLists(it)
        }
    }

    private fun handleError(throwable: Throwable) {
        view.stopLoadingIndicator()
        view.showErrorMessageView(throwable.localizedMessage)
    }
}
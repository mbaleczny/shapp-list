package com.mbaleczny.shapp_list.ui.list

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.mbaleczny.shapp_list.data.model.ShoppingList
import com.mbaleczny.shapp_list.data.repo.ShoppingListRepository
import com.mbaleczny.shapp_list.util.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
class ShoppingListPresenter @Inject constructor(
    private val isArchived: Boolean,
    private val view: ShoppingListContract.View,
    private val repository: ShoppingListRepository,
    private val schedulerProvider: SchedulerProvider
) :
    ShoppingListContract.Presenter, LifecycleObserver {

    private val disposable: CompositeDisposable

    init {
        if (view is LifecycleOwner) {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        }
        disposable = CompositeDisposable()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onAttach() {
        loadShoppingLists()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onDetach() {
        disposable.clear()
    }

    override fun loadShoppingLists() {
        if (view.isOffScreen()) {
            return
        }
        view.clearLists()

        disposable.add(
            repository.getAllShoppingLists(isArchived)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::handleReturnedData, this::handleError, view::stopLoadingIndicator)
        )
    }

    override fun createShoppingList(title: String) {
        view.startLoadingIndicator()
        disposable.add(
            Completable.fromAction { repository.addShoppingList(ShoppingList(null, false, title)) }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
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
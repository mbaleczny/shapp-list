package com.mbaleczny.shapp_list.ui.list

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.mbaleczny.shapp_list.data.model.ShoppingList
import com.mbaleczny.shapp_list.data.repo.ShoppingListRepository
import com.mbaleczny.shapp_list.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
class ShoppingListPresenter @Inject constructor(
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
        loadShoppingLists(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onDetach() {
        disposable.clear()
    }

    override fun loadShoppingLists(archived: Boolean) {
        if (!view.isVisible()) {
            return
        }
        view.clearLists()

        disposable.add(
            repository.getAllShoppingLists(archived)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::handleReturnedData, this::handleError, view::stopLoadingIndicator)
        )
    }

    private fun handleReturnedData(list: List<ShoppingList>) {
        view.stopLoadingIndicator()

        if (list.isEmpty()) {
            view.showEmptyListView(true)
        } else {
            view.showLists(list)
        }
    }

    private fun handleError(throwable: Throwable) {
        view.stopLoadingIndicator()
        view.showErrorMessageView(throwable.localizedMessage)
    }
}
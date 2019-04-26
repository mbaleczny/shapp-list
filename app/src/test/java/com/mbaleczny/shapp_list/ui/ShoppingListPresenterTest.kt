package com.mbaleczny.shapp_list.ui

import com.mbaleczny.shapp_list.data.model.ShoppingList
import com.mbaleczny.shapp_list.data.repo.ShoppingListRepository
import com.mbaleczny.shapp_list.ui.list.ShoppingListContract
import com.mbaleczny.shapp_list.ui.list.ShoppingListPresenter
import com.mbaleczny.shapp_list.util.TestSchedulerProvider
import com.nhaarman.mockitokotlin2.any
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.never
import org.mockito.MockitoAnnotations

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
class ShoppingListPresenterTest {

    @Mock
    private lateinit var view: ShoppingListContract.View
    @Mock
    private lateinit var repository: ShoppingListRepository

    private lateinit var testScheduler: TestScheduler
    private lateinit var presenter: ShoppingListPresenter

    companion object {
        private val CURRENT_SHOPPING_LIST = arrayListOf(
            ShoppingList(null, false, "List1"),
            ShoppingList(null, false, "List2")
        )

        private val EMPTY_SHOPPING_LIST = emptyList<ShoppingList>()
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        presenter = ShoppingListPresenter(false, view, repository, TestSchedulerProvider(testScheduler))
    }

    @Test
    fun loadData_ShouldAlwaysStopLoadingIndicator() {
        given(repository.getAllShoppingLists(false)).willReturn(Flowable.just(EMPTY_SHOPPING_LIST))

        presenter.loadShoppingLists()
        testScheduler.triggerActions()

        then(view).should(atLeastOnce()).stopLoadingIndicator()
    }

    @Test
    fun loadData_ShouldNotAct_WhenViewIsNotVisible() {
        given(view.isOffScreen()).willReturn(true)
        given(repository.getAllShoppingLists(false)).willReturn(Flowable.just(EMPTY_SHOPPING_LIST))

        presenter.loadShoppingLists()
        testScheduler.triggerActions()

        then(view).should(never()).stopLoadingIndicator()
        then(view).should(never()).clearLists()
        then(view).should(never()).showErrorMessageView(ArgumentMatchers.anyString())
        then(view).should(never()).showLists(ArgumentMatchers.anyList())
    }

    @Test
    fun loadShoppingLists_ShouldShowLists_WhenDataReturned() {
        given(repository.getAllShoppingLists(false)).willReturn(Flowable.just(CURRENT_SHOPPING_LIST))

        presenter.loadShoppingLists()
        testScheduler.triggerActions()

        then(view).should().clearLists()
        then(view).should().showLists(CURRENT_SHOPPING_LIST)
        then(view).should(atLeastOnce()).stopLoadingIndicator()
    }

    @Test
    fun loadShoppingLists_ShouldShowEmptyListView_WhenNoDataReturned() {
        given(repository.getAllShoppingLists(false)).willReturn(Flowable.just(EMPTY_SHOPPING_LIST))

        presenter.loadShoppingLists()
        testScheduler.triggerActions()

        then(view).should().clearLists()
        then(view).should().showEmptyListView(true)
        then(view).should(atLeastOnce()).stopLoadingIndicator()
    }

    @Test
    fun loadShoppingLists_ShouldShowErrorMessage_WhenExceptionThrown() {
        given(repository.getAllShoppingLists(false)).willReturn(Flowable.error(Exception("something bad happened")))

        presenter.loadShoppingLists()
        testScheduler.triggerActions()

        then(view).should().clearLists()
        then(view).should().showErrorMessageView("something bad happened")
        then(view).should(atLeastOnce()).stopLoadingIndicator()
    }

    @Test
    fun createShoppingList_ShouldAddElementAndReloadData() {
        val list = arrayListOf(ShoppingList(null, false, "New List"))
        given(repository.getAllShoppingLists(false)).willReturn(Flowable.just(list))
        given(repository.addShoppingList(any())).willAnswer { Completable.complete() }

        presenter.createShoppingList("New List")
        testScheduler.triggerActions()

        then(view).should().startLoadingIndicator()
        then(view).should().clearLists()
        then(view).should().showEmptyListView(false)
        then(view).should(atLeastOnce()).stopLoadingIndicator()
    }
}
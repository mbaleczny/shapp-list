package com.mbaleczny.shapp_list.data

import com.mbaleczny.shapp_list.data.dao.ItemDao
import com.mbaleczny.shapp_list.data.dao.ShoppingListAndItemsDao
import com.mbaleczny.shapp_list.data.dao.ShoppingListDao
import com.mbaleczny.shapp_list.data.model.Item
import com.mbaleczny.shapp_list.data.model.ShoppingList
import com.mbaleczny.shapp_list.data.model.ShoppingListAndItems
import com.mbaleczny.shapp_list.data.repo.ShoppingListRepository
import io.reactivex.Flowable
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
class ShoppingListRepositoryTest {

    @Mock
    lateinit var itemDao: ItemDao
    @Mock
    lateinit var shoppingListDao: ShoppingListDao
    @Mock
    lateinit var shoppingListAndItemsDao: ShoppingListAndItemsDao

    lateinit var repository: ShoppingListRepository

    private val testItem = Item(1L, 1L, "Item")

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = ShoppingListRepository(itemDao, shoppingListDao, shoppingListAndItemsDao)
    }

    @Test
    fun getAllCurrentShoppingLists_ShouldReturnNotArchived() {
        val items = arrayListOf(
            ShoppingList(0L, false, "List1"),
            ShoppingList(1L, false, "List2")
        )
        given(shoppingListDao.getAllShoppingLists(false)).willReturn(Flowable.just(items))

        repository.getAllShoppingLists(false).test()

        then(shoppingListDao).should().getAllShoppingLists(false)
    }

    @Test
    fun getAllArchivedShoppingLists_ShouldReturnArchived() {
        val items = arrayListOf(
            ShoppingList(0L, true, "List1"),
            ShoppingList(1L, true, "List2")
        )
        given(shoppingListDao.getAllShoppingLists(true)).willReturn(Flowable.just(items))

        repository.getAllShoppingLists(true).test()

        then(shoppingListDao).should().getAllShoppingLists(true)
    }

    @Test
    fun getShoppingListAndItems_ShouldReturnForSpecificId() {
        val shoppingList = ShoppingList(0L, false, "List")
        val items = arrayListOf(Item(0L, 0L, "Test"))
        given(shoppingListAndItemsDao.loadShoppingListAndItems(1L))
            .willReturn(Maybe.just(ShoppingListAndItems(shoppingList, items)))

        repository.getShoppingListWithItems(1L)

        then(shoppingListAndItemsDao).should().loadShoppingListAndItems(1L)
    }

    @Test
    fun addItem_ShouldInsertToDb() {
        repository.addItem(testItem)

        then(itemDao).should().insert(testItem)
    }

    @Test
    fun updateItem_ShouldUpdateInDb() {
        repository.updateItem(testItem)

        then(itemDao).should().update(testItem)
    }

    @Test
    fun removeItem_ShouldRemoveFromDb() {
        repository.removeItem(testItem)

        then(itemDao).should().delete(testItem)
    }
}
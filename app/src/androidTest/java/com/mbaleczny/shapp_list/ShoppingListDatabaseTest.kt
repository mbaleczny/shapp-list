package com.mbaleczny.shapp_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mbaleczny.shapp_list.data.dao.ItemDao
import com.mbaleczny.shapp_list.data.dao.ShoppingListAndItemsDao
import com.mbaleczny.shapp_list.data.dao.ShoppingListDao
import com.mbaleczny.shapp_list.data.db.ShoppingListDatabase
import com.mbaleczny.shapp_list.data.model.Item
import com.mbaleczny.shapp_list.data.model.ShoppingList
import com.mbaleczny.shapp_list.ui.MainActivity
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
@RunWith(AndroidJUnit4::class)
class ShoppingListDatabaseTest {

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @JvmField
    @Rule
    val mainActivity = ActivityTestRule<MainActivity>(MainActivity::class.java)

    private lateinit var db: ShoppingListDatabase

    private lateinit var itemDao: ItemDao
    private lateinit var shoppingListDao: ShoppingListDao
    private lateinit var shoppingListAndItemsDao: ShoppingListAndItemsDao

    @Before
    fun setUp() {
        db = Room
            .inMemoryDatabaseBuilder(mainActivity.activity.applicationContext, ShoppingListDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        itemDao = db.itemDao()
        shoppingListDao = db.shoppingListDao()
        shoppingListAndItemsDao = db.shoppingListAndItemsDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addItemAndVerify() {
        val item = Item(0L, null, "Test item")
        itemDao.insert(item)

        itemDao.getAllItems()
            .subscribeOn(Schedulers.trampoline())
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue(listOf(item))
    }

    @Test
    fun updateItemAndVerify() {
        itemDao.insert(Item(0L, null, "Test item"))
        val item = itemDao.getAllItems()
            .subscribeOn(Schedulers.trampoline())
            .test()
            .values()[0][0]

        itemDao.update(item.copy(name = "Test item CHANGED"))

        itemDao.findById(item.id)
            .subscribeOn(Schedulers.trampoline())
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue { it.name == "Test item CHANGED" }
    }

    @Test
    fun deleteItemAndVerify() {
        itemDao.insert(Item(0L, null, "Test item"))
        val item = itemDao.getAllItems()
            .subscribeOn(Schedulers.trampoline())
            .test()
            .values()[0][0]

        itemDao.delete(item)

        itemDao.findById(item.id)
            .subscribeOn(Schedulers.trampoline())
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertNoValues()
    }

    @Test
    fun addShoppingListWithItems() {
        shoppingListDao.insert(ShoppingList(null, false, "List1"))

        val listId = shoppingListDao.getAllShoppingLists(false)
            .subscribeOn(Schedulers.trampoline())
            .test()
            .values()[0][0].id

        itemDao.insert(Item(null, listId, "Item 1"))
        itemDao.insert(Item(null, listId, "Item 2"))

        shoppingListAndItemsDao.loadShoppingListAndItems(listId)
            .test()
            .assertNoErrors()
            .assertValue { it.items.size == 2 }
    }

    @Test
    fun updateShoppingList() {
        shoppingListDao.insert(ShoppingList(null, false, "List1"))

        val list = shoppingListDao.getAllShoppingLists(false)
            .subscribeOn(Schedulers.trampoline())
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValueCount(1)
            .values()[0][0]

        shoppingListDao.update(list.copy(title = "List1 CHANGED"))

        shoppingListDao.findById(list.id)
            .test()
            .assertNoErrors()
            .assertValue { it.title == "List1 CHANGED" }
    }

    @Test
    fun deleteShoppingListWithItems() {
        shoppingListDao.insert(ShoppingList(null, false, "List1"))

        val list = shoppingListDao.getAllShoppingLists(false)
            .subscribeOn(Schedulers.trampoline())
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValueCount(1)
            .values()[0][0]

        itemDao.insert(Item(null, list.id, "Item 1"))
        itemDao.insert(Item(null, list.id, "Item 2"))

        shoppingListAndItemsDao.loadShoppingListAndItems(list.id)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { it.items.size == 2 }

        shoppingListDao.delete(list)

        shoppingListDao.findById(list.id)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertNoValues()

        itemDao.getAllItems()
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { it.isEmpty() }
    }

    @Test
    fun deleteArchivedShoppingList() {
        shoppingListDao.insert(ShoppingList(null, true, "List0"))
        shoppingListDao.insert(ShoppingList(null, false, "List1"))
        shoppingListDao.insert(ShoppingList(null, false, "List2"))

        shoppingListDao.getAllShoppingLists(true)
            .subscribeOn(Schedulers.trampoline())
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { it.size == 1 && it.all { list -> list.archived } }

        shoppingListDao.deleteAll(true)

        shoppingListDao.getAllShoppingLists(true)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { it.isEmpty() }
    }
}
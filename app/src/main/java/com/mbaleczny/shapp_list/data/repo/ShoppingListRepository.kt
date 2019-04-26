package com.mbaleczny.shapp_list.data.repo

import com.mbaleczny.shapp_list.data.dao.ItemDao
import com.mbaleczny.shapp_list.data.dao.ShoppingListAndItemsDao
import com.mbaleczny.shapp_list.data.dao.ShoppingListDao
import com.mbaleczny.shapp_list.data.model.Item
import com.mbaleczny.shapp_list.data.model.ShoppingList
import com.mbaleczny.shapp_list.data.model.ShoppingListAndItems
import io.reactivex.Flowable
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
class ShoppingListRepository @Inject constructor(
    private val itemDao: ItemDao,
    private val shoppingListDao: ShoppingListDao,
    private val shoppingListAndItemsDao: ShoppingListAndItemsDao
) : ItemDataSource, ShoppingListDataSource {

    override fun addItem(item: Item) {
        itemDao.insert(item)
    }

    override fun removeItem(item: Item) {
        itemDao.delete(item)
    }

    override fun updateItem(item: Item) {
        itemDao.update(item)
    }

    override fun getAllShoppingLists(archived: Boolean): Flowable<List<ShoppingList>> {
        return shoppingListDao.getAllShoppingLists(archived)
    }

    override fun getShoppingListWithItems(listId: Long): Maybe<ShoppingListAndItems> {
        return shoppingListAndItemsDao.loadShoppingListAndItems(listId)
    }

    override fun addShoppingList(itemList: ShoppingList) {
        shoppingListDao.insert(itemList)
    }
}
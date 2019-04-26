package com.mbaleczny.shapp_list.data.repo

import com.mbaleczny.shapp_list.data.model.ShoppingList
import com.mbaleczny.shapp_list.data.model.ShoppingListAndItems
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
interface ShoppingListDataSource {

    fun getAllShoppingLists(archived: Boolean): Flowable<List<ShoppingList>>

    fun getShoppingListWithItems(listId: Long): Maybe<ShoppingListAndItems>

    fun addShoppingList(itemList: ShoppingList)
}
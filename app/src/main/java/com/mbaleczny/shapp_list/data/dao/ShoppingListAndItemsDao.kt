package com.mbaleczny.shapp_list.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.mbaleczny.shapp_list.data.model.ShoppingListAndItems
import io.reactivex.Maybe

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
@Dao
interface ShoppingListAndItemsDao {

    @Transaction
    @Query("SELECT * FROM ShoppingList WHERE id = :listId")
    fun loadShoppingListAndItems(listId: Long?): Maybe<ShoppingListAndItems>
}
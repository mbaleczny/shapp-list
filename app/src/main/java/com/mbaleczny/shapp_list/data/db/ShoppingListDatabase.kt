package com.mbaleczny.shapp_list.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mbaleczny.shapp_list.data.dao.ItemDao
import com.mbaleczny.shapp_list.data.dao.ShoppingListAndItemsDao
import com.mbaleczny.shapp_list.data.dao.ShoppingListDao
import com.mbaleczny.shapp_list.data.model.Item
import com.mbaleczny.shapp_list.data.model.ShoppingList

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
@Database(entities = [Item::class, ShoppingList::class], version = 1, exportSchema = false)
abstract class ShoppingListDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    abstract fun shoppingListDao(): ShoppingListDao

    abstract fun shoppingListAndItemsDao(): ShoppingListAndItemsDao
}
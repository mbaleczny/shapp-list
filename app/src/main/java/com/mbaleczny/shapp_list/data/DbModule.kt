package com.mbaleczny.shapp_list.data

import android.content.Context
import androidx.room.Room
import com.mbaleczny.shapp_list.data.dao.ItemDao
import com.mbaleczny.shapp_list.data.dao.ShoppingListAndItemsDao
import com.mbaleczny.shapp_list.data.dao.ShoppingListDao
import com.mbaleczny.shapp_list.data.db.ShoppingListDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
@Module
class DbModule {

    companion object {
        const val DATABASE = "db_name"
    }

    @Provides
    @Named(DATABASE)
    fun provideDbName() = "shopping_list.db"

    @Provides
    @Singleton
    fun provideShoppingListDb(context: Context, @Named(DATABASE) dbName: String): ShoppingListDatabase =
        Room
            .databaseBuilder(context, ShoppingListDatabase::class.java, dbName)
            .build()

    @Provides
    @Singleton
    fun provideItemDao(db: ShoppingListDatabase): ItemDao = db.itemDao()

    @Provides
    @Singleton
    fun provideShoppingListDao(db: ShoppingListDatabase): ShoppingListDao = db.shoppingListDao()

    @Provides
    @Singleton
    fun provideShoppingListAndItemsDao(db: ShoppingListDatabase): ShoppingListAndItemsDao = db.shoppingListAndItemsDao()
}
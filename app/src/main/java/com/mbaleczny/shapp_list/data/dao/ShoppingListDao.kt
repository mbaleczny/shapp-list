package com.mbaleczny.shapp_list.data.dao

import androidx.annotation.VisibleForTesting
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mbaleczny.shapp_list.data.model.ShoppingList
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
@Dao
interface ShoppingListDao {

    @Query("SELECT * from ShoppingList WHERE archived = :archived ORDER BY title ASC")
    fun getAllShoppingLists(archived: Boolean): Flowable<List<ShoppingList>>

    @Query("SELECT * FROM ShoppingList WHERE id = :id")
    fun findById(id: Long?): Maybe<ShoppingList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: ShoppingList)

    @Update
    fun update(copy: ShoppingList)

    @Delete
    fun delete(list: ShoppingList)

    @Query("DELETE FROM ShoppingList WHERE archived = :archived")
    fun deleteAll(archived: Boolean)
}
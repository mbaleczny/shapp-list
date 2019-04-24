package com.mbaleczny.shapp_list.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mbaleczny.shapp_list.data.model.Item
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
@Dao
interface ItemDao {

    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flowable<List<Item>>

    @Query("SELECT * FROM items WHERE id = :id")
    fun findById(id: Long?): Maybe<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Item)

    @Delete
    fun delete(item: Item)

    @Update
    fun update(item: Item)

    @Query("DELETE FROM items")
    fun deleteAll()
}
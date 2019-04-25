package com.mbaleczny.shapp_list.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
@Entity(
    tableName = "items",
    foreignKeys = [ForeignKey(
        entity = ShoppingList::class,
        parentColumns = ["id"],
        childColumns = ["list_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class Item(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "list_id", index = true) var listId: Long?,
    var name: String
)
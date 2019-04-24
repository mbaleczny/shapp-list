package com.mbaleczny.shapp_list.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
@Entity
data class ShoppingList(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    val archived: Boolean,
    var title: String
)
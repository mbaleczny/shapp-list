package com.mbaleczny.shapp_list.data.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
data class ShoppingListAndItems(
    @Embedded
    var shoppingList: ShoppingList,
    @Relation(parentColumn = "id", entityColumn = "list_id", entity = Item::class)
    var items: List<Item>
)
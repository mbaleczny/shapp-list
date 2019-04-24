package com.mbaleczny.shapp_list.data.repo

import com.mbaleczny.shapp_list.data.model.Item

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
interface ItemDataSource {

    fun addItem(item: Item)

    fun removeItem(item: Item)

    fun updateItem(item: Item)
}
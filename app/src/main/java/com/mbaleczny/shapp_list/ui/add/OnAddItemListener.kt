package com.mbaleczny.shapp_list.ui.add

/**
 * @author Mariusz Baleczny
 * @date 26/04/19
 */
@FunctionalInterface
interface OnAddItemListener {

    fun addItem(value: String)
}
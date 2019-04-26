package com.mbaleczny.shapp_list.ui.base

import android.view.View

/**
 * @author Mariusz Baleczny
 * @date 26/04/19
 */
interface RecyclerViewListener {

    @FunctionalInterface
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}
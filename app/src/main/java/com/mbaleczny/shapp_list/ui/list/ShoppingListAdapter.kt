package com.mbaleczny.shapp_list.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbaleczny.shapp_list.R
import com.mbaleczny.shapp_list.data.model.ShoppingList
import com.mbaleczny.shapp_list.ui.base.BaseRecyclerViewAdapter

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
class ShoppingListAdapter(l: ArrayList<ShoppingList>) :
    BaseRecyclerViewAdapter<ShoppingListAdapter.ViewHolder, ShoppingList>(l) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(list[position])
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(list: ShoppingList) {
            view.findViewById<TextView>(R.id.title).text = list.title
        }
    }
}
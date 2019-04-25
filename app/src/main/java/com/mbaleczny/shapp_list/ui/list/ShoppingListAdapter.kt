package com.mbaleczny.shapp_list.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbaleczny.shapp_list.R
import com.mbaleczny.shapp_list.data.model.ShoppingList

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
class ShoppingListAdapter(private val list: ArrayList<ShoppingList>) :
    RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun replaceData(newList: List<ShoppingList>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(list: ShoppingList) {
            view.findViewById<TextView>(R.id.title).text = list.title
        }
    }
}
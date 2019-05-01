package com.mbaleczny.shapp_list.ui.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbaleczny.shapp_list.R
import com.mbaleczny.shapp_list.data.model.Item
import com.mbaleczny.shapp_list.ui.base.BaseRecyclerViewAdapter
import com.mbaleczny.shapp_list.ui.base.RecyclerViewListener

/**
 * @author Mariusz Baleczny
 * @date 26/04/19
 */
class ItemListAdapter(list: ArrayList<Item>, var archived: Boolean) :
    BaseRecyclerViewAdapter<ItemListAdapter.ViewHolder, Item>(list) {

    var deleteItemClickListener: RecyclerViewListener.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_holder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var name: TextView = itemView.findViewById(R.id.name)
        private var delete: ImageView = itemView.findViewById(R.id.delete)

        fun bind(list: Item, position: Int) {
            delete.apply { visibility = if (archived) View.GONE else View.VISIBLE }
            name.text = list.name

            if (!archived) {
                deleteItemClickListener?.let {
                    delete.setOnClickListener { v -> deleteItemClickListener?.onItemClick(v, position) }
                }
            }
        }
    }
}
package com.mbaleczny.shapp_list.ui.base

import androidx.recyclerview.widget.RecyclerView
import java.security.InvalidParameterException

/**
 * @author Mariusz Baleczny
 * @date 26/04/19
 */
abstract class BaseRecyclerViewAdapter<VH : RecyclerView.ViewHolder, I>(protected val list: ArrayList<I>) :
    RecyclerView.Adapter<VH>() {

    var itemClickListener: RecyclerViewListener.OnItemClickListener? = null

    override fun onBindViewHolder(holder: VH, position: Int) {
        itemClickListener?.let {
            holder.itemView.setOnClickListener { v -> itemClickListener?.onItemClick(v, position) }
        }
    }

    override fun getItemCount(): Int = list.size

    fun replaceData(newList: List<I>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): I {
        if (position < 0 || position >= list.size) {
            throw InvalidParameterException("Invalid item index")
        }
        return list[position]
    }
}
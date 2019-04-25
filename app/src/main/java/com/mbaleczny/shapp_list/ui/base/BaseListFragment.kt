package com.mbaleczny.shapp_list.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleRegistry
import com.mbaleczny.shapp_list.R

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
open class BaseListFragment : Fragment() {

    val lifecycle = LifecycleRegistry(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }
}
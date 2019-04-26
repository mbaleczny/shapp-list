package com.mbaleczny.shapp_list.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mbaleczny.shapp_list.R
import dagger.android.support.DaggerFragment

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
open class BaseListFragment : DaggerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }
}
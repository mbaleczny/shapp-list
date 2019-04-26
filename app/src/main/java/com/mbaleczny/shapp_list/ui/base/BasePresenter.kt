package com.mbaleczny.shapp_list.ui.base

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
interface BasePresenter<V> {

    fun attachView(v: V)

    fun onAttach()

    fun onDetach()
}
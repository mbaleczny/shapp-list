package com.mbaleczny.shapp_list

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * @author Mariusz Baleczny
 * @date 24/04/19
 */
class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}
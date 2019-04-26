package com.mbaleczny.shapp_list

import com.mbaleczny.shapp_list.data.DbModule
import com.mbaleczny.shapp_list.ui.list.ShoppingListModule
import com.mbaleczny.shapp_list.util.AndroidBindingModule
import com.mbaleczny.shapp_list.util.SchedulerModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        SchedulerModule::class,
        DbModule::class,
        ShoppingListModule::class,
        AndroidBindingModule::class,
        AndroidSupportInjectionModule::class
    ]
)
internal interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}
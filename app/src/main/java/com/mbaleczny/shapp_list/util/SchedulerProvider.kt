package com.mbaleczny.shapp_list.util

import io.reactivex.Scheduler

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
interface SchedulerProvider {

    fun io(): Scheduler

    fun ui(): Scheduler
}
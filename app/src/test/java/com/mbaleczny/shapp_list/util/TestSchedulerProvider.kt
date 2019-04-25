package com.mbaleczny.shapp_list.util

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

/**
 * @author Mariusz Baleczny
 * @date 25/04/19
 */
class TestSchedulerProvider(private val scheduler: TestScheduler) : SchedulerProvider {

    override fun io(): Scheduler = scheduler

    override fun ui(): Scheduler = scheduler
}
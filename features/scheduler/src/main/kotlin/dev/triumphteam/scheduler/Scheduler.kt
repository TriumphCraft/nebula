/**
 * MIT License
 *
 * Copyright (c) 2021-2022 TriumphTeam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.scheduler

import dev.triumphteam.core.TriumphApplication
import dev.triumphteam.core.dsl.TriumphDsl
import dev.triumphteam.core.feature.ApplicationFeature
import dev.triumphteam.core.feature.attribute.AttributeKey
import dev.triumphteam.core.feature.attribute.key
import dev.triumphteam.core.feature.featureOrNull
import dev.triumphteam.core.feature.install
import dev.triumphteam.scheduler.schedule.DateSchedule
import dev.triumphteam.scheduler.schedule.DayTimeSchedule
import dev.triumphteam.scheduler.schedule.Schedule
import dev.triumphteam.scheduler.schedule.TimerSchedule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.EnumSet
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Simple coroutine based scheduler.
 */
public class Scheduler : CoroutineScope {

    private val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + dispatcher

    private val schedules = mutableListOf<Schedule>()
    private var clock = Clock.systemDefaultZone()
    private var started = false

    /**
     * Runs a task at a given date time.
     */
    public fun runTaskAt(date: LocalDateTime, action: suspend () -> Unit) {
        schedules.add(DateSchedule(date, action))
    }

    /**
     * Runs task after a given duration.
     */
    public fun runTaskIn(time: Duration, task: suspend () -> Unit) {
        schedules.add(TimerSchedule(0, time.inWholeSeconds, false, task))
    }

    /**
     * Runs task every `x` duration after `y` duration.
     */
    public fun runTaskEvery(period: Duration, delay: Duration, task: suspend () -> Unit) {
        schedules.add(TimerSchedule(period.inWholeSeconds, delay.inWholeSeconds, true, task))
    }

    /**
     * Runs task every given days at a given time.
     */
    public fun runTaskEvery(days: Set<DayOfWeek>, at: LocalTime, task: suspend () -> Unit) {
        schedules.add(DayTimeSchedule(days, at, true, task))
    }

    /**
     * Start the timer logic.
     */
    private fun start(): Scheduler {
        launch {
            var lastCheck: LocalDateTime? = null

            while (true) {
                val second = LocalDateTime.now(clock).truncatedTo(ChronoUnit.SECONDS)

                if (lastCheck != second) {
                    lastCheck = second

                    coroutineScope {
                        launchSchedules(second)
                    }

                }

                delay(50)
            }
        }

        started = true
        return this
    }

    /**
     * Launches each schedule.
     */
    private fun launchSchedules(nowMinute: LocalDateTime) {
        schedules.forEach {
            launch {
                if (!it.shouldRun(nowMinute)) return@launch

                it.execute()

                if (!it.repeating) schedules.remove(it)
            }
        }
    }

    /**
     * Feature companion, which is a factory for the [Scheduler].
     */
    public companion object Feature : ApplicationFeature<TriumphApplication, Scheduler, Scheduler> {

        /**
         * The locale [AttributeKey].
         */
        override val key: AttributeKey<Scheduler> = key("scheduler")

        /**
         * Installation function to create a [Scheduler] feature.
         */
        override fun install(application: TriumphApplication, configure: Scheduler.() -> Unit): Scheduler {
            return Scheduler().apply(configure).apply { start() }
        }
    }

}

/**
 * Runs task after a given duration.
 */
@TriumphDsl
public fun TriumphApplication.runTaskIn(time: Duration, task: suspend () -> Unit) {
    val scheduler = featureOrNull(Scheduler) ?: install(Scheduler)
    return scheduler.runTaskIn(time, task)
}

/**
 * Runs a task at a given date time.
 */
@TriumphDsl
public fun TriumphApplication.runTaskAt(date: LocalDateTime, task: suspend () -> Unit) {
    val scheduler = featureOrNull(Scheduler) ?: install(Scheduler)
    return scheduler.runTaskAt(date, task)
}

/**
 * Runs a task at a given time.
 */
@TriumphDsl
public fun TriumphApplication.runTaskAt(time: LocalTime, task: suspend () -> Unit) {
    val scheduler = featureOrNull(Scheduler) ?: install(Scheduler)
    return scheduler.runTaskAt(time.atDate(LocalDateTime.now().toLocalDate()), task)
}

/**
 * Runs task every `x` duration after `y` duration.
 */
@TriumphDsl
public fun TriumphApplication.runTaskEvery(period: Duration, delay: Duration = 0.seconds, task: suspend () -> Unit) {
    val scheduler = featureOrNull(Scheduler) ?: install(Scheduler)
    return scheduler.runTaskEvery(period, delay, task)
}

/**
 * Runs task every given days at a given time.
 */
@TriumphDsl
public fun TriumphApplication.runTaskEvery(days: Set<DayOfWeek>, at: LocalTime, task: suspend () -> Unit) {
    val scheduler = featureOrNull(Scheduler) ?: install(Scheduler)
    return scheduler.runTaskEvery(days, at, task)
}

/**
 * Runs task every given days at a given time.
 */
@TriumphDsl
public fun TriumphApplication.runTaskEvery(vararg days: DayOfWeek, at: LocalTime, task: suspend () -> Unit) {
    val scheduler = featureOrNull(Scheduler) ?: install(Scheduler)
    return scheduler.runTaskEvery(days.toCollection(EnumSet.noneOf(DayOfWeek::class.java)), at, task)
}

/**
 * Runs task every given day at a given time.
 */
@TriumphDsl
public fun TriumphApplication.runTaskEvery(day: DayOfWeek, at: LocalTime, task: suspend () -> Unit) {
    val scheduler = featureOrNull(Scheduler) ?: install(Scheduler)
    return scheduler.runTaskEvery(EnumSet.of(day), at, task)
}

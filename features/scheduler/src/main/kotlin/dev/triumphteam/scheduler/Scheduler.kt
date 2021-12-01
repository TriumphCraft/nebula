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
    public fun runTaskIn(time: Duration, action: suspend () -> Unit) {
        schedules.add(TimerSchedule(0, time.inWholeSeconds, false, action))
    }

    /**
     * Runs task every `x` duration after `y` duration.
     */
    public fun runTaskEvery(period: Duration, delay: Duration, action: suspend () -> Unit) {
        schedules.add(TimerSchedule(period.inWholeSeconds, delay.inWholeSeconds, true, action))
    }

    /**
     * Runs task every given days at a given time.
     */
    public fun runTaskEvery(days: Set<DayOfWeek>, at: LocalTime, action: suspend () -> Unit) {
        schedules.add(DayTimeSchedule(days, at, true, action))
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
public fun TriumphApplication.runTaskIn(time: Duration, block: suspend () -> Unit) {
    val scheduler = featureOrNull(Scheduler) ?: install(Scheduler)
    return scheduler.runTaskIn(time, block)
}

/**
 * Runs a task at a given date time.
 */
@TriumphDsl
public fun TriumphApplication.runTaskAt(date: LocalDateTime, block: suspend () -> Unit) {
    val scheduler = featureOrNull(Scheduler) ?: install(Scheduler)
    return scheduler.runTaskAt(date, block)
}

/**
 * Runs a task at a given time.
 */
@TriumphDsl
public fun TriumphApplication.runTaskAt(time: LocalTime, block: suspend () -> Unit) {
    val scheduler = featureOrNull(Scheduler) ?: install(Scheduler)
    return scheduler.runTaskAt(time.atDate(LocalDateTime.now().toLocalDate()), block)
}

/**
 * Runs task every `x` duration after `y` duration.
 */
@TriumphDsl
public fun TriumphApplication.runTaskEvery(period: Duration, delay: Duration = 0.seconds, block: suspend () -> Unit) {
    val scheduler = featureOrNull(Scheduler) ?: install(Scheduler)
    return scheduler.runTaskEvery(period, delay, block)
}

/**
 * Runs task every given days at a given time.
 */
@TriumphDsl
public fun TriumphApplication.runTaskEvery(days: Set<DayOfWeek>, at: LocalTime, block: suspend () -> Unit) {
    val scheduler = featureOrNull(Scheduler) ?: install(Scheduler)
    return scheduler.runTaskEvery(days, at, block)
}

/**
 * Runs task every given days at a given time.
 */
@TriumphDsl
public fun TriumphApplication.runTaskEvery(vararg days: DayOfWeek, at: LocalTime, block: suspend () -> Unit) {
    val scheduler = featureOrNull(Scheduler) ?: install(Scheduler)
    return scheduler.runTaskEvery(days.toCollection(EnumSet.noneOf(DayOfWeek::class.java)), at, block)
}

/**
 * Runs task every given day at a given time.
 */
@TriumphDsl
public fun TriumphApplication.runTaskEvery(day: DayOfWeek, at: LocalTime, block: suspend () -> Unit) {
    val scheduler = featureOrNull(Scheduler) ?: install(Scheduler)
    return scheduler.runTaskEvery(EnumSet.of(day), at, block)
}

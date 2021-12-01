package dev.triumphteam.scheduler.schedule

import java.time.LocalDateTime

/**
 * A schedule that is based on a date.
 */
public class DateSchedule(
    private val dateTime: LocalDateTime,
    private val block: suspend () -> Unit,
) : Schedule {

    // Will check if the date time is valid or not.
    init {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw IllegalArgumentException("DateTime must be after now")
        }
    }

    public override val repeating: Boolean = false

    public override suspend fun execute() {
        block()
    }

    public override fun shouldRun(nowDateTime: LocalDateTime): Boolean {
        return nowDateTime.isEqual(dateTime)
    }

}
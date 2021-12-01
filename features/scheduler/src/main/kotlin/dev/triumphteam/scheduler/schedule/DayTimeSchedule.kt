package dev.triumphteam.scheduler.schedule

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

public class DayTimeSchedule(
    private val days: Set<DayOfWeek>,
    private val time: LocalTime,
    public override val repeating: Boolean = true,
    private val block: suspend () -> Unit,
) : Schedule {

    public override suspend fun execute() {
        block()
    }

    public override fun shouldRun(nowDateTime: LocalDateTime): Boolean {
        if (nowDateTime.dayOfWeek !in days) return false
        return nowDateTime.toLocalTime() == time
    }

}
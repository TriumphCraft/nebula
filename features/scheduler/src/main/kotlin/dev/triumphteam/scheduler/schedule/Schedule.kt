package dev.triumphteam.scheduler.schedule

import java.time.LocalDateTime

/**
 * Defines a schedule for a task.
 */
public interface Schedule {

    /**
     * Whether the schedule is to be repeated.
     */
    public val repeating: Boolean

    /**
     * Executes the schedule's task.
     */
    public suspend fun execute()

    /**
     * Checks whether the schedule should run the task.
     */
    public fun shouldRun(nowDateTime: LocalDateTime): Boolean

}
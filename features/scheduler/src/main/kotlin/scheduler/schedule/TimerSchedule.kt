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
package dev.triumphteam.nebula.scheduler.schedule

import java.time.LocalDateTime

/**
 * A schedule that is based on a second timer.
 */
public class TimerSchedule(
    private val period: Long,
    private val delay: Long,
    public override val repeating: Boolean = true,
    private val block: suspend () -> Unit,
) : Schedule {

    private var delayOver = false
    private var counter = 0L

    public override suspend fun execute() {
        block()
    }

    public override fun shouldRun(nowDateTime: LocalDateTime): Boolean {
        counter++

        if (!delayOver && (delay == 0L || counter % delay == 0L)) {
            delayOver = true
            return true
        }

        if (!delayOver) return false
        if (period == 0L) return false
        if (counter % period == 0L) return true
        return false
    }

}
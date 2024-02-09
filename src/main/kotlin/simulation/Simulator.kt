package simulation

import java.util.PriorityQueue

abstract class Simulator : Clock, Scheduler {
    var time: Double = 0.0
    val events = PriorityQueue<Pair<Event, Double>>(compareBy { it.second })
    override fun currentTime(): Double {
        return time
    }

    override fun schedule(event: Event, dt: Double) {
        events.add(Pair(event, dt + currentTime()))
    }

    fun execute() {
        events.forEach { pair ->
            time += pair.second
            if (events.isEmpty() || shouldTerminate()) {
                return
            } else {
                pair.first.invoke()
            }
        }
    }
    abstract fun shouldTerminate(): Boolean
}

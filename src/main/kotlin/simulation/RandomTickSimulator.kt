package simulation

import java.io.PrintStream
import java.util.Random

class RandomTickSimulator(
    private val stoppingTime: Double,
    private val interval: Pair<Double, Double>,
    private val random: Random = Random(),
) : Simulator() {
    init {
        require(interval.first >= 0 && interval.second > interval.first) {
            "Interval must have lower bound < upper bound and be non-negative."
        }
    }

    override fun schedule(event: Event, dt: Double) {
        val delay = if (dt == 0.0) {
            interval.first + random.nextDouble() *
                (interval.second - interval.first)
        } else {
            dt
        }
        events.add(event to (currentTime() + delay))
    }

    override fun shouldTerminate(): Boolean = currentTime() >= stoppingTime

    inner class RandomTickEvent(
        private val printStream: PrintStream =
            System.out,
    ) : Event {
        override fun invoke() {
            printStream.println("Tick at $time")
            if (!shouldTerminate()) {
                schedule(
                    this,
                    0.0,
                ) // Schedule next event with a delay sampled from the interval
            }
        }
    }
}

fun main() {
    val random =
        Random() // Optional: Provide a Random instance for consistent randomness across the simulation
    val interval = 1.0 to 2.0 // Interval from which to sample random delays
    val simulator = RandomTickSimulator(10.0, interval, random)
    simulator.schedule(simulator.RandomTickEvent(), 0.5) // Initial tick at time 0.5
    simulator.execute()
}

package simulation

import java.util.*

class MM1Queue(
    private val lambda: Double,
    private val mu: Double,
    seed: Int,
    private val terminationTime: Double,
) :
    Simulator() {
    private var queueLength = 0
    private var accumQueueLength = 0.0
    private var lastEventTime = 0.0
    private val random = Random(seed.toLong())

    init {
        schedule(Arrival(), 0.0) // Start the simulation by scheduling the first Arrival event immediately
    }

    override fun shouldTerminate(): Boolean = currentTime() >= terminationTime

    inner class Arrival : Event {
        override fun invoke() {
            val interArrivalTime = -Math.log(1 - random.nextDouble()) / lambda
            queueLength++
            schedule(this, interArrivalTime) // Schedule the next arrival
            if (queueLength == 1) { // If this is the only job, start service immediately
                schedule(Completion(), -Math.log(1 - random.nextDouble()) / mu)
            }
            updateQueueLengthStats()
        }
    }

    inner class Completion : Event {
        override fun invoke() {
            queueLength--
            if (queueLength > 0) { // If there are more jobs, schedule the next completion
                schedule(this, -Math.log(1 - random.nextDouble()) / mu)
            }
            updateQueueLengthStats()
        }
    }

    private fun updateQueueLengthStats() {
        val currentTime = currentTime()
        accumQueueLength += queueLength * (currentTime - lastEventTime)
        lastEventTime = currentTime
    }

    fun runSim(): Double {
        execute()
        return accumQueueLength / terminationTime // Calculate the mean queue length
    }
}

fun main() {
    println(MM1Queue(4.0, 5.0, 12345, 1000000.0).runSim())
}

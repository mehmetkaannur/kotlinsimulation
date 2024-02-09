package simulation

import java.io.PrintStream

class BetterTickSimulator(
    val ps: PrintStream,
    private val stoppingTime: Double,
) : Simulator() {
    override fun shouldTerminate(): Boolean = currentTime() >= stoppingTime

    inner class TickEvent() : Event {
        override fun invoke() {
            ps.println("Tick at ${currentTime()}")
            schedule(TickEvent(), 1.0)
        }
    }
}

fun main() {
    val betterTickSimulator = BetterTickSimulator(System.out, 10.0)
    betterTickSimulator.schedule(betterTickSimulator.TickEvent(), 0.5)
    betterTickSimulator.execute()
}

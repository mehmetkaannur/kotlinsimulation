package simulation

import java.io.PrintStream

class TickSimulator(private val stoppingTime: Double) : Simulator() {
    override fun shouldTerminate(): Boolean = currentTime() >= stoppingTime
}

class TickEvent(private val ps: PrintStream, private val sim: Simulator) :
    Event {
    override fun invoke() {
        ps.println("Tick at ${sim.currentTime()}")
        sim.schedule(TickEvent(System.out, sim), 1.0)
    }
}

fun main() {
    val tickSimulator: Simulator = TickSimulator(10.0)
    tickSimulator.schedule(TickEvent(System.out, tickSimulator), 0.5)
    tickSimulator.execute()
}

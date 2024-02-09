package simulation

import java.io.PrintStream

class ToyEvent(val printer: PrintStream) : Event {
    override fun invoke() {
        printer.println("A toy event occurred.")
    }
}

class ToySimulator : Simulator() {
    override fun shouldTerminate(): Boolean = events.isEmpty()
}

fun main() {
    val printStream: PrintStream = System.out

    val toySimulator: Simulator = ToySimulator()
    for (i in 1..10) {
        toySimulator.schedule(ToyEvent(printStream), i.toDouble())
    }
    toySimulator.execute()
}

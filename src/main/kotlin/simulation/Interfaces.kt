package simulation

interface Clock {
    fun currentTime(): Double
}

interface Event {
    fun invoke()
}

interface Scheduler {
    fun schedule(event: Event, dt: Double)
}

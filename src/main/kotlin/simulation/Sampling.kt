 package simulation

 import java.util.*
 import kotlin.math.ln

 interface TimeDelay {
     fun nextSample(): Double
     }

 class UniformTimeDelay(private val a: Double, private val b: Double, random:
Random? = null) : TimeDelay {
     private val randomSample: () -> Double = random?.let { { it.nextDouble() } }
        ?: { Math.random() }

     override fun nextSample(): Double {
         return a + (b - a) * randomSample()
         }
     }

 class ExponentialTimeDelay(private val rate: Double, random: Random? = null) :
    TimeDelay {
     private val randomSample: () -> Double = random?.let { { it.nextDouble() } }
        ?: { Math.random() }

     override fun nextSample(): Double {
         return -ln(randomSample()) / rate
         }
     }

 fun main() {
     val random = Random()
     val uniformDelay = UniformTimeDelay(1.0, 5.0, random)
     val exponentialDelay = ExponentialTimeDelay(1.0 / 3.0, random) // Mean of 3, so rate is 1/3

     println("Uniform sample: ${uniformDelay.nextSample()}")
     println("Exponential sample: ${exponentialDelay.nextSample()}")
     }
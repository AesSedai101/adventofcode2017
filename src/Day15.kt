/**
 * @author elsabe.ros
 */

val divisor = 2147483647;

fun main(args: Array<String>) {
    val genA = Generator(16807, 512)
    val genB = Generator(48271, 191)

    var count = 0;
    for (i in 0 until 40_000_000) {
        val one = genA.calculate()
        val two = genB.calculate()
        if (lowerMatch(one.toInt(), two.toInt())) {
            count++
        }
    }
    println(count.toString())
}

fun lowerMatch(x: Int, y: Int): Boolean {
    return x shl 16 == y shl 16
}


class Generator(private val factor: Long, private var start: Long) {
    fun calculate(): Long {
        start = (start * factor) % divisor
        return start
    }

}
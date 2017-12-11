/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    // 48 -> 5
    val input = listOf(1, 12, 23, 48, 1024, 289326)
    input.forEach { v ->
        //println(" === " + v.toString())
        println(v.toString() + " -> " + calculate(v))
    }
}

fun calculate(i: Int): String {
    var square = 1
    while (square * square < i) {
        square += 2
    }
    if (square == 1) {
        return "0"
    }
    // the distance from center to closest value in square
    val path = (square - 1) / 2

    var largest = square * square // always bottom right
    while (true) {
        val bottom = largest - square + 1 // diff is exclusive
        val center = bottom + square / 2
        val diff = Math.abs(i - center)
        if (diff <= square / 2) {
            return (path + diff).toString()
        }
        largest = bottom
    }

    return ""
}
import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val walls = mutableMapOf<Int, Int>()

    val inputStream: InputStream = File("input/day13.txt").inputStream()
    inputStream.bufferedReader().lines().forEach { l ->
        val split = l.split(":")
        walls.put(split[0].trim().toInt(), split[1].trim().toInt())
    }

    val lastWall = walls.maxBy { entry -> entry.key }!!.key

    var delay = 0
    while(true) {
        val c = hasCollision(delay, lastWall, walls)
        if (c) {
            delay++
        } else {
            break;
        }
    }
    println(delay.toString())
}

private fun hasCollision(delay: Int, lastWall: Int, walls: MutableMap<Int, Int>): Boolean {
    var tick = 0
    while (tick <= lastWall) {
        val range = walls[tick]
        if (range != null) { // is there a wall at this delay
            val scanPos = calcPos(tick + delay, range)
            if (scanPos == 0) {
                return true
            }
        }
        tick++
    }
    return false
}

fun calcPos(tick: Int, range: Int): Int {
    val spread = range + range - 2
    var upTickStart = 0
    while (upTickStart + spread <= tick) {
        upTickStart += spread
    }
    // the tick falls in the range upTickStart..(upTickStart + spread - 1)
    if (tick < upTickStart + range) {
        return tick % (spread)
    } else {
        return (upTickStart + spread) - tick
    }
}

fun print(walls: Map<Int, Int>, tick: Int) {
    val lastWall = walls.maxBy { entry -> entry.key }!!.key
    for (i in 0..lastWall) {
        print("[$i]")
        val range = walls[i]
        if (tick == i) {
            print("*")
        } else {
            print("|")
        }
        if (range != null) {
            val scanPos = calcPos(tick, range)
            for (j in 0..range) {
                print(if (scanPos == j) "S|" else "  |")
            }
        }
        println()
    }
    println()
}
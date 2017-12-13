import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day11.txt").inputStream()
    inputStream.bufferedReader().lines().forEach { l ->
        println(l)
        val list = l.split(",").map { s -> s.trim().toLowerCase() }

        var max = 0
        for (i in 1..list.size) {
            max = kotlin.math.max(max, calculate(list.subList(0, i)))
        }
        println(max)
    }
}

fun calculate(list: List<String>) : Int {
    val directions = "n,s,ne,nw,se,sw".split(",")
    val dirCount = mutableMapOf<String, Int>()

    directions.forEach { d -> dirCount.put(d, list.count { s -> s == d }) }

    // cancel out opposites
    cancelOutOpposites(dirCount, "s", "n")
    cancelOutOpposites(dirCount, "sw", "ne")
    cancelOutOpposites(dirCount, "se", "nw")
    // cancel out adjacents
    do {
        var changed = false;
        changed = changed || adjacents(dirCount, "sw", "s", "se")
        changed = changed || adjacents(dirCount, "s", "se", "ne")
        changed = changed || adjacents(dirCount, "se", "ne", "n")
        changed = changed || adjacents(dirCount, "ne", "n", "nw")
        changed = changed || adjacents(dirCount, "n", "nw", "sw")
        changed = changed || adjacents(dirCount, "nw", "sw", "s")
    } while (changed);

    var sum = 0
    dirCount.forEach { t, u -> sum += u }
    return sum
}

fun adjacents(count: MutableMap<String, Int>, left: String, middle: String, right: String): Boolean {
    val l = count.get(left)!!
    val r = count.get(right)!!
    val m = count.get(middle)!!
    if (l == 0 || r == 0) {
        return false
    }
    if (l > r) {
        count.put(left, l - r)
        count.put(right, 0)
        count.put(middle, m + r)
    } else {
        count.put(right, r - l)
        count.put(left, 0)
        count.put(middle, m + l)
    }
    return true
}

fun cancelOutOpposites(count: MutableMap<String, Int>, d1: String, d2: String) {
    val c1 = count.get(d1)!!
    val c2 = count.get(d2)!!
    if (c1 > c2) {
        count.put(d1, c1 - c2)
        count.put(d2, 0)
    } else {
        count.put(d2, c2 - c1)
        count.put(d1, 0)
    }
}


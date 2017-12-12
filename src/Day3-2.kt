/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val input = 289326;
    val values = mutableMapOf(Pair(Pair(0, 0), 1))
    var found = false
    var block = 1
    while (!found) {
        // -1,-1   0,-1  1, -1
        // -1, 0   0,0   1, 0
        // -1,1    0, 1  1, 1

        for (j in block - 1 downTo -block) {
            // 1, 0 -> 1, -1 right column
            val value = calculate(values, block, j)
            if (value > input) {
                println(value.toString())
                found = true
            }
            values.put(Pair(block, j), value)
        }
        for (j in  block - 1 downTo -block) {
            //0,-1 -> -1,-1 top row
            val value = calculate(values, j, -block)
            if (value > input) {
                println(value.toString())
                found = true
            }
            values.put(Pair(j, -block), value)
        }
        for (j in -block+1..block) {
            // -1, 0 -> -1,1 left column
            val value = calculate(values, -block, j)
            if (value > input) {
                println(value.toString())
                found = true
            }
            values.put(Pair(-block, j), value)
        }

        for (j in -block+1..block) {
            // 0, 1 -> 1, 1 bottom row
            val value = calculate(values, j, block)
            if (value > input) {
                println(value.toString())
                found = true
            }
            values.put(Pair(j, block), value)
        }
        block++
    }
}

fun calculate(map: Map<Pair<Int, Int>, Int>, x: Int, y: Int): Int {
    var sum = 0
    for (i in x - 1..x + 1) {
        for (j in y - 1..y + 1) {
            sum += map.getOrDefault(Pair(i, j), 0)
        }
    }
    return sum
}

fun printMap(map: Map<Pair<Int, Int>, Int>) {
    val firstMin = map.minBy { k ->
        k.key.first;
    }!!.key.first
    val firstMax = map.maxBy { k ->
        k.key.first;
    }!!.key.first
    val secondMin = map.minBy { k ->
        k.key.second
    }!!.key.second
    val secondMax = map.maxBy { k ->
        k.key.second
    }!!.key.second

    for (i in firstMin..firstMax) {
        for (j in secondMin..secondMax) {
            val value = map.get(Pair(i, j))
            print((value?.toString() ?: "?") + "\t")
        }
        println("")
    }
    println("")
}
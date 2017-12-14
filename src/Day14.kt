/**
 * @author elsabe.ros
 */
val hex = "0123456789abcdef".toCharArray().toList()

fun main(args: Array<String>) {
    val values = Array(128, { BooleanArray(128) })
    val input = "jzgqcdpd"
    var count = 0
    for (i in 0..127) {
        val hash = calculateHash(input + "-" + i.toString())
        values[i] = calculateFlags(hash)
        count += values[i].count { b -> b }
    }
    println(count.toString())

    // calculate the regions
    val processed = mutableSetOf<Pair<Int, Int>>()
    val currentRegion = mutableSetOf<Pair<Int, Int>>()

    var regionCount = 0
    var done = false
    while (!done) {
        // find a block in the current region that has not been processed
        val value = currentRegion.firstOrNull { p -> !processed.contains(p) }
        if (value != null) {
            // add the block to the processed list
            processed.add(value)
            val (i, j) = value
            // if the block is occupied, add its neighbours  to the region
            if (bound(i) && bound(j) && values[i][j]) {
                currentRegion.add(Pair(i, j + 1))
                currentRegion.add(Pair(i, j - 1))
                currentRegion.add(Pair(i + 1, j))
                currentRegion.add(Pair(i - 1, j))
            }
        } else {
            // no such block found
            currentRegion.clear()
            val start = findValue(processed, values)
            if (start != null) {
                regionCount++
                currentRegion.add(start)
            } else {
                done = true
            }
        }
    }
    println(regionCount)
}

fun bound(i: Int): Boolean {
    return i in 0..127
}

fun findValue(processed: MutableSet<Pair<Int, Int>>, values: Array<BooleanArray>): Pair<Int, Int>? {
    for (i in 0..127) {
        for (j in 0..127) {
            val p = Pair(i, j)
            if (values[i][j] && !processed.contains(p)) {
                return p
            }
        }
    }
    return null
}

fun calculateFlags(hash: String): BooleanArray {
    val result = BooleanArray(128)
    var index = 0;
    while (index < 32) {
        var value = hex.indexOf(hash[index])

        if (value >= 8) {
            result[index * 4] = true
            value -= 8
        }
        if (value >= 4) {
            result[index * 4 + 1] = true
            value -= 4
        }
        if (value >= 2) {
            result[index * 4 + 2] = true
            value -= 2
        }
        result[index * 4 + 3] = value == 1
        index++
    }

    return result;
}

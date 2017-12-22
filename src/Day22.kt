import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val DOWN = Pair(1, 0)
    val UP = Pair(-1, 0)
    val LEFT = Pair(0, -1)
    val RIGHT = Pair(0, 1)
    val directions = listOf(DOWN, LEFT, UP, RIGHT)

    val map = mutableMapOf<Pair<Int, Int>, Boolean>()

    val inputStream: InputStream = File("input/day22.txt").inputStream()
    var rowCount = 0
    inputStream.bufferedReader().lines().forEach { line ->
        for (i in line.toCharArray().indices) {
            map.put(Pair(rowCount, i), (line[i] == '#'))
        }
        rowCount++
    }

    var vPos = Pair(rowCount / 2, rowCount / 2)
    var vDirection = UP
    var infectCount = 0

    repeat(10000, {
        val infected = map.getOrDefault(vPos, false)
        if (infected) {
            // turn right
            vDirection = directions[(directions.indexOf(vDirection) + 1) % directions.size]
        } else {
            // turn left
            val pos = directions.indexOf(vDirection) - 1 + directions.size
            vDirection = directions[pos % directions.size]
            infectCount++
        }
        map.put(vPos, !infected)
        vPos += vDirection
    })

    println(infectCount++)
}


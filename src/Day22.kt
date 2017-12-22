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


    val map = mutableMapOf<Pair<Int, Int>, BlockState>()

    val inputStream: InputStream = File("input/day22.txt").inputStream()
    var rowCount = 0
    inputStream.bufferedReader().lines().forEach { line ->
        for (i in line.toCharArray().indices) {
            map.put(Pair(rowCount, i), if(line[i] == '#') BlockState.INFECTED else BlockState.CLEAN)
        }
        rowCount++
    }

    var vPos = Pair(rowCount / 2, rowCount / 2)
    var vDirection = UP
    var infectCount = 0

    repeat(10000000, {
        val infected = map.getOrDefault(vPos, BlockState.CLEAN)
        when(infected) {
            BlockState.CLEAN -> {
                // turn left
                val pos = directions.indexOf(vDirection) - 1 + directions.size
                vDirection = directions[pos % directions.size]
                map.put(vPos, BlockState.WEAK)
            }
            BlockState.WEAK -> {
                // does not turn
                infectCount++
                map.put(vPos, BlockState.INFECTED)
            }
            BlockState.INFECTED -> {
                // turn right
                vDirection = directions[(directions.indexOf(vDirection) + 1) % directions.size]
                map.put(vPos, BlockState.FLAGGED)
            }
            BlockState.FLAGGED -> {
                // reverse direction
                vDirection = directions[(directions.indexOf(vDirection) + 2) % directions.size]
                map.put(vPos, BlockState.CLEAN)
            }
        }
        vPos += vDirection
    })

    println(infectCount++)
}

enum class BlockState {
    CLEAN, WEAK, INFECTED, FLAGGED
}

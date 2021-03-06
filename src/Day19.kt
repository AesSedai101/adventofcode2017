import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
val DOWN = Pair(1, 0)
val UP = Pair(-1, 0)
val LEFT = Pair(0, -1)
val RIGHT = Pair(0, 1)
val directions = listOf(UP, DOWN, LEFT, RIGHT)

operator fun Pair<Int, Int>.plus(p: Pair<Int, Int>) = Pair(first + p.first, second + p.second);
fun Pair<Int, Int>.opposite() = Pair(-1 * first, -1 * second)

fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day19.txt").inputStream()
    val map = mutableMapOf<Pair<Int, Int>, Char>()

    var row = 0
    inputStream.bufferedReader().lines().forEach { line ->
        var col = 0
        line.forEach { c ->
            if (c != ' ') {
                map.put(Pair(row, col), c)
            }
            col++
        }
        row++
    }

    var count = 0
    var direction = DOWN
    var position: Pair<Int, Int> = map.filter { (k, v) ->
        k.first == 0 && v == '|'
    }.entries.first().key

    while (true) {
        val c = map[position]
        count++
        when (c) {
            null -> {
                println()
                println((count - 1).toString() + " steps")
                return
            }
            '+' -> {
                val dirs = directions.filter { d -> d != direction && d != direction.opposite() }
                dirs.forEach { d ->
                    if (map.contains(position + d)) direction = d
                }
                position += direction
            }
            '|', '-' -> {
                position += direction
            }
            else -> {
                print(c)
                position += direction
            }
        }
    }
}

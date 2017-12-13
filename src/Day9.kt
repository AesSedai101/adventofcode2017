import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day9.txt").inputStream()
    inputStream.bufferedReader().lines().forEach { l ->
        if (!l.startsWith("{")) throw UnsupportedOperationException("Line needs to starts with {")
        println(l)
        println(processLine(l).toString())
    }
}

fun processLine(l: String): Int {
    if (l.isEmpty()) return 0
    val start: Char = l[0]
    when (start) {
        '{' -> return processLine(l.substring(1))
        '}' -> return processLine(l.substring(1))
        '!' -> return processLine(l.substring(2))
        ',' -> return processLine(l.substring(1))
        '<' -> {
            val garbageEnd = findGarbageEnd(l.substring(1));
            return ((garbageEnd.first - garbageEnd.second) + processLine(l.substring(garbageEnd.first + 2)))
        }
        else -> throw UnsupportedOperationException("Unknown [" + start + "]")
    }
}

fun findGarbageEnd(l: String): Pair<Int, Int> {
    var index = 0
    var canceledCount = 0
    while (l[index] != '>') {
        if (l[index] == '!') {
            index += 2
            canceledCount += 2
        } else {
            index += 1
        }
    }
    return Pair(index, canceledCount)
}


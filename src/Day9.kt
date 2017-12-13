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
        println(processLine(l, 0).toString())
    }
}

fun processLine(l: String, score: Int): Int {
    if (l.isEmpty()) return 0
    val start: Char = l[0]
    when (start) {
        '{' -> return processLine(l.substring(1), score + 1)
        '}' -> return score + processLine(l.substring(1), score - 1)
        '!' -> return processLine(l.substring(2), score)
        ',' -> return processLine(l.substring(1), score)
        '<' -> {
            val garbageEnd = findGarbageEnd(l.substring(1));
            return processLine(l.substring(garbageEnd + 2), score)
        }
        else -> throw UnsupportedOperationException("Unknown [" + start + "]")
    }
}

fun findGarbageEnd(l: String): Int {
    var index = 0
    while (l[index] != '>') {
        if (l[index] == '!') {
            index += 2
        } else {
            index += 1
        }
    }
    return index
}


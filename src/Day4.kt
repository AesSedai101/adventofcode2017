import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day4.txt").inputStream()
    var count = 0
    inputStream.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            val list = line.split(" ", "\t").map { s -> s.trim().sort() }
            val all = list

            val set = all.toSet()
            if (all.size == set.size) {
                count++
            }
        }
    }
    println(count.toString())
}

fun String.sort(): String {
    val toCharArray = this.toCharArray()
    toCharArray.sort()
    return String(toCharArray)
}
import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day2.txt").inputStream()
    var check = 0
    inputStream.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            val values = line.split(" ", "\t").map { v ->
                v.toInt()
            }.sortedDescending()
            val size = values.size
            values.forEachIndexed { i, v ->
                for (j in i + 1 until size) {
                    if (v % values[j] == 0) {
                        check += v / values[j]
                    }
                }
            }
        }
    }
    print(check)
}
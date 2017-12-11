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
            }
            val max = values.max()
            val min = values.min()
            check += (max!! - min!!)
        }
    }
    print(check)
}
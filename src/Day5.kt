import java.io.File
import java.io.InputStream
import kotlin.streams.toList

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day5.txt").inputStream()
    var count = 0
    val list = inputStream.bufferedReader().lines().map { l -> l.trim().toInt() }.toList().toIntArray()
    var index = 0
    do {
        count++
        val value = list[index]
        list[index] = value + 1
        index += value
    } while (index >= 0 && index < list.size)

    println(count.toString())
}

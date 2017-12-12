import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day6.txt").inputStream()
    inputStream.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            var count = 0;
            var cycleCount = 0;
            var hashes = mutableSetOf<Int>()
            var cycleOfHash = mutableMapOf<Int, Int>()
            var values = line.split("\t").map { s -> s.trim().toInt() }.toIntArray()
            hashes.add(values.contentHashCode())
            cycleOfHash.put(values.contentHashCode(), 0)

            while (true) {
                val maxIndex = values.indexOf(values.max()!!)
                var redis = values[maxIndex]
                values[maxIndex] = 0
                var index = maxIndex + 1;
                while (redis > 0) {
                    values[index % values.size] += 1
                    redis--;
                    index++;
                }
                count++
                val code = values.contentHashCode()
                if (hashes.contains(code)) {
                    break;
                }
                hashes.add(code)
                cycleOfHash.put(code, count)
            }
            println(count.toString() + " " + (count - cycleOfHash.get(values.contentHashCode())!!))
        }
    }
}

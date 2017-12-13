import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val connections = mutableMapOf<String, List<String>>()

    val inputStream: InputStream = File("input/day12.txt").inputStream()
    inputStream.bufferedReader().lines().forEach { l ->
        val split = l.split("<->")
        connections.put(split[0].trim(), split[1].trim().split(",").map { s -> s.trim() })
    }

    val connectedList = mutableSetOf("0")
    while(true) {
        // find the first entry that is still in the map
        val entry = connectedList.firstOrNull { s -> connections.containsKey(s) } ?: break
        connectedList.addAll(connections.remove(entry).orEmpty())

    }
    println(connectedList.size.toString())
}

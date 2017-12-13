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

    val unprocessedList = connections.keys.toMutableList()
    val connectedList = mutableSetOf("0")
    var groupCount = 1 // always have at least one group

    while(!unprocessedList.isEmpty()) {
        // process the connectedList
        val entry = connectedList.firstOrNull { s -> unprocessedList.contains(s) }
        if (entry == null) {
            // we are done with this group
            groupCount++
            connectedList.clear()
            connectedList.add(unprocessedList.get(0))
        } else {
            unprocessedList.remove(entry)
            connectedList.addAll(connections[entry].orEmpty())
        }
    }
    println(groupCount.toString())
}

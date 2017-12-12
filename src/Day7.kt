import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day7.txt").inputStream()
    val programmes = mutableMapOf<String, List<String>>()

    inputStream.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            val name = line.substring(0, line.indexOf(" ")).trim()
            val startIndex = line.indexOf("->")
            var children: List<String> = mutableListOf()
            if (startIndex != -1) {
                children = line.substring(startIndex + 2).trim().split(",").map { s -> s.trim() }
            }
            programmes.put(name, children)
        }
    }

    println(Process(programmes).simplify())
}

class Process(val programmes: MutableMap<String, List<String>>) {
    private val processed = mutableSetOf<String>()

    fun simplify(): String {
        while (programmes.size > 1) {
            val filter = programmes.filter { it -> !it.value.isEmpty() && !processed.contains(it.key) }.entries.iterator().next()
            filter.value.forEach { f -> removeSubTree(f) }
            processed.add(filter.key)
        }

        return programmes.entries.iterator().next().key;
    }

    private fun removeSubTree(key: String) {
        val children = programmes[key]
        children?.forEach { c ->
            removeSubTree(c)
        }
        programmes.remove(key)
    }
}

import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day16.txt").inputStream()
    inputStream.bufferedReader().lines().forEach { line ->
        val instructions = line.split(",").map { s -> s.trim() }

        val initial = listOf('a'..'p').flatten().toCharArray()
        val rounds = 1000000000

        /*println("Manual")
        var manual = initial.copyOf()
        for (i in 1..rounds) {
            manual = compute(instructions, manual)
        }
        manual.forEach { d -> print(d) }
        println()*/

        println("Smarty")
        val valueMap = mutableMapOf<String, CharArray>()
        var start = initial.copyOf()
        for (i in 1..rounds) {
            val key = start.toList().toString()
            if (valueMap.containsKey(key)) {
                start = valueMap.get(key)!!
            } else {
                start = compute(instructions, start)
                valueMap.put(key, start)
            }
        }
        println()
        start.forEach { d -> print(d) }
        println()
    }
}

private fun compute(instructions: List<String>, init: CharArray): CharArray {
    var dancers = init.copyOf()
    instructions.forEach { ins ->
        when (ins[0]) {
            's' -> {
                val shift = ins.substring(1).toInt()
                val split = dancers.size - shift
                dancers = dancers.sliceArray(split until dancers.size) + dancers.sliceArray(0 until split)
            }
            'x' -> {
                val (first, second) = ins.substring(1).split("/").map { it.toInt() }
                dancers[first] = dancers[second].also { dancers[second] = dancers[first] }
            }
            'p' -> {
                val (first, second) = ins.substring(1).split("/").map { dancers.indexOf(it[0]) }
                dancers[first] = dancers[second].also { dancers[second] = dancers[first] }
            }
        }
    }
    return dancers
}

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
        val oneRound = compute(instructions, initial.copyOf())

        initial.forEach { d -> print(d) }
        println()
        oneRound.forEach { d -> print(d) }
        println()

        val changes = mutableMapOf<Int, Int>()
        for(i in 0..15) {
            changes.put(i, oneRound.indexOf(initial[i]))
        }

        var dancers = initial.copyOf()
        for(i in 0 until 1000000000) {
            val new = dancers.copyOf()
            changes.forEach{ k, v ->
                new[v] = dancers[k]
            }
            dancers = new
            dancers.forEach { d -> print(d) }
            println()
        }
        dancers.forEach { d -> print(d) }
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

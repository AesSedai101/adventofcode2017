import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day7.txt").inputStream()
    val input = mutableMapOf<String, Pair<String, List<String>>>()

    inputStream.bufferedReader().forEachLine { line ->
        val name = line.substring(0, line.indexOf(" ")).trim()
        val startIndex = line.indexOf("->")
        var children: List<String> = mutableListOf()
        if (startIndex != -1) {
            children = line.substring(startIndex + 2).trim().split(",").map { s -> s.trim() }

        }
        val weight = line.substring(line.indexOf("(") + 1, line.indexOf(")"))
        input.put(name, Pair(weight, children))
    }

    val programRoot = Processor(input).build()
    process(programRoot)
}

fun process(program: Program, diff: Int = 0) {
    println(program.name + " [" + program.weight.toString() + "] -> " + program.calc())
    // figure out if this is funky, or if it got the funky from a child
    val weights = program.children.map { c -> c.calc() }
    val weightSet = weights.toSet()
    if (weightSet.size > 1) {
        // it's one of the kids
        program.children.forEach { c ->
            if (weights.count { i -> i == c.calc() } == 1) {
                process(c, c.calc() - weightSet.first { i -> i != c.calc() })
            }
        }
    } else {
        // it's this one
        println("[" + diff.toString() + "] " + (program.weight + diff).toString() + " or " + (program.weight - diff).toString())
    }
}

fun printProgram(program: Program, prefix: String = "") {
    println(prefix + program.name + " - " + program.weight.toString())
    program.children.forEach { c ->
        printProgram(c, prefix + "   ")
    }
}

class Processor(val input: MutableMap<String, Pair<String, List<String>>>) {
    val temp = mutableMapOf<String, Program>()

    fun build(): Program {
        while (!input.isEmpty()) {
            process(input.entries.iterator().next().key)
        }
        return temp.iterator().next().value;
    }

    private fun process(key: String): Program {
        val value = input.remove(key)
        if (value != null) {
            val p = Program(value.first.toInt(), key)
            value.second.forEach { child ->
                p.children.add(process(child))
                temp.remove(child)
            }
            temp.put(key, p)
        }
        return temp.get(key)!!
    }
}

class Program(val weight: Int, val name: String) {
    val children = mutableListOf<Program>()
    fun calc(): Int {
        var total = weight
        children.forEach { c -> total += c.calc() }
        return total
    }
}